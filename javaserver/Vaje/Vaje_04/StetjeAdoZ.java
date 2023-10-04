public class StetjeAdoZ
{
	public static void main(String[] args)
	{
		String besedilo = "Ob zmagi klerikalnega politiènega tabora se vaško uèiteljstvo odpove svojim liberalnim nazorom. " +
						  "Samo uèitelj Jerman, v predvolilnem boju indiferenten, se ne pusti podrediti in pride tako v konflikt " +
						  "z materjo in župnikom. Mati, ki jo ljubi, zahteva od njega, naj ne zataji boga, lastno preprièanje pa " +
						  "ga postavlja na stran socialdemokrata, kovaèa Kalandra. Na agitacijskem veèeru ga ljudstvo skoraj linèa " +
						  "in župnik ga sklene premestiti na oddaljeno Golièavo. Na predveèer odhoda se prideta od Jermana poslovit " +
						  "vanj zaljubljena Lojzka in nasprotni, pa vendar simpatièni Hvastja.";

		int stZnakov = 0;

		for(int i=0; i<besedilo.length(); i++)
		{
			if(besedilo.charAt(i) == 'z' || besedilo.charAt(i) == 'Z')
				break;
			if(besedilo.charAt(i) != 'a' && besedilo.charAt(i) != 'A')
				continue;
			stZnakov++;
		}

		System.out.println("Do prvega 'z' je v besedilu " + stZnakov + " 'a'-jev.");
	}
}