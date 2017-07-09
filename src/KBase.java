import java.util.ArrayList;


public class KBase 
{
	private ArrayList<Clause> Sentences;
	
	KBase()
	{
		Sentences=new ArrayList<Clause>();
	}
	KBase(ArrayList<Clause> Sent)
	{
		ArrayList<Clause> copySent=new ArrayList<Clause>();
		copySent.addAll(Sent);
		this.Sentences=copySent;
	}
	
	public void tellKB(Clause clause)
	{
		Sentences.add(clause);
	}
	
	public boolean askKB(Clause clause)
	{
		return false;
	}
	public ArrayList<Clause> getKB()
	{
		return Sentences;
	}

}
