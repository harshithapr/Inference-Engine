import java.util.HashMap;

public class StandardizeApart 
{
	int index;
	String var;
	StandardizeApart(String var)
	{
		this.var=var;
		index=0;
	}
	
	public Clause standardize(Clause c)
	{
		HashMap<String,String> variableMap=new HashMap<String,String>();
		for(Literal l:c.getLiterals())
		{
			Param p=l.getPredicate().getParam();
			for(Unit v: p.getParameters())
			{
				if(v instanceof Variable)
				{
					
					if(variableMap.containsKey(((Variable) v).getValue()))
						((Variable) v).setValue(variableMap.get(((Variable) v).getValue()));
					else
					{
						index++;
						String str=var+index;
						variableMap.put(((Variable) v).getValue(), str);
						((Variable) v).setValue(str);
						
					}
							
				}
			}
		}
		return c;
	}
}
