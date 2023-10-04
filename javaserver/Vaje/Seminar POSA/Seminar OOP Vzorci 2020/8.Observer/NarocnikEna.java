

public class NarocnikEna implements Observer
{
    @Override
    public void posodobi(Sporocilo m) {
        System.out.println("NarocnikEna :: " + m.getVsebinaSporocila());
    }
}
