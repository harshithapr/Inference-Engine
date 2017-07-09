

public class ComplexSentence
{
	String predicate;
	boolean atomic;
	boolean negated;
	ComplexSentence op1,op2;
	int operator;
	ComplexSentence(ComplexSentence op1, ComplexSentence op2,int operator,String predicate, boolean atomic, boolean negated) 
	{
		this.predicate=predicate;
		this.atomic=atomic;
		this.negated=negated;
		this.op1=op1;
		this.op2=op2;
		this.operator=operator;
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
	public ComplexSentence getOperand1()
	{
		return op1;
	}
	
	public ComplexSentence getOperand2()
	{
		return op2;
	}
	
	public int getOperator()
	{
		return operator;
	}
	public void setOperand1(ComplexSentence op1)
	{
		this.op1=op1;
	}
	public void setOperand2(ComplexSentence op2)
	{
		this.op2=op2;
	}
	public void setOperator(int op)
	{
		operator=op;
	}
	public String getOperatorDisplay()
	{
		String op=null;
		switch(operator)
		{
		case TokenTypes.NEG:
			op="~";
			break;
		case TokenTypes.AND:
			op="&";
			break;
		case TokenTypes.OR:
			op="|";
			break;
		case TokenTypes.IMP:
			op="=>";
			break;
		case -1:
			op="";
			break;
		}
		return op;
	}
	public String toString()
	{
		String str="";
		if(this.isAtomic())
		   {
			   if(this.negated)
			   {
				   str=str+"~";
			   } 
			   str=str+this.getPredicate();
		   }
		   else
		   {
		   if(this.getOperand1()!=null)
			   str=display(this.getOperand1(),str);
		   str=str+this.getOperatorDisplay();
		   if(this.getOperand2()!=null)
			   str=display(this.getOperand2(),str);
		   }
		   return str;
	}
	
	public boolean equals(Object o)
	{
		Sentence s;
		if(o instanceof Sentence)
		{
			s=(Sentence)o;
		if(this.toString().equals(s.toString()))
			return true;
		else
			return false;	
		}
		return false;
	}
	
	public String display(ComplexSentence s, String str)
	{
		   if(s.isAtomic())
		   {
			   if(s.negated)
			   {
				   str=str+"~";
			   } 
			   str=str+s.getPredicate();
		   }
		   else
		   {
		   if(s.getOperand1()!=null)
			   str=display(s.getOperand1(),str);
		   str=str+s.getOperatorDisplay();
		   if(s.getOperand2()!=null)
			   str=display(s.getOperand2(),str);
		   }
		   return str;
	}
	
}
