import java.util.ArrayList;

public class homework 
{
	public static void main(String args[])
	{
		System.out.println("Hi");
		ReadInput r=new ReadInput("failed1.txt");
		Input ip=r.read();
//		ip.display();
		KBFactory KBF=new KBFactory(ip.getKBInput());
		ArrayList<Clause> kbClauses=KBF.CreateKB();
		
//		System.out.println("Knowledge Base");
		KBase kbase=new KBase();
		for(Clause c:kbClauses)
		{
//			System.out.println(c.toString());
			kbase.tellKB(c);
		}
		
		
		String[] EntailmentResult=new String[ip.getQueries().size()];
		int k=-1;
//		System.out.println("Query Added to KB");
		for(String q:ip.getQueries())
		{
			Resolution res=new Resolution(kbase);
			if(res.isEntailsKB(q))
				EntailmentResult[++k]="TRUE";
			else
				EntailmentResult[++k]="FALSE";
		}
//		System.out.println("Query Result");
		for(String op:EntailmentResult)
			System.out.println(op);
		WriteOutput wp=new WriteOutput("output.txt");
		wp.write(EntailmentResult);
	}
}
