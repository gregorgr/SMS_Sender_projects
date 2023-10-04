import java.io.*;

public class Izjema1
{
	public static void main(String[] args)
	{
		try
		{
			int stevilo = Integer.parseInt(args[0]);
			System.out.println("Argument je " + stevilo + ".");
		}
		catch(IndexOutOfBoundsException ind)
		{
			System.out.println("Pozabil si vnesti argument!");
		}
		catch(NumberFormatException form)
		{
			System.out.println("Argument ni število!");
		}
	}
}