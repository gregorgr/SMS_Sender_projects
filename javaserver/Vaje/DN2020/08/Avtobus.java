/*
 * 	Razred Avtobus je podrazred razreda Avto. Deduje vse lastnosti, ki jih ima avto,
 *	ima pa še dve dodatni.
 */
public class Avtobus extends Avto
{
	private int stSedezev;
	private int stStojisc;

	public Avtobus()
	{
		this(40, 20);	// V tem primeru kličemo konstruktor razreda Kabrio z dvema parametroma
	}

	public Avtobus(int sed, int sto)
	{
		this.stSedezev = sed;
		this.stStojisc = sto;
	}


	// Set metodi
	public void setStSedezev(int s)
	{
		this.stSedezev = s;
	}

	public void setStStojisc(int s)
	{
		this.stStojisc = s;
	}


	// Get metodi
	public int getStSedezev()
	{
		return this.stSedezev;
	}

	public int getStStojisc()
	{
		return this.stStojisc;
	}


	public void izpisiPodatke()
	{
		super.izpisiPodatke();
		System.out.println("Sedišč: " + this.stSedezev);
		System.out.println("Stojišč: " + this.stStojisc);
		System.out.println();
	}
}
