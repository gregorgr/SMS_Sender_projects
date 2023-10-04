/* 
Domaca naloga 6

Za razred Avto si izmislite se dve lastnosti avta, zanju naredite
polji, dodajte ju v konstruktor, dolocite privzeti vrednosti in
napisite metodi za upravljanje z njima. Popravite tudi izpis.
*/

/* 
 *	Razred Avto opisuje nekaj lastnosti in funkcionalnosti avtomobila
 */
import java.io.*;

public class DN6_Avto
{
	/////////////////////////////////////////////////////////////////////////////
	// Polja
	private String lastnik;
	private int prestava;
	private double hitrost;
	private String registracija;
	private double maxHitrost;
	private int stVrat;
	private int stSedezev;

	/////////////////////////////////////////////////////////////////////////////
	// Konstruktorji:
	// Imajo enako ime kot razred,
	// izvedejo se ob uporabi kljuène besede new.
	// Uporabljamo jih za zaèetne nastavitve stanj objektov
	public DN6_Avto()
	{
		this.lastnik = "Neznan";
		this.prestava = 1;
		this.hitrost = 0;
		this.registracija = "Neznana";
		this.maxHitrost = 150;
		this.stVrat = 5;
		this.stSedezev = 5;
	}

	public DN6_Avto(String prviLastnik, int zacPrestava, double zacHitrost, String zacRegistracija, double mh, int sv, int ss)
	{
		this.lastnik = prviLastnik;
		this.prestava = zacPrestava;
		this.hitrost = zacHitrost;
		this.registracija = zacRegistracija;
		this.maxHitrost = mh;
		this.stVrat = sv;
		this.stSedezev = ss;
	}

	/*
	ta dva konstruktorja sem kar zakomentiral, ker realno nista uporabna pri toliko število polj v razredu
	
	public DN6_Avto(String zacRegistracija)
	{
		this.prestava = 1;
		this.hitrost = 0.0;
		this.registracija = zacRegistracija;
		this.maxHitrost = 150;
	}

	public DN6_Avto(double maximalnaHitrost)
	{
		this.prestava = 1;
		this.hitrost = 0;
		this.registracija = "Neznana";
		this.maxHitrost = maximalnaHitrost;
	}
	*/
	
	/////////////////////////////////////////////////////////////////////////////
	// Metode
	
	/////////////////////////////////////////////////////////////////////////////
	// Metode tipa get nam vrnejo vrednosti polj
	
	public String getLastnik()
	{
		return this.lastnik;
	}
	
	public int getPrestava()
	{
		return this.prestava;
	}

	public double getHitrost()
	{
		return this.hitrost;
	}

	public String getRegistracija()
	{
		return this.registracija;
	}
	
	public double getMaxHitrost()
	{
		return this.maxHitrost;
	}
	
	public int getSteviloVrat()
	{
		return this.stVrat;
	}
	
	public int getSteviloSedezev()
	{
		return this.stSedezev;
	}

	/////////////////////////////////////////////////////////////////////////////
	// Metode tipa set spremenijo vrednost polja
	
	public void setLastnik (String noviLastnik)
	{
		this.lastnik = noviLastnik;
	}
	
	public void setRegistracija(String novaRegistracija)
	{
		this.registracija = novaRegistracija;
	}
	
	public void setMaxHitrost(double mh)
	{
		this.maxHitrost = mh; 
	}
	
	public void setSteviloVrat(int sv)
	{
		this.stVrat = sv;
	}

	public void setSteviloSedezev(int ss)
	{
		this.stSedezev = ss;
	}
	
	/////////////////////////////////////////////////////////////////////////////
	// Ostale metode razreda DN6_Avto
	
	public void pospesi(double pospesek)
	{
		// omejitev hitrosti
		if(this.hitrost + pospesek > this.maxHitrost)
		{
			this.hitrost=this.maxHitrost;
		}
		else
		{
			this.hitrost += pospesek;
		}

		// prestava avtomobila
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
		System.out.println("Lastnik: " + this.getLastnik());
		System.out.println("Prestava: " + this.getPrestava() + ".");
		System.out.println("Hitrost: " + this.getHitrost() + " km/h");
		System.out.println("Registracija: " + this.getRegistracija());
		System.out.println("Maximalna hitrost: " + this.getMaxHitrost());
		System.out.println("Stevilo Vrat: " + this.stVrat);
		System.out.println("Stevilo Sedezev: " + this.stSedezev);
		System.out.println();
	}

	// Izvajanje programa
	public static void main(String[] args) throws IOException
	{
		// Naredimo konstruktorja dveh avtomobilov
		DN6_Avto nasAvto = new DN6_Avto();
		DN6_Avto sosedovAvto = new DN6_Avto("Sosed", 5, 130, "NM-XY-001", 180, 5, 7);

		// Izpišimo podatke
		nasAvto.izpisiPodatke();
		sosedovAvto.izpisiPodatke();
		
		// Upravljajmo z njima
		nasAvto.setLastnik("Zena");
		nasAvto.pospesi(30);
		nasAvto.setRegistracija("NM-KT-224");
		nasAvto.setMaxHitrost(190);
		nasAvto.setSteviloVrat(3);
		nasAvto.setSteviloSedezev(5);
		nasAvto.izpisiPodatke();
		

		// Branje hitrosti iz tipkovnice
		InputStreamReader inStream = new InputStreamReader(System.in);
		BufferedReader bfr = new BufferedReader(inStream);
		while(nasAvto.getHitrost() < 180)
		{
			System.out.print("Vnesite pospešek našega avta: ");
			int novaHitrost = Integer.parseInt(bfr.readLine());
			nasAvto.pospesi(novaHitrost);
			System.out.println();
			nasAvto.izpisiPodatke();
		}
		System.out.print("Prevec pritiskate na plin. Predlagam, da koncamo z voznjo.");
		
	}
}

/*
***   Podatki   ***
Lastnik: Neznan
Prestava: 1.
Hitrost: 0.0 km/h
Registracija: Neznana
Maximalna hitrost: 150.0
Stevilo Vrat: 5
Stevilo Sedezev: 5

***   Podatki   ***
Lastnik: Sosed
Prestava: 5.
Hitrost: 130.0 km/h
Registracija: NM-XY-001
Maximalna hitrost: 180.0
Stevilo Vrat: 5
Stevilo Sedezev: 7

***   Podatki   ***
Lastnik: Zena
Prestava: 2.
Hitrost: 30.0 km/h
Registracija: NM-KT-224
Maximalna hitrost: 190.0
Stevilo Vrat: 3
Stevilo Sedezev: 5

Vnesite pospešek našega avta: 40

***   Podatki   ***
Lastnik: Zena
Prestava: 4.
Hitrost: 70.0 km/h
Registracija: NM-KT-224
Maximalna hitrost: 190.0
Stevilo Vrat: 3
Stevilo Sedezev: 5

Vnesite pospešek našega avta: 150

***   Podatki   ***
Lastnik: Zena
Prestava: 5.
Hitrost: 190.0 km/h
Registracija: NM-KT-224
Maximalna hitrost: 190.0
Stevilo Vrat: 3
Stevilo Sedezev: 5

Prevec pritiskate na plin. Predlagam, da koncamo z voznjo.

*/