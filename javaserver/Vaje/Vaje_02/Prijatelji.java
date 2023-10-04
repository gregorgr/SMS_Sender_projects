class Prijatelji {

    public static void main(String[] args) {

		String[] prijatelji = new String[]{"Alenka", "Bine", "Cene", "David", "Eneja"};
		int[] stevilo = new int[]{4,23,2,54,43};

		for(int i = 0; i < prijatelji.length; i++)
		{
			System.out.println("Prijatelj " + prijatelji[i] + " ima " + stevilo[i] + " prijateljev.");
		}
    }
}