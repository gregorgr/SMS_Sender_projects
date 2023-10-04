

class Mravlja extends Zival{

  private String barva;
  private boolean leti;


  public Mravlja(){
    super();
    this.leti = false;
    this.barva = "Črna";
  }

  public Mravlja(String imeZivali){
    this();
    this.vrsta ="Mravlja";
    this.ime = imeZivali;
    //super(imeZivali) ;

  }

  public Mravlja(String imeZivali, int zacStarost, boolean jeMesojeda){

      this(imeZivali);
      this.starost = zacStarost;
      this.mesojeda = jeMesojeda;
      //super(imeZivali, zacStarost, jeMesojeda);

  }

  public void setBarva(String barvaMravlje){
    this.barva = barvaMravlje;
  }
  public void setBarva(boolean leteca){
    this.leti = leteca;
  }

  public String getBarva(){
    return this.barva;
  }
  public boolean getLeti(){
    return this.leti;
  }

  public void izpisiPodatke()   {
       super.izpisiPodatke();

       String jeLeteca = this.leti ? "Leteča mravlja"  : "Navadna mravlja";

       System.out.println(jeLeteca);
       System.out.println("Barva mravlje je: " + this.barva);
  }

}
