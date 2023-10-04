
import java.util.Random;

class TestJan2017{

  public static int[] generirajtabelo(int n){
    Random  r = new Random();

    int[] tab = new int[n];
    for(int i = 0; i< tab.length; i++){
      tab[i] = r.nextInt(1000);
    }
    return tab;
  }

  public static void main(String[] args){

    int[] t = generirajtabelo(7);
    int i = 0;
    for(int a : t){
      System.out.println(i + " : " +a);
      i++;
    }
  }
}
