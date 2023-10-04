

public class DomacaNabava implements  Nabava{

    public DomacaNabava(){
        System.out.println("Ustvarjena povezava z oddelkom domača nabava");
    }

    @Override
    public void dobaviBlago(){
        System.out.println("Pokliči tončka naj nabavi robo!\n");
    }

}
