

class NabavniPosrednik{

  public enum TipNabave {
      uvoz,
      domace
  }

    public NabavniPosrednik(){
      System.out.println("NabavniPosrednik vzpostavljen, sledi vnos tipa izdelka\n");
    }

    public Nabava getOddelekNabave(TipNabave tip){

      switch (tip){
        case uvoz:
          return new NabavaTujina();
          // break;
        case domace:
        default:
          return new DomacaNabava();

      }
    }

 /*
 *
 *
 */
  public static void main(String[] args) {

      NabavniPosrednik poisciOddelek = new NabavniPosrednik();

      // kupujemo domač izdelek
      Nabava izdelek1 = poisciOddelek.getOddelekNabave(NabavniPosrednik.TipNabave.domace);
       izdelek1.dobaviBlago();

      // kupujemo izdelek iz tujine
      Nabava izdelek2 = poisciOddelek.getOddelekNabave(NabavniPosrednik.TipNabave.uvoz);
      izdelek2.dobaviBlago();

    }
}
