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
				Avto a = Avto.vnesiAvto();	// Kličemo stati�no metodo vnesiPotnika()
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

	public void odstraniAvto(int stParkirnegaMesta){


		// remove()
		for(int i=0; i < this.avtomobili.size(); i++){

			if(this.avtomobili.get(i).getParkirniProstor() == stParkirnegaMesta){

				System.out.println("Avto  mesto z registrsko št  " + this.avtomobili.get(i).getRegistracija() + " odstranjen iz parkirnega mesta " + stParkirnegaMesta + " \n");
				this.avtomobili.remove(i);
				return;
			}
		}
		System.out.println("Parkirno mesto   " + stParkirnegaMesta + " je PROSTO.\n" );

	}
	/*
	*
	* funkcija najde prvo prazno parkirano mesto
	**/
	private int najdiPrvoPraznoParkirisce(){

		int prostoMesto = this.avtomobili.size() + 1;

		if (prostoMesto  > 1){

			// iščem prvo prazno mesto
			prostoMesto = 0;
			boolean prosto = false;

			while (!prosto){
				// prvo naslednje prazno mesto
				prostoMesto++;

				for(int i=0; i < this.avtomobili.size(); i++){

					if(this.avtomobili.get(i).getParkirniProstor()  == prostoMesto){
						prosto = false;
						break;
					}else{
						prosto = true;
					}
				}
			}
		}

		return prostoMesto;
	}

	public void dodajAvto(Avto avto)
	{
		// shranim avto na naslednje prazno parkirno mesto in začnem z 1
		int naslednjeNezasedenoParkirnoMesto = najdiPrvoPraznoParkirisce();


		avto.setparkirniProstor(naslednjeNezasedenoParkirnoMesto);
		this.avtomobili.add(avto);
	}

	@Override
	public String toString()
	{

		String opis = "";

		if (this.avtomobili.size()==0){

			opis = "Parkirišče je PRAZNO!";

		}else{

			opis = "Na parkirišču so naslednji avtomobili:\n\n";

			for(int i=0; i < this.avtomobili.size(); i++)
			{
				opis += this.avtomobili.get(i).toString() + "\n";
			}
		}
		return opis+"\n";
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
