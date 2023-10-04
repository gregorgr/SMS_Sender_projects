import java.net.*;
import java.io.*;

public class ServerProgram {
	
	public static int udpServer(int portUDP, int sestevek)  throws Exception {
		
		/*
		
		1.3 Ko prejmemo odgovor s strani streznika, preverimo, ce nam je ta poslal potrdilni
		odgovor (številko 10); ce je odgovor potrdilen, na standardni izhod izpisemo sporocilo:
		Prejel sem potrdilno sporocilo in s tem UDP odjemalec zakljucuje z delovanjem.
		**/
        
        // Najprej ustvarimo Socket UDP
		
		// pritrdilni odgovor UDP streznika
		int odgovor = 10;

				
        try {
            DatagramSocket serverSocket = new DatagramSocket(portUDP);
        
            // Streznik poslusa in odgovarja v nedogled
            while (true) {
            
                // Sprejemanje UDP paketov
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receiveData = new DatagramPacket(
                                receiveBuffer, receiveBuffer.length);
                
				/// sprejemanje UDP paketov
                // System.out.println("Cakam na paket na vratih: " + portUDP);
				System.out.println("Cakam na UDP paket.");
                serverSocket.receive(receiveData);
            
                String porejetoUDP = new String(receiveData.getData());
				porejetoUDP = porejetoUDP.trim();

   
                // odgovor klientu
                byte[] sendBuffer = new byte[1024];
                int port = receiveData.getPort();
                InetAddress IPAddress = receiveData.getAddress();
                // System.out.println("Sprejel paket iz " + IPAddress + ", vrata " + port);
                System.out.println("Prejel sem UDP paket z vsebino : '" + porejetoUDP + "'" );
				
				try{
					sestevek += Integer.parseInt(porejetoUDP);
				}catch (Exception ex){
				
				}
				
					
				// pretvorim numericen odgovor v string
				System.out.println("Posiljam potrditveno sporocilo UDP odjemalcu");
                sendBuffer = Integer.toString(odgovor).getBytes();
                
                DatagramPacket sendPacket = new DatagramPacket(
                                sendBuffer, sendBuffer.length, IPAddress, port);
                serverSocket.send(sendPacket);
				
				// na tem mestu izstopim iz neskoncne zanke
				// UDP strežnik preneha z delom
				break;
                
            }
            System.out.println("UDP odjemalec zakljucuje z delovanjem");
            //serverSocket.close();
        }
        
        catch (SocketException ex) {
            System.out.println("UDP Port je zaseden.");
            System.exit(1);
        }
		
		return sestevek;
	}
	
	/**
	* TCP server za nalogo 2
	**/
	public static void tcpServer(int port, int potrditevPrejema, int sestevek){
		
		int stetjeInterakcij = 0;
		
        ServerSocket serverSocket = null;
		Socket clientSocket = null;
		
		 try {	
        // Ustvarimo socket/ vticico
        try {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e) {
            // zasedena vrata
            System.out.println("Nisem mogel zasesti vrat" + port);
            System.exit(1);
        }
        System.out.println("Cakam ...");

        // Sprejmemo odjemalca
        
        try {
            clientSocket = serverSocket.accept();
        }
        catch (IOException e) {
            System.out.println("Nisem mogel dobiti povezave");
            System.exit(1);
        }

        System.out.println("Povezan, cakam na promet.");
		
		
		
	
			  // inicializacija
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			
				// vse je pripravljeno za sprejemanje paketov
			String inputLine;

			
			// Prebiramo kaj smo dobili od odjemalca
			while ((inputLine = in.readLine()) != null) {

					
				
				// potrimam prejeto vsebino
				inputLine = inputLine.trim();
				String odgovor = "";
				// sestejem vsakic
				try{
					
					// System.out.print("Sestevam " + String.valueOf(sestevek) + " + " + inputLine );
					sestevek += Integer.parseInt(inputLine);
					// System.out.println(" =  " + String.valueOf(sestevek));
				} catch (Exception ex){
				
				}
					
				if(stetjeInterakcij==0){
					odgovor = String.valueOf(potrditevPrejema);
					System.out.println("Prejel sem podatke. Stevilo besed v datoteki je " + inputLine);
	
				}else{
					
					// sestel sem ze vse prejeto in dam v odjemalca
					odgovor= String.valueOf(sestevek);
				}
				
		
				// Oddamo potrditev o prejemu
				out.println(odgovor);
			
				//break;
				stetjeInterakcij++;
				
				// izstopim po drugi interakciji s programom
				if ( stetjeInterakcij >1 ) break;
			}

			System.out.println("TCP streznik zakljucuje z delom");


			// Zapremo "tokove"
			if(in != null){
				in.close();
			}
			if(out != null){
				out.close();
			}
 
			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			// ujamemo izjeme
		}
	}
	
	/**
	* Glavni program 
	*
	**/
	public static void main(String[] args) {
		
		int portUDP = 2227;
		int portTCP = 2228;
		
		int sestevek = 0;
		try{
			// naloga: 1 UDP komunikacija
			sestevek = udpServer(portUDP, sestevek);
			
			// naloga: 2. TCP komunikacija
			int potrditevPrejema = 11;
			tcpServer(portTCP, potrditevPrejema, sestevek);
	
	

		} 	 catch (Exception e) {
		}
		
		
		
	}
	
}