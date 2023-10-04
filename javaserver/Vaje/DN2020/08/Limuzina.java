/*
 * 	Razred Limuzina je podrazred razreda Avto. Deduje vse lastnosti, ki jih ima avto,
 *	ima pa še dve dodatni.
 */
public class Limuzina extends Avto
{
	private double dolzina;
	private boolean minibar;

	public Limuzina()
	{
		this(8.0, true);	// V tem primeru kličemo konstruktor razreda Kabrio z dvema parametroma
	}

	public Limuzina(double d, boolean mb)
	{
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
}
