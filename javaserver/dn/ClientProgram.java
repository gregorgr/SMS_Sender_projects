
import java.io.*;
import java.util.Scanner;
import java.net.*;
import java.io.*;

public class ClientProgram {
	
	/**
	* Branje vsebine iz url naslova
	* Na zacetku se odjemalec poveze na URL naslov v http://www.fis.unm.si/ ter prebere
	*	vsebino vrstico po vrstico, presteje stevilo vrstic ter vsebino zapise v datoteko s
	* imenom vsebina.txt. Na standardni izhod izpise sporocilo: Povezal sem se na URL
	* naslov, prebral sem vsebino in jo shranil v datoteko. Stevilo vrstic je: XX.
	**/
	private static  int naloga_1_1(String readUrl, String shraniV)throws Exception{
		
		//ustvarimo URL objekt
        URL url = new URL(readUrl);
        //ustvarimo bralca z medpomnilikom, ki bere iz URLja s pomocjo bralca vhodnega toka
        BufferedReader in = new BufferedReader(
            new InputStreamReader(url.openStream()));	
			
		// output stream
		PrintWriter outputStream = null;
		int stevec = 0;
       
		
        try {
			// zqapisemo v datoteko
            outputStream = new PrintWriter(new FileWriter(shraniV));

            String l;
			
			String inputLine;
			
			//beremo iz toka vse dokler je kaj za prebrati
			while ((inputLine = in.readLine()) != null){
	
				//System.out.println(inputLine);
				outputStream.println(inputLine);
				stevec++;				
			}
			System.out.println("Povezal sem se na URL naslov " + readUrl + ", prebral sem vsebino in jo shranil v datoteko. Stevilo vrstic je: " + stevec + ".");

        } finally {
			
			//na koncu obvezno pozapremo objekte IO povezav
            if (in != null) {
                in.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
        System.out.println("Konec: naloga_1_1\n");
		System.out.println("---\n");
		return stevec;
		
	}
	
	private static int uporabnikovVnosStevila(){
		
		int stevilo = 0;
	
        System.out.print("Vnesi besedilo: ");
       
		// uporabnik vnese število
		Scanner uporabnikovVnos = new Scanner(System.in);
		System.out.print("Vpisite poljubno stevilo: ");
		stevilo = uporabnikovVnos.nextInt();
		
		return stevilo;
		
	}
	
	
	
	private static  void naloga_1_2_UDP(int port , int vrstic, int timeOutSek, int pravilniOdgovor) throws Exception {
		/*
			1.2 Potem s pomocjo UDP komunikacije poslje sporocilo strezniku, ki deluje na
		naslovu localhost na stevilki vrat 2227. Sporocilo vsebuje stevilko vrstic prebrane
		vsebine. Na standardnem izhodu se izpise sporocilo: Posiljam podatke UDP strezniku
		ter cakam na odgovor. Nastavite timeOut interval za sprejemanje na vsakih 10
		sekund.
*/
		// tu prejmem pravilni odgovor in ga konvertiram v string za primerjavo
		String pritrdilniOdgovor = String.valueOf(pravilniOdgovor);
		
        // Povezemo se na streznik
        String serverName = new String("localhost");
        System.out.println("Psiljam podatke UDP strezniku ter cakam na odgovor.");
		
		DatagramSocket clientSocket;
		
        try {
			
            clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(serverName);
            
            // Pripravimo vsebino UDP paketa
            byte[] sendBuffer = new byte[1024];
            sendBuffer = Integer.toString(vrstic).getBytes(); // Podatki se morajo spremeniti v byte za prenos
            DatagramPacket sendPacket = new DatagramPacket(
                            sendBuffer, sendBuffer.length, IPAddress, port);
							
			// posljeno UDP paket
            clientSocket.send(sendPacket);
        
            // Sprejmemo
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(
                            receiveBuffer, receiveBuffer.length);
            clientSocket.setSoTimeout(timeOutSek * 1000); // Nastavimo timeout na timeOutSek sekund (10)
            
            try {
				
				// sprejemanje UDP odgovora
                clientSocket.receive(receivedPacket);
                String sprejeto = new String(receivedPacket.getData());
				sprejeto = sprejeto.trim();
				
				// pregledam odgovor streznika ce je PRITRDILEN
				if(sprejeto.equals(pritrdilniOdgovor)){
					System.out.println("Prejel sem potrdilno sporocilo in s tem UDP odjemalec zakljucuje z delovanjem.");
				} else {
					System.out.println("NAPAKA: Prejel sem '" + sprejeto +"'");
				}
                
                
            } catch (SocketTimeoutException ste) {
                System.out.println("Potekel je zahtevek za sprejem");
            } finally {
	
				// pozaprem objekte	
				if(clientSocket!= null ){
					clientSocket.close();
				}
			}

            
        } catch (UnknownHostException ex) {
            System.out.println("Naslova ne morem razresiti na IP");
        } catch (IOException ex) {
            System.out.println(ex);
        } 
		
        System.out.println("---\n");
		
    }
	
	
	private static int stetjeBesed(String datoteka){
		
		int counter = 0;
		Scanner s = null;
		System.out.println("Naloga 2");
        try {
            s = new Scanner(new BufferedReader(new FileReader(datoteka)));
			//System.out.print("x");
            while (s.hasNext()) {
				// preberem vsebino in ne naredim nic z njo
				String vsebina = s.next();
                //System.out.println(s.next());
				/// System.out.print(".");
				// samo prestejem stevilo besed
				counter++;
            }
			System.out.println("\nIz datoteke smo prebrali " + counter + " besed.");
			
        } catch (FileNotFoundException e) {
				// Do something with 'e'
		} finally {
			
			// pozaprem objekte
            if (s != null) {
                s.close();
            }
        }
		return counter;
	}
	
	public static void tcpKomunikacija(int portTCP, int potrditveniOdgovor, int steviloBesed){
		
		System.out.println("TCP komunikacija");
        String server = new String("localhost");

        Socket clientSocket = null;
        try {
            clientSocket = new Socket(server, portTCP);
        }
        catch (UnknownHostException e) {
            System.out.println("Ne morem razresiti domene");
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("Ne morem se povezati na streznik");
            System.exit(1);
        }
		
		try{
			// Za komunikacijo s streznikom
			BufferedReader in = new BufferedReader(
									new InputStreamReader(
										clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(
								  clientSocket.getOutputStream(), true);

			// Branje iz terminala
			BufferedReader stdIn = new BufferedReader(
									   new InputStreamReader(System.in));
									   
			// pretvori vsebino ,za poslati strezniku						   
			String vsebinaZaPoslati = String.valueOf(steviloBesed);


			// Odposljemo prebrano besedilo
			out.println(vsebinaZaPoslati);
			
			// branje vrnjene vsebine
			String prejeto = in.readLine().trim();
			// preverimo potrditveno sporocilo 11
			if(prejeto.equals(String.valueOf(potrditveniOdgovor))){
				System.out.println("Prejel sem potrdilno sporocilo.");

				// prejmemo potrditveno sporocilo izvedemo posiljanje stevilke strezniku
				///int stevilo = uporabnikovVnosStevila();	
				out.println(String.valueOf(uporabnikovVnosStevila()));
				
				prejeto = in.readLine().trim();
				
				System.out.println("Sestevek, ki ga je podal streznik, je " + prejeto.trim());
				
			}else{
				System.out.println("NAPAKA: Prejel sem: '" + in.readLine() + "'");
			}
  
			// pozaprem objekte
			if (out != null){
				out.close();
			}
			if (in != null){
				in.close();
			}
			if (stdIn != null){
				stdIn.close();
			}			

		} catch (IOException e) {
		} finally{
			// pozaprem zunanji objekt
			try{
				if (clientSocket != null){
					clientSocket.close();
				}
			} catch (IOException e) {}
		
		}
	}
	
    public static void main(String[] args) throws Exception {

		int portUDP = 2227;
		int portTCP = 2228;
		
		System.out.println("\nKlient zacenja z delom!");

		String datoteka = "xanadu.txt";

		// naloga 
		int vrstic = naloga_1_1("https://www.fis.unm.si/", "vsebina.txt");
		
		// 1.2 UDP komunikacija
		naloga_1_2_UDP(portUDP , vrstic, 10, 10);
		
		
		// 2. TCP komunikacija
		// Naloga 2.1 prestejem stevilo besed
		int steviloBesed = stetjeBesed(datoteka);
		
		int potrditveniOdgovor=11;
		
		// procedure za TCP komunikacijo
		tcpKomunikacija(portTCP, potrditveniOdgovor, steviloBesed);
		
		
	}
}