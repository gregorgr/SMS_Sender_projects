
/*
7. domača naloga

Ustvarite razred Zival, ki ima lastnosti
starost,
ime,
mesojeda.

Ustvarite še tri podrazrede (tri vrste živali), ki
dedujejo razred Zival. Vsaj ena vrsta živali naj ima prvo črko
enako prvi črki vašega imena.

Podrazredom določite dve polji
in metode tipa get in set zanju.

V razredu Zival ustvarite
metodo main in v njej ustvarite pet živali (treh različnih vrst)
ter izpišite njihove lastnosti.
*/

import java.util.Scanner;

class Zival{

  protected String vrsta;

  protected int starost;
  protected String ime;
  protected boolean mesojeda;


  public  Zival(){
  }

  public Zival(String imeZivali){

    this.ime = imeZivali;

  }

  public Zival(String imeZivali, int zacStarost, boolean jeMesojeda){

    this.ime = imeZivali;
    this.starost = zacStarost;
    this.mesojeda = jeMesojeda;

  }


  protected void izpisiPodatke()   {
    System.out.println("");
    System.out.println("***   Podatki  o živali ***");

    System.out.println("Žival vrste: " + this.vrsta + ".");
    System.out.println("Ime živali: " + this.ime + ".");
    System.out.println("Starost: " + this.starost + ".");
    String mes = this.mesojeda ? "Je"  : "Ni";
    System.out.println(mes + " mesojeda");

  }


  public static void main (String[] args){

    Gazela zival1 = new Gazela("Simona", 4);
    Mravlja zival2 = new Mravlja("Pikica", 1, true);
    Mravlja zival3 = new Mravlja("Mimi", 2,false);
    Gazela zival4 = new Gazela("Gizela", 3, "Pase se na savani.");



    Tiger zival5 = new Tiger("Jakob", 11);

    zival1.setHitrost(44);
    zival1.setKajDela("Beži");

    zival2.setBarva("Rdeča");
    zival5.setLacen(true);

    zival1.izpisiPodatke();
    zival2.izpisiPodatke();
    zival3.izpisiPodatke();
    zival4.izpisiPodatke();
    zival5.izpisiPodatke();

    System.out.println("");

 }
}
