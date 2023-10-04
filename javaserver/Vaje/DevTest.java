
import java.math.RoundingMode;
import java.text.DecimalFormat;


class ObsegKroga{


			private static DecimalFormat df2 = new DecimalFormat("#.###");

			public static void main(String[] args){
					System.out.println("DN02 Izračun obsega s pomerom meseca Marca:");

					int steviloDni = 31;
					int polmer = steviloDni;

					double obsegKroga = 2 * Math.PI * polmer;

					System.out.println("Obseg kroga s polmerom "+ polmer +" je "+obsegKroga +".");
					System.out.println("Zaokroženo na tri decimalke je obseg " +  df2.format(obsegKroga));
			}
}
