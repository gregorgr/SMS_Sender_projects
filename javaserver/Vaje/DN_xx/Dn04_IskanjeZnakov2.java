
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


class Dn04_IskanjeZnakov2{

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

    int [][] tabelaZnakov = new int[dolzinaBesedila][2];
    /*{
      new int [dolzinaBesedila],
      new int [2]
    };
*/
    // najprej vse skupaj izpišemo
    System.out.print("\nBesedilo: \n" + besedilo + "\n");

    for (int i=0; i<dolzinaBesedila; i++) {

      // vzamem pomanjšan znak
      char znak = Character.toLowerCase(besedilo.charAt(i));

      String original = ""+ znak;
      byte[] utf8Bytes = original.getBytes("UTF-8");
    //  int znakInt = Character.getNumericValue(znak);
      // Integer.parseInt(String.valueOf(znak));
      int znakInt = (int) utf8Bytes[0];
      // System.out.println(i + " : razl:" +steviloRazlicnihZnakov);

      if (znakInt != 32){

        //System.out.println(i + "-  "+ znak + ": " + znakInt);

        for (int j=0; j <= i; j++){
          //
          // znakInt.equals(tabelaZnakov[j][0])
          if(tabelaZnakov[j][0] == znakInt){

            tabelaZnakov[j][1] = i;
            //System.out.println("  -posodobim :" + znak + ":" + znakInt);
            break;

          }else if (j==steviloRazlicnihZnakov){

            System.out.println("  -vpišem :" + znak + ":" + znakInt + " na poz:" + j);
            tabelaZnakov[j][0] = znakInt;
            tabelaZnakov[j][1] = i;
            steviloRazlicnihZnakov++;
          } else{

            //System.out.println("Ni enak :" + znak + "  " + znakInt + " != " + tabelaZnakov[j][0]);
          }
        }

        // poglejmo še samoglasnike
        if(samoglasniki.indexOf(znak)>=0)
            steviloSamoglasinkov++;
        }
      }
    System.out.println("Izpis rezultata: ");

    // se srehodim samo do zadnjega indeksa in vse izpišem...
    for (int i=0; i < steviloRazlicnihZnakov; i++) {
        char c=(char)(int)tabelaZnakov[i][0];
        String szUT8 = new String(tabelaZnakov[i][0], "UTF-8");
        System.out.println("'" + szUT8 + ":"+String.valueOf(tabelaZnakov[i][0])+"' : " + tabelaZnakov[i][1]);
    }

    System.out.println("Število samoglasnikov je: " + steviloSamoglasinkov);
    System.out.println("Število različnih znakov je: " + steviloRazlicnihZnakov);
 }
}
