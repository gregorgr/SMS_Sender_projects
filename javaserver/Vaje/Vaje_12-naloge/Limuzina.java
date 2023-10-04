/*
 * 	Razred Limuzina je podrazred razreda Avto. Deduje vse lastnosti, ki jih ima avto,
 *	ima pa še dve dodatni.
 */
import java.io.*;
import java.util.*;

public class Limuzina extends Avto
{
	private double dolzina;
	private boolean minibar;

	public Limuzina()
	{
		this(8.0, true);	// V tem primeru klièemo konstruktor razreda Kabrio z dvema parametroma
	}

	public Limuzina(double d, boolean mb)
	{
		this.dolzina = d;
		this.minibar = mb;
	}

	public Limuzina(String zacRegistracija, int stPotnikov, double d, boolean mb)
	{
		super(zacRegistracija, stPotnikov);
		this.dolzina = d;
		this.minibar = mb;
	}


	// Set metodi
	public void setDolzina(double d)
	{
		this.dolzina = d;
	}

	public void setMinibar(boolean mb)
	{
		this.minibar = mb;
	}


	// Get metodi
	public double getDolzina()
	{
		return this.dolzina;
	}

	public boolean getMinibar()
	{
		return this.minibar;
	}

	public void izpisiPodatke()
	{
		super.izpisiPodatke();
		System.out.println("Dolžina: " + this.dolzina + " m.");
		System.out.println("Minibar: " + this.minibar);
		System.out.println();
	}

	public String toString()
	{
		String opis = "";
		opis += super.toString();	// Najprej dodamo opis iz nadrazreda

		// Dodamo še lastnosti, ki ju imamo le v razredu Limuzina
		opis += "Dolžina: " + this.dolzina  + "\n";
		opis += "Minibar: " + this.minibar + "\n";

		return opis;  // Na koncu vrnemo opis
	}

	public static Limuzina vnesiAvto() throws Exception
	{
		BufferedReader vhod = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("***   Vnos nove limuzine:   ***");
		System.out.print(" Vnesi registracijo: ");
		String reg = vhod.readLine();

		System.out.print(" Vnesi število potnikov: ");
		int stevilo = Integer.parseInt(vhod.readLine());

		System.out.print(" Dolžina: ");
		double dolz = Double.parseDouble(vhod.readLine());

		System.out.print(" Ima minibar: ");
		boolean mb = false;
		if(vhod.readLine().toLowerCase().equals("da"))
			mb = true;

		Limuzina novAvto = new Limuzina(reg, stevilo, dolz, mb);
		return novAvto;
	}

	public String shraniKotNiz()
	{
		String zapis = "*L\r\n";
		zapis += this.getRegistracija() + "\r\n";	// Zapišemo registracijo
		zapis += this.getMaxHitrost() + "\r\n";			// Zapišemo maksimalno hitrost
		zapis += this.dolzina + "\r\n";
		zapis += this.minibar + "\r\n";

		for(int i=0; i < this.getPotniki().size(); i++) // Zapišemo še vsakega potnika posebej
		{
			zapis += this.getPotniki().get(i).shraniKotNiz();
		}
		zapis += "##\r\n";					// Oznaèimo konec branja
		return zapis;
	}

	public static Limuzina preberiIzNiza(ArrayList<String> zapis)
	{
		Limuzina avto = new Limuzina();  // Najprej ustvarimo objekt, kateremu bomo nastavili podane lastnosti
		try
		{
			// Prvi element v seznamu je registracija
			avto.setRegistracija(zapis.get(0));
			// Drugi element v seznamu je maksimalna hitrost
			avto.setMaxHitrost(Double.parseDouble(zapis.get(1)));
			avto.setDolzina(Double.parseDouble(zapis.get(2)));
			avto.setMinibar(zapis.get(3).toLowerCase().equals("true") ? true : false);

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
}