import java.io.*;

public class Prepisovanje
{
	public static void main(String[] args) throws Exception
	{
		String imeBranje = "Besedilo.txt";
		FileReader fr = new FileReader(imeBranje);
		BufferedReader datBerem = new BufferedReader(fr);

		String imePisanje = "BesediloBrezE.txt";
		FileWriter fw = new FileWriter(imePisanje);
		PrintWriter datPisem = new PrintWriter(fw);

		while(datBerem.ready()) {
			String vrstica = datBerem.readLine();
			String novaVrstica = "";
			for(int i = 0; i < vrstica.length(); i++)
			{
				if(Character.toLowerCase(vrstica.charAt(i)) == 'e')
					novaVrstica += 'a';
				else
					novaVrstica += vrstica.charAt(i);
			}
			datPisem.println(novaVrstica);
		}
		datBerem.close();
		datPisem.close();
	}
}