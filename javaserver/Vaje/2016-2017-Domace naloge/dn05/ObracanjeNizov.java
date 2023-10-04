/*
Domaca naloga 05:
Napisite metodo obrniNiz, ki kot parameter prejme niz, vrne
pa njegovo obrnjeno vrednost. Primer obrniNiz("zirafa")
= "afariz". Nato iz tipkovnice preberite ime datoteke A, v
kateri imate besedilo ter ime datoteke B, v katero boste
zapisovali nove podatke. Na koncu zapisite vsako izmed vrstic
v datoteki A v datoteko B z njeno obrnjeno vrednostjo.
*/

/*
Algoritem:
Metoda obrniNiz
{
	- obracanje nizov
	- vrni obrnjen niz
}

Metoda main
{
	- branje nazivov datotek za branje in zapisovanje iz tipkovnice
	- odpiranje datotek za branje in zapisovanje
	- ponavljaj dokler lahko karkoli preberes iz datoteke
	{
		- branje vrstice iz datoteke v niz
		- obracanje niza z pomocjo metode
		- zapisovanje vrstice v datoteko
	}
	-zapri datoteki za branje in zapisovanje
}
*/

import java.io.*; // Zaradi branja iz tipkovnice in iz/v datoteke


public class ObracanjeNizov
{
	// Metoda za obracanje nizov
	public static String obrniNiz (String niz)
	// Java zahteva, da je metoda static!
	// Zato, ker jo klicemo v main metodi brez objekta
	{
		// Inicializacija pomoznih spremenljivk
		String obrnjenNiz="";
		int dolzinaNiza=niz.length();

		// Obrnjenemu nizu dodaja crko po crko iz zadnjega konca prvotnega niza
		for(int i=0;i<dolzinaNiza;i++)
		{
			obrnjenNiz += niz.charAt(dolzinaNiza-i-1);
		}

		// Vrne obrnjen niz
		return obrnjenNiz;
	}

	public static void main (String[] args) throws Exception
	{
		// Testiranje metode obrniNiz
		/*
		String niz1 = "Janez Zupancic";
		String obr;
		obr = obrniNiz (niz1);
		System.out.println("Prvoten niz: "+niz1);
		System.out.println("Obrnjen niz: "+obr);
		*/

		// Pozdrav v programu
		System.out.println ("Pozdravljeni v programu za obracanje nizov.");

		// Odpiranje vhodnega toka za tipkovnico
		InputStreamReader vt = new InputStreamReader(System.in);
		BufferedReader vhod = new BufferedReader(vt);

		// Ime datoteke za branje
		System.out.print ("Vnesite ime datoteke, iz katere zelite brati besedilo: ");
		String imeDatBeri = vhod.readLine();

		// Ime datoteke za zapisovanje
		System.out.print ("Vnesite ime datoteke, v katero zelite zapisati obrnjene vrstice: ");
		String imeDatZapisi = vhod.readLine();

		// Odpiranje datoteke za branje
		FileReader fr = new FileReader(imeDatBeri);
		BufferedReader datBeri = new BufferedReader(fr);

		// Odpiranje datoteke za pisanje
		FileWriter fw = new FileWriter (imeDatZapisi);
		PrintWriter datZapisi = new PrintWriter(fw);

		// Inicializacija pomožnih spremenljivk
		String niz = "";
		String obr = "";

		// Bere toliko casa, dokler lahko se kaj prebere iz datoteke za branje
		while (datBeri.ready())
		{
			// Prebere, obrne, zapise posamezno vrstico
			niz = datBeri.readLine();
			obr = obrniNiz (niz);
			datZapisi.println (obr);

			// Drugacen zapis zgornjih treh vrstic
			// datZapisi.println(obrniNiz(datBeri.readLine()));
		}
		// Zapiranje obeh datotek
		datBeri.close();
		datZapisi.close();
	}
}
