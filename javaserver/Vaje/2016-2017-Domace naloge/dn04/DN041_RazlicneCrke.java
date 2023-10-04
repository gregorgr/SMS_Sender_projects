/*
DN041_RazlicneCrke
Napisite program, ki za dano besedilo izpise vse razlicne
znake, ki se v besedilu pojavijo ter mesta (indekse), na katerih
se zadnjikrat pojavijo. Izpise naj tudi, koliko razlicnih znakov
je v besedilu. Velikih in malih crk naj ne locuje! Besedilo
skopirajte s spleta, vsebuje pa naj vsaj trideset besed.
Opomba: Crko, zapisano v spremenljivki crka tipa char,
spremenimo v malo (ne glede ali je velika ali mala) s funkcijo
Character.toLowerCase(crka).
*/


class DN041_RazlicneCrke {
	public static void main (String[] args) {
		// Vir besedila: https://slo-tech.com/novice/t686570#crta
		String besediloVelikeMale = 	"BBC - Fotografija pove več kot tisoč besed, v modernih časih pa smo se navadili, da tudi kaj neresničnega. " +
										"Photoshop je pač sinonim za manipulacijo slik, ki ga zna uporabljati slehernik. Podobno je mogoče manipulirati " +
										"tudi z zvočnimi posnetki, a je to terjalo nekaj več znanja. Nič več. Adobe je na konferenci MAX 2016 pokazal " +
										"projekt VoCo, ki ga označujejo kot 'Photoshop za zvok'. Z zgolj nekaj kliki omogoča spreminjanje posnetka, " +
										"tako da ljudem v usta položimo besede ali stavke, ki jih sploh niso nikoli spregovorili.";
				
		// izpis besedila in dolzina besedila
		System.out.println("\nOriginal besedilo: ");
		System.out.println(besediloVelikeMale); // izpis besedila
		int dolzinaBesedila = besediloVelikeMale.length(); // dolzina besedila
		System.out.println("");
				
		// pretvorba besedila v male crke in izpis besedila
		int maxIndexZnak=0; // najvecji index razlicnih znakov
		char[] besediloMale = new char [dolzinaBesedila]; // besedilo pretvorjeno v male crke
		System.out.println("\nBesedilo s malimi crkami: ");
		for (int i=0; i<dolzinaBesedila; i++) {
			besediloMale[i]=Character.toLowerCase(besediloVelikeMale.charAt(i));
			if(maxIndexZnak<(int)besediloMale[i]) {
				maxIndexZnak=(int)besediloMale[i];
			}
			System.out.print(besediloMale[i]);
		}
				
		// deklaracije in definicije spremenljivk in tabel na zacetne vrednosti
		char[] znak = new char[maxIndexZnak+1]; // zapisan posamezen znak
		int[] steviloZnakov = new int [maxIndexZnak+1]; //
		int[] zadnjiPojavZnaka = new int [maxIndexZnak+1];
		boolean[] znakVBesedilu = new boolean [maxIndexZnak+1];
		int razlicnihZnakov = 0;
		for (int j=0;j<=maxIndexZnak;j++) {
			steviloZnakov[j]=0;
			zadnjiPojavZnaka[j]=0;
			znakVBesedilu[j]=false;
		}
		
		// stetje pozameznih razlicnih znakov
		for (int i=0; i<dolzinaBesedila; i++) {
			znak[(int) besediloMale[i]]=besediloMale[i];
			steviloZnakov[(int) besediloMale[i]]++;
			zadnjiPojavZnaka[(int) besediloMale[i]]=i;
			znakVBesedilu[(int) besediloMale[i]]=true;
		}
		
		// izpisi razultatov
		System.out.println("\n\n: Znak : St. znakov v besedilu : Mesto zadnjega pojava znaka : ");
		for (int j=0;j<=maxIndexZnak;j++) {
			if(znakVBesedilu[j]==true) {
				System.out.println(" : "+znak[j]+" : "+steviloZnakov[j]+" : "+zadnjiPojavZnaka[j]+" : ");
				razlicnihZnakov++;
			}	
		}
		System.out.println("\nV besedilu je "+dolzinaBesedila+" znakov in "+razlicnihZnakov+" razlicnih znakov.");
		
	}
}




/*
Izpisi:
java DN041_RazlicneCrke

Original besedilo:
BBC - Fotografija pove veÄŤ kot tisoÄŤ besed, v modernih ÄŤasih pa smo se navadili, da tudi kaj neresniÄŤnega. Photoshop je paÄŤ sinonim za manipulacijo slik, ki ga zna uporabljati slehernik. Podobno je mogoÄŤe manipulirati tudi z zvoÄŤnimi posnetki, a je to terjalo nekaj veÄŤ znanja. NiÄŤ veÄŤ. Adobe je na konferenci MAX 2016 pokazal projekt VoCo, ki ga oznaÄŤujejo kot 'Photoshop za zvok'. Z zgolj nekaj kliki omogoÄŤa spreminjanje posnetka, tako da ljudem v usta poloĹľimo besede ali stavke, ki jih sploh niso nikoli spregovorili.


Besedilo s malimi crkami:
bbc - fotografija pove veäť kot tisoäť besed, v modernih äťasih pa smo se navadili, da tudi kaj neresniäťnega. photoshop je paäť sinonim za manipulacijo slik, ki ga zna uporabljati slehernik. podobno je mogoäťe manipulirati tudi z zvoäťnimi posnetki, a je to terjalo nekaj veäť znanja. niäť veäť. adobe je na konferenci max 2016 pokazal projekt voco, ki ga oznaäťujejo kot 'photoshop za zvok'. z zgolj nekaj kliki omogoäťa spreminjanje posnetka, tako da ljudem v usta poloĺľimo besede ali stavke, ki jih sploh niso nikoli spregovorili.

: Znak : St. znakov v besedilu : Mesto zadnjega pojava znaka :
 :   : 85 : 521 :
 : ' : 2 : 391 :
 : , : 7 : 495 :
 : - : 1 : 4 :
 : . : 6 : 534 :
 : 0 : 1 : 325 :
 : 1 : 1 : 326 :
 : 2 : 1 : 324 :
 : 6 : 1 : 327 :
 : a : 41 : 491 :
 : b : 7 : 478 :
 : c : 4 : 347 :
 : d : 11 : 482 :
 : e : 36 : 525 :
 : f : 3 : 312 :
 : g : 8 : 526 :
 : h : 9 : 508 :
 : i : 38 : 533 :
 : j : 20 : 500 :
 : k : 21 : 517 :
 : l : 16 : 532 :
 : m : 12 : 475 :
 : n : 28 : 515 :
 : o : 49 : 529 :
 : p : 19 : 523 :
 : r : 12 : 530 :
 : s : 20 : 522 :
 : t : 18 : 490 :
 : u : 8 : 463 :
 : v : 12 : 528 :
 : x : 1 : 322 :
 : z : 11 : 396 :
 : ä : 12 : 419 :
 : ĺ : 1 : 472 :
 : ľ : 1 : 473 :
 : ť : 12 : 420 :

V besedilu je 535 znakov in 36 razlicnih znakov.
*/
