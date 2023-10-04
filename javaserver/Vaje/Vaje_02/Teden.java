class Teden {

    public static void main(String[] args) {

		String[] teden = new String[]{"ponedeljek", "torek", "sreda", "èetrtek", "petek", "sobota", "nedelja"};

		// Naèin 1
		System.out.println(teden[0]);
		System.out.println(teden[1]);
		System.out.println(teden[2]);
		System.out.println(teden[3]);
		System.out.println(teden[4]);
		System.out.println(teden[5]);
		System.out.println(teden[6]);

		// Naèin 2
		for(int i = 0; i < teden.length; i++)
		{
			System.out.println(teden[i]);
		}
    }
}