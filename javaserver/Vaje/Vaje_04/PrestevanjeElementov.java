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
			// Èe je število sodo, ga prištej
			if(i % 2 == 0)
			{
				stSodih++;
			}

			// Èe je število delitelj 100, ga prištej
			if(i != 0 && 100 % i == 0)
			{
				stDeliteljev100++;
			}
		}

		System.out.println("Število podanih sodih števil je " +
		stSodih + ", število števil, ki delijo 100, pa " +
		stDeliteljev100 + ".");
	}
}