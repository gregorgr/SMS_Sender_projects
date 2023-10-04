


class MeteroloskaMeritev{

    int ura;
    double temeratura;
    double tlak;

    public MeteroloskaMeritev(){

    }

    public MeteroloskaMeritev(int ura, double temp, double tlak){
      this.ura = ura;
      this.temeratura = temp;
      this.tlak = tlak;
    }


    public int getUra(){
      return this.ura;
    }

    public double getTemeratura(){
      return this.temeratura;
    }

    public double getTlak(){
      return this.tlak;
    }


    public void setUra (int setUra){
      this.ura = ura;
    }

    public void setTemeratura (double xTemeratura){
      this.temeratura =  xTemeratura;
    }

    public void setTlak(double xTlak){
      this.tlak = tlak;
    }

}
