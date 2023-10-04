class Ocene {

    public static void main(String[] args) {

		String[] predmeti = new String[]{"Matematika", "Programiranje", "Informatika"};
		int[] ocene = new int[]{10,10,6};

		System.out.println("Sprièevalo\n");
		double vsota = 0;
		for(int i = 0; i < predmeti.length; i++)
		{
			vsota = vsota + ocene[i];
			System.out.println(predmeti[i] + ": " + ocene[i]);
		}
		double povprecje = vsota / ocene.length;
		System.out.println("Povpreèje: " + povprecje);
    }
}