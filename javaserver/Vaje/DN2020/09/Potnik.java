import java.util.*;
import java.io.*;

public class Potnik
{
	protected String ime;
	protected int starost;
	protected boolean voznik;

	public Potnik()
	{
		// Ne nastavimo nič - lahko pa bi
	}

	public Potnik(int starost, String ime)
	{
		this.starost = starost;
		this.ime = ime;
		this.voznik = false;
	}

	public String getIme()
	{
		return ime;
	}

	public void setIme(String ime)
	{
		this.ime = ime;
	}

	public int getStarost()
	{
		return starost;
	}

	public void setStarost(int starost)
	{
		this.starost = starost;
	}

	public boolean isVoznik()
	{
		return voznik;
	}

	public void setVoznik(boolean voznik)
	{
		this.voznik = voznik;
	}

	// Povozimo metodo toString() nadrazreda Object
	public String toString()
	{
		String str = "Potnik: [Ime = " + ime + "; " + "Starost = " + starost + "; " + "Voznik = " + voznik + "]\r\n";
		return str;
	}

	public static Potnik vnesiPotnika() throws Exception
	{
		BufferedReader vhod = new BufferedReader(new InputStreamReader(System.in));

		Potnik noviPotnik = new Potnik();
		System.out.println("***   Vnos novega potnika:   ***");
		System.out.print(" Vnesi ime: ");
		String niz = vhod.readLine();
		noviPotnik.setIme(niz);

		System.out.print(" Vnesi starost: ");
		int stevilo = Integer.parseInt(vhod.readLine());
		noviPotnik.setStarost(stevilo);

		System.out.print(" Je potnik voznik (da/ne): ");
		niz = vhod.readLine().toLowerCase();
		if (niz.equals("da"))
		{
			noviPotnik.setVoznik(true);
		}
		else
		{
			noviPotnik.setVoznik(false);
		}

		System.out.println("Novi potnik uspesno vnesen!\n");
		return noviPotnik;
	}

	/*
	 *	Metoda zapiše podatke o potniku v poseben niz, iz katerega znamo enolično razbrati, za katerega potnika gre.
	 *  Implementiramo jo kot objektno metodo
	 */
	public String shraniKotNiz()
	{
		String zapis = "*P\r\n";		// Zapišemo kodo "P", ki označuje potnika
		zapis += this.ime + "\r\n";		// Zapišemo ime
		zapis += this.starost + "\r\n";	// Zapišemo starost
		zapis += this.voznik + "\r\n";		// Zapišemo ali je voznik
		zapis += "#\r\n";				// Z '#' označimo konec branja
		return zapis;
	}

	/*
	 *	Metoda iz danega seznama nizov rekonstruira potnika, ki je bil z danimi podatki shranjen.
	 *  Implementiramo jo kot statično metodo
	 */
	public static Potnik preberiIzNiza(ArrayList<String> zapis)
	{
		Potnik potnik = new Potnik();
		try
		{
			// Prvi element v seznamu je ime
			potnik.setIme(zapis.get(0));
			// Drugi element v seznamu je starost
			potnik.setStarost(Integer.parseInt(zapis.get(1)));
			// Tretji element pove ali je voznik
			potnik.setVoznik(zapis.get(2).toLowerCase().equals("true") ? true : false);

			return potnik;
		}
		catch(Exception ex)
		{
			System.out.println("Prišlo je do napake v zapisu!");
			throw ex;
		}
	}
}
