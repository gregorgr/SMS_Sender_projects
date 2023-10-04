


class NarocniskaSluzbaImpl implements INarocniskaSluzbaFasada{

  public boolean izvediNarocilo(int pId){

      boolean narociloIzvedeno=false;

      Izdelek izdelek=new Izdelek();
      izdelek.idIzdelka=pId;

      if(SkladiscnaSluzba.naZalogi(izdelek))
      {
          System.out.println("Izdelek z ID-jem : "+ izdelek.idIzdelka+" je na zalogi.");

          boolean placiloPotrjeno= PlacilniServis.izvediPlacilo();

          if(placiloPotrjeno){

              System.out.println("Plačilo potrjeno...");

              DostavniServis.dostaviIzdelek(izdelek);

              System.out.println("Izdelek poslan kupcu...");
              
              narociloIzvedeno=true;
          }
      }
      return narociloIzvedeno;
  }

}
