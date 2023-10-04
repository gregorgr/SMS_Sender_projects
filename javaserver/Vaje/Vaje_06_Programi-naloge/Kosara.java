public class Kosara
{
	// Polja
	private int stRdecihKrompirjev;
	private int stBelihKrompirjev;


	// Konstruktorji
	public Kosara()
	{
		stRdecihKrompirjev = 0;
		stBelihKrompirjev = 0;
	}

	public Kosara(int stBelih, int stRdecih)
	{
		stRdecihKrompirjev = stRdecih;
		stBelihKrompirjev = stBelih;
	}


	// Metode
	public int getSteviloRdecihKrompirjev()
	{
		return stRdecihKrompirjev;
	}

	public int getSteviloBelihKrompirjev()
	{
		return stBelihKrompirjev;
	}

	public void setSteviloBelihKrompirjev(int stBelih)
	{
		stBelihKrompirjev = stBelih;
	}

	public void setSteviloRdecihKrompirjev(int stRdecih)
	{
		stRdecihKrompirjev = stRdecih;
	}

	// Metoda prestavi en krompir iz ko�are druga v trenutno ko�aro
	public void prestaviKrompir(byte barva, Kosara druga)
	{
		if(barva == 0)
		{
			this.stRdecihKrompirjev++;
			druga.stRdecihKrompirjev--;
		}
		else
		{
			this.stBelihKrompirjev++;
			druga.stBelihKrompirjev--;
		}
	}

	public void izpis()
	{
		System.out.println("Ko�ara skupaj vsebuje " + stRdecihKrompirjev +
		" rde�ih in " + stBelihKrompirjev + " belih krompirjev.");
		System.out.println();
	}

	// Izvajanje programa
	public static void main(String[] args)
	{
		// Ustvarimo tri ko�are
		Kosara kos1 = new Kosara(5,0);
		Kosara kos2 = new Kosara(3,12);
		Kosara kos3 = new Kosara(4,10);

		// Izpi�imo njihove za�etne lastnosti
		System.out.println("Ustvarili smo ko�are: ");
		kos1.izpis();
		kos2.izpis();
		kos3.izpis();


		// Prestavi vse rde�e krompirje iz ko�are 2 v ko�aro 1
		while(kos2.stRdecihKrompirjev > 0)
		{
			kos1.prestaviKrompir((byte)0, kos2);
		}
		// Prestavi vse rde�e krompirje iz ko�are 3 v ko�aro 1
		while(kos3.stRdecihKrompirjev > 0)
		{
			kos1.prestaviKrompir((byte)0, kos3);
		}

		// Prestavi vse bele krompirje iz ko�are 1 v ko�aro 3
		while(kos1.stBelihKrompirjev > 0)
		{
			kos3.prestaviKrompir((byte)1, kos1);
		}
		// Prestavi vse bele krompirje iz ko�are 2 v ko�aro 3
		while(kos2.stBelihKrompirjev > 0)
		{
			kos3.prestaviKrompir((byte)1, kos2);
		}

		// Izpi�i koli�ino krompirjev v ko�arah po prestavljanju
		System.out.println("Po prestavljanju vsebujejo: ");
		kos1.izpis();
		kos2.izpis();
		kos3.izpis();
	}
}
