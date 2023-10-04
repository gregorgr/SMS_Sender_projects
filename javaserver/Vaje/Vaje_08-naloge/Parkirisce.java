import java.util.*;

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

	@Override
	public String toString()
	{
		String opis = "Na parkirišèu so naslednji avtomobili:\n\n";

		for(int i=0; i < this.avtomobili.size(); i++)
		{
			opis += this.avtomobili.get(i).toString() + "\n";
		}
		return opis;
	}

	public static void main(String[] args)
	{
		// Ustvarimo nov objekt tipa Parkirisce
		Parkirisce mojeParkirisce = new Parkirisce(2);

		// Izpisi avtomobile na parkiriscu
		for(int i=0; i < mojeParkirisce.avtomobili.size(); i++)
		{
			System.out.println(mojeParkirisce.avtomobili.get(i).toString());
		}

		// Ali spodnji izpis
		//System.out.println(mojeParkirisce.toString());
	}
}