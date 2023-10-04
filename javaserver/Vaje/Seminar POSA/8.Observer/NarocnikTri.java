

public class NarocnikTri implements Observer
{
    @Override
    public void posodobi(Sporocilo m) {
        System.out.println("NarocnikTri :: " + m.getVsebinaSporocila());
    }
}
