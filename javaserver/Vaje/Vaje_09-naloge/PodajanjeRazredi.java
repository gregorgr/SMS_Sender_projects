public class PodajanjeRazredi
{
	public static void main(String[] args)
	{
		int stevilo = 3;
		System.out.println("Prej: stevilo = " + stevilo);
		podajOsnovni(stevilo);
		System.out.println("Potem: stevilo = " + stevilo);

		/*
		Avto mojAvto = new Avto();
		System.out.println("Prej: maksimalna hitrost = " + mojAvto.getMaxHitrost());
		podajSklicni(mojAvto);
		//podajSklicni2(mojAvto);
		System.out.println("Potem: maksimalna hitrost = " + mojAvto.getMaxHitrost());
		*/
	}

	public static void podajOsnovni(int steviloX)
	{
		// Spremenimo vrednost spremenljivki, vendar bo njena vrednost zunaj metode ostala enaka
		steviloX = steviloX + 1;
		System.out.println("V metodi: stevilo = " + steviloX);
	}

	public static void podajSklicni(Avto avto)
	{
		// Spremenimo samo lastnost objekta, ki smo ga ustvarili zunaj metode,
		// zato kazalec na objekt ostane isti
		avto.setMaxHitrost(90);
		System.out.println("V metodi: maksimalna hitrost = " + avto.getMaxHitrost());
	}

	public static void podajSklicni2(Avto avto)
	{
		// Ustvarimo nov objekt
		avto = new Avto();
		// Sedaj spremenimo lastnost objektu, ki smo ga ustvarili znotraj metode in zunanji ostane nespremenjen
		avto.setMaxHitrost(90);
		System.out.println("V metodi: maksimalna hitrost = " + avto.getMaxHitrost());
	}
}