import java.util.ArrayList;

public class Input 
{
	private ArrayList<String> KBInput=new ArrayList<String>();
	private ArrayList<String> Queries=new ArrayList<String>();
	Input(ArrayList<String> KBInput, ArrayList<String> Queries)
	{
		this.KBInput=KBInput;
		this.Queries=Queries;
	}
	
	public ArrayList<String> getKBInput()
	{
		return KBInput;
	}
	
	public ArrayList<String> getQueries()
	{
		return Queries;
	}
	
	void display()
	{
		System.out.println("No of sentences in KB="+KBInput.size());
		System.out.println("KB Data");
		for(int i=0;i<KBInput.size();i++)
		{
			System.out.println(KBInput.get(i));
		}
		
		System.out.println("No of queries in KB="+Queries.size());
		System.out.println("Queries");
		for(int i=0;i<Queries.size();i++)
		{
			System.out.println(Queries.get(i));
		}
	}
}
