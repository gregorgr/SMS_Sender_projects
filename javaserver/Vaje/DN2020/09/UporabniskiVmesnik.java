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
			System.out.println("Pritisnite (b) za vnos avtobusa");
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

					case 'b':
							Avtobus nasNoviAvtobus = Avtobus.vnesiBus();
							String obvestilo2 = parkirisce.dodajAvtobus(nasNoviAvtobus );

							if(obvestilo2.length() > 0)
								System.out.println(obvestilo2);
							else
								System.out.println(nasNoviAvtobus.toString());
							break;
					case 'x':
						System.out.println("Program zakljucen!");
						return;
					case 'p':
						System.out.println(parkirisce.toString());
						break;
					case 's':
						System.out.println("Vnesite ime datoteke za shranjevanje [parking.txt]!");
						String datShrani = vhod.readLine();
						if (datShrani.length()==0){
							datShrani = "parking.txt";
						}
						parkirisce.shraniVDatoteko(datShrani);
						System.out.println("Parkirišče je uspešno shranjeno!\n");
						break;
					case 'v':
						System.out.println("Vnesite ime datoteke za branje [parking.txt]!");
						String datBeri = vhod.readLine();
						/// prednastavitve
						if (datBeri.length()==0){
							datBeri = "parking.txt";
						}
						int stevecDodanih = parkirisce.dodajIzDatoteke(datBeri);
						System.out.println("Iz datoteke je bilo dodanih " + stevecDodanih + " avtomobilov!\n");
						break;
					case 'i':
						System.out.println("Iskanje po parkirišču:");
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
