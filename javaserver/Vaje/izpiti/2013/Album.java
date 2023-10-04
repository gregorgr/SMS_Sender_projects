import java.util.ArrayList;


class Album{
  public ArrayList<Slicica> tabela = new ArrayList<Slicica>();;

  public void dodaj(Slicica s){

    tabela.add(s);
  }

  public void odstrani(int brisi){

    int i=0;
    for(Slicica s: tabela){
      //  if(s.getStPolja() == del.getStPolja()){
        if(s.getStPolja() == brisi){
          tabela.remove(i);
          System.out.println("odstranjen: \n" + s);
          break;
        }
        i++;
    }
  }


  public boolean imamSlicico(byte b){
    int i = (int)b;
    for(Slicica s: tabela){
      if(s.getStPolja() == i){
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args){
    Album a = new Album();
    a.dodaj(new Slicica(1, "Tone"));
    a.dodaj(new Slicica(3, "Marko"));
    a.dodaj(new Slicica(2, "Janez"));

    for(Slicica s: a.tabela){
      System.out.println(s);
    }

    a.odstrani(2);
    for(Slicica s: a.tabela){
      System.out.println(s);
    }

    }
}
