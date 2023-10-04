public class EnakostNizov
{
	public static void main(String[] args)
	{
		String x = "abc";
		String y = "abc";

		if(x.equals(y))
		{
			System.out.println("Niza sta enaka glede 'equals', saj imata enako vsebino.");
		}

		if(x == y)
		{
			System.out.println("Niza sta enaka tudi glede na '==', ker imata konstantno vrednost.");
		}

		y = x + "";
		if(x.equals(vrniNiz(y)))
		{
			System.out.println("Tudi druga dva niza sta enaka glede 'equals', saj imata enako vsebino.");
		}

		if(x == vrniNiz(y))
		{
			System.out.println("Nista pa enaka tudi glede na '==', ker drugi nima konstantne vrednosti, ampak jo dobi, glede na podani parameter 'niz'.");
		}

	}

	private static String vrniNiz(String niz)
	{
		return niz;
	}
}