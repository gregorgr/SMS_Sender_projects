import java.util.List;
import java.util.ArrayList;

/**
* Ustvarimo objekt  za vsak posamezen kriterij
* z javno funkcijo ustrezaKriteriju, ki podan parameter prefiltrira
* in vrne seznam zadetkov
**/
class KriterijZenske implements Kriterij {

  @Override
  public List<Usluzbenec> ustrezaKriteriju(List<Usluzbenec> osebe) {

    List<Usluzbenec> zenskeOsebe = new ArrayList<Usluzbenec>();

    for (Usluzbenec oseba : osebe) {
      // zanka poišče vse elemente, ki ustrezajo kriteriju
      if (oseba.getSpol().equalsIgnoreCase("Zenska")) {
        zenskeOsebe.add(oseba);
      }
    }
    // vrenemo rezultat zadetkov
    return zenskeOsebe;
  }
}
