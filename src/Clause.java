import java.util.ArrayList;
import java.util.List;

public class Clause implements Operand, Cloneable
{
	private ArrayList<Literal> literals;
	Clause(ArrayList<Literal> literals)
	{
//		ArrayList<Literal> copylit=new ArrayList<Literal>();
//		copylit.addAll(literals);
//		this.literals=copylit;
//		this.literals=new ArrayList<Literal>();
//		this.literals.addAll(literals);
		this.literals=literals;
	}
	public ArrayList<Literal> getLiterals()
	{
		return literals;
	}
	public boolean isCompound() 
	{
		return false;
	}
	
	public String getName()
	{
		return "clause";
	}

	public List<? extends Operand> getArgs()
	{
		return literals;
	}
	public String toString()
	{
		Literal lit=literals.get(0);
		String str=lit.toString();
		for(int i=1;i<literals.size();i++)
		{
			str=str+"|"+literals.get(i);
		}
		return str;
	}
	public int hashcode()
	{
		System.out.println("Hello HashCode Clause");
		return toString().hashCode();
	}
	public boolean equals(Object o)
	{
		if(o instanceof Clause)
		{
		Clause p=(Clause)o;
		if(this.toString().equals(p.toString()))
			return true;
		else
			return false;
		}
		return false;
	}
	
	public Object Clone() throws CloneNotSupportedException
	{
		ArrayList<Literal> copylit=new ArrayList<Literal>();
		for(Literal l:this.literals)
		{
			copylit.add((Literal)l.Clone());
		}
		return new Clause(copylit);
	}
	
	public int length()
	{
		return literals.size();
	}
	public boolean isEmpty()
	{
		if(length()>0)
			return false;
		else
			return true;
	}
}
