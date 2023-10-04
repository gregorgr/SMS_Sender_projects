import org.json.*;



public class SMSSender {
	
	
	  private String session_uid;
	  private String phone_to;
	  private String message;
	  private String messageText;
	  private String action;
	  private JSONObject jObject ;
	  
	  private String serverResponse ="";
	  private int lastError =0;



	  public void messageJsnon(String jsonMessage){
		  try {
			  this.jObject = new JSONObject(jsonMessage);
			  
			  //  = this.jObject.getJSONObject("guid").toString();
			  this.session_uid = this.jObject.getString("guid");
			  this.action = this.jObject.getString("action");
			  //JSONObject obj = new JSONObject(jsonMessage);
			  this.message = jsonMessage;
		  }catch(Exception e) {
			  
			  System.out.println("Napaka: " + e.toString());
			  
		  }
		  

		 // return true;
	  }

	  public String getAction(){
	    return this.action;
	  }
	  
	  
	  public String getServerResponse() {
		  return this.serverResponse;
	  }

	  private boolean sendSMS() {
		  
		  try {
			  this.lastError= 1;
			  this.prepareSMS();
			  

			  this.lastError= 2;
			  // TODO 
			  // 1. subsbscription is valid (
			  //   - je veljaven mesec
			  //   - ima še prostih kapacitet
			  
			  
			  this.lastError= 3;
			  // TODO 
			  // 2. Shrani v baso s statusom send status 0
			  
			  this.lastError= 4;
			  // TODO
			  // send SMS
			  this.sendSMSaction();
			  
			  this.lastError= 5;
			  // TODO 
			  // 3. Update baze send status 1 in send date now!!!
			  
			  this.lastError= 0;
			  this.serverResponse +="SMS send OK\n";
			  return true;
		  }catch(Exception e) {
			  
			  this.serverResponse +="Error send SMS\n";
			  System.out.println("Napaka ["+ this.lastError + "]: " + e.toString());
			  return false;
			  
		  }
		//  return false;

	  } 
	  
	  private void sendSMSaction() {
		  
		  System.out.println("\nSMS poslan ["+this.phone_to +"]: " );
		  System.out.println(this.messageText);
		  System.out.println("----");
		  
		  
	  }
	  
	  
	  private void prepareSMS() {
		  
		  this.phone_to = this.jObject.getString("phone_to");
		  this.messageText = this.jObject.getString("messageText");
		  
	  }
	  
	  public boolean action(){
		  switch (this.action) {
		  
		  	case "SENDSMS":
		  		
			  return this.sendSMS();
			 
			 default:
				 
			  
		  }
	
		  return false;
	  }

}
