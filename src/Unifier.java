import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Unifier 
{
	public Map<Variable, Unit> unify(Operand x, Operand y) 
	{
		Literal lx=(Literal)x;
		Literal ly=(Literal)y;
		if((lx.isNegated()&&!ly.isNegated())||(!lx.isNegated()&&ly.isNegated()))
		{
			Map<Variable, Unit> finaltheta=unify(lx.getPredicate(), ly.getPredicate(), new LinkedHashMap<Variable, Unit>());
//			if(finaltheta!=null)
//			{
//			for(Variable vt: finaltheta.keySet())
//			{
//				if(finaltheta.get(vt) instanceof Variable)
//					return null;
//			}
//			}
			return finaltheta;
		}
		else
			return null;
	}
	public Map<Variable, Unit> unify(Operand x, Operand y, Map<Variable, Unit> theta) 
	{

		if (theta == null) 
		{
			return null;
		} 
		else if (x.equals(y)) 
		{
			return theta;
		} 
		else if (x instanceof Variable) 
		{
			return unifyVar((Variable) x, y, theta);
		} 
		else if (y instanceof Variable) 
		{
			return unifyVar((Variable) y, x, theta);
		} 
		else if (isCompound(x) && isCompound(y)) 
		{
			return unify(args(x), args(y), unifyOps(op(x), op(y), theta));
		} 
		else 
		{
			return null;
		}
	}
	
	public Map<Variable, Unit> unify(List<? extends Operand> x, List<? extends Operand> y, Map<Variable, Unit> theta) 
	{
		if (theta == null) 
		{
			return null;
		} 
		else if (x.size() != y.size()) 
		{
			return null;
		} 
		else if (x.size() == 0 && y.size() == 0) 
		{
			return theta;
		} 
		else if (x.size() == 1 && y.size() == 1) 
		{
			return unify(x.get(0), y.get(0), theta);
		} 
		else 
		{
			return unify(x.subList(1, x.size()), y.subList(1, y.size()),
					unify(x.get(0), y.get(0), theta));
		}
	}
	
	private Map<Variable, Unit> unifyVar(Variable var, Operand x, Map<Variable, Unit> theta) 
	{

		if (!Unit.class.isInstance(x)) 
		{
			return null;
		} 
		else if (theta.keySet().contains(var)) 
		{
			return unify(theta.get(var), x, theta);
		} 
		else if (theta.keySet().contains(x)) 
		{
			return unify(var, theta.get(x), theta);
		} 
		else if (occurCheck(theta, var, x)) 
		{
			return null;
		} 
		else 
		{
			Subst(theta, var, (Unit) x);
			return theta;
		}
	}
	
	private Map<Variable, Unit> unifyOps(String x, String y, Map<Variable, Unit> theta) 
	{
		if (theta == null) 
		{
			return null;
		} 
		else if (x.equals(y)) 
		{
			return theta;
		} 
		else 
		{
			return null;
		}
	}
	
	protected boolean occurCheck(Map<Variable, Unit> theta, Variable var, Operand x) 
	{
		if (var.equals(x)) 
		{
			return true;
		} 
		else if (theta.containsKey(x)) 
		{
			return occurCheck(theta, var, theta.get(x));	
		} 
		return false;
	}
	
	private Map<Variable, Unit> Subst(Map<Variable, Unit> theta, Variable v, Unit t) 
	{
		theta.put(v, t);
		for(Variable vt: theta.keySet())
		{
			if(theta.get(vt)==v)
				theta.put(vt, t);
		}
		return theta;
	}
	
	private List<? extends Operand> args(Operand x) 
	{
		return x.getArgs();
	}

	private String op(Operand x) 
	{
		return x.getName();
	}

	private boolean isCompound(Operand x) 
	{
		return x.isCompound();
	}
}
