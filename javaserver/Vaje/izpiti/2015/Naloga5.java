

public class Naloga5
{
    public static void main( String [] args) throws Exception {
        int n = Integer . parseInt (args [0]);

        skrivnost (n);

        throw new Exception("my.own.Exception");
    }


    public static void skrivnost (int n)  {
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                String znak = " ";
                if(j==0 || j==n -1 || j==i || i==n-j -1
                    || (j > n/2 && j > i && i > n-j -1)
                    || (j < n/2 && j < i && i < n-j -1))
                      znak = "" + j;

                System .out. print (znak );

                }
            System .out. println ();
        }
    }






}
