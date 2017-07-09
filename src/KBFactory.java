import java.util.ArrayList;
import java.util.LinkedList;

public class KBFactory 
{
	private ArrayList<String> KBInput;
	KBFactory(ArrayList<String> KBInput)
	{
		this.KBInput=KBInput;
	}
	
	public ArrayList<Clause> CreateKB()
	{
		StandardizeApart std=new StandardizeApart("v");
		String pattern="[A-Z][A-Za-z0-9]*[\\s]?[\\(][\\s]?[A-Za-z0-9]+([\\s]?[,]?[\\s]?[A-Za-z0-9]+)*[\\s]?[\\)][\\s]?";
		Lexer lex=new Lexer(pattern);
		Parser yacc=new Parser();
		ArrayList<Token> tokens;
		LinkedList<ComplexSentence> ParsedSentences;
		CNF cnf;
		ArrayList<Clause> clauses;
		ArrayList<Clause> clauseset=new ArrayList<Clause>();
		
		for(String s:KBInput)
	    {
			tokens=lex.getTokens(s);
			ParsedSentences=yacc.parse(tokens);
			cnf=new CNF(ParsedSentences);
			clauses=cnf.ConvertToCNF();
			clauseset.addAll(clauses);
	    }
		
//		System.out.println("Standardizing Variables");
		for(Clause c: clauseset)
		{
			c=std.standardize(c);
//			System.out.println(c.toString());
		}
		
		return clauseset;		
	}
}
