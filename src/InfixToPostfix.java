import java.util.ArrayList;
import java.util.Stack;

public class InfixToPostfix 
{
	Stack<Token> opstk=new Stack<Token>();
	
	public ArrayList<Token> convert(ArrayList<Token> tokens)
	{
		ArrayList<Token> postfix=new ArrayList<Token>();
		Token topsymb;
		for(Token t:tokens)
		{
			if(t.getType()==TokenTypes.SYMB)
				postfix.add(t);
			else
			{
				while(!opstk.empty() && precedence(opstk.peek(),t))
				{
					topsymb=opstk.pop();
					postfix.add(topsymb);
				}
				if(opstk.empty()||t.getType()!=TokenTypes.RPAREN)
				{
					opstk.push(t);
				}
				else
				{
					topsymb=opstk.pop();
				}
			}
		}
		while(!opstk.empty())
		{
			topsymb=opstk.pop();
			postfix.add(topsymb);

		}
		return postfix;
	}
	
	public boolean precedence(Token t1, Token t2)
	{
		int op1=t1.getType();
		int op2=t2.getType();
		
		if(op1==TokenTypes.LPAREN && op2==TokenTypes.RPAREN)
			return false;
		else if(op1!=TokenTypes.RPAREN && op2==TokenTypes.LPAREN)
			return false;
		else if(op1==TokenTypes.LPAREN && op2!=TokenTypes.RPAREN)
			return false;
		else if(op1!=TokenTypes.LPAREN && op2==TokenTypes.RPAREN)
			return true;
		else if(op1!=TokenTypes.LPAREN && op2!=TokenTypes.RPAREN)
		{	
			if(op1-op2>=0)
				return true;
			else
				return false;
		}
		return false;
}
}
