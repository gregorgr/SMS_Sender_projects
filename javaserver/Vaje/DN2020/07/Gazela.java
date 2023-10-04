

class Gazela extends Zival{
  private String kajDela;
  private int hitrost;

  public Gazela(){
    super();
    this.vrsta ="Gazela";
    this.mesojeda = false;
  }

  public Gazela(String imeZivali){
    this() ;
    this.ime = imeZivali;
  }

  public Gazela(String imeZivali, int zacStarost){
      this(imeZivali);
      this.starost = zacStarost;
      //super(imeZivali, zacStarost, jeMesojeda);

  }

  public Gazela(String imeZivali, int zacStarost, String kajDelaZival){
      this(imeZivali, zacStarost);
      this.kajDela = kajDelaZival;
  }


  public void setKajDela(String kajdelazival){
    this.kajDela =kajdelazival;
  }

  public void setHitrost(int zaHitrost){
    this.hitrost=zaHitrost;
  }

  public String getKajDela(){
    return this.kajDela;
  }

  public int getHitrost(){
    return this.hitrost;
  }

  public void izpisiPodatke()   {
       super.izpisiPodatke();
       System.out.println(this.kajDela);
       System.out.println("Njena trenutna hitrost je: " + this.hitrost + "km/h.");
  }

}
