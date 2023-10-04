// Vklju�itev paketa util
import java.util.*;
import java.io.*;

public class NakupovalniSeznam
{
	ArrayList<String> seznam;

	public NakupovalniSeznam()
	{
		this.seznam = new ArrayList<String>();
	}

	public void dodaj(String izdelek)
	{
		this.seznam.add(izdelek);
	}

	public void odstrani(String izdelek)
	{
		for(int i=0; i<this.seznam.size(); i++)
		{
			if(this.seznam.get(i).equals(izdelek))
			{
				this.seznam.remove(i);
				//break;
			}
		}
	}

	public String izpisi()
	{
		String izpis = "Stvari na seznamu so:\r\n";
		for(int i=0; i<this.seznam.size(); i++)
		{
			izpis += this.seznam.get(i) + "\r\n";
		}
		return izpis;
	}

	public static void main(String[] args) throws IOException
	{
		NakupovalniSeznam sez = new NakupovalniSeznam();
		sez.dodaj("Pi�koti");
		sez.dodaj("�okolada");
		sez.dodaj("Liter mleka");
		sez.dodaj("Dva �ipsa");

		System.out.println(sez.izpisi());
	}
}