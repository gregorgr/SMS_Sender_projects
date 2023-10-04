

/*
Razred Mesec naj vsebuje:
• polje (tabelo) za shranjevanje vseh dni v mesecu,
• metodo, ki bo vrnila število dni v mesecu, ko je temperatura dosegla 30 ◦ C ali več.
*/
import java.util.*;

class MeteroloskeMeritveMesecno{



  ArrayList <MeteroloskeMeritveDnevno> meritve;

  public MeteroloskeMeritveMesecno(){

  }



  public ArrayList <MeteroloskeMeritveDnevno> dneviPrekoTridesetStopinj(){

    ArrayList <MeteroloskeMeritveDnevno> xmeritve = new ArrayList <MeteroloskeMeritveDnevno>();

    for(MeteroloskeMeritveDnevno m : this.meritve){
      if (m.isPrekoTridesetStopinj()){
        xmeritve.add(m);
      }
    }
    return xmeritve;
  }
}
