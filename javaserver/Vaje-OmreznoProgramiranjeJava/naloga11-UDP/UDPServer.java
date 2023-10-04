import java.io.*;
import java.net.*;

public class UDPServer {

    public static void main(String[] args) throws Exception {
        
        int lport = 2400;
        
        // Najprej ustvarimo Socket UDP
        try {
            DatagramSocket serverSocket = new DatagramSocket(lport);
        
            // Streznik poslusa in odgovarja v nedogled
            while (true) {
            
                // Sprejmemo
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receiveData = new DatagramPacket(
                                receiveBuffer, receiveBuffer.length);
                
                System.out.println("Cakam na paket na vratih: " + lport);
                serverSocket.receive(receiveData);
            
                String sentence = new String(receiveData.getData());
                String VELIKE = sentence.toUpperCase();
            
                // Pošljemo nazaj
                byte[] sendBuffer = new byte[1024];
                int port = receiveData.getPort();
                InetAddress IPAddress = receiveData.getAddress();
                System.out.println("Sprejel paket iz " + IPAddress + ", vrata " + port);
                System.out.println("Sprejel vsebino: " + sentence);
                sendBuffer = VELIKE.getBytes();
                
                DatagramPacket sendPacket = new DatagramPacket(
                                sendBuffer, sendBuffer.length, IPAddress, port);
                serverSocket.send(sendPacket);
                
            }
            
            //serverSocket.close();
        }
        
        catch (SocketException ex) {
            System.out.println("UDP Port je zaseden.");
            System.exit(1);
        }
        
        
    }

}