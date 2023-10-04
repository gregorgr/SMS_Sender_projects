
/*
5. domača naloga

Napišite
- metodo obrniNiz, ki kot parameter prejme niz, OK
- vrne pa njegovo obrnjeno vrednost. Primer obrniNiz("žirafa") OK
= "afariž".
-Nato iz tipkovnice preberite ime datoteke A, v
kateri imate besedilo ter ime datoteke B, v katero boste
zapisovali nove podatke.
- Na koncu zapišite vsako izmed vrstic iz datoteke A v datoteko B z njeno obrnjeno vrednostjo.
textB.txt
*/


import java.io.*; // Zaradi branja iz tipkovnice in iz/v datoteke
import java.util.ArrayList;

class ObrniNiz{


  public static String obrniNiz(String niz){

    String obrnjenNiz="";
    int dolzinaNiza=niz.length();

    for(int i=dolzinaNiza;i>0;i--)  {
      obrnjenNiz += niz.charAt(i-1);
    }
    return obrnjenNiz;
  }


  public static void main (String[] args){

    System.out.println ("Pozdravljeni v programu za obracanje nizov.");
    // v seznam bomo shranili vrstice datoteke
    ArrayList<String> vrstice = new   ArrayList<String>();

    // najprej beremo
    InputStreamReader vt = new InputStreamReader(System.in);
    BufferedReader vnos = new BufferedReader(vt);

    // Ime datoteke za branje
    System.out.print ("Vnesite ime datoteke, iz katere zelite brati besedilo [textA.txt]: ");

    try{
      String datotekaA = vnos.readLine();

      // če gre za prazen vnos, nastavimo default datoteko textA.txt
      if(datotekaA.length()==0) datotekaA = "textA.txt";
      // za enkrat ne vemo imena datotekeB (ime se skriva v datoteki A)
      String datotekaB = "";

      // Odpiranje datoteke za branje
      FileReader fr = new FileReader(datotekaA);
      BufferedReader branje = new BufferedReader(fr);

      // Inicializacija pomožnih spremenljivk
      String prebranNiz = "";

      int i=0;


      // Bere toliko casa, dokler lahko se kaj prebere iz datoteke za branje
      while (branje.ready()){

        prebranNiz = branje.readLine();
        /// v prvi vrstici je vpisano ime datoteke B
        if (i==0){
          System.out.println("Datoteka v katero bomo pisali je: '" +prebranNiz + "'");
          datotekaB = prebranNiz;
        }
        // shrani vsebino vrstice v ArrayList
        vrstice.add(prebranNiz);
        i++;
      }
      branje.close();

      // zapisovanje vsebine v datoteko
      FileWriter fw = new FileWriter (datotekaB);
      PrintWriter zapisovalnik= new PrintWriter(fw);
      // Zapiranje obeh datotek

      for (int k = 0; k < vrstice.size(); k++) {
        // prebere shranjeno vrstico, jo  obrne
        String obrnjeno = obrniNiz (vrstice.get(k));
        // in zapiše obrnjeno vrednost v datoteko
        zapisovalnik.println (obrnjeno);
        // izpiše še na zaslon
        System.out.println(obrnjeno);
      }
      // zapremo zapisovalnik
      zapisovalnik.close();
    }catch(Exception e){}

 }
}
