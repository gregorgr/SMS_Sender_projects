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

	public void dodajAvto(Avto avto)
	{
		this.avtomobili.add(avto);
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
		dat.println("###");

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
				this.avtomobili.add(novAvto);
			}
		}
		dat.close();
	}
}