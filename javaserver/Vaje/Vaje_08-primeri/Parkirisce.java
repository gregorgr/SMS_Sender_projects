import java.util.*;

public class Parkirisce
{
	private ArrayList<Avto> avtomobili;


	public Parkirisce()
	{
		avtomobili = new ArrayList<Avto>();
	}

	public ArrayList<Avto> getAvtomobili()
	{
		return this.avtomobili;
	}

	public void dodajAvto(Avto avto)
	{
		this.avtomobili.add(avto);
	}

	public static void main(String[] args)
	{
		// Ustvarimo nov objekt tipa Parkirisce
		Parkirisce mojeParkirisce = new Parkirisce();

		// Na parkirisce dodajmo avtomobile
		Avto prviAvto = new Avto("abc", 1);
		Avto drugiAvto = new Avto("efg", 2);

		mojeParkirisce.dodajAvto(prviAvto);
		mojeParkirisce.dodajAvto(drugiAvto);

		// Izpisi avtomobile na parkiriscu
		for(int i=0; i < mojeParkirisce.avtomobili.size(); i++)
		{
			System.out.println(mojeParkirisce.avtomobili.get(i).toString());
		}
	}
}