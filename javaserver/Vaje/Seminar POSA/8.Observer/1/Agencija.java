

public class Agencija {
    private String novica;
    private List<Obvescanje> seznamKanalov = new ArrayList<>();

    public void addObserver(Obvescanje kanal) {
        this.seznamKanalov.add(kanal);
    }

    public void removeObserver(Obvescanje kanal) {
        this.seznamKanalov.remove(kanal);
    }

    public void setNovica(String novica) {
        this.novica = novica;
        for (Obvescanje kanal : this.seznamKanalov) {
            kanal.posodobiNovice(this.novica);
        }
    }
}
