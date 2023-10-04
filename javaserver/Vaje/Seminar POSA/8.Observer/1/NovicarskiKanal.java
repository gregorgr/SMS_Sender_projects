


public class NovicarskiKanal implements Kanal {
    private String novice;

    @Override
    public void posodobiNovice(Object novice) {
        this.setNews((String) novice);
    }
}
