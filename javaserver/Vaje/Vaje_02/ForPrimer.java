class ForPrimer {

    public static void main(String[] args) {

		int vsota = 0;

		for(int i = 1; i <= 10; i = i + 1)
		{
			vsota = vsota + i;
		}
		System.out.println("Vsota prvih desetih naravnih števil je " + vsota + ".");
    }
}