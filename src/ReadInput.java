import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadInput 
{
	String filename;
	ReadInput(String filename)
	{
		this.filename=filename;
	}
	
	public Input read()
	{
		ArrayList<String> lines=new ArrayList<String>();
		try
		{
		File fp=new File(filename);
		FileReader  in=new FileReader(fp);
		BufferedReader br=new BufferedReader(in);
		
		String line;
		while((line=br.readLine())!=null)
		{
			lines.add(line.trim());
		}
		br.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Input file doesn't exists");
		}
		catch(IOException e)
		{
			System.out.println("Error while reading data from file");
		}
		ArrayList<String> KBInput=new ArrayList<String>();
		ArrayList<String> Queries=new ArrayList<String>();
		int querycount=Integer.parseInt(lines.get(0));
		int i;
		for(i=1; i<=querycount; i++)
		{
			Queries.add(lines.get(i));
		}
		int KBCount=Integer.parseInt(lines.get(i));
		for(int j=i+1; j<=i+KBCount; j++)
		{
			KBInput.add(lines.get(j));
		}
		
		Input ip=new Input(KBInput,Queries);
		
		return ip;
	}
	
}
