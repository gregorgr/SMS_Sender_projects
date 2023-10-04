public class PrestevanjeElementov
{
	public static void main(String[] args)
	{
		int[] tabela = new int[args.length];

		for(int i=0; i< args.length; i++)
		{
			tabela[i] = Integer.parseInt(args[i]);
		}

		int stSodih = 0;
		int stDeliteljev100 = 0;

		for(int i : tabela)
		{
			// �e je �tevilo sodo, ga pri�tej
			if(i % 2 == 0)
			{
				stSodih++;
			}

			// �e je �tevilo delitelj 100, ga pri�tej
			if(i != 0 && 100 % i == 0)
			{
				stDeliteljev100++;
			}
		}

		System.out.println("�tevilo podanih sodih �tevil je " +
		stSodih + ", �tevilo �tevil, ki delijo 100, pa " +
		stDeliteljev100 + ".");
	}
}