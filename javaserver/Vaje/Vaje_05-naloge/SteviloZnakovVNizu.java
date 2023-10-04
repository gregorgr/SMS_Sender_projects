class SteviloZnakovVNizu
{
    public static void main(String[] args)
    {
		// Prvi argument programa je niz
		String niz = args[0];

		// Drugi argument je znak, ki ga preštevamo
		char znak = args[1].charAt(0);

		int stZnakov = steviloZnakovVNizu(niz, znak);
		System.out.println("Število znakov '" + znak + "' v nizu '" + niz + "' je " + stZnakov);
    }

	/* Metoda za dani niz in znak vrne število pojavitev znaka v nizu. */
    public static int steviloZnakovVNizu(String niz, char znak)
    {
		int stZnakov = 0;
		for(int i=0; i<niz.length(); i++)
		{
			if(niz.charAt(i) == znak)
				stZnakov++;
		}
		return stZnakov;
	}
}