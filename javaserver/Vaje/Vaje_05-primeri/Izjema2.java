import java.io.*;

public class Izjema2
{
	public static void main(String[] args) throws Exception
	{
		int stevilo = method();

		System.out.println("Argument je " + stevilo + ".");
	}

	public static int method() throws IOException
	{
		throw new IOException("Error!");
	}
}