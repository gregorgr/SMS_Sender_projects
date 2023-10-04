//V tej nalogi bomo si ogledali kako poteka komunikacija med odjemalcem in
//streznikom, ki komunicirata s pomocjo TCP povezave. Odjemalec prebere nizo
//znakov iz standardnega vhoda ter jo poslje strezniku. Streznik prebere
//sporocilo ter preveri ce je sporocilo bilo sprejeto v doloceni uri in ce je
//vrne sporocilo DA (v nasprotnem primeru vrne NE). Na koncu vrne tudi isto
//sporocilo z velikimi crkami.
import java.net.*;
import java.io.*;

public class TCPClient {

    public static void main(String[] args) throws IOException {

        int sport = 8002;
        String server = new String("localhost");

        Socket clientSocket = null;
        try {
            clientSocket = new Socket(server, sport);
        }
        catch (UnknownHostException e) {
            System.out.println("Ne morem razresiti domene");
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("Ne morem se povezati na streznik");
            System.exit(1);
        }

        // Za komunikacijo s streznikom
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(
                              clientSocket.getOutputStream(), true);

        // Branje iz terminala
        BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in));
        String prebrano;

        System.out.print("Vnesi besedilo: ");
        while ((prebrano = stdIn.readLine()) != null) {
            // Odposljemo prebrano besedilo
            out.println(prebrano);
            System.out.println("Dobim: " + in.readLine());

            if (prebrano.equalsIgnoreCase("exit")) break;
            System.out.print("Vnesi besedilo: ");
        }

        out.close();
        in.close();
        stdIn.close();
        clientSocket.close();
    }

}
