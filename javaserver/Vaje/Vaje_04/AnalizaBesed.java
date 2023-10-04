class AnalizaBesed
{
	public static void main(String[] args)
	{
		zunanja:
		for(String beseda : args)
		{
			System.out.println("Analiza besede " + beseda + ":");
			System.out.println("dolžina: " + beseda.length());
			System.out.println("zadnji znak: " + beseda.charAt(beseda.length() - 1));
			System.out.print("pod-beseda: ");
			for(int i=0; i<beseda.length(); i++)
			{
				if(beseda.charAt(i) == 'z' || beseda.charAt(i) == 'Z')
				{
					System.out.println("...prekinjam...");
					break zunanja;
				}

				if(beseda.charAt(i) != 'a' && beseda.charAt(i) != 'A')
				{
					System.out.print(beseda.charAt(i));
				}
				else
					break;
			}
			System.out.println("\n");
		}
	}
}