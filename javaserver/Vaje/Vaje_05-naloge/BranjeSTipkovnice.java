import java.io.*;

public class BranjeSTipkovnice
{
	public static void main(String[] args) throws Exception
	{
		InputStreamReader vt = new InputStreamReader(System.in);
		BufferedReader vhod = new BufferedReader(vt);

		System.out.print("Vnesi poljuben niz: ");
		String niz = vhod.readLine();

		int celo = 0;
		try
		{
			System.out.print("Vnesi celo stevilo: ");
			celo = Integer.parseInt(vhod.readLine());
		}
		catch(Exception ex)
		{
			System.out.println("Pri�lo je do neke �udne napake.");
		}

		System.out.print("Vnesi realno stevilo: ");
		double realno = Double.parseDouble(vhod.readLine());

		System.out.print("Vnesi znak: ");
		char znak = vhod.readLine().charAt(0);

		System.out.println("\nVnesel si niz '" + niz + "', celo �tevilo '" + celo +
			"', realno �tevilo '" + realno + "' in znak '" + znak + "'.");
	}


}