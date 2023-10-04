//Koda UDP streznika je sestavljena iz dveh razredov: QuoteServer in QuoteServerThread
//Razred QuoteServer vsebuje samo eno metodo main, ki samo ustvari objekt
//QuoteServerThread in ga zagoni
import java.io.*;

public class QuoteServer {
    public static void main(String[] args) throws IOException {
        new QuoteServerThread().start();
    }
}