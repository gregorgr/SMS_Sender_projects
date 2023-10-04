
/*
4. domača naloga
Napišite program, ki za dano besedilo
- izpiše vse različne znake, ki se v besedilu pojavijo, ter
- mesta (indekse), na katerih se pojavijo zadnjič (presledkov ne štejemo kot znake). Izpiše naj
tudi,
- koliko različnih znakov je v besedilu in
- koliko je vseh samoglasnikov v besedilu.

Velikih in malih črk naj ne ločuje!
Besedilo skopirajte s spleta, vsebuje pa naj vsaj 30 besed.
I Opomba: črko, zapisano v spremenljivki crka tipa char,
spremenimo v malo (ne glede ali je velika ali mala) s funkcijo
Character.toLowerCase(crka).
*/

class IskanjeZnakov{

  public static void main (String[] args){
    String besedilo = "Contrary to popular belief, Lorem Ipsum is not simply random text. " +
                      "It has roots in a piece of classical Latin literature from 45 BC, " +
                      "making it over 2000 years old. Richard McClintock, a Latin professor " +
                      "at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, " +
                      "consectetur, from a Lorem Ipsum passage, and going through the cites of the word in" +
                      "classical literature, discovered the undoubtable source. ";

    int dolzinaBesedila = besedilo.length();

    // te spremenljivke bom uporabljal
    String samoglasniki ="aeiou";
    int steviloRazlicnihZnakov = 0;
    int steviloSamoglasinkov = 0;
    char[] seznamZnakov = new char [dolzinaBesedila];
    int[] seznamIndeksov = new int [dolzinaBesedila];
    // najprej vse skupaj izpišemo
    System.out.print("\nBesedilo: \n" + besedilo + "\n");

    for (int i=0; i<dolzinaBesedila; i++) {
      // vzamem pomanjšan znak
      char znak = Character.toLowerCase(besedilo.charAt(i));

      String vmesniSeznam = new String(seznamZnakov); // Convert
      if ((int) znak != 32){
        if ((steviloRazlicnihZnakov==0 || (vmesniSeznam.indexOf(znak) == -1))) {
          // vpišemo znak in index
          seznamZnakov[steviloRazlicnihZnakov] = znak;
          seznamIndeksov[steviloRazlicnihZnakov] = i;
          steviloRazlicnihZnakov++;

        } else {
          // vpišem novo zadnjo pozicijo
          int indexZnaka = vmesniSeznam.indexOf(znak);
          seznamIndeksov[indexZnaka] = i;
          // zadnja pojavitev
        }

        // poglejmo še samoglasnike
        if(samoglasniki.indexOf(znak)>=0)
            steviloSamoglasinkov++;
        }
    }

    // se srehodim samo do zadnjega indeksa in vse izpišem...
    for (int i=0; i<steviloRazlicnihZnakov; i++) {
        System.out.println("'" +seznamZnakov[i] + "' : " + seznamIndeksov[i]);
    }
    System.out.println("Število samoglasnikov je: " + steviloSamoglasinkov);
    System.out.println("Število različnih znakov je: " + steviloRazlicnihZnakov);
 }
}
