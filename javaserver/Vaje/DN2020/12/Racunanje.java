

/*

5. naloga (25%): Napišite metodo v Javi,
ki bo izpisala tabelo poštevanke za množenje
pozitivnih celih števil manjših ali enakih podanemu številu int n.
Klic metode postevanka(4)
bi tako na zaslonu izpisal:
|| 1 | 2 | 3 | 4 |
========================
1 || 1 | 2 | 3 | 4 |
________________________
2 || 2 | 4 | 6 | 8 |
________________________
3 || 3 | 6 | 9 | 12 |
________________________
4 || 4 | 8 | 12 | 16 |
________________________ .

*/

class Racunanje{

  public static void postevanka(int n){

    // nasovna vrstica
    System.out.print("   || ");
    for (int i=1; i<=n; i++){
      System.out.print( i + " | ");
    }
    System.out.println("\n========================");

    for (int i=1; i<=n; i++){
      // prvi stolpec
      System.out.print( i + " || ");
      for (int j=1; j<=n; j++){
        System.out.print( i*j + " | ");
      }
      System.out.println("\n________________________\n");
    }
  }

  public static void main(String[] args){
    postevanka(4);
  }
}
