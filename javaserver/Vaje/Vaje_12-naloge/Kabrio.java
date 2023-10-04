/*
 * 	Razred Kabrio je podrazred razreda Avto. Deduje vse lastnosti, ki jih ima avto,
 *	ima pa še dve dodatni.
 */
import java.io.*;
import java.util.*;

public class Kabrio extends Avto
{
	private boolean strehaGor;
	private boolean zadnjiSedezi;

	public Kabrio()
	{
		this(true, false);	// V tem primeru klièemo konstruktor razreda Kabrio z dvema parametroma
		//super(1, 0, "Neznana");	// V tem primeru klièemo konstruktor nadrazreda, ki dobi tri parametre
		//super(1, 0, "Neznana", 250);
	}

	public Kabrio(boolean stGor, boolean zadnjiSed)
	{
		this.strehaGor = stGor;
		this.zadnjiSedezi = zadnjiSed;
	}

	public Kabrio(String zacRegistracija, int stPotnikov, boolean stGor, boolean zadnjiSed)
	{
		super(zacRegistracija, stPotnikov);
		this.strehaGor = stGor;
		this.zadnjiSedezi = zadnjiSed;
	}

	// Set metodi
	public void setStrehaGor(boolean sg)
	{
		this.strehaGor = sg;
	}

	public void setZadnjiSedezi(boolean zs)
	{
		this.zadnjiSedezi = zs;
	}


	// Get metodi
	public boolean getStrehaGor()
	{
		return this.strehaGor;
	}

	public boolean getZadnjiSedezi()
	{
		return this.zadnjiSedezi;
	}


	/*
	 *	Te metode ne uporabljamo veè, saj smo jo nadomestili z metodo toString.
	 */
	public void izpisiPodatke()
	{
		super.izpisiPodatke();
		System.out.println("Maksimalna hitrost: " + getMaxHitrost() + ".");	// getMaxHitrost() je metoda definirana v nadrazredu, zato jo lahko klièemo tukaj
		System.out.println("Zadnji sedeži: " + getZadnjiSedezi()); 	// getZadnjiSedezi() je metoda razreda Kabrio
		System.out.println();
	}

	public String toString()
	{
		String opis = "";
		opis += super.toString();	// Najprej dodamo opis iz nadrazreda

		// Dodamo še lastnosti, ki ju imamo le v razredu Kabrio
		opis += "Zadnji sedeži: " + this.zadnjiSedezi + "\n";
		opis += "Streha dvignjena: " + this.strehaGor + "\n";

		return opis;  // Na koncu vrnemo opis
	}

	public void pospesi(double pospesek)
	{
		super.pospesi(pospesek);

		if(super.getMaxHitrost() < 130 && super.getMaxHitrost() >= 90)
		{
			super.setPrestava(5);
		}
		else if(super.getMaxHitrost() >= 130)
		{
			super.setPrestava(6);
		}
	}

	public static Kabrio vnesiAvto() throws Exception
	{
		BufferedReader vhod = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("***   Vnos novega kabrioleta:   ***");
		System.out.print(" Vnesi registracijo: ");
		String reg = vhod.readLine();

		System.out.print(" Vnesi število potnikov: ");
		int stevilo = Integer.parseInt(vhod.readLine());

		System.out.print(" Je streha dvignjena: ");
		boolean stGor = false;
		if(vhod.readLine().toLowerCase().equals("da"))
			stGor = true;

		System.out.print(" Ima zadnje sedeže: ");
		boolean zadSed = false;
		if(vhod.readLine().toLowerCase().equals("da"))
			zadSed = true;

		Kabrio novAvto = new Kabrio(reg, stevilo, stGor, zadSed);
		return novAvto;
	}

	public String shraniKotNiz()
	{
		String zapis = "*K\r\n";
		zapis += this.getRegistracija() + "\r\n";	// Zapišemo registracijo
		zapis += this.getMaxHitrost() + "\r\n";			// Zapišemo maksimalno hitrost
		zapis += this.strehaGor + "\r\n";
		zapis += this.zadnjiSedezi + "\r\n";

		for(int i=0; i < this.getPotniki().size(); i++) // Zapišemo še vsakega potnika posebej
		{
			zapis += this.getPotniki().get(i).shraniKotNiz();
		}
		zapis += "##\r\n";					// Oznaèimo konec branja
		return zapis;
	}

	public static Kabrio preberiIzNiza(ArrayList<String> zapis)
	{
		Kabrio avto = new Kabrio();  // Najprej ustvarimo objekt, kateremu bomo nastavili podane lastnosti
		try
		{
			// Prvi element v seznamu je registracija
			avto.setRegistracija(zapis.get(0));
			// Drugi element v seznamu je maksimalna hitrost
			avto.setMaxHitrost(Double.parseDouble(zapis.get(1)));
			avto.setStrehaGor(zapis.get(2).toLowerCase().equals("true") ? true : false);
			avto.setZadnjiSedezi(zapis.get(3).toLowerCase().equals("true") ? true : false);

			// Preostali elementi so potniki
			ArrayList<String> potnikPodatki;
			for(int i=4; i < zapis.size(); i++)
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

					avto.getPotniki().add(potnik);
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

	/*
	 *	Pozor! V tem razredu nimamo metode main. Ukaz 'java Kabrio' bo zato poiskal in zagnal metodo main v razredu Avto.
	 *	Razredov brez metode main zato ne poganjamo z ukazom java!
	 */
}