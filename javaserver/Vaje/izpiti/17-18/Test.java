

class Test{

  private static void skrivnost ( int n){
    int spaces = n/2;
    int spaces2 = 0 ;
    for ( int i = 0 ; i <n ; i ++) {
      for ( int j = 0 ; j < spaces ; j ++){
        System.out.print( " " ) ;
    }

    System.out.print ( "*" ) ;
    for ( int j = 0 ; j <2 * spaces2-1;j++){
      System.out.print ( " " ) ;
    }

    if ( spaces2 > 0)
      System.out.print( "*" ) ;

    if (i < n/2) {
      spaces--;
      spaces2 ++;
    } else {
      spaces++;
      spaces2--;
    }
    System.out.println( ) ;
    }
  }



    public static void main (String[] args){

        skrivnost(9);

    }
}
