

public class DecoratorDemo {
   public static void main(String[] args) {

      Zgradba appartma1= new Apartman();

      Zgradba vikend1 = new Vikend();

      Zgradba luksuznaVila = new  LuksuzDecorator(new Vikend());


      System.out.println("appartma1:");
      appartma1.izpisi();

      System.out.println("\nVikend1:");
      vikend1.izpisi();

      System.out.println("\nLuksuzna vila:");
      luksuznaVila.izpisi();

   }
}
