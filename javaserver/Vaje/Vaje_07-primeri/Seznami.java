// Vkljuèitev paketa util
import java.util.*;
import java.io.*;

public class Seznami
{
	public static void main(String[] args) throws IOException
	{
		// Definicija spremenljivke tipa "seznam celih števil"
		ArrayList<Integer> prastevila;

		// Inicializacija seznama celih števil
		prastevila = new ArrayList<Integer>();

		int n = 100;

		// Iskanje praštevil
		zunanja:
		for(int i=2; i<n; i++)
		{
			for(int j=2; j <= Math.sqrt(i); j++)
			{
				if( i % j == 0)
					continue zunanja;
			}

			// Dodajanje elementa v seznam
			prastevila.add(i);
		}

		// Izpis
		System.out.print("Praštevila med 2 in " + n + " so: ");

		// Za pridobitev dolžine seznama uporabimo metodo size.
		for(int i = 0; i < prastevila.size(); i++)
		{
			System.out.print(prastevila.get(i));	// Z metodo get pridobimo vrednost na danem indeksu
			if(i < prastevila.size() - 1)
				System.out.print(", ");
		}

		System.out.print("\n\nOdstranimo prve tri elemente in dobimo:\n");

		// Trikrat odstranimo prvi "element"
		prastevila.remove(0);
		prastevila.remove(0);
		prastevila.remove(0);

		// Izpis
		System.out.print("Praštevila med 2 in " + n + " brez prvih treh so: ");
		for(int i = 0; i < prastevila.size(); i++)
		{
			System.out.print(prastevila.get(i));
			if(i < prastevila.size() - 1)
				System.out.print(", ");
		}
	}
}