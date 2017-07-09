import java.util.ArrayList;
import java.util.HashMap;

public class KB 
{
	private HashMap<String,Pair> Sentences;
	
	KB()
	{
		Sentences=new HashMap<String,Pair>();
	}
	
	public boolean tellKB(Clause clause)
	{
		ArrayList<Literal> lits=clause.getLiterals();
		String p;
		for(Literal l:lits)
		{
			p=l.getPredicate().getName();
			if(Sentences.containsKey(p))
			{
				//Sentences.get(p).add(clause);
				Pair pn=Sentences.get(p);
				if(l.isNegated())
					pn.addNegativeClause(clause);
				else
					pn.addPositiveClause(clause);
			}
			else
			{
				Pair np=new Pair();
				if(l.isNegated())
					np.addNegativeClause(clause);
				else
					np.addPositiveClause(clause);
				Sentences.put(p, np);
			}
		}
		return false;
	}
	
	public boolean askKB(Clause clause)
	{
		return false;
	}
	
	public HashMap<String,Pair> getKB()
	{
		return Sentences;
	}
	
	public boolean contains(Clause c)
	{
		Pair p;
		for(Literal l: c.getLiterals())
		{
			p=Sentences.get(l.getPredicate().getName());
			if(p.getPositive().contains(c) || p.getNegative().contains(c))
					return true;
		}
		return false;
	}
}
