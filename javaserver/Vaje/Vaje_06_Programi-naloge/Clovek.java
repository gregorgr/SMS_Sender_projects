/*
 *    V tem razredu pripravimo naèrt za opis posameznega primerka èloveka.
 */
public class Clovek
{
	private double teza;  // Vsakemu èloveku bomo pripisali lastnost teža
	private int starost;  // Starost
	private String barvaLas;  // Barvo las
	private double visina;

	public Clovek()	// Konstruktor (prazen), ki ustvari nov primerek èloveka in mu priredi privzete vrednosti za njegove lastnosti
	{
		this.teza = 80.0;
		this.starost = 20;
		this.barvaLas = "blond";
	}

	public Clovek(double t, int s, String bl, double v) // Še en konstruktor, ki pa dobi parametre za vrednosti lastnosti èloveka, ki ga ustvari.
	{
		this.teza = t;
		this.starost = s;
		this.barvaLas = bl;
		this.visina = v;
	}

	public void izpisi()	// Metoda, ki na zaslon izpiše lastnosti objekta èlovek, ki jo poklièe.
	{
		System.out.println("Lastnosti objekta, ki me je poklical, so naslednje: ");
		System.out.println("***********************");
		System.out.println("Teža: " + this.teza);
		System.out.println("Starost: " + this.starost);
		System.out.println("Barva las: " + this.barvaLas);
		System.out.println("Višina: " + this.visina);
		System.out.println("***********************");
		System.out.println();
	}

	public void shujsaj(double x) // Metoda, ki èloveku zmanjša težo za x
	{
		this.teza = this.teza - x;
	}

	public void zrasti(double x) // Metoda, ki èloveku zmanjša težo za x
	{
		this.visina = this.visina + x;
	}


	public static void main(String[] args) // Metodo main za potrebe testiranja ustvarimo kar v tem razredu.
	{
		// Ustvarimo nov objekt èlovek s praznim konstruktorjem:
		Clovek borut = new Clovek();

		// Izpišimo lastnosti objekta borut:
		borut.izpisi();


		// Ustvarimo nov objekt èlovek in mu v naprej doloèimo lastnosti:
		Clovek terezija = new Clovek(60.0, 23, "rdeèa", 170);

		// Izpišimo lastnosti objekta terezija:
		terezija.izpisi();

		// Ukažimo tereziji naj shujša za 3 kg:
		terezija.shujsaj(3);

		terezija.zrasti(5);

		// Še enkrat izpišimo lastnosti terezije:
		terezija.izpisi();
	}
}