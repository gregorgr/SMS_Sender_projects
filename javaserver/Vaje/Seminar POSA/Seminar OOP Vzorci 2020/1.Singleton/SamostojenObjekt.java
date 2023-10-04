

class SamostojenObjekt{

  private mojaInstanca = SamostojenObjekt();

  public static getInstance(){
      if(this.mojaInstanca == null){
        this.mojaInstanca = new SamostojenObjekt();
      }
      return this.mojaInstanca;
  }

}
