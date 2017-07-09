public class Sentence 
{
	String predicate;
	boolean atomic;
	boolean negated;
	Sentence(String predicate, boolean atomic, boolean negated)
	{
		this.predicate=predicate;
		this.atomic=atomic;
		this.negated=negated;
	}
	public void setAtomic(boolean b)
	{
		atomic=b;
	}
	public void setNegated(boolean b)
	{
		negated=b;
	}
	public boolean isAtomic()
	{
		return atomic;
	}
	public boolean isNegated()
	{
		return negated;
	}
	public String getPredicate()
	{
		return predicate;
	}
}
