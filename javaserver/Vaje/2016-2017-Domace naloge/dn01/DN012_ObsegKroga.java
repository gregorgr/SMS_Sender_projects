/*
** Domača naloga 01.2:
** Izracunaj obseg kroga s polmerom enakim številu dni v mesecu, v katerem si se rodil
*/

class DN012_ObsegKroga {
	public static void main (String[] args) {
		
		// Deklaracija spremenljivk in prireditev vrednosti
		byte polmer = 31;
		double obseg = 2 * Math.PI * polmer;
		
		// Izpis rezultata
		System.out.println("Obseg kroga s polmerom "+polmer+" je "+obseg+".");
	}
}

/*
Izpis
java DN012_ObsegKroga
Obseg kroga s polmerom 31 je 194.77874452256717.
*/