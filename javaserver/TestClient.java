import java.io.*;
import java.util.Scanner;
import java.net.*;
import java.io.*;

//import org.json.JSONArray ;
import javax.json.Json;
//import javax.json.Json.*;
/*

import javax.json.stream.JsonParser;
import javax.swing.text.html.parser.ParserDelegator;
*/

class TestClient{

  private int portTCP = 2228;
  private String setverIP = "localhost";
	private String apiKey = "06E0CB04-F8DE-4A2C-A891-D253192EFA06";


  private void sendSMS(String number, String text){

    String server = new String(this.setverIP);

    System.out.println("TCP komunikacija z " + server);


    Socket clientSocket = null;
    try {
        clientSocket = new Socket(server, this.portTCP);
    }
    catch (UnknownHostException e) {
        System.out.println("Ne morem razresiti domene");
        System.exit(1);
    }
    catch (IOException e) {
        System.out.println("Ne morem se povezati na streznik");
        System.exit(1);
    }


    /// Povezan

    // pretvori vsebino ,za poslati strezniku
    System.out.println("Povezan pošiljam ključ." );
    String vsebinaZaPoslati = "key=" + this.apiKey;

    try{
      // Za komunikacijo s streznikom
      BufferedReader in = new BufferedReader(
                  new InputStreamReader(
                    clientSocket.getInputStream()));
      PrintWriter out = new PrintWriter(
                  clientSocket.getOutputStream(), true);

      // Branje iz terminala
      BufferedReader stdIn = new BufferedReader(
                     new InputStreamReader(System.in));




      // Odposljemo prebrano besedilo
      out.println(vsebinaZaPoslati);

      // branje vrnjene vsebine
      String prejeto = in.readLine().trim();
      // preverimo potrditveno sporocilo 11

      System.out.println("Odgovor je: " + prejeto );

      if(prejeto.equals("OK:1")){
        System.out.println("Pripravljam SMS" );
/*
        JSONObject jo = new JSONObject();
        jo.put("phone", number);
        jo.put("text", text);
        jo.put("command", "SENDSMS");
*/
JsonObject jo = Json.createObjectBuilder()
  .add("content", Json.createArrayBuilder()
    .add(Json.createObjectBuilder()
      .add("phone", number)
      .add("text", text)
      .add("command", "SENDSMS")))
      .build();
/*
        JsonObject jo = Json.createObjectBuilder()
          .add("content", Json.createArrayBuilder()
            .add(Json.createObjectBuilder()
              .add("phone", number)
              .add("text", text)
              .add("command", "SENDSMS")))
              .build();
        // String number, String text
        vsebinaZaPoslati = jo.toString();*/
        vsebinaZaPoslati = "vsebinaZaPoslati";
        out.println(vsebinaZaPoslati);
      }



      // pozaprem objekte
      if (out != null){
        out.close();
      }
      if (in != null){
        in.close();
      }
      if (stdIn != null){
        stdIn.close();
      }

    } catch (IOException e) {
    } finally{
      // pozaprem zunanji objekt
      try{
        if (clientSocket != null){
          clientSocket.close();
        }
      } catch (IOException e) {}

    }


  }


  public static void main(String[] args) {



		try{

			// vzpostavimo TCP srežnik za komunikacijo
			//	smsTcpServer(this.portTCP);
			TestClient client = new TestClient();
      client.sendSMS("041111222", "testna vsebina\ncoda\n\n2345\nvpisite");



		} catch (Exception e) {
		}

	}


}
