

public abstract class DekoratorZgradbe implements Zgradba {
   protected Zgradba dekoriranaZgradba;

   public DekoratorZgradbe(Zgradba dekoriranaZgradba){
      this.dekoriranaZgradba = dekoriranaZgradba;
   }

   public void izpisi(){
      dekoriranaZgradba.izpisi();
   }
}
