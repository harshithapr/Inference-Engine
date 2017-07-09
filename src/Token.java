public class Token 
{
	int type;
	String name;
	Token(int type, String name)
	{
		this.type=type;
		this.name=name;
	}
	
	int getType()
	{
		return type;
	}
	
	String getName()
	{
		return name;
	}
	
}
