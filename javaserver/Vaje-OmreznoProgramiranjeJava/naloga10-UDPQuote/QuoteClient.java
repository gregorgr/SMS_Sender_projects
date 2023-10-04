// Razred QuoteClient izvaja odjemalski proces, ki se poveze na streznik,
// ki ga izvaja razred QuoteServer. Aplikacija poslje zahtevek strezniku,
// caka na odgovor, in ko ga pridobi ga prikaze na standarden izhod.
import java.io.*;
import java.net.*;
import java.util.*;

public class QuoteClient {
    public static void main(String[] args) throws IOException {
        //arg0 je ime streznika
        if (args.length != 1) {
             System.out.println("Usage: java QuoteClient <hostname>");
             return;
        }

        // pridobitev datagramske vticnice
        DatagramSocket socket = new DatagramSocket();

        // zahtevek k strezniku in posiljanje paketa 
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
    
        // pridobitev odgovora, odjemalec caka dokler ne dobi odgovor
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // prikazovanje odgovora
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);
        
        //obvezno zapremo vticnico
        socket.close();
    }
}