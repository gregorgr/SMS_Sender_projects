//Razred QuoteServerThread implementira glavno logiko streznika, ki posilja
//citate.

import java.io.*;
import java.net.*;
import java.util.*;
 
public class QuoteServerThread extends Thread {
 
    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;
 
    public QuoteServerThread() throws IOException {
    this("QuoteServerThread");
    }
 
    public QuoteServerThread(String name) throws IOException {
        super(name);
        //Ko se ustvari objekt QuoteServerThread, ta ustvari tudi datagram
        //vticnico na stevilki vrat 4445. Preko te vticnice streznik
        //komunicira z vsemi odjemalci.
        socket = new DatagramSocket(4445);
 
        try {
            //Konstruktor odpre bralca datoteke, ki
            //vsebuje seznam citatov. Vsaka vrstica datoteke vsebuje en
            //citat.
            in = new BufferedReader(new FileReader("one-liners.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
    }

    //Metoda run nam podaja kodo, ki jo izvaja vsaka nit. 
    public void run() {

        //While zanka se izvaja dokler obstajajo citati v datoteki. V vsaki
        //iteraciji nit čaka da pride datagram paket s pomočjo datagramske
        //vticnice. Prihod paketa oznacuje da je prisel zahtevek s strani
        //odjemalca. Kot odgovor object QuoteServerThread pridobi en citat
        //iz datoteke, zapakira citat v datagramski paket in poslje odjemalcu
        //ki ga je zahteval.
        while (moreQuotes) {
            try {
                //Tukaj prihajajo zahtevki s strani odjemalcev. Prvo se 
                //ustvari polje bajtov, ki se potem uporabi za ustvarjanje
                //datagramskega paketa.
                byte[] buf = new byte[256];
 
                //Prejem zahtevka. Metoda receive caka vse dokler paket
                //ne pride.
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
 
                // Tukaj se pripravi odgovor streznika
                String dString = null;
                if (in == null)
                    //ce slucajno se datoteka, ki vsebuje citate ni odprla
                    //odjemalcu se poslje datum namesto citata
                    dString = new Date().toString();
                else
                    //ce se je datoteka uspesno odprla, streznik pridobi
                    //naslednji citat
                    dString = getNextQuote();
                //nizo znakov pretvorimo v nizo bajtov
                buf = dString.getBytes();
 
        // odjemalcu posljemo odgovor na njegov naslov in stevilko
        // vrat 
                // metoda getAddress() nam pomaga pridobiti IP
                // naslov in stevilko vrat odjemalca iz
                // njegovega zahtevka               
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                
                // ustvarimo paket in ga posljemo odjemalcu
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        //obvezno zapremo vticnico
        socket.close();
    }
        
    // metoda, ki prebere naslednji citat iz datoteke
    protected String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
        moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }
}
