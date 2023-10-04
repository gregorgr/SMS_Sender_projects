import java.io.*;
import java.util.*;

public class UporabniskiVmesnik
{
	public static void main(String[] args)
	{
		System.out.println("\n*** Dobrodošli v naš program! ***\n");

		Parkirisce parkirisce = new Parkirisce();

		while (true)
		{
			BufferedReader vhod = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Pritisnite (a) za vnos avta");
			System.out.println("Pritisnite (p) za izpis");
			System.out.println("Pritisnite (s) za shranjevanje v datoteko");
			System.out.println("Pritisnite (v) za vpis iz datoteke");
			System.out.println("Pritisnite (i) za iskanje");
			System.out.println("Pritisnite (x) za izhod");
			String niz;

			try
			{
				niz = vhod.readLine();
				char izbira = niz.charAt(0);

				switch (izbira)
				{
					case 'a':
						Avto nasNoviAvto = Avto.vnesiAvto();
						String obvestilo = parkirisce.dodajAvto(nasNoviAvto);

						if(obvestilo.length() > 0)
							System.out.println(obvestilo);
						else
							System.out.println(nasNoviAvto.toString());
						break;
					case 'x':
						System.out.println("Program zakljucen!");
						return;
					case 'p':
						System.out.println(parkirisce.toString());
						break;
					case 's':
						System.out.println("Vnesite ime datoteke za shranjevanje!");
						String datShrani = vhod.readLine();
						parkirisce.shraniVDatoteko(datShrani);
						System.out.println("Parkirišèe je uspešno shranjeno!");
						break;
					case 'v':
						System.out.println("Vnesite ime datoteke za branje!");
						String datBeri = vhod.readLine();
						int stevecDodanih = parkirisce.dodajIzDatoteke(datBeri);
						System.out.println("Iz datoteke je bilo dodanih " + stevecDodanih + " avtomobilov!");
						break;
					case 'i':
						System.out.println("Iskanje po parkirišèu:");
						System.out.println("  Pritisnite 'p' za iskanje po številu potnikov");
						niz = vhod.readLine();
						izbira = niz.charAt(0);

						switch(izbira)
						{
							case 'p':
								System.out.println("  Vnesite število potnikov:");
								int stPot = Integer.parseInt(vhod.readLine());
								System.out.println(parkirisce.izpisiAvtomobilePoSteviluPotnikov(stPot));
								break;
							default:
								break;
						}
						break;
					default:
						break;
				}
			}
			catch (Exception e)
			{
				System.out.println("Napaka - poskusite znova!");
			}
		}
	}
}