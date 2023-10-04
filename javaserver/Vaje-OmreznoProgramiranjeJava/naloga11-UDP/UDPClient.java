// V tej nalogi bomo ustvarili odjemalca in streznika, ki komunicirata
// s pomocjo UDP povezave. Odjemalski process prebere vnos uporabnika in
// ga poslje strezniku. Streznik vrne isto sporocilo transformirano v
// velike crke.
import java.io.*;
import java.net.*;

public class UDPClient {

    public static void main(String[] args) throws Exception {
        
        // Povežemo se na strežnik
        String server = new String("localhost");
        int port = 2400;
        
        try {
            DatagramSocket clientSocket = new DatagramSocket();
        
            // Preberemo vnos uporabnika
            BufferedReader inFromUser = new BufferedReader(
                                        new InputStreamReader(System.in));
            System.out.print("Vnesi stavek: ");
            String prebrano = inFromUser.readLine();
        
            InetAddress IPAddress = InetAddress.getByName(server);
            
            // Posljemo
            byte[] sendBuffer = new byte[1024];
            sendBuffer = prebrano.getBytes(); // Podatki se morajo spremeniti v byte za prenos
            DatagramPacket sendPacket = new DatagramPacket(
                            sendBuffer, sendBuffer.length, IPAddress, port);
            clientSocket.send(sendPacket);
        
            // Sprejmemo
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(
                            receiveBuffer, receiveBuffer.length);
            clientSocket.setSoTimeout(10000); // Nastavimo timeout na 10 sekund
            
            try {
                clientSocket.receive(receivedPacket);
                String sprejeto = new String(receivedPacket.getData());
                System.out.println("Nazaj sem dobil: " + sprejeto);
                
            }
            catch (SocketTimeoutException ste) {
                System.out.println("Potekel je zahtevek za sprejem");
            }
            
            clientSocket.close();
        }
        catch (UnknownHostException ex) {
            System.out.println("Naslova ne morem razrešiti na IP");
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
            
    }
    
}
