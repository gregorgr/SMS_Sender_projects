
import java.util.Scanner;

class FibonacijevaStevila{

		private static int vnosStevila(){
				int vnesenoStevilo = 0;
				String stevilo;
				Scanner mojBralnikVnosa = new Scanner(System.in);

				System.out.print("Vnesi število: ");

				// ponavljamo, dokler ni pravilen vnos
				while(vnesenoStevilo==0){

					try{

						 stevilo = mojBralnikVnosa.nextLine();  // Read user input
						 //System.out.println("Vneseno ševilo je: " + stevilo);  // Output user input
						 vnesenoStevilo = Integer.parseInt(stevilo);
						 if(vnesenoStevilo==0){
							 System.out.println("Napačen vnos. Število mora biti naravno in večje od 0.");
							 System.out.print("Prosim, ponovite vnos števila: ");
						 }

					 }catch(Exception e){

						 System.out.println("Napačen vnos. Število mora biti naravno in večje od 0.");
						 System.out.print("Prosim, ponovite vnos števila: ");

					 }
				}
				return vnesenoStevilo;
			}


			public static void main(String[] args){

				int n1=0, n2=1, n3, sestevek = 0;

				// uporabnik vnese poljubno naravno število
				int stevil = vnosStevila();
				int[] tabela = new int[stevil];
				System.out.println("Izračunavam prvih " + stevil + " Fibonaccijevega zaporedja.");  // Output user input

				/// ZAČETEK IZRAČUNAVANJA fiBONACCIJEVEGA ZAPOREDJA
				// prištejemo seštevku, če je število večje od 1

				tabela[0] = n1;
				//System.out.println(n1);

				if(stevil>1){
					//System.out.println(n2);
					tabela[1] = n2;
					sestevek += n2;
				}


				for(int i=2;i<stevil;i++) {
				  n3=n1+n2;
					//System.out.println(i);
					tabela[i] = n3;
					sestevek += n3;
					// če izpisujemo sproti
				  // System.out.println(n3);
				  n1=n2;
				  n2=n3;
				 }
				 System.out.println("Izpisujem števila:");
				 for (int i=0; i< tabela.length; i++){
					  System.out.println("+ " +tabela[i]);
				 }
				 System.out.println("-------------------------");
				 System.out.println("Seštevek prvih "+ stevil+" Fibonaccijevega zaaporedja je: " + sestevek);

      }

}
