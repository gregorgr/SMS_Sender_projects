/*
9. domača naloga

Ustvarite:
1. razred Avtobus z lastnostmi:
- registracija, this.registracija OK
- velikost (dvonadstropni, navadni, mini) in VelikostBusa velikost; OK
- sofer  soferBusa

Razredu
Parkirisce dodajte lastnost:
-avtobusi (seznam avtobusov)
in zanje dopišite enake funkcionalnosti kot za avtomobile.
Shranjevanje avtomobilov in avtobusov na parkirišču naj bo v
eno datoteko.
*/



import java.io.*;
import java.util.*;

/*
 * 	Razred Avtobus je podrazred razreda Avto. Deduje vse lastnosti, ki jih ima avto,
 *	ima pa še dve dodatni.
 */
public class Avtobus extends Avto
{
	private int stSedezev;
	private int stStojisc;

	// dodan šofer
	private String soferBusa;

	// dodana velikost busa kot enum
	private VelikostBusa velikost;

	// VelikostBusa.valueOf("dvonadstropni").ordinal());
	/*
	for (VelikostBusa busVelikost : VelikostBusa.values()) {
			System.out.println(day);
	}
	*/
	public enum VelikostBusa {
		mini,
		navadni,
		dvonadstropni,
		neznan;
	}



	public Avtobus(){
		this(40, 20);
	}

	public Avtobus(int sed, int sto)
	{
		this(sed, sto, "Neznana");
	}

	public Avtobus(int sed, int sto,  String zacRegistracija)
	{
		this(sed, sto, VelikostBusa.navadni, "Neznana");
	}

	public Avtobus(int sed, int sto, VelikostBusa vel)
	{
		this(sed, sto, vel, "Neznana");
	}

	public Avtobus(int sed, int sto, VelikostBusa vel, String zacRegistracija)
	{
		this.stSedezev = sed;
		this.stStojisc = sto;
		this.velikost = vel;
		this.registracija = zacRegistracija;
	}


	// Set metodi
	public void setStSedezev(int s)
	{
		this.stSedezev = s;
	}


	public void setStStojisc(int s)
	{
		this.stStojisc = s;
	}

	public void setSoferBusa(String zacSofer)
	{
		this.soferBusa = zacSofer;
	}

	/*
	public void setRegistracija(String zacRegistracija)
	{
		this.registracija = zacRegistracija;
	}*/

	public void setVelikostBusa(VelikostBusa vel)
	{
		this.velikost = vel;
	}

	// Get metodi
	public int getStSedezev()
	{
		return this.stSedezev;
	}

	public int getStStojisc()
	{
		return this.stStojisc;
	}



	public String getSoferBusa()
	{
		return this.soferBusa;
	}

/*
	public String getRegistracija()
	{
		return this.registracija ;
	}
*/

	public VelikostBusa getVelikostBusa()
	{
		return this.velikost;
	}




	public void izpisiPodatke()
	{
		super.izpisiPodatke();
		System.out.println("Sedišč: " + this.stSedezev);
		System.out.println("Stojišč: " + this.stStojisc);

		//TODO
		// Šofer
		System.out.println();
	}

	/*
	 *  Metoda toString() je definirana že v razredu Object, ki je prednik razreda Avto.
	 *  Namenjena je izpisu opisa objekta - vrne String z opisom. če želimo opis za avto imeti
	 *  drugačen od privzetega opisa objekta, jo moramo "povoziti" v razredu Avto.
	 */
	 @Override
	public String toString()
	{
		String opis = "";
		opis += "Bus z registracijo: " + this.getRegistracija() + "\n";
		opis += "Tip velikosti: " + this.velikost + "\n";
		opis += "Šofer: " + this.soferBusa + "\n";
		opis += "Sedišč: " + this.stSedezev + "\n";
		opis += "Stojišč: " + this.stStojisc + " \n";
		//opis += " -" + this.sofer.toString() + " \n";
		//opis += "Potniki: \n";

		return opis;  // Na koncu vrnemo opis
	}


	public static Avtobus vnesiBus() throws Exception
	{

		BufferedReader vhod = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("***   Vnos novega avtobusa:   ***");
		System.out.print(" Vnesi registracijo [Neznana]: ");
		String reg = prednastavljeno(vhod.readLine(), "Neznana");

		System.out.print(" Vnesi ime šoferja [Tone]: ");
		String sofer = prednastavljeno(vhod.readLine(), "Tone");



		System.out.print(" Vnesi število sedežev: ");
		int steviloSed = Integer.parseInt(vhod.readLine());

		System.out.print(" Vnesi število stojišč: ");
		int steviloStojisc = Integer.parseInt(vhod.readLine());
		//
		VelikostBusa vel = vnesiVelikostBusa(vhod);

		Avtobus bus = new Avtobus(steviloSed, steviloStojisc, vel, reg);
		bus.setSoferBusa(sofer);
		return bus;
		// Ustvarimo nov objekt s podanima podatkoma

	}

//
	private static VelikostBusa vnesiVelikostBusa(BufferedReader v)  throws Exception{
		System.out.println("Vnesi velikost busa:");

		int i=1;
		for (VelikostBusa busVelikost : VelikostBusa.values()) {
				System.out.println(i+ " " + busVelikost);
				i++;
		}
		System.out.print(" Številko velikosti [2]: ");
		// TOTO
		// vnosi
		int intVelikost = Integer.parseInt(v.readLine());

		return VelikostBusa.values()[intVelikost-1]; // VelikostBusa.navadni;
	}

	private static String prednastavljeno(String vnos, String defaultStr){

			if( vnos.length()==0){
				return defaultStr;
			}else{
				return vnos;
			}
	}

	/*
	 *	Metoda zapiše podatke o avtu v poseben niz, iz katerega znamo enolično razbrati, za kateri avto gre.
	 *  Implementiramo jo kot objektno metodo
	 */
	public String shraniKotNiz()
	{
		String zapis = "*BUS\r\n";				// Zapišemo kodo "A", ki označuje avto
		zapis += this.registracija + "\r\n";	// Zapišemo registracijo
		zapis += this.stSedezev + "\r\n";	// Zapišemo št sedežev
		zapis += this.stStojisc + "\r\n";			// Zapišemo this.stStojisc
		// TODO
		zapis += this.velikost + "\r\n";			// Zapišemo this.stStojisc
		zapis += this.soferBusa + "\r\n";			// Zapišemo this.stStojisc
		zapis += "##\r\n";					// Označimo konec branja
		return zapis;
	}
//this.registracija: Neznana
//23
//2
//mini
//null

	public static VelikostBusa parseVelikost(String strVelikost){

		for (VelikostBusa busVelikost : VelikostBusa.values()) {
				//if(String(strVelikost.trim()).equals(busVelikost.toString())) {
				if(strVelikost.equalsIgnoreCase(busVelikost.name())) {
					return busVelikost;
				}

		}
		return VelikostBusa.neznan;
	}
	/*
	 *	Metoda iz danega seznama nizov rekonstruira avto, ki je bil z danimi podatki shranjen.
	 *  Implementiramo jo kot statično metodo
	 */


	{
		Avtobus bus = new Avtobus();  // Najprej ustvarimo objekt, kateremu bomo nastavili podane lastnosti
		try 		{
			/*
			*BUS
			this.registracija: 0: LJ BUS1
			this.stSedezev:1: 	23
			this.stStojisc:2:		2
			this.velikost:3: 		mini
			this.soferBusa:4: 	Francelj
			*/

			// Prvi element v seznamu je registracija
			bus.setRegistracija(zapis.get(0));
			// Drugi element v seznamu je maksimalna hitrost
			bus.setStSedezev(Integer.parseInt(zapis.get(1)));
			bus.setStStojisc(Integer.parseInt(zapis.get(2)));

			bus.setVelikostBusa(Avtobus.parseVelikost(zapis.get(3)));

			bus.setSoferBusa(zapis.get(4));
			// Preostali elementi so potniki
			/// ArrayList<String> potnikPodatki;
			/*
			for(int i=2; i < zapis.size(); i++)
			{
				if(zapis.get(i).trim().equals("*P"))	// če vrstica vsebuje *P, imamo zapis o potniku
				{
					potnikPodatki = new ArrayList<String>();	// Pripravimo nov seznam, v katerega bomo dodajali podatke o trenutnem potniku
					i++;
					while(!zapis.get(i).trim().equals("#"))	// Dokler se zapis o potniku ne konča (dokler se ne pojavi #), dodajamo podatke v seznam
					{
						potnikPodatki.add(zapis.get(i));
						i++;
					}
					Potnik potnik = Potnik.preberiIzNiza(potnikPodatki);

					avto.potniki.add(potnik);
				}
			}*/
			return bus;
		}
		catch(Exception ex)
		{
			System.out.println("Prišlo je do napake v zapisu!");
			throw ex;
		}
	}

}
