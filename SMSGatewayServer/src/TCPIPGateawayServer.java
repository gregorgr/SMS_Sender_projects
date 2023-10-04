import java.net.*;
import java.io.*;

// 


public class TCPIPGateawayServer {
	
	
	private int portTCP = 2228;
	private String[] apiKey 	= {"06E0CB04-F8DE-4A2C-A891-D253192EFA06"};
	private String adminApiKey 	= "UPDATE-F8DE-4A2C-A891-D253192EFA06";

	private int connectionsCounter = 0;
	private boolean isAdmin = false;
	
	
	public void smsTcpServer(){

		int stetjeInterakcij = 0;
		this.connectionsCounter++;
		
		ServerSocket serverSocket = null;
		Socket clientSocket = null;

		 try {
        // Ustvarimo socket/ vticico
	        try {
	            serverSocket = new ServerSocket(this.portTCP);
	        }
	        catch (IOException e) {
	            // zasedena vrata
	            System.out.println("Nisem mogel zasesti vrat: " + this.portTCP);
	            System.exit(1);
	        }
				// // vzpostavimo TCP srežnik za komunikacijo
	        System.out.println("Čakam klienta [" + this.connectionsCounter + "]...");

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

				// najprej preberem ključ
				// potrimam prejeto vsebino

				inputLine = inputLine.trim();

				String odgovor = "";
				// sestejem vsakic
				try{

					System.out.println("Prejel["+stetjeInterakcij+"]: " + inputLine);

						// pri interakciji 0 preverimo API key
					if (stetjeInterakcij==0 ){

						if(apiKeyOK(inputLine)){

							System.out.println("apiKeyOK čakam navodila" );
							odgovor = "OK";
							stetjeInterakcij++;

						}else {
							System.out.println("apiKey NI OK" );
							stetjeInterakcij=500;
							odgovor = "NO";
						}



						// Oddamo potrditev o prejemu
						out.println(odgovor+":"+stetjeInterakcij);
						odgovor = "";

					} else {
						
						stetjeInterakcij++;
						// System.out.println("prejel: '" + inputLine + "'");

						// TODO send SMS
						System.out.println("Sedaj bom poslal");
						SMSSender sms = new SMSSender();
						
						sms.messageJsnon(inputLine.trim());
						sms.action();
						
						odgovor = "OK:";
						odgovor += sms.getServerResponse();
						
						out.println(odgovor+":"+stetjeInterakcij);
					}

					// System.out.print("Sestevam " + String.valueOf(sestevek) + " + " + inputLine );
					// System.out.println(" =  " + String.valueOf(sestevek));
				} catch (Exception ex){

				}



				// izstopim po drugi interakciji s programom
				if ( stetjeInterakcij >2 ) break;
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
	 * TEsts API key 
	 * @param inLine
	 * @return
	 */
	private boolean apiKeyOK(String inLine){

		System.out.println();
		if(inLine.indexOf("key=")==0){
			
			int len = this.apiKey.length;
			
			inLine=inLine.substring(4);
			// TODO
			// admin key funkcije
			
			for(int i = 0; i < len; i++) {
				
				if (inLine.equals(this.apiKey[i])){
					System.out.println("OK key ["+i+"]: ");
					return true;
				}		
			}	
		}
		return false;
	}
	
	/**
	 * Glavni program
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			// vzpostavimo TCP srežnik za komunikacijo
			//	smsTcpServer(this.portTCP);
			TCPIPGateawayServer server = new TCPIPGateawayServer();

			while(true){
					server.smsTcpServer();
					System.out.println("");
			}

		} catch (Exception e) {
		}

	}

}
