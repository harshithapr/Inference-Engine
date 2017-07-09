import java.util.ArrayList;
import java.util.LinkedList;

public class Parser 
{
	Parser()
	{
		
	}
	
	public LinkedList<ComplexSentence> parse(ArrayList<Token> tokens)
	{
//		for(Token t:tokens)
//	      {
//	    	  System.out.println(t.getType()+" "+t.getName());
//	      }
	      
	      InfixToPostfix inf=new InfixToPostfix();
	      ArrayList<Token> postfix=inf.convert(tokens);
//	      System.out.println("Postfix Conversion");
//	      for(Token t:postfix)
//	      {
//	    	  System.out.println(t.getType()+" "+t.getName());
//	      }
	      
	      EvaluatePostfix eval=new EvaluatePostfix();
	      LinkedList<ComplexSentence> sentlist=eval.evaluate(postfix);
//	      System.out.println("Evaluate Postfix");
//	      for(ComplexSentence cs:sentlist)
//	      {
//	    	  display(cs);
//	    	  System.out.println();
//	      }
	   
	      return sentlist;
	}
	
	public void display(ComplexSentence s)
	   {
		   if(s.isAtomic())
		   {
			   if(s.negated)	
			   {
				   System.out.print("~");
			   }
			   System.out.print(s.getPredicate());
		   }
		   if(s.getOperand1()!=null)
			   display(s.getOperand1());
		   System.out.print(s.getOperatorDisplay());
		   if(s.getOperand2()!=null)
			   display(s.getOperand2());
	   }
}
