
import java.util.ArrayList;

import java.util.*;
import java.io.*;




class Izpit{

  private ArrayList<Naloga> naloge;
  // private ArrayList<Avto> avtomobili;
  // private ArrayList<Avto> avtomobili;
  private int casPisanja=0;

  public Izpit(){
    this.naloge = new ArrayList<Naloga>();
  }

  public Izpit(ArrayList<Naloga> naloge){

    this.naloge = naloge;

  }


  public void dodajNalogo(Naloga naloga){
    this.naloge.add(naloga);
  }

  public void izpisiTezke(){

      for(Naloga naloga : naloge){
        if(naloga.GetTezavnost()> 2){
          System.out.println (naloga);
        }
      }
  }

    public static void postevanka(int n){

        for(int i=1; i<=n;i++){

          for(int j=1; j<=n;j++){
            int produkt = i*j;
            String izpis = (produkt % 2)==1 ? "*" : Integer.toString(produkt);
            System.out.print(izpis + " ");
            if(produkt<10) System.out.print(" ");

          }
          System.out.println("");
        }

    }

  public static void sodaLiha(int n){
    int steviloLihih=0;
    for(int i = 1; i<=n;i++){
      //steviloLihih = steviloLihih + (i % 2);
      if ((i % 2)==1) {
        steviloLihih++;
        continue;
      }

      System.out.println(i);
    }
    System.out.println("Lihih med 1 in " + n + " je " +steviloLihih);

  }


    public static void main(String[] args){

      try{
        ArrayList<Naloga> n= new ArrayList<Naloga>();

        n.add(new Naloga( "besedilo 1",  3));
        n.add(new Naloga( "besedilo 2",  1));
        n.add(new Naloga( "besedilo 3",  2));
        n.add(new Naloga( "besedilo 4",  3));

        //this.avtomobili.add//
        Izpit i = new Izpit(n);
        i.izpisiTezke();

      }catch(Exception e){
        System.out.println(e);
      }



      System.out.println("------------------------ ");

      postevanka(5);
      sodaLiha(9);


  }

}
