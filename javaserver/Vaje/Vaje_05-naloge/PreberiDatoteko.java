import java.io.*;

public class PreberiDatoteko
{
	public static void main(String[] args) throws Exception
	{
		String ime = args[0];
		FileReader fr = new FileReader(ime);
		BufferedReader dat = new BufferedReader(fr);

		while(dat.ready())
		{
			System.out.println(dat.readLine());
		}
		dat.close();
	}
}