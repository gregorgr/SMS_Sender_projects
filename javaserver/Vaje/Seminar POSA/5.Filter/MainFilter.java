import java.util.List;
import java.util.ArrayList;



class MainFilter{


  public static void main(String[] args) {
    List<Usluzbenec> osebe = new ArrayList<Usluzbenec>();

    osebe.add(new Usluzbenec("Tone", "Moski", "DA"));
    osebe.add(new Usluzbenec("Janez", "Moski", "NE"));
    osebe.add(new Usluzbenec("Ana", "Zenska", "NE"));
    osebe.add(new Usluzbenec("Kristina", "Zenska", "DA"));
    osebe.add(new Usluzbenec("Marko", "Moski", "NE"));
    osebe.add(new Usluzbenec("Brane", "Moski", "DA"));

    Kriterij moski = new KriterijMoski();
    Kriterij zenske = new KriterijZenske();
    Kriterij upokojenci = new KriterijUpokojenec();
    Kriterij upokojeniMoski = new DodajKriterij( upokojenci, moski);
    Kriterij upokojenAliZenska= new AliKriterij( upokojenci, zenske);

    System.out.println("\nMoški: ");
    izpisiOsebe(moski.ustrezaKriteriju(osebe));

    System.out.println("\nŽenske: ");
    izpisiOsebe(zenske.ustrezaKriteriju(osebe));

    System.out.println("\nUpokojeni moški: ");
    izpisiOsebe( upokojeniMoski.ustrezaKriteriju(osebe));

    System.out.println("\nUpokojen ali ženska: ");
    izpisiOsebe(upokojenAliZenska.ustrezaKriteriju(osebe));
  }

  public static void izpisiOsebe(List<Usluzbenec> osebe) {
    for (Usluzbenec oseba : osebe) {
      System.out.println(oseba);
    }
  }
}
