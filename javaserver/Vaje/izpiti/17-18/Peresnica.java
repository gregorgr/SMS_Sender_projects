import java.util.ArrayList;

class Peresnica{

  private int maxNum;
  private ArrayList<Pisalo> vsebina;

  public Peresnica(ArrayList<Pisalo> pisala, int maxNum) throws Exception {
    this.maxNum = maxNum;

    if (pisala.size()>this.maxNum){
      throw new Exception("Preveč jih je");
    }

    this.vsebina = pisala;
  }

  public void IzpisiNad10(){

    for(Pisalo p: vsebina){
      if(p.getCena()>10){
        System.out.println(p);
      }

    }
  }





  public static void main (String[] args){

    try{
      ArrayList<Pisalo> p = new ArrayList<Pisalo>();
      p.add(new Pisalo());
      p.add(new Pisalo(Pisalo.tipiPisala.nalivnik, 15, 10, "Parkler1"));
      p.add(new Pisalo(Pisalo.tipiPisala.nalivnik, 12, 10, "Parkler2"));
      p.add(new Pisalo(Pisalo.tipiPisala.nalivnik, 10, 10, "Parkler3"));

      Peresnica peresnica = new Peresnica(p, 10);
      peresnica.IzpisiNad10();
    }catch(Exception e){
System.out.println(e);

    }





  }

}
