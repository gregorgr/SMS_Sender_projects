public class OznaceniContinue
{
	public static void main(String[] args)
	{
		String[] tabelaNizov = new String[]{"riba", "res", "re�e", "raci", "rep"};

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
		System.out.println("Na�el " + besedZRji + " besed z \'r\'-ji.");
	}
}