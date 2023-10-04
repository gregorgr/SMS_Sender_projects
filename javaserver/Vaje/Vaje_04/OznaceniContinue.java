public class OznaceniContinue
{
	public static void main(String[] args)
	{
		String[] tabelaNizov = new String[]{"riba", "res", "reže", "raci", "rep"};

		int besedZRji = 0;
		zunanja:
		for(int i=0; i<tabelaNizov.length; i++)
		{
			for (int j = 0; j < tabelaNizov[i].length(); j++)
			{
				if (tabelaNizov[i].charAt(j) == 'r')
				{
					besedZRji++;
					continue zunanja;
				}
			}
		}
		System.out.println("Našel " + besedZRji + " besed z \'r\'-ji.");
	}
}