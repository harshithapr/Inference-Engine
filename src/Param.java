import java.util.ArrayList;
import java.util.List;

public class Param implements Operand
{
	ArrayList<Unit> vset;
	Param(ArrayList<Unit> vset)
	{
		this.vset=new ArrayList<Unit>();
		this.vset.addAll(vset);
	}
	public ArrayList<Unit> getParameters()
	{
		return vset;
	}
	public void setParameters(ArrayList<Unit> vset)
	{
		this.vset=vset;
	}
	public boolean isCompound() 
	{
		return false;
	}
	
	public String getName()
	{
		return "param";
	}

	public List<? extends Operand> getArgs()
	{
		return vset;
	}
	public String toString()
	{
		String str=null,p;
		ArrayList<Unit> plist=getParameters();
		Unit t=plist.get(0);
		if(t instanceof Variable)
		{
			str=((Variable) t).getValue();
		}
		else
		{
			str=((Constant) t).getValue();
		}
		
		for(int i=1;i<plist.size();i++)
		{
			t=plist.get(i);
			if(t instanceof Variable)
			{
				p=((Variable) t).getValue();
			}
			else
			{
				p=((Constant) t).getValue();
			}
			str=str+","+p;
		}
		return str;
	}
	
	public int hashcode()
	{
		return toString().hashCode();
	}
	
	public boolean equals(Object o)
	{
		Param p=(Param)o;
		if(this.toString().equals(p.toString()))
			return true;
		else
			return false;
	}
	
	public Object Clone() throws CloneNotSupportedException
	{
		return new Param(this.vset);
	}
}
