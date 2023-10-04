import java.net.*;
import java.io.*;
import java.util.Calendar;

public class TCPServer {

    public static void main(String[] args) throws IOException {

        int lport = 8002;
        ServerSocket serverSocket = null;

        // Ustvarimo socket/ vticico
        try {
            serverSocket = new ServerSocket(lport);
        }
        catch (IOException e) {
            // zasedena vrata
            System.out.println("Nisem mogel zasesti vrat" + lport);
            System.exit(1);
        }
        System.out.println("Cakam ...");

        // Sprejmemo odjemalca
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        }
        catch (IOException e) {
            System.out.println("Nisem mogel dobiti povezave");
            System.exit(1);
        }

        System.out.println("Povezan, cakam na promet.");

        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(
                              clientSocket.getOutputStream(), true);

        String inputLine;
        String VELIKE;

        Calendar cal;
        int hour, minute;

        // Prebiramo kaj smo dobili od odjemalca
        while ((inputLine = in.readLine()) != null) {

            // Osvezimo cas
            cal = Calendar.getInstance();
            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);
            System.out.println("Sprejel sem: " + inputLine);
            VELIKE = inputLine.toUpperCase();
            // Oddamo
            if (hour >= 19 && minute >= 5) {
                out.println("DA");
            }
            else {
                out.println("NE");
            }
            out.println(VELIKE);
            // izhod na exit
            if (inputLine.equalsIgnoreCase("exit")) break;

        }

        // Zapremo "tokove"
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();

    }

}
