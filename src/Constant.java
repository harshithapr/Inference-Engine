

import java.util.List;

public class Constant implements Unit
{
	String value;
	Constant(String value)
	{
		this.value=value;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public boolean isCompound() 
	{
		return false;
	}
	
	public String getName()
	{
		return getValue();
	}

	public List<Unit> getArgs()
	{
		return null;
	}
	
	public int hashcode()
	{
		return value.hashCode();
	}
	public String toString()
	{
		return value;
	}
	public boolean equals(Object o)
	{
		if(o instanceof Constant)
		{
		Constant c=(Constant)o;
		if(this.value.equals(c.value))
			return true;
		else
			return false;
		}
		return false;
	}
	public Object Clone() throws CloneNotSupportedException
	{
		return new Constant(this.value);
	}
}
