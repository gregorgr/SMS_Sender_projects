
import java.util.ArrayList;

class Histogram{

  public static void histogram(byte[][] h){

      byte sestevek=0;

      for (int i=0; i<h.length;i++){
        // resetiramo seštevek
        sestevek = 0;
        for (int j=0; j<h[i].length;j++){
          System.out.print(h[i][j]+ " ");
          sestevek+=h[i][j]; // prištejemo vrednost vrstice
        }
        for (int n=0; n<sestevek;n++){
          System.out.print("*"); // zvezica seštevka
        }
        System.out.print("\n"); // nova vrsta
      }
  }

  public static void main(String[] args){

      String[] mojStringSeznam = new String[5];
      ArrayList<String> mojDinamicenSeznam = new ArrayList<String>();

      byte[][] h = new byte[][]{
          {1,0,1,2},    {3,2,0,0},     {0,1,1,1}, {1,0,1,0}
        };
        histogram(h);

  }

}
