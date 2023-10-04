import java.io.*;

public class Potnik
{
	private String ime;
	private int starost;
	private boolean voznik;

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
	@Override
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
}
