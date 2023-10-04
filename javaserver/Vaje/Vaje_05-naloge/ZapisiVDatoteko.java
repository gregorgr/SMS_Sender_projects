import java.io.*;

public class ZapisiVDatoteko
{
	public static void main(String[] args) throws Exception
	{
		String ime = "NovaDatoteka.txt";

		FileWriter fw = new FileWriter(ime);
		PrintWriter dat = new PrintWriter(fw);

		dat.println("Prva vrstica v datoteki.");
		dat.println();
		dat.println("Zadnja vrstica v datoteki.");
		dat.close();
	}
}