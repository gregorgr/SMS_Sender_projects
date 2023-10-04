


class Tiger extends Zival{

  private boolean lovi;
  private boolean lacen;

  public Tiger(){
    super();
    this.vrsta ="Tiger";
  }

  public Tiger(String imeZivali){
    this();
    this.ime = imeZivali;
    //super(imeZivali) ;

  }

  public Tiger(String imeZivali, int zacStarost){

      this(imeZivali);
      this.starost = zacStarost;
      this.mesojeda = true;
      //super(imeZivali, zacStarost, jeMesojeda);

  }

  public void setLacen(boolean nastaviLakoto){
    this.lacen = nastaviLakoto;
  }
  public void setLovi(boolean nastaviLovi){
    this.lovi = nastaviLovi;
  }

  public boolean getLacen(){
    return this.lacen;
  }
  public boolean getLovi(){
    return this.lovi;
  }

  public void izpisiPodatke()   {
    super.izpisiPodatke();

    String aliLovi = this.lovi ? " "  : " ne ";
    String aliJeLacen = this.lacen ? "je"  : "ni";
    //   System.out.println(jeLeteca);
    System.out.println("Tiger trenutno " + aliJeLacen + " lačen.");
    System.out.println("Tiger trenutno" + aliLovi + "lovi.");

  }

}
