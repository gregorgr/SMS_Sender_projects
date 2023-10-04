

public class  NarocnikDva implements Observer
{
    @Override
    public void posodobi(Sporocilo m) {
        System.out.println("NarocnikDva :: " + m.getVsebinaSporocila());
    }
}
