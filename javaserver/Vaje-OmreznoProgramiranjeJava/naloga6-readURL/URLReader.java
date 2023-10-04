//Probali bomo prebrati vsebino, ki se nahaja na spletnem naslovz http://www.oracle.com

import java.net.*;
import java.io.*;

public class URLReader {
    public static void main(String[] args) throws Exception {

        //ustvarimo URL objekt
        URL oracle = new URL("http://www.oracle.com/");
        //ustvarimo bralca z medpomnilikom, ki bere iz URLja s pomočjo bralca vhodnega toka
        BufferedReader in = new BufferedReader(
            new InputStreamReader(oracle.openStream()));

        //beremo iz toka vse dokler je kaj za prebrati
        String inputLine;
        System.out.println("Začenjam branje");
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        //na koncu obvezno zapremo povezavo
        in.close();
        System.out.println("Konec");
    }
}
