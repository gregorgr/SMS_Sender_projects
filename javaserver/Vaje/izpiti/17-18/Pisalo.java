
public class Pisalo{
  public enum tipiPisala{
    svincnik,
    kemik,
    nalivnik
  }

  private Pisalo.tipiPisala vrstaPisala;
  private int cena;
  private int dolzina;
  private String proizvajalec;

  public Pisalo(){
    this.vrstaPisala = tipiPisala.svincnik;
    this.cena = 5;
    this.dolzina = 5;
    this.proizvajalec =  "Parker";
  }

  public Pisalo(tipiPisala vrstaPisala, int cena, int dolzina, String proizvajalec){
    this.vrstaPisala = vrstaPisala;
    this.cena = cena;
    this.dolzina = dolzina;
    this.proizvajalec =  proizvajalec;
  }

public tipiPisala GettipiPisala(){
  return this.vrstaPisala;
}

public int  getCena(){
  return this.cena;
}

public int  GetDolzina(){
  return this.dolzina;
}

public String GetProizvajalec(){
  return this.proizvajalec;
}

public void SetCena(int cena){
  this.cena = cena;
}
public void SetProizvajalec(String s){
  this.proizvajalec = s;
}


public String toString(){
  String s= (String) vrstaPisala.name();
  s=s + "; Cena: "+ this.cena + "; dolzina" + this.dolzina + "; Proizvajalec: " + this.proizvajalec;
  return s;
}

public void Izpisi(){
  System.out.println(this);
}
/*

private tipiPisala vrstaPisala;

this.vrstaPisala;
this.cena;
this.proizvajalec;
this.proizvajalec;
}
*/



}
