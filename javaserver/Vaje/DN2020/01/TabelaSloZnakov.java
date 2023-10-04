
// update-alternatives --config java


class TabelaSloZnakov{



			public static void main(String[] args){

				char[] sloAbeceda = {	'\u0061', '\u0062', '\u0063',
														'\u010d', '\u0064', '\u0065',
														'\u0066', '\u0067', '\u0068',
														'\u0069', '\u006a', '\u006b',
														'\u006c', '\u006d', '\u006e',
														'\u006f', '\u0070', '\u0072',
														'\u0073', '\u0161', '\u0074',
														'\u0075', '\u0076', '\u007a',
														'\u017e'};

					System.out.println("DN01 Izpis slovenske abecede v treh stolpcih: ");
					// System.out.println("L: " + sloAbeceda.length);
					for(int i = 0;  i < sloAbeceda.length; i++){

						if(i%3 == 2){
							System.out.println(sloAbeceda[i] );
						}else{
							System.out.print(sloAbeceda[i] + "\t");
						}
					}
					System.out.println("");
					//System.out.println("konec");

			}



}
