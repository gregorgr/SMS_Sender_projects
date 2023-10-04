


class KontrolerIzvedenihNarocil{

  INarocniskaSluzbaFasada facade;

  boolean narociloIzvedeno=false;

  // orderProduct
  public void narociIzdelek(int idIzdelka) {

      narociloIzvedeno = facade.izvediNarocilo(idIzdelka);

      System.out.println("KontrolerIzvedenihNarocil: funkcija narociIzdelek je koncana.");

  }

}
