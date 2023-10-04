public class StetjeAdoZ
{
	public static void main(String[] args)
	{
		String besedilo = "Ob zmagi klerikalnega politi�nega tabora se va�ko u�iteljstvo odpove svojim liberalnim nazorom. " +
						  "Samo u�itelj Jerman, v predvolilnem boju indiferenten, se ne pusti podrediti in pride tako v konflikt " +
						  "z materjo in �upnikom. Mati, ki jo ljubi, zahteva od njega, naj ne zataji boga, lastno prepri�anje pa " +
						  "ga postavlja na stran socialdemokrata, kova�a Kalandra. Na agitacijskem ve�eru ga ljudstvo skoraj lin�a " +
						  "in �upnik ga sklene premestiti na oddaljeno Goli�avo. Na predve�er odhoda se prideta od Jermana poslovit " +
						  "vanj zaljubljena Lojzka in nasprotni, pa vendar simpati�ni Hvastja.";

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