import java.util.List;

public class Literal implements Operand
{
	private Predicate pred;
	private boolean negated;
	Literal(Predicate pred,boolean negated)
	{
		this.pred=pred;
		this.negated=negated;
	}
	Literal(Literal copy)
	{
		this.pred=copy.pred;
		this.negated=copy.negated;
	}
	
	public Predicate getPredicate()
	{
		return pred;
	}
	
	public boolean isNegated()
	{
		return negated;
	}
	
	public void negate()
	{
		if(negated)
			negated=false;
		else
			negated=true;
	}
	public String toString()
	{
		String str="";
		if(negated)
			str="~";
		str=str+pred.toString();
		
		return str;
	}
	public int hashcode()
	{
		return toString().hashCode();
	}
	public boolean equals(Object o)
	{
		if(o instanceof Literal)
		{
		Literal p=(Literal)o;
		if(this.toString().equals(p.toString()))
			return true;
		else
			return false;
		}
		return false;
	}
	
	public String getName()
	{
		return pred.getName();
	}
	
	public boolean isCompound() 
	{
		return true;
	}
	
	public List<? extends Operand> getArgs()
	{
		return null;
	}
	public Object Clone() throws CloneNotSupportedException
	{
		return new Literal((Predicate)pred.Clone(),negated);
	}
}
