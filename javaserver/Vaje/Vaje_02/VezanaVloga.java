class VezanaVloga {

    public static void main(String[] args) {

		double start = 1000;
		double[] zneski = new double[13];

		zneski[0] = start;
		for(int i = 1; i < zneski.length; i++)
		{
			zneski[i] = zneski[i-1] * 1.03;
		}

		System.out.println("Zneski po mesecih:");
		for(int i = 0; i < zneski.length; i++)
		{
			System.out.println(i + ". mesec: " + zneski[i]);
		}
    }
}