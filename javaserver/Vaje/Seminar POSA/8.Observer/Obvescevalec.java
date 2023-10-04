

import java.util.ArrayList;
import java.util.List;

public class Obvescevalec implements Subjekt {

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void dodaj(Observer o) {
        observers.add(o);
    }

    @Override
    public void odstrani(Observer o) {
        observers.remove(o);
    }

    @Override
    public void obvestiNarocnike(Sporocilo m) {
        for(Observer o: observers) {
            o.posodobi(m);
        }
    }
}
