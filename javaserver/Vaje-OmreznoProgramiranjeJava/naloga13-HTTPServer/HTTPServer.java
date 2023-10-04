import java.net.*;
import java.io.*;

public class HTTPServer {
    
    public static void main(String[] args) throws IOException {
        
        int lport = 7777;
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(lport);
            System.out.println("Poslusam na vratih " + lport);
            while (true) {
                // Cakam na povezave
                new Handler(serverSocket.accept()).start();
            }
        }
        catch (IOException e) {
            System.out.println("Ne morem odpreti vrat");
        }
        finally {
            serverSocket.close();
        }
        
    }
    
    public static class Handler extends Thread {
        
        // Lokalne spremenljivke 
        private Socket socket;
        
        public Handler(Socket socket) {
            this.socket = socket;
            System.out.println("Sprejel sem povezavo");
        }
        
        public void run() {
            
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                
                // Nacin odgovarjanja
                boolean keepAlive;
            
                do {
                    
                    String line;
                    String connection = "";

                    // Odjemalec mora vsakič zahtevati nacin odgovarjanja
                    keepAlive = false;

                    // Preberemo prvo vrstico HTTP zahtevka
                    String requestLine = in.readLine();

                    if (requestLine == null) {
                        break;
                    }
                    
                    // Preberemo se ostale vrstice galve, da izkuscimo parameter Connection
                    do {
                        line = in.readLine();
                        System.out.println("Glava: " + line);
                        if (line.length() == 0) {
                            // Pri prazni vrstici nehamo
                            break;
                        }
                        if (line.startsWith("Connection:")) {
                            connection = line.substring(11).trim();
                        }
                    } while (true);

                    // Zahtevek izgleda kot: GET / HTTP/1.1
                    
                    int idx = requestLine.indexOf(" ");
                    if (idx == -1) {
                        // Presledka v prvi vrstici zahtevka nismo nasli, zato izpisemo napako
                        out.println("HTTP/1.0 400 Bad Request");
                        out.println("Connection: close");
                        out.println("");
                        out.flush();
                        continue;
                    }
                    
                    // Izluscimo metodo (do presledka), npr. GET
                    String metoda = requestLine.substring(0, idx);
                    System.out.println("Metoda: " + metoda);
                    
                    requestLine = requestLine.substring(idx + 1);
                    
                    int endIndex = requestLine.lastIndexOf(" ");
                    if (endIndex == -1) {
                        // Zadnjega presledka v prvi vrstici zahtevka nismo nasli, zato izpisemo napako
                        out.println("HTTP/1.0 400 Bad Request");
                        out.println("Connection: close");
                        out.println("");
                        out.flush();
                        continue;
                    }
                    
                    // S konca zahtevka izluscimo verzijo protokola
                    String httpVersion = requestLine.substring(endIndex + 1);
                    if (!httpVersion.equalsIgnoreCase("HTTP/1.0") && !httpVersion.equalsIgnoreCase("HTTP/1.1")) {
                        // Verzija HTTP protokola ni ustrezna, izpisimo napako
                        out.println("HTTP/1.0 505 HTTP Version Not Supported");
                        out.println("Connection: close");
                        out.println("");
                        out.flush();
                        continue;
                    }
                    
                    // Nastavimo nacin odgovarjanja
                    if (connection != "") {
                        keepAlive = connection.equalsIgnoreCase("keep-alive");
                    } else {
                        // Poskrbimo, da privzeta vrednost ustreza verziji protokola
                        keepAlive = httpVersion.equals("HTTP/1.1");
                    }
                    
                    // Iz prve vrstice izluscimo zeljeno datoteko
                    String filePath = requestLine.substring(0, endIndex);
                    if (filePath.startsWith("/")) {
                        filePath = filePath.substring(1);        
                    }
                    System.out.println("Zelim stram: " + filePath);
                    
                    // Zaenkrat nas streznik podpira le metodo GET
                    if (!metoda.equals("GET")) {
                        out.println(httpVersion + " 405 Method Not Allowed");
                        out.println("Allow: GET");
                        if (keepAlive) {
                            out.println("Connection: keep-alive");
                        }
                        else {
                            out.println("Connection: close");
                        }
                        out.println("");
                        out.flush();
                        continue;
                    }
                    
                    // Na tem mestu je vse v redu, zato odjemalcu izpisemo datoteko
                    String text = "Odziv, zelim vsebino: " + filePath;
                    
                    out.println(httpVersion + " 200 OK");
                    out.println("Content-Type: text/html");
                    out.println("Content-Length: " + text.length());
                    if (keepAlive) {
                        out.println("Connection: keep-alive");
                    }
                    else {
                        out.println("Connection: close");
                    }
                    out.println("");
                    out.flush();
                    
                    // Izpis vsebine
                    out.println(text);
                    out.flush();
                    
                } while (keepAlive);
                
            }
            catch (IOException e) {
                System.out.println("Napaka");
            }
            finally {
                try {
                    socket.close();
                }
                catch (IOException e) {
                    System.out.println("Napaka pri prekinitvi");
                }
            }
            
            
        }
        
    }
    
    

}
