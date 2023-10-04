
/*
3. domača naloga

Napišite program, ki za dani argument n izpiše vsa praštevila
manjša od n. Pozor: 1 ni praštevilo!
*/

import java.util.Scanner;

class Prastevila{

/*
* Precej poenostavljeno iskanje praštevil
*
*/
  private static void izpisiVsaPrastevila(int n){

    // gremo skozi vse možnosti manjše od n
    nov_loop:
    for (int i=2; i<n; i++){

      for(int j=2; j<i;j++){
        // če najdemo deljitelje, gremo po ovo število! To gotovo ni praštevilo
        if (i!=j && i%j==0) continue nov_loop;
      }
      // malo estetike z vejicami
      if (i>2) System.out.print(", ");
      // če ta reč pride do sem, imamo praštevilo in ga iypišemo
      System.out.print(i);
    }
  }

  public static void main (String[] args){

     // uporabnik vnese število
     Scanner uporabnikovVnos = new Scanner(System.in);
     System.out.print("Vpišite poljubno število: ");
     int n = uporabnikovVnos.nextInt();

     izpisiVsaPrastevila(n);

     // nova vrstica zaradi estetike
     System.out.println("");
     uporabnikovVnos.close();
 }
}
