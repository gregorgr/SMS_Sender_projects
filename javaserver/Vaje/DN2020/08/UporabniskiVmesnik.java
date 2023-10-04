import java.io.*;
import java.util.*;

public class UporabniskiVmesnik
{
	public static void main(String[] args)
	{
		System.out.println("\n*** Dobrodošli v našem programu! ***\n");

		Parkirisce parkirisce = new Parkirisce();

		while (true)
		{
			BufferedReader vhod = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Pritisnite (a) za vnos avta");
			System.out.println("Pritisnite (i) za izpis");
			System.out.println("Pritisnite (številka) za izpraznitev parkirnega mesta");
			System.out.println("Pritisnite (x) za izhod");
			String niz;

			try
			{
				niz = vhod.readLine();
				char izbira = niz.charAt(0);

				switch (izbira)
				{
					case 'a':
						// Ustvarimo nov avto
						Avto nasNoviAvto = Avto.vnesiAvto();
						// Dodamo ga na parkirišče
						parkirisce.dodajAvto(nasNoviAvto);

						//System.out.println(nasNoviAvto);
						break;
					case 'x':
						System.out.println("Program zaključen!");
						return;
					case 'i':
						System.out.println(parkirisce.toString());
						break;
					default:
						// TODO:
						// odstrani avto iz parkirnega mesta št
						int mesto = Integer.parseInt(String.valueOf(izbira));
						System.out.println("Poizkušam izprazniti parkirno mesto " + mesto);
						parkirisce.odstraniAvto(mesto);

				}
			}
			catch (Exception e)
			{
				System.out.println("Napaka - poskusite znova!");
			}
		}
	}
}
