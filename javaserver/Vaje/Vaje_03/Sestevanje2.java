class Sestevanje2 {
	public static void main(String[] args) {

		int n = Integer.parseInt(args[0]);
		int vsota = 0;

		// Uporabimo zanko FOR
		for(int i = 1; i <= n; i++)
		{
			vsota += i;
		}
		System.out.println("1. naèin: Vsota števil od 1 do " + n + " je: " + vsota);

		vsota = 0; // Ponastavimo vrednost spremenljivke
		int stevec = 1;
		// Uporabimo zanko WHILE
		while (stevec <= n)
		{
			vsota += stevec;
			stevec++;
		}
		System.out.println("2. naèin: Vsota števil od 1 do " + n + " je: " + vsota);

		vsota = 0; // Ponastavimo vrednost spremenljivke
		stevec = 1;
		// Uporabimo zanko DO - WHILE
		do
		{
			vsota += stevec;
			stevec++;
		} while (stevec <= n);
		System.out.println("3. naèin: Vsota števil od 1 do " + n + " je: " + vsota);
	}
}