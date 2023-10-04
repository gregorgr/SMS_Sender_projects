import java.net.*;
import java.io.*;

public class URLConnectionReader {
    public static void main(String[] args) throws Exception {
        URL oracle = new URL("http://www.fis.unm.si/");
        URLConnection yc = oracle.openConnection();
        //vhodni tok dobimo iz objekta URLConnection s pomocjo metode
        //getInputStream()
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();
    }
}