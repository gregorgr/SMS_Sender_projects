/*
 * 	Razred Kabrio je podrazred razreda Avto. Deduje vse lastnosti, ki jih ima avto,
 *	ima pa še dve dodatni.
 */
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


	public void izpisiPodatke()
	{
		super.izpisiPodatke();
		System.out.println("Maksimalna hitrost: " + getMaxHitrost() + ".");	// getMaxHitrost() je metoda definirana v nadrazredu, zato jo lahko klièemo tukaj
		System.out.println("Zadnji sedeži: " + getZadnjiSedezi()); 	// getZadnjiSedezi() je metoda razreda Kabrio
		System.out.println();
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

	/*
	 *	Pozor! V tem razredu nimamo metode main. Ukaz 'java Kabrio' bo zato poiskal in zagnal metodo main v razredu Avto.
	 *	Razredov brez metode main zato ne poganjamo z ukazom java!
	 */
}