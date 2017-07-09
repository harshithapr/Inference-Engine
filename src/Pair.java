

import java.util.LinkedList;

public class Pair 
{
	LinkedList<Clause> positive;
	LinkedList<Clause> negative;
	Pair(LinkedList<Clause> positive,LinkedList<Clause> negative)
	{
		this.positive=positive;
		this.negative=negative;
	}
	Pair()
	{
		this.positive=new LinkedList<Clause>();
		this.negative=new LinkedList<Clause>();
	}
	public LinkedList<Clause> getPositive()
	{
		return positive;
	}
	public LinkedList<Clause> getNegative()
	{
		return negative;
	}
	public void addPositive(LinkedList<Clause> positive)
	{
		this.positive=positive;
	}
	public void addNegative(LinkedList<Clause> negative)
	{
		this.negative=negative;
	}
	public void addPositiveClause(Clause positive)
	{
		this.positive.add(positive);
	}
	public void addNegativeClause(Clause negative)
	{
		this.negative.add(negative);
	}
}
