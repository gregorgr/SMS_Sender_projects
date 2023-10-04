import java.io.*;

class SteviloZnakovVNizu2
{
    public static void main(String[] args) throws Exception
    {
		InputStreamReader vt = new InputStreamReader(System.in);
		BufferedReader vhod = new BufferedReader(vt);

		System.out.print("Vnesi niz: ");
		String niz = vhod.readLine();

		System.out.print("Vnesi znak: ");
		char znak = vhod.readLine().charAt(0);

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