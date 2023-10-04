

public class NavadniContinue {

	public static void main(String[] args) {
		String niz = "riba reže raci rep";
		int stRjev = 0;
		for (int i = 0; i < niz.length(); i++) {
			System.out.println("i =" + i + "  " + niz.charAt(i) + "  "+ stRjev);
			if (niz.charAt(i) != 'r'){
				System.out.println("continue");
				continue;
			}
				//
				//

			//}

			stRjev++;
			System.out.println("2. Se izvede -" + stRjev);
		}
		System.out.println("Našel " + stRjev +
		" r-je v nizu.");
	}
}
