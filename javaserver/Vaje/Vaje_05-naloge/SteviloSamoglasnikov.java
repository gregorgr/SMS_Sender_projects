import java.io.*;

public class SteviloSamoglasnikov
{

	public static void main(String[] args) throws Exception
	{
		String ime = "Besedilo.txt";
		FileReader fr = new FileReader(ime);
		BufferedReader dat = new BufferedReader(fr);

		int stSamoglasnikov = 0;
		while(dat.ready()) {
			String vrstica = dat.readLine();
			vrstica = vrstica.toLowerCase();
			stSamoglasnikov += steviloZnakovVNizu(vrstica, 'a');
			stSamoglasnikov += steviloZnakovVNizu(vrstica, 'e');
			stSamoglasnikov += steviloZnakovVNizu(vrstica, 'i');
			stSamoglasnikov += steviloZnakovVNizu(vrstica, 'o');
			stSamoglasnikov += steviloZnakovVNizu(vrstica, 'u');
		}
		dat.close();

		System.out.println("Število samoglasnikov v datoteki je " + stSamoglasnikov + ".");
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