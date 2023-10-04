class Urejanje
{
	public static void main(String[] args)
	{
		int[] stevila = new int[args.length];
		int[][] ureditev = new int[args.length][];

		System.out.print("A = [");
		for(int i=0; i<args.length; i++)
		{
			stevila[i] = Integer.parseInt(args[i]);
			ureditev[i] = new int[2];

			if(i==0)
			{
				System.out.print(stevila[i]);
			}
			else
			{
				System.out.print(", " + stevila[i]);
			}
		}
		System.out.println("]");
		System.out.println();

		for(int i = 0; i<stevila.length; i++)
		{
			int max = 0;
			int maxIndex = -1;
			for(int j = 0; j < stevila.length; j++)
			{
				if(max < stevila[j])
				{
					max = stevila[j];
					maxIndex = j;
				}
			}
			ureditev[i][0] = stevila[maxIndex];
			ureditev[i][1] = maxIndex;
			stevila[maxIndex] = 0;
		}

		System.out.print("B = [");
		for(int i = 0; i<stevila.length; i++)
		{
			if(i==0)
			{
				System.out.print("[" + ureditev[i][0] + ":" + ureditev[i][1] + "]");
			}
			else
			{
				System.out.print(", [" + ureditev[i][0] + ":" + ureditev[i][1] + "]");
			}
		}
		System.out.println("]");
	}
}