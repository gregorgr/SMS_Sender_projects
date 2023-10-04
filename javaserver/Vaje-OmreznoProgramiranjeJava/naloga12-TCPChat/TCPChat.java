import java.io.*;
import java.net.*;
import java.util.HashSet;

public class TCPChat {

    // Hranimo imena udelezencev
    private static HashSet<String> names = new HashSet<String>();

    // Seznam izhodov za izpis udelezencem
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    
    public static void main(String[] args) throws Exception {
    
        int lport = 9001;
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(lport);
            System.out.println("Sprejemam na vratih " + lport);
            while (true) {
                new Client(serverSocket.accept()).start();
            }
        }
        catch (IOException e) {
            System.out.println("Sistem mi ne more dodeliti teh vrat");
        }
        finally {
            serverSocket.close();
        }

    }
    
    private static class Client extends Thread {

        // Vsak udelezenec ima nekaj lastnosti
        private BufferedReader in;
        private PrintWriter out;
        private String name;
        private Socket socket;
        
        public Client(Socket socket) {
            // Shranimo socket, ki ga nam je posredoval accept
            this.socket = socket;
        }
        
        public void run() {

            // glavna koda programa    
            try {

                in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(
                            socket.getOutputStream(), true);
                
                while (true) {
                    out.println("Povej svoje ime");
                    name = in.readLine();
                    
                    // Ce se odjemalec slucajno odklopi
                    if (name == null) {
                        return;
                    }
                    
                    // Poskrbimo, da do strukture dostopa lahko le en udelezenec socasno
                    synchronized (names) {
                        if (!names.contains(name)) {
                            // Dodamo ime na seznam
                            names.add(name);
                            break;
                        }
                    }
                }
                
                out.println("Ime je sprejeto");
                writers.add(out);
                
                String sporocilo;
                while (true) {

                    // Preberemo sporocilo udelezenca
                    sporocilo = in.readLine();
                    if (sporocilo == null) {
                        return;
                    }
                    for (PrintWriter writer : writers) {
                        // Vsakemu odjemalcu moramo poslati njegovo sporocilo
                        writer.println(name + ": " + sporocilo);
                    }
                }
            
            }
            catch (IOException e) {
                System.out.println(e);
            }
            finally {
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                }
                catch (IOException e) {
                    System.out.println("Ne morem zakljuciti seje z odjemalcem");
                }
            }
            
        }
    }

}

