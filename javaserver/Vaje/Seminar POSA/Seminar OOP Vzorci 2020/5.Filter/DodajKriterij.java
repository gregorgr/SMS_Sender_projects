import java.util.List;
import java.util.ArrayList;


class DodajKriterij implements Kriterij {

  private Kriterij kriterij;
  private Kriterij drugKriterij;

  public DodajKriterij(Kriterij enKriterij, Kriterij  drugKriterij) {
    this.kriterij = enKriterij;
    this.drugKriterij = drugKriterij;
  }

  @Override
  public List<Usluzbenec> ustrezaKriteriju(List<Usluzbenec> osebe) {
    List<Usluzbenec> prviKriterijOsebe = kriterij.ustrezaKriteriju(osebe);
    return drugKriterij.ustrezaKriteriju(prviKriterijOsebe);
  }
}
