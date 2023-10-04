/*
6. domača naloga

    Za razred Avto
    - si izmislite še dve lastnosti avta,  XX
    - zanju naredite polji,
    - dodajte ju v konstruktor,
    -določite privzeti vrednosti in
    napišite metodi za upravljanje z njima.
    - Popravite tudi izpis.

*/
/*
 *	Razred Avto opisuje nekaj lastnosti in funkcionalnosti avtomobila
 */
import java.io.*;

public class Avto
{
	// Polja
	private int prestava;
	private double hitrost;
	private String registracija;
	private double maxHitrost;

	// dodani lastnosti
	private int steviloSedezev;
	private String stevilkaSasije;
	private String ime;

	// Uporabljamo jih za za�etne nastavitve stanj objektov
	public Avto()
	{
		this.ime= "Navaden avto";
		this.prestava = 1;
		this.hitrost = 0;
		this.registracija = "Neznana";
		this.maxHitrost = 180;

		this.steviloSedezev = 5;
		this.stevilkaSasije = "XXX XXXXXXXXXXXX";
	}

	public Avto( int zacPrestava, double zacHitrost, String zacRegistracija)
	{
		this.ime= "Navaden avto";
		this.prestava = zacPrestava;
		this.hitrost = zacHitrost;
		this.registracija = zacRegistracija;
		this.maxHitrost = 180;

		this.steviloSedezev = 5;
		this.stevilkaSasije = "XXX XXXXXXXXXXXX";
	}

	public Avto(int zacPrestava, double zacHitrost, String zacRegistracija, int zacSteviloSedezev)
	{
		this.ime= "Navaden avto";
		this.prestava = zacPrestava;
		this.hitrost = zacHitrost;
		this.registracija = zacRegistracija;
		this.maxHitrost = 180;

		this.steviloSedezev = zacSteviloSedezev;
		this.stevilkaSasije = "XXX XXXXXXXXXXXX";
	}

	public Avto(int zacPrestava, double zacHitrost, String zacRegistracija, String zacStevilkaSasije)
	{
		this.ime= "Navaden avto";
		this.prestava = zacPrestava;
		this.hitrost = zacHitrost;
		this.registracija = zacRegistracija;
		this.maxHitrost = 180;

		this.steviloSedezev = 5;
		this.stevilkaSasije = zacStevilkaSasije;
	}

	public Avto(int zacPrestava, double zacHitrost, String zacRegistracija, int zacSteviloSedezev, String zacStevilkaSasije)
	{
		this.ime= "Navaden avto";
		this.prestava = zacPrestava;
		this.hitrost = zacHitrost;
		this.registracija = zacRegistracija;
		this.maxHitrost = 180;

		this.steviloSedezev = zacSteviloSedezev;
		this.stevilkaSasije = zacStevilkaSasije;
	}

	public Avto(String zacRegistracija)
	{
		this.ime= "Navaden avto";
		this.prestava = 1;
		this.hitrost = 0.0;
		this.registracija = zacRegistracija;
		this.maxHitrost = 180;

		this.steviloSedezev = 5;
		this.stevilkaSasije = "XXX XXXXXXXXXXXX";
	}

	public Avto(double mh)
	{
		this.ime= "Navaden avto";
		this.maxHitrost = mh;
		this.prestava = 1;
		this.hitrost = 0;
		this.registracija = "Neznana";

		this.steviloSedezev = 5;
		this.stevilkaSasije = "XXX XXXXXXXXXXXX";
	}


	// Metode

	// Metode tipa get nam vrnejo vrednosti polj
	public int getPrestava()
	{
		return this.prestava;
	}

	public double getHitrost()
	{
		return this.hitrost;
	}

	public double getMaxHitrost()
	{
		return this.maxHitrost;
	}

	public String getRegistracija()
	{
		return this.registracija;
	}

	public int getSteviloSedezev()
	{
		return this.steviloSedezev;
	}

	public String getStevilkaSasije()
	{
		return this.stevilkaSasije;
	}

	// Metode tipa set spremenijo vrednost polja
	public void setRegistracija(String novaRegistracija)
	{
		this.registracija = novaRegistracija;
	}

	public void setMaxHitrost(double mh)
	{
		this.maxHitrost = mh;
	}

	public void setIme(String zacIme)
	{
		this.ime = zacIme;
	}


	// dodani lastnosti
	public void setSteviloSedezev(int zacSteviloSedezev)
	{
		this.steviloSedezev = zacSteviloSedezev;
	}

	public void setStevilkaSasije(String zacStevilkaSasije)
	{
		this.stevilkaSasije = zacStevilkaSasije;
	}






	public void pospesi(double pospesek)
	{
		if(this.hitrost + pospesek > this.maxHitrost)
		{
			this.hitrost = this.maxHitrost;
		}
		else
		{
			this.hitrost += pospesek;
		}

		if(this.hitrost < 20)
		{
			this.prestava = 1;
		}
		else if(this.hitrost < 40)
		{
			this.prestava = 2;
		}
		else if(this.hitrost < 60)
		{
			this.prestava = 3;
		}
		else if(this.hitrost < 90)
		{
			this.prestava = 4;
		}
		else
		{
			this.prestava = 5;
		}
	}

	public void izpisiPodatke()
	{
		System.out.println("***   Podatki   ***");
		System.out.println( this.ime);
		System.out.println("Prestava: " + this.getPrestava() + ".");
		System.out.println("Hitrost: " + this.getHitrost() + " km/h");
		System.out.println("Registracija: " + this.getRegistracija());
		System.out.println("Maksimalna hitrost: " + this.getMaxHitrost() + " km/h");

		// dodani lastnosti
		System.out.println("Število sedežev: " + this.steviloSedezev);
		System.out.println("Številka šasije: " + this.stevilkaSasije);


		System.out.println();
	}

	// Izvajanje programa
	public static void main(String[] args) throws IOException
	{
		// Naredimo konstruktorja dveh avtomobilov
		Avto nasAvto = new Avto();
		Avto sosedovAvto = new Avto(5, 130, "NM-XY-001", 2);

sosedovAvto.setMaxHitrost(225);

		// Izpi�imo podatke
		nasAvto.izpisiPodatke();
		sosedovAvto.izpisiPodatke();

		//sosedovAvto.setMaxHitrost(200);
		//sosedovAvto.izpisiPodatke();

		nasAvto.setIme("Naš Avto");
		sosedovAvto.setIme("Sosedov Avto");
		/*


		*/
		// Upravljajmo z njima
		nasAvto.pospesi(30);
		nasAvto.setRegistracija("NM-KT-224");
		nasAvto.setStevilkaSasije("FIAT57-26732WW462904");
		nasAvto.izpisiPodatke();




		InputStreamReader inStream = new InputStreamReader(System.in);
		BufferedReader bfr = new BufferedReader(inStream);
		while(nasAvto.getHitrost() < nasAvto.getMaxHitrost())
		{
			try{
				System.out.print("Vnesite pospešek našega avta: ");
				int novaHitrost = Integer.parseInt(bfr.readLine());
				nasAvto.pospesi(novaHitrost);
				System.out.println();
				nasAvto.izpisiPodatke();
			}catch(Exception e){}
		}
		System.out.println("Pretiravati ne smemo.");
		System.out.println("Naš avto ima maximalno hitrost samo " + nasAvto.getMaxHitrost() + "km/h medtem, ko gre lahko sosedov tudi " + sosedovAvto.getMaxHitrost() + "km/h.");

	}
}
