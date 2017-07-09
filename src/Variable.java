import java.util.List;

public class Variable implements Unit
{
	private String value;
	
	Variable(String value)
	{
		this.value=value;
	}
	
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value=value;
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
	
	public boolean equals(Object o)
	{
		if(o instanceof Variable)
		{
		Variable c=(Variable)o;
		if(this.value.equals(c.value))
			return true;
		else
			return false;
		}
		return false;
	}
	public String toString()
	{
		return value;
	}
	
	public Object Clone() throws CloneNotSupportedException
	{
		
		return new Variable(this.value);
	}
	
}
