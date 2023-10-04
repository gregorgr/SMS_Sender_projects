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
				Avto a = Avto.vnesiAvto();	// Kli�emo stati�no metodo vnesiPotnika()
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

	public String dodajAvto(Avto avto) // Metodi dodamo vra�anje niza za obve��anje uporabnika
	{
		// Avto dodamo samo, �e avta s to registracijo �e ni na parkiri��u
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
			 *  Opomba: V tej metodi ne moremo neposredno uporabiti izpisa s System.out.println(), ker jo kli�emo iz stati�ne metode.
			 */
		return "";
	}

	public String toString()
	{
		String opis = "";
		if(this.avtomobili.size() > 0)
		{
			opis += "Na parkirišču so naslednji avtomobili:\n\n";

			for(int i=0; i < this.avtomobili.size(); i++)
			{
				opis += this.avtomobili.get(i).toString() + "\n";
			}
		}
		else
		{
			opis += "Na parkirišču trenutno ni avtomobilov. \n";
		}
		return opis;
	}

	// Metoda shrani podatke o avtomobilih na parkiri��u v datoteko
	public void shraniVDatoteko(String imeDatoteke) throws IOException
	{
		FileWriter fw = new FileWriter(imeDatoteke, true); // Drugi parameter dolo�a, da se �e obstoje�i datoteki zapis doda
		PrintWriter dat = new PrintWriter(fw);

		for(Avto avto : this.avtomobili)
		{
			dat.print(avto.shraniKotNiz());
		}

		dat.close();
	}

	// Metoda doda avtomobile, shranjene v datoteki, na parkiri��e
	public void dodajIzDatoteke(String imeDatoteke) throws Exception
	{
		FileReader fr = new FileReader(imeDatoteke);
		BufferedReader dat = new BufferedReader(fr);

		ArrayList<String> avtoPodatki;
		while(dat.ready())
		{
			String vrstica = dat.readLine().trim().toUpperCase();
			if(vrstica.equals("*A"))	// �e se vrstica za�ne z *A, za�nemo brati podatke za avto
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
		}
		dat.close();
	}

	// Metoda preveri, �e je na parkiri��u �e kak�en avto z enako registracijo
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
		String izpis = "Naslednji avtomobili na parkiri��u imajo �tevilo potnikov enako vsaj " + st + ":\n\n";

		for(Avto a : this.getAvtomobili())
		{
			if(a.getPotniki().size() >= st)
				izpis += a.toString() + "\n";
		}
		return izpis;
	}
}
