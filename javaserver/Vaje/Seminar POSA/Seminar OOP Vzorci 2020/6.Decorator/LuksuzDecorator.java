public class  LuksuzDecorator extends DekoratorZgradbe {

   public LuksuzDecorator(Zgradba dekoriranaZgradba) {
      super(dekoriranaZgradba);
   }

   @Override
   public void izpisi() {
      dekoriranaZgradba.izpisi();
      setVilla(dekoriranaZgradba);
   }

   private void setVilla(Zgradba dekoriranaZgradba){
      System.out.println("Objekt je: Villa");
   }
}
