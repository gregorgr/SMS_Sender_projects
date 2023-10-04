class Vreme {
	public static void main(String[] args) {

		int n = Integer.parseInt(args[0]);

		if(n < 1)
		{
			System.out.println("Obleci bundo!");
		}
		else if(n < 10)
		{
			System.out.println("Obleci jakno!");
		}
		else if(n < 15)
		{
			System.out.println("Obleci majico z dolgimi rokavi!");
		}
		else
		{
			System.out.println("Obleci majico s kratkimi rokavi!");
		}
	}
}