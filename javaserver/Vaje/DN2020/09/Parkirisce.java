/*
9. domača naloga

Razredu
Parkirisce dodajte lastnost:
-avtobusi (seznam avtobusov) OK
in zanje dopišite enake funkcionalnosti kot za avtomobile.
Shranjevanje avtomobilov in avtobusov na parkirišču naj bo v
eno datoteko.
*/

import java.util.*;
import java.io.*;

public class Parkirisce
{
	private ArrayList<Avto> avtomobili;
	private ArrayList<Avtobus> avtobusi;


	public Parkirisce() 	{
		this.avtomobili = new ArrayList<Avto>();
		this.avtobusi = new ArrayList<Avtobus>();
	}


	public Parkirisce(int steviloAvt) {
		super();
		// avtomobili = new ArrayList<Avto>();

		for(int i=0; i < steviloAvt; i++)
		{
			try
			{
				Avto a = Avto.vnesiAvto();	// Kličemo statično metodo vnesiPotnika()
				this.avtomobili.add(a);
			}
			catch (Exception e)
			{
				System.out.println("Prišlo je do napake pri vnosu avta!");
			}
		}
	}

	public ArrayList<Avto> getAvtomobili()
	{
		return this.avtomobili;
	}

	public ArrayList<Avtobus> getAvtobusi()
	{
		return this.avtobusi;
	}

	public String dodajAvto(Avto avto) // Metodi dodamo vračanje niza za obveščanje uporabnika
	{
		// Avto dodamo samo, če avta s to registracijo če ni na parkirišču
		boolean obstaja = false;
		for(Avto a : this.avtomobili)
		{
			if(a.getRegistracija().equals(avto.getRegistracija()))
			{
				obstaja = true;
				break;
			}
		}

		if(obstaja == false)
			this.avtomobili.add(avto);
		else
			return "Avto s takšno registracijo če obstaja, zato ga ne bomo dodali.";
			/*
			 *  Opomba: V tej metodi ne moremo neposredno uporabiti izpisa s System.out.println(), ker jo kličemo iz statične metode.
			 */
		return "";
	}



public String dodajAvtobus(Avtobus novAvtobus){
	// TODO
	// 	dodajAvtobus
	boolean obstaja = false;
	for(Avtobus b : this.avtobusi)
	{
		if(b.getRegistracija().equals(novAvtobus.getRegistracija()))	{
			obstaja = true;
			break;
		}
	}

	if(obstaja == false)
		this.avtobusi.add(novAvtobus);
	else
		return "Avtobus s takšno registracijo če obstaja, zato ga ne bomo dodali.";
		/*
		 *  Opomba: V tej metodi ne moremo neposredno uporabiti izpisa s System.out.println(), ker jo kličemo iz statične metode.
		 */
	return "";

}


	public String toString()
	{
		String opis = "";
		if(this.avtomobili.size() > 0)	{

			opis += "Na parkirišču so naslednji avtomobili:\n\n";

			for(int i=0; i < this.avtomobili.size(); i++) 	{
				opis += this.avtomobili.get(i).toString() + "\n";
			}

		} 	else 	{

			opis += "Na parkirišču trenutno ni avtomobilov. \n";
		}
		// izpiše še avtobuse
		if(this.avtobusi.size() > 0) {

			opis += "Na parkirišču so naslednji BUS-i:\n\n";
			for(int i=0; i < this.avtobusi.size(); i++) 	{
				opis += this.avtobusi.get(i).toString() + "\n";
			}
		} else {
			opis += "Na parkirišču trenutno ni BUS-ov. \n";
		}

		return opis;
	}

	// Metoda shrani podatke o avtomobilih na parkirišču v datoteko
	public void shraniVDatoteko(String imeDatoteke) throws IOException
	{
		FileWriter fw = new FileWriter(imeDatoteke, true); // Drugi parameter določa, da se če obstoječi datoteki zapis doda
		PrintWriter dat = new PrintWriter(fw);

		for(Avto avto : this.avtomobili)
		{
			dat.print(avto.shraniKotNiz());
		}
		dat.println("###");

		// dodaj avtobese
		for(Avtobus bus : this.avtobusi)
		{
			dat.print(bus.shraniKotNiz());
		}
		dat.println("###");

		dat.close();
	}

	// Metoda doda avtomobile, shranjene v datoteki, na parkirišče
	public int dodajIzDatoteke(String imeDatoteke) 	throws FileNotFoundException{

		FileReader fr = new FileReader(imeDatoteke);
		BufferedReader dat = new BufferedReader(fr);

		ArrayList<String> avtoPodatki;
		int stevecDodanih = 0;

		try{

			while(dat.ready())	{

				String vrstica = dat.readLine().trim().toUpperCase();
				if(vrstica.equals("*A"))	// če se vrstica začne z *A, začnemo brati podatke za avto
				{
					avtoPodatki = new ArrayList<String>();
					while(dat.ready() && !vrstica.equals("##"))  {
						vrstica = dat.readLine().trim();
						avtoPodatki.add(vrstica);
					}

					Avto novAvto = Avto.preberiIzNiza(avtoPodatki);

					if(!obstajaAvtoZRegistracijo(novAvto)) 	{
						this.avtomobili.add(novAvto);
						stevecDodanih++;
					}	else	{
						System.out.println("Avto z registracijo " + novAvto.getRegistracija() + " če obstaja, zato ne bo dodan.");
					}
				}else if(vrstica.equals("*BUS")){

					// TODO
					// vnsedi proceduro
					avtoPodatki = new ArrayList<String>();
					while(dat.ready() && !vrstica.equals("##")) {
							vrstica = dat.readLine().trim();
							avtoPodatki.add(vrstica);
						}

						Avtobus novBus = Avtobus.preberiIzNiza(avtoPodatki);

						if(!obstajaBusZRegistracijo(novBus)) {
							this.avtobusi.add(novBus);
							stevecDodanih++;
						} else {
							System.out.println("Avto z registracijo " + novBus.getRegistracija() + " če obstaja, zato ne bo dodan.");
						}
				}
			}
		} catch (Exception e){
			//
		}finally{


			try{
				dat.close();
			//} catch (Exception ex){
			} catch (IOException ex){
			}
		}

		return stevecDodanih;
	}

	// Metoda preveri, če je na parkirišču še kakšen avto z enako registracijo

	private boolean obstajaBusZRegistracijo(Avtobus b){

		//return obstajaVoziloZRegistracijo((Vozilo)(IVozilo)b, this.avtobusi);
		boolean obstaja = false;
		for(Vozilo v : this.avtobusi)
		{
			if(v.getRegistracija().equals(b.getRegistracija()))
			{
				obstaja = true;
				break;
			}
		}
		return obstaja;
	}

	private boolean obstajaAvtoZRegistracijo(Avto avto){

		//return obstajaVoziloZRegistracijo((Vozilo)(IVozilo)avto, );
		boolean obstaja = false;
		for(Vozilo v : this.avtomobili)
		{
			if(v.getRegistracija().equals(avto.getRegistracija()))
			{
				obstaja = true;
				break;
			}
		}
		return obstaja;

	}


	public String izpisiAvtomobilePoSteviluPotnikov(int st)
	{
		String izpis = "Naslednji avtomobili/busi na parkirišču imajo število potnikov enako vsaj " + st + ":\n\n";

		for(Avto a : this.getAvtomobili()) 	{
			if(a.getPotniki().size() >= st)
				izpis += a.toString() + "\n";
		}
		for(Avtobus b : this.getAvtobusi()) 	{
			int potniki = b.getStSedezev() + b.getStStojisc();
			if(potniki  >= st)
				izpis += b.toString() + "\n";
		}
		return izpis;
	}
}
