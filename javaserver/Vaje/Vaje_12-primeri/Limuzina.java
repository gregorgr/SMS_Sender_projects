/*
 * 	Razred Limuzina je podrazred razreda Avto. Deduje vse lastnosti, ki jih ima avto,
 *	ima pa še dve dodatni.
 */
import java.io.*;

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
}