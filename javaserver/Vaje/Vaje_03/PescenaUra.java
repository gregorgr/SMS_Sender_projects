class PescenaUra {

    public static void main(String[] args) {

		int n = Integer.parseInt(args[0]);

		if( n % 2 == 0)
		{
			System.out.println("Vrednost atributa je soda. Takšne ure ne znam narisati.");
		}
		else
		{
			int tmpZvezda = n;
			int tmpPresledki = 0;

			for(int i = 0; i < n; i++)
			{
				for(int j=0; j<tmpPresledki; j++)
				{
					System.out.print(" ");
				}

				for(int j=0; j<tmpZvezda; j++)
				{
					System.out.print("*");
				}
				System.out.println("");

				if(i < (n-1) / 2)
				{
					tmpZvezda = tmpZvezda - 2;
				}
				else
				{
					tmpZvezda = tmpZvezda + 2;
				}

				if(i < (n-1) / 2)
				{
					tmpPresledki = tmpPresledki + 1;
				}
				else
				{
					tmpPresledki = tmpPresledki - 1;
				}
			}
		}
    }
}