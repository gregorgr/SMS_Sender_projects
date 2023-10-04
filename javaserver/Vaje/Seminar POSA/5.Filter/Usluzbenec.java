

import java.util.List;
import java.util.ArrayList;


class Usluzbenec {
  private String ime;
  private String spol;
  private String upokojenStatus;

  public Usluzbenec(String ime, String spol, String r) {
    this.ime = ime;
    this.spol = spol;
    this.upokojenStatus = r;
  }

  public String getIme() {
    return ime;
  }

  public String getSpol() {
    return spol;
  }

  public String getUpokojenStatus() {
    return upokojenStatus;
  }

  @Override
  public String toString() {
    return "Usluzbenec [ime=" + ime + ", spol=" + spol
        + ", upokojenStatus=" + upokojenStatus + "]";
  }
}
