/*
*	V razredu izpišemo štiri znake v Unicode standardu.
*/
class UbeznaZaporedja {

    public static void main(String[] args) {

		//
		char znak_A = '\u010d';
		char znak_a = '\u010c';
		char znak_zh = '\u00F1';
		char znak_hash = '\u0023';

		// Izpis
		System.out.println("A = " + znak_A);
		System.out.println("a = " + znak_a);
		System.out.println("ž = " + znak_zh);
		System.out.println("# = " + znak_hash);
   }
}