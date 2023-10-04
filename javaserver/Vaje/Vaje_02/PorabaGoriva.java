class PorabaGoriva {
	public static void main(String[] args) {

		String[] mesta = new String[]{"Ljubljana", "Celje", "Krško", "Lepše"};
		double[] razdalja = new double[]{80, 101, 30, 2};
		double poraba = 5.8 / 100; // 0.058 litra na kilometer

		double kmSkupaj = 0, gorivoSkupaj = 0;

		for(int i = 0; i < mesta.length; i++) {
			double porabaDanes = razdalja[i] * poraba;
			System.out.println((i+1) + ". dan: Odpeljal sem se v " + mesta[i] + ", prevozil " + razdalja[i] + " km in porabil " + porabaDanes + " litrov goriva." );
			kmSkupaj = 	kmSkupaj + razdalja[i];
			gorivoSkupaj = gorivoSkupaj + porabaDanes;
		}

		System.out.println("Prevozil sem " + kmSkupaj + " km in porabil " + gorivoSkupaj + " litrov goriva.");
	}
}