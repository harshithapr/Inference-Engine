import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Resolution
{
	KB FOLKB;
	Resolution(KBase FOLKBase)
	{
		this.FOLKB=new KB();
		for(Clause c: FOLKBase.getKB())
		{
			this.FOLKB.tellKB(c);
		}
	}
	
	public boolean isEntailsKB(String query)
	{
		Clause queryClause=addNegatedQuery(query);
		return inferenceResult(queryClause,2000);
	}
	private Clause parseQuery(String query)
	{
		ArrayList<Literal> cliterals=new ArrayList<Literal>();
	
			String pattern="([A-Z][A-Za-z0-9]*)[\\s]?[\\(][\\s]?([A-Za-z0-9]+([\\s]?[,]?[\\s]?[A-Za-z0-9]+)*)[\\s]?[\\)][\\s]?";
			boolean neg=false;
			String pname;
			String paramString;
			String[] param;
			ArrayList<Unit> predParam=new ArrayList<Unit>();
			Boolean paramType;
			Predicate p;
			Literal lit=null;
			

			if(query.startsWith("~"))
				neg=true;
			
			 Pattern r = Pattern.compile(pattern);
		     Matcher m = r.matcher(query);
		     
		     if(m.find())
		     {
		    	 pname=m.group(1);
		    	 paramString=m.group(2);
		    	 param=paramString.split(",");
		    	 for(String st:param)
		    	 {
		    		 paramType=st.trim().matches("[a-z]");
		    		 if(paramType)
		    		 {
		    			 predParam.add(new Variable(st.trim()));
		    		 }
		    		 else
		    		 {
		    			 Constant c=new Constant(st.trim());
		    			 predParam.add(c); 
		    		 }
		    	 }
		    	 Param params=new Param(predParam);
		    	 p=new Predicate(pname.trim(), params);
		    	 lit=new Literal(p,neg);
		     }
		cliterals.add(lit);
		return new Clause(cliterals);
	}
	public Clause addNegatedQuery(String query)
	{
		Clause queryclause=parseQuery(query);
		queryclause.getLiterals().get(0).negate();
		System.out.println(queryclause.toString());
		FOLKB.tellKB(queryclause);
		System.out.println("____________________________________________");
		for(Map.Entry<String, Pair> mp:FOLKB.getKB().entrySet())
		{
			System.out.println("Predicate: "+mp.getKey());
			//System.out.println("____________________________________________");
			System.out.println("Positive Literals");
			for(Clause c:mp.getValue().getPositive())
				System.out.println("Clause: "+c.toString());
			System.out.println("Negative Literals");
			for(Clause c:mp.getValue().getNegative())
				System.out.println("Clause: "+c.toString());
			
			System.out.println("____________________________________________");
		}
		return queryclause;
	}
	
	private boolean inferenceResult(Clause queryClause, int loopmax)
	{
		StandardizeApart std=new StandardizeApart("u");
		HashMap<String,Pair> clauses=FOLKB.getKB();
		Comparator<Clause> pqcomp=new PriorityQueueClauseComparator();
		PriorityQueue<Clause> resolvents=new PriorityQueue<Clause>(pqcomp);
		resolvents.add(queryClause);
		int i=0;
		while(!resolvents.isEmpty()&&i<loopmax)
		{
			i++;
			clauses=FOLKB.getKB();
			
			Clause c1=resolvents.poll();
//			LinkedList<Clause> matchingClauses=new LinkedList<Clause>();
			PriorityQueue<Clause> matchingClauses=new PriorityQueue<Clause>(pqcomp);
			for(Literal l:c1.getLiterals())
			{
				if(l.isNegated())
					matchingClauses.addAll(clauses.get(l.getPredicate().getName()).getPositive());
				else
					matchingClauses.addAll(clauses.get(l.getPredicate().getName()).getNegative());
			}
			
			if(matchingClauses.isEmpty())
				continue;
			
			LinkedList<Clause> newClauses=new LinkedList<Clause>();
			for(Clause c2: matchingClauses)
			{
				if(!c1.equals(c2))
				{
//					System.out.println("Resolving");
//					System.out.println(c1.toString());
//					System.out.println(c2.toString());
//					System.out.println("____________________________________________");
					newClauses=FOLResolve(c1,c2,std);

//					System.out.println("Resolved");
					for(Clause newc:newClauses)
					{	
						if(newc.isEmpty())
							return true;
//						System.out.println(newc.toString());
						if(!FOLKB.contains(newc))
						{
							FOLKB.tellKB(newc);
							resolvents.add(newc);
						}	
					}
//					System.out.println("=============================================");
				}
			}
		}//end while
		return false;
	}
	
	private LinkedList<Clause> FOLResolve(Clause c1,Clause c2,StandardizeApart std)
	{
		int i=0,j=0;
		Map<Variable,Unit> theta=null;
		Unifier un=new Unifier();
		LinkedList<Clause> resolvents=new LinkedList<Clause>();
		ArrayList<Literal> l1=c1.getLiterals();
		ArrayList<Literal> l2=c2.getLiterals();
		
		for(i=0;i<l1.size();i++)
		{
			for(j=0;j<l2.size();j++)
			{
				Literal lx=l1.get(i);
				Literal ly=l2.get(j);
				//System.out.println("Unifying: "+l1.get(i).toString()+" &"+l2.get(j).toString());
				theta=un.unify(lx,ly);
				if(theta!=null && !theta.isEmpty())
				{
					try
					{
					Clause newClause=resolveClause((Clause)c1.Clone(),(Clause)c2.Clone(),theta,i,j);
					//resolvents.add(std.standardize(newClause));
					resolvents.add(newClause);
					}
					catch(CloneNotSupportedException e)
					{
						System.err.println("Cloning error");
						e.printStackTrace();
					}
				}
				else
				{
					if((lx.isNegated()&&!ly.isNegated())||(!lx.isNegated()&&ly.isNegated()))
					{
						if(lx.getPredicate().equals(ly.getPredicate()))	
						{
							try
							{
							Clause newClause=resolveClause((Clause)c1.Clone(),(Clause)c2.Clone(),theta,i,j);
							//resolvents.add(std.standardize(newClause));
							resolvents.add(newClause);
							}
							catch(CloneNotSupportedException e)
							{
								System.err.println("Cloning error");
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		
		return resolvents;
	}
	public Clause resolveClause(Clause c1, Clause c2, Map<Variable,Unit> theta,int i, int j)
	{
			ArrayList<Literal> l1=new ArrayList<Literal>();
			ArrayList<Literal> l2=new ArrayList<Literal>();
			try
			{
			for(Literal l: c1.getLiterals())
					l1.add((Literal)l.Clone());
			for(Literal l: c2.getLiterals())
					l2.add((Literal)l.Clone());
			}
			catch(CloneNotSupportedException e)
			{
				System.err.println("Clone error");
				e.printStackTrace();
			}
			ArrayList<Literal> ul=new ArrayList<Literal>();
			ArrayList<Literal> nul=new ArrayList<Literal>();
			l1.remove(i);
			l2.remove(j);
			for(Literal l:l1)
			{
				if(!ul.contains(l))
					ul.add(l);
			}
			for(Literal l: l2)
			{
				if(!ul.contains(l))
					ul.add(l);
			}
			ArrayList<Unit> p=null;
			int n=ul.size();
			for(i=0;i<n;i++)
			{
				Literal l=ul.get(i);
				p=l.getPredicate().getParam().getParameters();
				for(Variable v: theta.keySet())
				{
					if(p.indexOf(v)!=-1)
					{
						p.set(p.indexOf(v), theta.get(v));
					}
				}
				if(!nul.contains(l))
					nul.add(l);
			}		
			return new Clause(nul);
	}

}
