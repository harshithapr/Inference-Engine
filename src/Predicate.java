import java.util.List;


public class Predicate implements Operand
{
	private String pname;
	Param params;
	Predicate(String name, Param params)
	{
		this.pname=name;
		this.params=params;
	}
	
	public Param getParam()
	{
		return params;
	}
	public String toString()
	{
		String str="",pstr="";
		String p;
//		ArrayList<Unit> plist=params.getParameters();
//		Unit t=plist.get(0);
//		if(t instanceof Variable)
//		{
//			str=((Variable) t).getValue();
//		}
//		else
//		{
//			str=((Constant) t).getValue();
//		}
//		
//		for(int i=1;i<plist.size();i++)
//		{
//			t=plist.get(i);
//			if(t instanceof Variable)
//			{
//				p=((Variable) t).getValue();
//			}
//			else
//			{
//				p=((Constant) t).getValue();
//			}
//			str=str+","+p;
//		}
		pstr=pname+"("+params.toString()+")";
		return pstr;
	}
	
	public int hashcode()
	{
		return toString().hashCode();
		//return pname.hashCode();
	}
	public boolean equals(Object o)
	{
		if(o instanceof Predicate)
		{
		Predicate p=(Predicate)o;
		if(this.toString().equals(p.toString()))
			return true;
		else
			return false;
		}
		return false;
	}
	
	public String getName()
	{
		return pname;
	}
	
	public boolean isCompound() 
	{
		return true;
	}
	
	public List<? extends Operand> getArgs()
	{
		return params.getParameters();
	}
	public Object Clone() throws CloneNotSupportedException
	{
		return new Predicate(this.pname,(Param)params.Clone());
	}
}


