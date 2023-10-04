class OznaceniBreak
{
	public static void main(String[] args)
	{
		int[] tabela1 = new int[]{1,2,3,4,5};
		int[] tabela2 = new int[]{9,8,7,6,5};

		zunanja:
		for(int i : tabela1)
		{
			for(int j : tabela2)
			{
				if(j < 9)
				{
					System.out.println("Elementa sta: " + i + " in " + j);
					break zunanja;
				}
			}
		}
	}
}

