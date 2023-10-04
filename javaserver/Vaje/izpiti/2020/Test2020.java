

class Test2020{

  public static void stevilke ( int n ) {

    System.out.print ( "Stevilke : \n ");
    while ( n > 0 ) {
      if ( n%2 == 0 ) {
        n -= 3 ;
        System.out.println( n ) ;
      } else {
        n += 1 ;
        System.out.print ( n + " " ) ;
        continue ;
      }
      if (n < 0) {
        System.out.print ( "Zmanjkalo je stevilk ! " ) ;
        break ;
      }
      System.out.print ( "Naslednje stevilke : \n " ) ;
    }
  }


  public static void main(String args[]) {
stevilke(10);
  }

}
