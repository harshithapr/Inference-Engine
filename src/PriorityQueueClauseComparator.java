import java.util.Comparator;

public class PriorityQueueClauseComparator implements Comparator<Clause>
{

	@Override
	public int compare(Clause c1, Clause c2) 
	{
		if(c1.length()<c2.length())
			return -1;
		else if(c1.length()>c2.length())
			return 1;
		else
			return 0;
	}
	
}
