import java.util.List;
import java.util.ArrayList;


class AliKriterij implements Kriterij {

  private Kriterij kriterij;
  private Kriterij drugKriterij;

  public AliKriterij(Kriterij kriterij, Kriterij drugKriterij) {
    this.kriterij = kriterij;
    this.drugKriterij = drugKriterij;
  }

  @Override
  public List<Usluzbenec> ustrezaKriteriju(List<Usluzbenec> osebe) {
    List<Usluzbenec> seznamPrvegaKriterija = kriterij.ustrezaKriteriju(osebe);
    List<Usluzbenec> seznamDrugegaKriterija = drugKriterij.ustrezaKriteriju(osebe);

    for (Usluzbenec oseba : seznamDrugegaKriterija) {
      if (!seznamPrvegaKriterija.contains(oseba)) {
        seznamPrvegaKriterija.add(oseba);
      }
    }
    return seznamPrvegaKriterija;
  }
}
