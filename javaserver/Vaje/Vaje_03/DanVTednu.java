class DanVTednu {
	public static void main(String[] args) {

		int n = Integer.parseInt(args[0]);

		switch(n)
		{
			case 1:
				System.out.println("Ponedeljek");
				break;
			case 2:
				System.out.println("Torek");
				break;
			case 3:
				System.out.println("Sreda");
				break;
			case 4:
				System.out.println("Èetrtek");
				break;
			case 5:
				System.out.println("Petek");
				break;
			case 6:
				System.out.println("Sobota");
				break;
			case 7:
				System.out.println("Nedelja");
				break;
			default:
				System.out.println("Tako dolgega tedna pa nimamo.");
		}
	}
}