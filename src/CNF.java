import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CNF
{
	LinkedList<ComplexSentence> Sentences;
	ArrayList<Clause> Clauses;
	CNF(LinkedList<ComplexSentence> Sentences)
	{
		this.Sentences=Sentences;
	}
	
	public ArrayList<Clause> ConvertToCNF()
	{
		Sentences=EliminateImplication();
		Sentences=MoveNegation();
		Sentences=DistributeOROverAnd();
		String cnfsentence=getCNFSentence(Sentences.getFirst(),"");
		return BuildClauses(cnfsentence);
	}
	public LinkedList<ComplexSentence> EliminateImplication()
	{
		for(ComplexSentence s:Sentences)
		{
			if(s.getOperator()==TokenTypes.IMP)
			{
				negate(s.getOperand1());
				s.setOperator(TokenTypes.OR);
			}
		}
		return Sentences;
	}
	
	public LinkedList<ComplexSentence> MoveNegation()
	{
		for(ComplexSentence s:Sentences)
		{
			if(!s.isAtomic() && s.isNegated())
			{
				negate(s.getOperand1());
				negate(s.getOperand2());
				if(s.getOperator()==TokenTypes.AND)
					s.setOperator(TokenTypes.OR);
				else if(s.getOperator()==TokenTypes.OR)
					s.setOperator(TokenTypes.AND);
				s.setNegated(false);
			}
		}
		return Sentences;
	}
	
	public LinkedList<ComplexSentence> DistributeOROverAnd()
	{
		int count=Sentences.size();
		LinkedList<ComplexSentence> newSentences=new LinkedList<ComplexSentence>();
		while(count!=0)
		{
			ComplexSentence s=Sentences.poll();
			count--;
			if(!s.isAtomic())
			{
				if(s.getOperator()==TokenTypes.OR)
				{
					ComplexSentence opd1=s.getOperand1();
					ComplexSentence opd2=s.getOperand2();
					
					if(!opd2.isAtomic() && opd2.getOperator()==TokenTypes.AND)
					{
							ComplexSentence c1=new ComplexSentence(opd1,opd2.getOperand1(),TokenTypes.OR,null,false,false);
							ComplexSentence c2=new ComplexSentence(opd1,opd2.getOperand2(),TokenTypes.OR,null,false,false);
							Sentences.addLast(c1);
							Sentences.addLast(c2);
							count=count+2;
							s.setOperand1(c1);
							s.setOperand2(c2);
							s.setOperator(TokenTypes.AND);
					}
					else if(!opd1.isAtomic() && opd1.getOperator()==TokenTypes.AND)
					{
							ComplexSentence c1=new ComplexSentence(opd1.getOperand1(),opd2,TokenTypes.OR,null,false,false);
							ComplexSentence c2=new ComplexSentence(opd1.getOperand2(),opd2,TokenTypes.OR,null,false,false);
							Sentences.addLast(c1);
							Sentences.addLast(c2);
							count=count+2;
							s.setOperand1(c1);
							s.setOperand2(c2);
							s.setOperator(TokenTypes.AND);
					}
				}
			}
			newSentences.add(s);
		}
		return newSentences;
	}
	
	public String getCNFSentence(ComplexSentence s, String str)
	{
		   if(s.isAtomic())
		   {
			   if(s.negated)
			   {
				   str=str+"~";
			   } 
			   str=str+s.getPredicate();
		   }
		   else
		   {
		   if(s.getOperand1()!=null)
			   str=getCNFSentence(s.getOperand1(),str);
		   str=str+s.getOperatorDisplay();
		   if(s.getOperand2()!=null)
			   str=getCNFSentence(s.getOperand2(),str);
		   }
		   return str;
	}
	
	public ArrayList<Clause> BuildClauses(String sent)
	{
		
		ArrayList<Clause> clauses=new ArrayList<Clause>();
		StringTokenizer ORTokens=new StringTokenizer(sent,"&");
		ArrayList<String> ORSent=new ArrayList<String>(); 
		while(ORTokens.hasMoreTokens())
		{
			ORSent.add(ORTokens.nextToken());
		}
		
//		System.out.println("OR Sentences");
		for(String OR:ORSent)
		{
			ArrayList<Literal> cliterals=new ArrayList<Literal>();
//			System.out.println("Predicates of "+OR);
			StringTokenizer PredicateTokens=new StringTokenizer(OR,"|");
			while(PredicateTokens.hasMoreTokens())
			{
				String pattern="([A-Z][A-Za-z0-9]*)[\\s]?[\\(][\\s]?([A-Za-z0-9]+([\\s]?[,]?[\\s]?[A-Za-z0-9]+)*)[\\s]?[\\)][\\s]?";
				boolean neg=false;
				String pname;
				String paramString;
				String[] param;
				ArrayList<Unit> predParam=new ArrayList<Unit>();
				Boolean paramType;
				Predicate p;
				Literal lit=null;
				
				
				String token=PredicateTokens.nextToken();
//			System.out.println(token);
				
				if(token.startsWith("~"))
					neg=true;
				
				 Pattern r = Pattern.compile(pattern);
			     Matcher m = r.matcher(token);
			     
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
//			    			 System.out.println("Constant="+st);
			    			 Constant c=new Constant(st.trim());
			    			 predParam.add(c);
//			    			 System.out.println("Constant created"+c.getValue());
			    			 
			    		 }
			    	 }
			    	 Param params=new Param(predParam);
			    	 p=new Predicate(pname.trim(), params);
			    	 lit=new Literal(p,neg);
//			    	 System.out.println();
			     }
			cliterals.add(lit);
			}
			

			clauses.add(new Clause(cliterals));
			
		}

		return clauses;
	}
	
	public void negate(ComplexSentence s)
	{
		if(s.isNegated())
			s.setNegated(false);
		else
			s.setNegated(true);
	}
}
