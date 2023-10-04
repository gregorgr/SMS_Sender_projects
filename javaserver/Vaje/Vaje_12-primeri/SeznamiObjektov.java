import java.util.*;

public class SeznamiObjektov
{
    public static void main(String[] args)
    {
		// Seznam objektov
		ArrayList<Object> seznam = new ArrayList<Object>();

		// Ustvarimo �tiri objekte razli�nih tipov (njihov nadrazred je Object)
		Avto avto = new Avto();
		String beseda = "Beseda";
		int[] stevila = new int[]{1,2,7,8,8};
		Potnik potnik = new Potnik();

		// Vstavimo jih v seznam objektov
		seznam.add(avto);
		seznam.add(beseda);
		seznam.add(stevila);
		seznam.add(potnik);

		System.out.println("Legenda:");

		System.out.println("*  0 - vsi tipi");
		System.out.println("*  1 - Avto");
		System.out.println("*  2 - Potnik");
	 	System.out.println("*  3 - String");

		for(int i=0; i < 4; i++)
		{
			System.out.println("\n\nIzpisujemo objekte s seznama za tip " + i + ":\n");
			izpisiSeznam(seznam, i);
			System.out.println();
		}
    }

	/*
	 *	Izpišemo objekte s seznama, ki ustrezajo določenemu tipu:
	 *  0 - vsi tipi
	 *  1 - Avto
	 *  2 - Potnik
	 *  3 - String
	 */
    private static void izpisiSeznam(ArrayList<Object> seznam, int tip)
    {
		for(int i=0; i<seznam.size(); i++)
		{
			switch(tip)
			{
				case 1:
					if(seznam.get(i) instanceof Avto)
						System.out.println(seznam.get(i).toString());
					break;
				case 2:
					if(seznam.get(i) instanceof Potnik)
						System.out.println(seznam.get(i).toString());
					break;
				case 3:
					if(seznam.get(i) instanceof String)
						System.out.println(seznam.get(i).toString());
					break;
				case 0:
				default:
					System.out.println(seznam.get(i).toString());
					break;

			}
		}
	}
}
