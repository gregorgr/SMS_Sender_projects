/*
 *    V tem razredu pripravimo na�rt za opis posameznega primerka �loveka.
 */
public class Clovek
{
	private double teza;  // Vsakemu �loveku bomo pripisali lastnost te�a
	private int starost;  // Starost
	private String barvaLas;  // Barvo las
	private double visina;

	public Clovek()	// Konstruktor (prazen), ki ustvari nov primerek �loveka in mu priredi privzete vrednosti za njegove lastnosti
	{
		this.teza = 80.0;
		this.starost = 20;
		this.barvaLas = "blond";
	}

	public Clovek(double t, int s, String bl, double v) // �e en konstruktor, ki pa dobi parametre za vrednosti lastnosti �loveka, ki ga ustvari.
	{
		this.teza = t;
		this.starost = s;
		this.barvaLas = bl;
		this.visina = v;
	}

	public void izpisi()	// Metoda, ki na zaslon izpi�e lastnosti objekta �lovek, ki jo pokli�e.
	{
		System.out.println("Lastnosti objekta, ki me je poklical, so naslednje: ");
		System.out.println("***********************");
		System.out.println("Te�a: " + this.teza);
		System.out.println("Starost: " + this.starost);
		System.out.println("Barva las: " + this.barvaLas);
		System.out.println("Vi�ina: " + this.visina);
		System.out.println("***********************");
		System.out.println();
	}

	public void shujsaj(double x) // Metoda, ki �loveku zmanj�a te�o za x
	{
		this.teza = this.teza - x;
	}

	public void zrasti(double x) // Metoda, ki �loveku zmanj�a te�o za x
	{
		this.visina = this.visina + x;
	}


	public static void main(String[] args) // Metodo main za potrebe testiranja ustvarimo kar v tem razredu.
	{
		// Ustvarimo nov objekt �lovek s praznim konstruktorjem:
		Clovek borut = new Clovek();

		// Izpi�imo lastnosti objekta borut:
		borut.izpisi();


		// Ustvarimo nov objekt �lovek in mu v naprej dolo�imo lastnosti:
		Clovek terezija = new Clovek(60.0, 23, "rde�a", 170);

		// Izpi�imo lastnosti objekta terezija:
		terezija.izpisi();

		// Uka�imo tereziji naj shuj�a za 3 kg:
		terezija.shujsaj(3);

		terezija.zrasti(5);

		// �e enkrat izpi�imo lastnosti terezije:
		terezija.izpisi();
	}
}