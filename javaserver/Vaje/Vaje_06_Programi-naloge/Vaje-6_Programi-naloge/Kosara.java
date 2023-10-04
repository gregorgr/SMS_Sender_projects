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

	// Metoda prestavi en krompir iz košare druga v trenutno košaro
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
		System.out.println("Košara skupaj vsebuje " + stRdecihKrompirjev +
		" rdeèih in " + stBelihKrompirjev + " belih krompirjev.");
		System.out.println();
	}

	// Izvajanje programa
	public static void main(String[] args)
	{
		// Ustvarimo tri košare
		Kosara kos1 = new Kosara(5,0);
		Kosara kos2 = new Kosara(3,12);
		Kosara kos3 = new Kosara(4,10);

		// Izpišimo njihove zaèetne lastnosti
		System.out.println("Ustvarili smo košare: ");
		kos1.izpis();
		kos2.izpis();
		kos3.izpis();


		// Prestavi vse rdeèe krompirje iz košare 2 v košaro 1
		while(kos2.stRdecihKrompirjev > 0)
		{
			kos1.prestaviKrompir((byte)0, kos2);
		}
		// Prestavi vse rdeèe krompirje iz košare 3 v košaro 1
		while(kos3.stRdecihKrompirjev > 0)
		{
			kos1.prestaviKrompir((byte)0, kos3);
		}

		// Prestavi vse bele krompirje iz košare 1 v košaro 3
		while(kos1.stBelihKrompirjev > 0)
		{
			kos3.prestaviKrompir((byte)1, kos1);
		}
		// Prestavi vse bele krompirje iz košare 2 v košaro 3
		while(kos2.stBelihKrompirjev > 0)
		{
			kos3.prestaviKrompir((byte)1, kos2);
		}

		// Izpiši kolièino krompirjev v košarah po prestavljanju
		System.out.println("Po prestavljanju vsebujejo: ");
		kos1.izpis();
		kos2.izpis();
		kos3.izpis();
	}
}
