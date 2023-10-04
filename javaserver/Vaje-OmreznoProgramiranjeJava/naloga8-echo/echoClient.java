//Ogledali si bomo primer eho odjemalca, ki se poveze na eho streznik. Ta
//prebere podatke od odjemalca in jih pošlje nazaj kot eho. Odjemalec ustvari
//vticnico in prebere vhodne podatke iz standardnega vhoda. Podatke potem
//posreduje eho strezniku in zapise jih v vticnico. Streznik vrne iste
//podatke preko vticnice. Odjemalec te podatke prebere in jih prikaze.

import java.io.*;
import java.net.*;
 
public class EchoClient {
    public static void main(String[] args) throws IOException {
         
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }
 
        //v tem delu se vzpostavi vticnica, prebere se ime gostitelja ter
        //stevilka vrat iz ukazne vrstice
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
 
        try (
            //vzpostavimo vtičnico
            Socket echoSocket = new Socket(hostName, portNumber);
            //odpremo zapisovalca podatkov
            PrintWriter out =
                new PrintWriter(echoSocket.getOutputStream(), true);
            //odpremo bralca podatkov, ki ima medpomnilnik
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
            //odpremo se en tok, ki bere iz standardnega vhoda
            BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in))
        ) {
            //zanka bere iz standardnega vhoda vrstico po vrstico
            //ter podatke posilja strezniku s pomočjo zapisovalca
            //ki je povezan na vticnico. Zanka se izvaja dokler 
            //uporabnik ne vnese znaka za konec vnosa (CTRL+C)
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                //Naslednji ukaz prebere podatke od bralca, ki je povezan na
                //vticnico. Metoda readLine() caka dokler streznik ne
                //vrne eho odjemalcu. Ko prejme informacijo, odjemalec
                //jo poslje na standardni izhod.
                System.out.println("echo: " + in.readLine());
            }
            //Ko se while zanka zakljuci, Java avtomaticno zapre bralce
            //in zapisovalce, ki so povezani na vticnico 
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
}