


/*



4. naloga (25%): V jeziku Java napišite dva razreda:

- razred, ki bo predstavljal mesečno evidenco meteoroloških meritev,
- razred, ki bo predstavljal meteorološke meritve enega dneva.

Razred
Dan naj vsebuje:
• polji (ali polja) za shranjevanje temperature in zračnega tlaka  OK
zjutraj,
sredi dneva in
zvečer,

• konstruktor, ki bo omogočal poljubno določitev začetne vrednosti vseh polj, OK
• metode za dostop do vseh polj (t.i. get in set metode).

Razred Mesec naj vsebuje:
• polje (tabelo) za shranjevanje vseh dni v mesecu,
• metodo, ki bo vrnila število dni v mesecu, ko je temperatura dosegla 30 ◦ C ali več.
*/
import java.util.*;

/// Aka Dan
class MeteroloskeMeritveDnevno{

/*
Čas meritev:
1- zjutraj
2 - opoldne
3 - zvečer
*/
  private ArrayList<MeteroloskaMeritev> meritve;
//  private ArrayList<Avto> avtomobili;

  public MeteroloskeMeritveDnevno(){

    this.meritve = new ArrayList <MeteroloskaMeritev> ();

  }

  public MeteroloskeMeritveDnevno(int casMeritve, Double temp, Double tlak){
    super();
    this.meritve.add(new MeteroloskaMeritev(casMeritve,temp, tlak ));
  }
  /*
  * Začetna vrednost 1 zjutraj
  *
  */
  public MeteroloskeMeritveDnevno(Double temp, Double tlak){
    super();
    this.meritve.add(new MeteroloskaMeritev(1,temp, tlak ));
  }

  public void dodajMeritevOpoldne(Double temp, Double tlak){
    this.meritve.add(new MeteroloskaMeritev(2,temp, tlak ));

  }
  public void dodajMeritevZvecer(Double temp, Double tlak){
    this.meritve.add(new MeteroloskaMeritev(3,temp, tlak ));
  }

  public void dodajMeritev(int ura, Double temp, Double tlak){
    this.meritve.add(new MeteroloskaMeritev(ura,temp, tlak ));
  }

  public MeteroloskaMeritev getMeritev(int ura) throws Exception{

    for(MeteroloskaMeritev m : this.meritve){
      if (m.getUra() == ura){
        return m;
      }
    }
    throw new Exception("Out od range error");
  }


  public boolean isPrekoTridesetStopinj(){

    for(MeteroloskaMeritev m : this.meritve){
      if (m.getTemeratura() > 30){
        return true;
      }
    }
    return false;
  }

}
