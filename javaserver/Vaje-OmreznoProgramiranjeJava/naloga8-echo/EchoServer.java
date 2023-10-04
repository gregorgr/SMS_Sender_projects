import java.net.*;
import java.io.*;
 
public class EchoServer {
    public static void main(String[] args) throws IOException {
         
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
        
        //prebere stevilko vrat iz ukazne vrstice 
        int portNumber = Integer.parseInt(args[0]);
         
        try (
            //definira streznisko vticnico, ki ima moznost
            //prejema odjemalskih zahtevkov
            ServerSocket serverSocket =
                new ServerSocket(Integer.parseInt(args[0]));

            //metoda accept() caka na zahtevke odjemalca. Ko se enkrat
            //vzpostavi uzpesna povezava, metoda vrne novo vticnico (nov 
            //objekt), ki je povezana na isto stevilko vrat in se nahaja
            //na istem naslovu. Na ta nacin streznik lahko komunicira s
            //odjemalcem preko nove vticnice in na stari lahko poslusa
            //ce so novi zahtevki. V tem programu to ni mozno. Ce hocemo
            //podpirat se to moramo uporabljati niti. Vec o nitih v predmetu
            //operacijski sistemi.
            Socket clientSocket = serverSocket.accept();
 
            //ko se odjemalec poveze na streznik odpremo zapisovalca, ki 
            //bo zapisoval na izhodni tok    
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);      
            
            //odpremo tudi bralca z medpomnilnikom, ki bo bral
            //iz vhodnega toka             
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            //zanka bere iz citalca vrstico po vrstico
            //ter podatke posilja odjemalcu s pomočjo zapisovalca
            //ki je povezan na vticnico. Zanka se izvaja dokler 
            //uporabnik na odjemalski strani ne vnese znaka
            //za konec vnosa(CTRL+C)
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
