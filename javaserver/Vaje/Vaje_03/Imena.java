class Imena {
	public static void main(String[] args) {

		char zacetnica = args[0].charAt(0);

		switch(zacetnica)
		{
			case 'a':
				System.out.println("Anica");
				break;
			case 'b':
				System.out.println("Branka");
				break;
			case 'c':
				System.out.println("Cecilija");
				break;
			case 'd':
				System.out.println("Danica");
				break;
			case 'ž':
				System.out.println("Željka");
				break;
			default:
				System.out.println("Imena ni v imeniku.");
		}
	}
}