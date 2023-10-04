/* 
DN021_FibonaccijevoStevilo:
Napisite program, ki za poljuben n izpise prvih n Fibonaccijevih stevil.
Na koncu izpisite se njihovo vsoto.
*/

class DN021_FibonaccijevoStevilo {
	public static void main (String[] args) {
		
		// poljubno stevilo Fibonaccijevih stevil
		int n=10;
		
		// deklaracija Fibonaccijevih stevil
		int fs[] = new int [n];
		
		// dolocitev prvih dveh Fibonaccijevih stevil
		fs[0]=1;
		fs[1]=1;
		
		// deklaracija in inicializacija vsote n Fibonaccijevih števil
		int vsota = fs[0]+fs[1];
				
		
		// izracun preostalih Fibonaccijevih stevil in njihove vsote
		for (int i=2;i<n;i++) {
			fs[i]=fs[i-1]+fs[i-2];
			vsota+=fs[i];
		}
		
		// izpis Fibonacijevih stevil in njihove vsote
		System.out.println("Prvih "+ n +" Fibonaccijevih stevil je :");
		for (int i=0;i<n;i++) {
			System.out.println("F"+ i +" = "+ fs[i]);
		}
		System.out.println ("Vsota "+ n +" Fibonaccijevih stevil je "+ vsota +".");
		
	}
}

/*
Izpis:
java DN021_FibonaccijevoStevilo
Prvih 10 Fibonaccijevih stevil je :
F0 = 1
F1 = 1
F2 = 2
F3 = 3
F4 = 5
F5 = 8
F6 = 13
F7 = 21
F8 = 34
F9 = 55
Vsota 10 Fibonaccijevih stevil je 143.
*/