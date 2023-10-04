


class Slicica{
    private int stPolja;
    private String imeKosarkarja;

    public Slicica(int stPolja, String imeKosarkarja){
      this.stPolja = stPolja;
      this.imeKosarkarja = imeKosarkarja;
    }

    public String getImeKosarkarja(){
      return this.imeKosarkarja;
    }

    public void setImeKosarkarja(String imeKosarkarja){
      this.imeKosarkarja = imeKosarkarja;
    }

    public int getStPolja(){
      return this.stPolja;
    }

    public void setStPolja(int stPolja){
      this.stPolja = stPolja;
    }

    public String toString(){
        String s= "slicica st: " + this.stPolja + " - Ime kosarkasa: " + this.imeKosarkarja + "\n";
        return s;
    }
}
