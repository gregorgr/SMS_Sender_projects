import java.net.*;
import java.io.*;

//import org.json.*;

// SMSGatewayServer
// TCPIP-GateawayServer
 // SMSServerProgram

public class SMSServerProgram {

	private int portTCP = 2228;
	private String apiKey 			= "06E0CB04-F8DE-4A2C-A891-D253192EFA06";
	private String adminApiKey 	= "UPDATE-F8DE-4A2C-A891-D253192EFA06";

	private int connectionCounter = 0;

	private boolean apiKeyOK(String inLine){


		System.out.println();
		if(inLine.indexOf("key=")==0){

			inLine= 	inLine.substring(4);
			if (inLine.equals(this.apiKey)){
				System.out.println("key: " + inLine + " OK");
				return true;
			}
		}
		return false;
	}

	/**
	* TCP server za SMS komunikacijo
	**/
	public void smsTcpServer(){

		int stetjeInterakcij = 0;
		this.connectionCounter++;
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
				// // vzpostavimo TCP srež7nik za komunikacijo
        System.out.println("Čakam klienta [" + this.connectionCounter + "]...");

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

						odgovor = "OK";
						out.println(odgovor+":"+stetjeInterakcij);
					}

					// System.out.print("Sestevam " + String.valueOf(sestevek) + " + " + inputLine );
					// System.out.println(" =  " + String.valueOf(sestevek));
				} catch (Exception ex){

				}

/*
				if(stetjeInterakcij==0){
					odgovor = String.valueOf(potrditevPrejema);
					System.out.println("Prejel sem podatke. Stevilo besed v datoteki je " + inputLine);

				}else{

					// sestel sem ze vse prejeto in dam v odjemalca
					odgovor= String.valueOf(sestevek);
				}
*/





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
	* Glavni program
	*
	**/
	public static void main(String[] args) {


		try{
			// vzpostavimo TCP srežnik za komunikacijo
			//	smsTcpServer(this.portTCP);
			SMSServerProgram server = new SMSServerProgram();

			while(true){
					server.smsTcpServer();
					System.out.println("");
			}

		} catch (Exception e) {
		}

	}

}
