import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteOutput 
{
	String filename;
	WriteOutput(String filename)
	{
		this.filename=filename;
	}
	
	public void write(String[] data)
	{
		FileWriter fw;
		BufferedWriter bw;
		try
		{
			fw=new FileWriter(filename);
			bw=new BufferedWriter(fw);
			int size=data.length-1;
		for(int i=0;i<size;i++)
		{
			bw.write(data[i]);
			bw.write("\n");
		}
		bw.write(data[size]);
		
		bw.close();
		}
		catch(IOException e)
		{
			System.out.println("Error in writing data to a file");
		}

	}
}

