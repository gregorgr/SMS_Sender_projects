/*
8. domača naloga

Najprej razredu Avto
-
dodajte polje za shranjevanje številke parkirnega prostora.

- Nato v program UporabniskiVmesnik
- dodajte funkcionalnost za odstranitev avta s parkirišča. Avto
odstranite tako, da ga odstranite s seznama avtomobili
glede na njegovo številko parkirnega prostora.

*/

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

	// TODO:
	// DN: dodajte polje za shranjevanje številke parkirnega prostora.
	private int parkirniProstor;

	// Konstruktorji:
	// Imajo enako ime kot razred,
	// izvedejo se ob uporabi ključne besede new.
	// Uporabljamo jih za začetne nastavitve stanj objektov
	public Avto()
	{
		this.parkirniProstor = 0;
		this.prestava = 1;
		this.hitrost = 0;
		this.registracija = "Neznana";
		this.maxHitrost = 180;
		this.potniki = new ArrayList<Potnik>();
	}

	public Avto(int zacPrestava, double zacHitrost, String zacRegistracija)
	{
		this.parkirniProstor = 0;
		this.prestava = zacPrestava;
		this.hitrost = zacHitrost;
		this.registracija = zacRegistracija;
		this.maxHitrost = 180;
		this.potniki = new ArrayList<Potnik>();
	}

	public Avto(String zacRegistracija, int stPotnikov)
	{
		this.parkirniProstor = 0;
		this.prestava = 1;
		this.hitrost = 0.0;
		this.registracija = zacRegistracija;
		this.maxHitrost = 180;

		this.potniki = new ArrayList<Potnik>();
		// Kli�emo metodo vnesiPotnika, dokler Avto nima določenega števila potnikov
		for(int i=0; i < stPotnikov; i++)
		{
			try
			{
				Potnik p = Potnik.vnesiPotnika();	// Kličemo statično metodo vnesiPotnika()
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
		this.parkirniProstor = 0;
		this.maxHitrost = mh;
		this.prestava = 1;
		this.hitrost = 0;
		this.registracija = "Neznana";
		this.potniki = new ArrayList<Potnik>();
	}


	// Metode

	// Metode tipa get nam vrnejo vrednosti polj
	/// dodano, vrne št parkirnega prostora
	public int getParkirniProstor(){
		return this.parkirniProstor;
	}

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

	public void setPrestava(int p)
	{
		this.prestava = p;
	}

	// shrani stevilko parkirnega prostora
	public void setparkirniProstor(int stevilkaParkirnegaProstora){

		this.parkirniProstor = stevilkaParkirnegaProstora;
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
	 *  Namenjena je izpisu opisa objekta - vrne String z opisom. če želimo opis za avto imeti
	 *  drugačen od privzetega opisa objekta, jo moramo "povoziti" v razredu Avto.
	 */
	@Override
	public String toString()
	{
		String opis = "";
		String parkiran = this.parkirniProstor > 0 ? "je parkiran na parkirno mesto " + this.parkirniProstor : "ni parkiran.";
		opis += "Avto z registracijo: " + this.getRegistracija() + "\n";
		opis += "Prestava: " + this.getPrestava() + ".\n";
		opis += "Hitrost: " + this.getHitrost() + " km/h\n";
		opis += "Maksimalna hitrost: " + this.getMaxHitrost() + " km/h\n";
		opis += "Avto " + parkiran + "\n";
		opis += "Potniki: \n";

		for(int i=0; i < this.potniki.size(); i++)
		{
			opis += this.potniki.get(i).toString();
		}

		return opis;  // Na koncu vrnemo opis
	}

	// Povozimo še metodo equals, ki jo uporabljamo za primerjanje dveh objektov
	@Override
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

	// Izvajanje programa
	public static void main(String[] args) throws IOException
	{
		// Naredimo objekt tipa Kabrio
		Kabrio ferrari = new Kabrio();
		ferrari.setMaxHitrost(250);
		ferrari.pospesi(210);
		System.out.println(ferrari.toString());	// Uporabimo metodo toString() namesto izpisiPodatke

		Avtobus bus = new Avtobus();
		bus.izpisiPodatke();

		Limuzina limo = new Limuzina();
		limo.izpisiPodatke();

		// Preverimo, če je bus enak kot limo
		System.out.println("Sta limo in bus enaka: " + limo.equals(bus));

		// Kaj pa če zamenjamo registracijo?
		limo.setRegistracija("xy");
		System.out.println("Sta limo in bus enaka: " + limo.equals(bus));
	}
}
