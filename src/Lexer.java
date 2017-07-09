

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer 
{
	String pattern;
	Lexer(String pattern)
	{
		this.pattern=pattern;
	}
	
	public ArrayList<Token> getTokens(String line)
	{
		  Pattern r = Pattern.compile(pattern);
	      Matcher m = r.matcher(line);
	      
	      int i=0;
	      char[] buffer=line.toCharArray();
	      ArrayList<Token> tokens=new ArrayList<Token>();
	     
	      while(i<line.length())
	      {
	    	  if(buffer[i]=='~')
	    	  {
	    		  tokens.add(new Token(TokenTypes.NEG,"~"));
	    		  i++;
	    	  }
	    	  else if(buffer[i]=='&')
	    	  {
	    		  tokens.add(new Token(TokenTypes.AND,"&"));
	    		  i++;
	    	  }
	       	  else if(buffer[i]=='|')
	    	  {
	    		  tokens.add(new Token(TokenTypes.OR,"|"));
	    		  i++;
	    	  }
	       	  else if(buffer[i]=='=' && buffer[i+1]=='>')
	       	  {
	       		  tokens.add(new Token(TokenTypes.IMP,"=>"));
	       		  i=i+2;
	       	  }
	       	  else if(buffer[i]=='(')
		  	  {
		  		  tokens.add(new Token(TokenTypes.LPAREN,"("));
		  		  i++;
		  	  }
	       	  else if(buffer[i]==')')
	       	  {
	       		tokens.add(new Token(TokenTypes.RPAREN,")"));
	       		i++;
	       	  }
	       	  else if(buffer[i]==' ')
	       	  {
	       		  i++;
	       	  }
	       	  else
	       	  {
	       		  m.find();
	       		  tokens.add(new Token(TokenTypes.SYMB,line.substring(m.start(),m.end())));
	       		  i=m.end();
	       	  }
	      }
	return tokens;
	}
}
