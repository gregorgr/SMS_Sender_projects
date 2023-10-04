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


	// Konstruktorji:
	// Imajo enako ime kot razred,
	// izvedejo se ob uporabi kljuène besede new.
	// Uporabljamo jih za zaèetne nastavitve stanj objektov
	public Avto()
	{
		this.prestava = 1;
		this.hitrost = 0;
		this.registracija = "Neznana";
		this.maxHitrost = 180;
	}

	public Avto(int zacPrestava, double zacHitrost, String zacRegistracija)
	{
		this.prestava = zacPrestava;
		this.hitrost = zacHitrost;
		this.registracija = zacRegistracija;
		this.maxHitrost = 180;
	}

	public Avto(String zacRegistracija)
	{
		this.prestava = 1;
		this.hitrost = 0.0;
		this.registracija = zacRegistracija;
		this.maxHitrost = 180;
	}

	public Avto(double mh)
	{
		this.maxHitrost = mh;
		this.prestava = 1;
		this.hitrost = 0;
		this.registracija = "Neznana";
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

	// Metode tipa set spremenijo vrednost polja
	public void setRegistracija(String novaRegistracija)
	{
		this.registracija = novaRegistracija;
	}

	public void setMaxHitrost(double mh)
	{
		this.maxHitrost = mh;
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
		System.out.println("Prestava: " + this.getPrestava() + ".");
		System.out.println("Hitrost: " + this.getHitrost() + " km/h");
		System.out.println("Registracija: " + this.getRegistracija());
		System.out.println("Maksimalna hitrost: " + this.getMaxHitrost() + " km/h");
		System.out.println();
	}

	// Izvajanje programa
	public static void main(String[] args) throws IOException
	{
		// Naredimo konstruktorja dveh avtomobilov
		Avto nasAvto = new Avto();
		Avto sosedovAvto = new Avto(5, 130, "NM-XY-001");

		// Izpišimo podatke
		nasAvto.izpisiPodatke();
		sosedovAvto.izpisiPodatke();

		//sosedovAvto.setMaxHitrost(200);
		//sosedovAvto.izpisiPodatke();

		/*
		// Upravljajmo z njima
		nasAvto.pospesi(30);
		nasAvto.setRegistracija("NM-KT-224");
		nasAvto.izpisiPodatke();
		*/


		/*
		InputStreamReader inStream = new InputStreamReader(System.in);
		BufferedReader bfr = new BufferedReader(inStream);
		while(nasAvto.getHitrost() < 240)
		{
			System.out.print("Vnesite pospešek našega avta: ");
			int novaHitrost = Integer.parseInt(bfr.readLine());
			nasAvto.pospesi(novaHitrost);
			System.out.println();
			nasAvto.izpisiPodatke();
		}
		System.out.print("Pretiravati ne smemo.");
		*/
	}
}