

class Naloga{

    private String besedilo;
    private String sestavljalec;

    private int tezavnost = 1; // 1 do 3
    private int dosezeno = 0; // 1 do 3
    private int moznoTock = 0; // 1 do 3

    // int dosezeno,
    public Naloga(String besedilo, String sestavljalec, int tezavnost,  int moznoTock, int dosezeno) throws Exception{
      this(besedilo, sestavljalec, tezavnost,  moznoTock);
      this.dosezeno = dosezeno;


    }

    public Naloga(String besedilo, int tezavnost) throws Exception{
      this(besedilo, "sestavljalec", tezavnost,  tezavnost, 5);
    }

    public Naloga(String besedilo, String sestavljalec, int tezavnost,  int moznoTock) throws Exception{
      this.besedilo = besedilo;
      this.sestavljalec = sestavljalec;

      if (tezavnost<0 || tezavnost>3){
        throw new Exception ("Težavnost izven mej 1-3");
      }else{
        this.tezavnost = tezavnost;
        this.moznoTock = moznoTock;
      }
    }

    public String GetBesedilo(){
      return this.besedilo;
    }

    public String GetSestavljalec(){
      return this.sestavljalec;
    }

    public int GetTezavnost(){
      return this.tezavnost;
    }

    public int GetDosezeno(){
        return this.dosezeno;
    }

    public int GetMoznoTock(){
  return this.moznoTock;
    }


    public void SetTezavnost(int tezavnost) throws Exception{
      if (tezavnost<0 || tezavnost>3){
        throw new Exception ("Težavnost izven mej 1-3");
      }
      this.tezavnost = tezavnost;
    }
    public void SetBesedilo(String b){
      this.besedilo = b;
    }


    public String toString(){
      String b = "Naloga: \n Vprasanje: " + this.besedilo;
      b += "\n sestavljalec: " + this.sestavljalec;
      b += "\n tezavnost: " + this.tezavnost;
      b += "\n dosezeno: " + this.dosezeno;
      b += "\n moznoTock: " + this.moznoTock;
      b += "\n ";

      return b;
    }

    public void izpisi(){
      System.out.println(this);
    }
/*
    this.besedilo;
    this.sestavljalec;
    this.tezavnost = 1; // 1 do 3

    this.dosezeno = 0; // 1 do 3
    this.moznoTock = 0; // 1 do 3
    this.dosezeno = 0; // 1 do 3*/


}
