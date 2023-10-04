/*
 *	Razred Avto opisuje nekaj lastnosti in funkcionalnosti avtomobila
 */
import java.io.*;
import java.util.*;

public class Avto
{
	// Polja
	private int prestava;
	private double hitrost;
	private String registracija;
	private double maxHitrost;
	private ArrayList<Potnik> potniki;


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
		this.potniki = new ArrayList<Potnik>();
	}

	public Avto(int zacPrestava, double zacHitrost, String zacRegistracija)
	{
		this.prestava = zacPrestava;
		this.hitrost = zacHitrost;
		this.registracija = zacRegistracija;
		this.maxHitrost = 180;
		this.potniki = new ArrayList<Potnik>();
	}

	public Avto(String zacRegistracija, int stPotnikov)
	{
		this.prestava = 1;
		this.hitrost = 0.0;
		this.registracija = zacRegistracija;
		this.maxHitrost = 180;

		this.potniki = new ArrayList<Potnik>();

		// Klièemo metodo vnesiPotnika, dokler Avto nima doloèenega števila potnikov
		int stejVoznike = 0;
		for(int i=0; i < stPotnikov; i++)
		{
			try
			{
				Potnik p = Potnik.vnesiPotnika();	// Klièemo statièno metodo vnesiPotnika()
				if(p.isVoznik())
					stejVoznike++;
				else if(stejVoznike == 0 && i == stPotnikov - 1)
					p.setVoznik(true);

				if(stejVoznike > 1)
					p.setVoznik(false);
				this.potniki.add(p);
			}
			catch (Exception e)
			{
				System.out.println("Prišlo je do napake pri vnosu potnika!");
			}
		}
	}

	public Avto(double mh)
	{
		this.maxHitrost = mh;
		this.prestava = 1;
		this.hitrost = 0;
		this.registracija = "Neznana";
		this.potniki = new ArrayList<Potnik>();
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

	public ArrayList<Potnik> getPotniki()
	{
		return this.potniki;
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

	public void setPrestava(int p)
	{
		this.prestava = p;
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

	/*
	 *  Metoda toString() je definirana že v razredu Object, ki je prednik razreda Avto.
	 *  Namenjena je izpisu opisa objekta - vrne String z opisom. Èe želimo opis za avto imeti
	 *  drugaèen od privzetega opisa objekta, jo moramo "povoziti" v razredu Avto.
	 */
	public String toString()
	{
		String opis = "";
		opis += "Avto z registracijo: " + this.getRegistracija() + "\n";
		opis += "Prestava: " + this.getPrestava() + ".\n";
		opis += "Hitrost: " + this.getHitrost() + " km/h\n";
		opis += "Maksimalna hitrost: " + this.getMaxHitrost() + " km/h\n";
		opis += "Potniki: \n";

		for(int i=0; i < this.potniki.size(); i++)
		{
			opis += this.potniki.get(i).toString();
		}

		return opis;  // Na koncu vrnemo opis
	}

	// Povozimo še metodo equals, ki jo uporabljamo za primerjanje dveh objektov
	public boolean equals(Object avto2)
	{
		if(this.registracija == ((Avto)avto2).registracija)  // Parameter, ki ga dobi equals je tipa Object, zato moramo metodi povedati, da ga želimo obravnavati kot Avto
			return true;
		else
			return false;
	}

	public static Avto vnesiAvto() throws Exception
	{
		BufferedReader vhod = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("***   Vnos novega avta:   ***");
		System.out.print(" Vnesi registracijo: ");
		String reg = vhod.readLine();

		System.out.print(" Vnesi število potnikov: ");
		int stevilo = Integer.parseInt(vhod.readLine());

		Avto novAvto = new Avto(reg, stevilo); // Ustvarimo nov objekt s podanima podatkoma
		return novAvto;
	}

	/*
	 *	Metoda zapiše podatke o avtu v poseben niz, iz katerega znamo enolièno razbrati, za kateri avto gre.
	 *  Implementiramo jo kot objektno metodo
	 */
	public String shraniKotNiz()
	{
		String zapis = "*A\r\n";				// Zapišemo kodo "A", ki oznaèuje avto
		zapis += this.registracija + "\r\n";	// Zapišemo registracijo
		zapis += this.maxHitrost + "\r\n";			// Zapišemo maksimalno hitrost

		for(int i=0; i < this.potniki.size(); i++) // Zapišemo še vsakega potnika posebej
		{
			zapis += this.potniki.get(i).shraniKotNiz();
		}
		zapis += "##\r\n";					// Oznaèimo konec branja
		return zapis;
	}

	/*
	 *	Metoda iz danega seznama nizov rekonstruira avto, ki je bil z danimi podatki shranjen.
	 *  Implementiramo jo kot statièno metodo
	 */
	public static Avto preberiIzNiza(ArrayList<String> zapis)
	{
		Avto avto = new Avto();  // Najprej ustvarimo objekt, kateremu bomo nastavili podane lastnosti
		try
		{
			// Prvi element v seznamu je registracija
			avto.setRegistracija(zapis.get(0));
			// Drugi element v seznamu je maksimalna hitrost
			avto.setMaxHitrost(Double.parseDouble(zapis.get(1)));

			// Preostali elementi so potniki
			ArrayList<String> potnikPodatki;
			for(int i=2; i < zapis.size(); i++)
			{
				if(zapis.get(i).trim().equals("*P"))	// Èe vrstica vsebuje *P, imamo zapis o potniku
				{
					potnikPodatki = new ArrayList<String>();	// Pripravimo nov seznam, v katerega bomo dodajali podatke o trenutnem potniku
					i++;
					while(!zapis.get(i).trim().equals("#"))	// Dokler se zapis o potniku ne konèa (dokler se ne pojavi #), dodajamo podatke v seznam
					{
						potnikPodatki.add(zapis.get(i));
						i++;
					}
					Potnik potnik = Potnik.preberiIzNiza(potnikPodatki);

					avto.potniki.add(potnik);
				}
			}
			return avto;
		}
		catch(Exception ex)
		{
			System.out.println("Prišlo je do napake v zapisu!");
			throw ex;
		}
	}
}