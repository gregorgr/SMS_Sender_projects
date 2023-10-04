import java.util.*;
import java.io.*;

public class Parkirisce
{
	private ArrayList<Avto> avtomobili;


	public Parkirisce()
	{
		avtomobili = new ArrayList<Avto>();
	}

	public Parkirisce(int steviloAvt)
	{
		avtomobili = new ArrayList<Avto>();

		for(int i=0; i < steviloAvt; i++)
		{
			try
			{
				Avto a = Avto.vnesiAvto();	// Klièemo statièno metodo vnesiPotnika()
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

	public String dodajAvto(Avto avto) // Metodi dodamo vraèanje niza za obvešèanje uporabnika
	{
		// Avto dodamo samo, èe avta s to registracijo še ni na parkirišèu
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
			return "Avto s takšno registracijo že obstaja, zato ga ne bomo dodali.";
			/*
			 *  Opomba: V tej metodi ne moremo neposredno uporabiti izpisa s System.out.println(), ker jo klièemo iz statiène metode.
			 */
		return "";
	}

	public String toString()
	{
		String opis = "";
		if(this.avtomobili.size() > 0)
		{
			opis += "Na parkirišèu so naslednji avtomobili:\n\n";

			for(int i=0; i < this.avtomobili.size(); i++)
			{
				opis += this.avtomobili.get(i).toString() + "\n";
			}
		}
		else
		{
			opis += "Na parkirišèu trenutno ni avtomobilov. \n";
		}
		return opis;
	}

	// Metoda shrani podatke o avtomobilih na parkirišèu v datoteko
	public void shraniVDatoteko(String imeDatoteke) throws IOException
	{
		FileWriter fw = new FileWriter(imeDatoteke, true); // Drugi parameter doloèa, da se že obstojeèi datoteki zapis doda
		PrintWriter dat = new PrintWriter(fw);

		for(Avto avto : this.avtomobili)
		{
			dat.print(avto.shraniKotNiz());
		}

		dat.close();
	}

	// Metoda doda avtomobile, shranjene v datoteki, na parkirišèe
	public void dodajIzDatoteke(String imeDatoteke) throws Exception
	{
		FileReader fr = new FileReader(imeDatoteke);
		BufferedReader dat = new BufferedReader(fr);

		ArrayList<String> avtoPodatki;
		while(dat.ready())
		{
			String vrstica = dat.readLine().trim().toUpperCase();
			if(vrstica.equals("*A"))	// Èe se vrstica zaène z *A, zaènemo brati podatke za avto
			{
				avtoPodatki = new ArrayList<String>();
				while(dat.ready() && !vrstica.equals("##"))
				{
					vrstica = dat.readLine().trim();
					avtoPodatki.add(vrstica);
				}

				Avto novAvto = Avto.preberiIzNiza(avtoPodatki);

				if(!obstajaAvtoZRegistracijo(novAvto))
					this.avtomobili.add(novAvto);
			}
			else if(vrstica.equals("*K"))	// Èe se vrstica zaène s *K, zaènemo brati podatke za kabrio
			{
				avtoPodatki = new ArrayList<String>();
				while(dat.ready() && !vrstica.equals("##"))
				{
					vrstica = dat.readLine().trim();
					avtoPodatki.add(vrstica);
				}

				Kabrio novAvto = Kabrio.preberiIzNiza(avtoPodatki);

				if(!obstajaAvtoZRegistracijo(novAvto))
					this.avtomobili.add(novAvto);
			}
			else if(vrstica.equals("*L"))	// Èe se vrstica zaène z *L, zaènemo brati podatke za limuzino
			{
				avtoPodatki = new ArrayList<String>();
				while(dat.ready() && !vrstica.equals("##"))
				{
					vrstica = dat.readLine().trim();
					avtoPodatki.add(vrstica);
				}

				Limuzina novAvto = Limuzina.preberiIzNiza(avtoPodatki);

				if(!obstajaAvtoZRegistracijo(novAvto))
					this.avtomobili.add(novAvto);
			}
		}
		dat.close();
	}

	// Metoda preveri, èe je na parkirišèu že kakšen avto z enako registracijo
	private boolean obstajaAvtoZRegistracijo(Avto avto)
	{
		boolean obstaja = false;
		for(Avto a : this.avtomobili)
		{
			if(a.getRegistracija().equals(avto.getRegistracija()))
			{
				obstaja = true;
				break;
			}
		}
		return obstaja;
	}

	public String izpisiAvtomobilePoSteviluPotnikov(int st)
	{
		String izpis = "Naslednji avtomobili na parkirišèu imajo število potnikov enako vsaj " + st + ":\n\n";

		for(Avto a : this.getAvtomobili())
		{
			if(a.getPotniki().size() >= st)
				izpis += a.toString() + "\n";
		}
		return izpis;
	}
}