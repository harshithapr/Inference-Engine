import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class EvaluatePostfix 
{
	Stack<ComplexSentence> opndstk=new Stack<ComplexSentence>();
	LinkedList<ComplexSentence> Sentences=new LinkedList<ComplexSentence>();
	public LinkedList<ComplexSentence> evaluate(ArrayList<Token> postfix)
	{
		for(Token t:postfix)
		{
			if(t.getType()==TokenTypes.SYMB)
			{
				ComplexSentence comp=new ComplexSentence(null,null,-1,t.getName(),true,false);
				opndstk.push(comp);
			}
			else
			{
				if(t.getType()!=TokenTypes.NEG)
				{
					ComplexSentence op2=opndstk.pop();
					ComplexSentence op1=opndstk.pop();
					Sentences.addFirst(op1);
					Sentences.addFirst(op2);
					ComplexSentence res=new ComplexSentence(op1,op2,t.getType(),null,false,false);
					opndstk.push(res);
					
				}
				else
				{
					ComplexSentence op1=opndstk.pop();
					op1.setNegated(true);
					opndstk.push(op1);
				}
			}
		}
		
		ComplexSentence last=opndstk.pop();
		Sentences.addFirst(last);
		return Sentences;
	}
}
