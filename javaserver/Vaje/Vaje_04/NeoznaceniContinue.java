public class NeoznaceniContinue
{
	public static void main(String[] args)
	{
		String niz = "riba re�e raci rep";
		int stRjev = 0;
		for (int i = 0; i < niz.length(); i++)
		{
			if (niz.charAt(i) != 'r')
				continue;
			stRjev++;
		}
		System.out.println("Na�el " + stRjev + " r-je v nizu.");
	}
}