

public class RadioEna implements Obvescanje {

    private String novica;

    @Override
    public void posodobiNovice(Observable o, Object novica) {
        this.setNovica((String) novica);
    }
}
