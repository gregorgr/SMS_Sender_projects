<?
// require("phpunit.php");
include_once '../../inc/phpmailer/class.phpmailer.php';	

class mass_mail_sender
{

	private $Mail;
	private $defaultMailBody;
	private $textMailBody ="";
	// private $currentMailBody;
	private $send =true;
	
	private $mailSender;
	private $mailSender_mail;
	private $mailSubject;
	
	private $mailImages;
	private $mailReceiversArray;
	private $userReplaceString;
	
	private $userIdentParam;
	
	private $mailIdentImage;
	private $sendMailCounter=0;
	private $lastMail="";
	private $mailFailed=array();
	//     $myMailUserParam = $_POST["mass_ident_param"];
	// $myMailIdentImage = $_POST["mass_ident_image"]; 
	// konstructor
	
	public function MailIdentImage($urlString)
	{
	   	$img = "<br /><img src=\"$urlString";
	   	if(strlen(stripos($urlString,"?"))>0) 
   		{
   			$img.="&".$this->userReplaceString."\"/>";
   		}
   		else
   		{
   			$img.="?".$this->userReplaceString."\"/>";	
   		}		
		$this->mailIdentImage = $img;
	}
	
	public function DontSend()
	{
		$this->send = false;
	}
	
	public function TextMailBody($text)
	{
		$this->textMailBody=$text;
	}
	
	public function UserIdentParam($urlParam)
	{
		$this->userIdentParam = $urlParam;
	}
	
    /**
     * vrne zadnji mail, ki ga je poizkušal poslati
     */
    public function LastMail()
    {
    	return $this->lastMail;	
    }
    
    /**
     * Vrne seznam vseh mailov, ki niso bili uspešno poslani
     * v originalni array obliki
     * @return array eMail_naslovi
     */
    public function MailFailed()
    {
    	return $this->mailFailed;
    }

	/**
	* 	takle array potrebuje konstruktor
	* 	array(
   	*		"sender"		=> "...",
   	* 		"sender_mail"	=> "...",
   	* 		"subject"		=> "...",
   	*		"body"			=> "...",
   	* 		"txtbody"		=> "...",
   	*		"images"		=> 
   	*  			array(	array("path"=>$imageUrl1, "name"=>$imageNameInMail1, "cid"=>guid1),
   	* 					array("path"=>$imageUrl2, "name"=>$imageNameInMail2, "cid"=>guid2)
   	* 				),
   	*		"userreplace"	=>  "##USERPARAMS##" - default vrednost  		
   	*	);
	* )
	* @param array $MailParserObject 
	*/
	public function __construct($MailParserObject) 
	{
		if(count($MailParserObject)>0)
		{					
			// print_r($MailParserObject);
			$this->mailIdentImage 	= "";
			$this->prepareImages($MailParserObject["images"]);
			$this->MailSubject 		= $MailParserObject["subject"];
			$this->defaultMailBody 	= $MailParserObject["body"];
			$this->textMailBody		= $MailParserObject["txtbody"];	
			
			$this->userReplaceString = $MailParserObject["userreplace"];
			
			$this->mailSender 		= $MailParserObject["sender"];
			$this->mailSender_mail 	= $MailParserObject["sender_mail"];
		}
		else
		{
			die("splošna napaka pri odpiranju nastavitev class mass_mail_sender __construct");			
		}
    }
    
    
    private function prepareImages($imgArray)
    {
    	$this->mailImages = $imgArray;
    }   
    
    public function MailReceiversList($list)
    {  	
    	if(count($list)>0)
		{
			$this->MailReceiversArray = $list;
		}
		else
		{
			die("splošna napaka pri odpiranju nastavitev class mass_mail_sender  funkcija MailReceiversList");			
		}
    }
    
  
    /**
     * Funkcija pošlje mail vsem uporabnikom
     *
     */
    public function  START_SENDING()
    {
    	// $mailinglist_date = datum_str ("D, j. ## Y",$timestamp);
    	$sendOK=false;
    	$counter=0;
		try
		{
			// pripravi text body. če ga ni -> strip_tags($body)...
			if (strlen($this->textMailBody)==0)
				$this->textMailBody = strip_tags($this->defaultMailBody);
			$this->Mail = new PHPMailer();
	    	// te vrednosti so že default in so zakomentirane
			// $this->Mail->Priority 	= 3;
			$this->Mail->From 		= $this->mailSender_mail;
        	$this->Mail->FromName 	= $this->mailSender_mail;
        	$this->Mail->Subject 	= $this->MailSubject;
        	$this->Mail->CharSet 	= "utf-8";
        	// $this->Mail->IsHTML(true);
        	// dodam slike
        	foreach ($this->mailImages as $mimage)
        	{
        		$this->Mail->AddEmbeddedImage($mimage["path"],$mimage["cid"],$mimage["name"]);
        	}   
			//$this->Mail->AddStringAttachment($mimage[0],$mimage[1]);   
        	//$this->Mail->AddAttachment($mimage[0],$mimage[1]); 
	    	for ($i=0;$i<count($this->MailReceiversArray);$i++)
	    	{
	
	    		// prejemnik
	    		$eMail=$this->MailReceiversArray[$i][0];
	    		$receiverName = $this->MailReceiversArray[$i][1];
	    		$this->lastMail=$eMail;
	    		// sestavi user id,
	    		// id je ali email ali id, ki je zapisan v tretjem stolpcu
	    		$userID = (count($this->MailReceiversArray[$i])>2?$this->MailReceiversArray[$i][2]:$eMail);
	    		$userID = $this->userIdentParam."=".$userID;	
	    		
	    		$this->Mail->AddAddress($eMail, $receiverName);
	    		// pripravi email
	    		// dodaj sliko
	    		$myMailContent = $this->ReplaceUserID($this->defaultMailBody,$userID);
	    		$myMailContent .=$this->identImageReplace($userID);
	    		
	    		// sestavim in pripnem mail
	    		$myMailTXTContent = $this->ReplaceUserID($this->textMailBody,$userID);
	    		// html body
	    		$this->Mail->Body    = $myMailContent; //"This is the message body";
	    		// txt body
	    		$this->Mail->AltBody = $myMailTXTContent;
	    		//$this->Mail->CreateBody();
	    		$this->Mail->prepare_message();
	    		  		
	    		if($this->send)
	    		{
		    		if(!$this->Mail->Send())
					{
	   					$this->mailFailed[] = $this->MailReceiversArray[$i];
						echo "There was an error sending the message";
	   					$sendOK=false;
	   					exit;
					}
					else
					{
						$sendOK=true;	
						$counter++;				
					}
	    		}
				$this->Mail->ClearAddresses();
				$this->Mail->Body    = "";
	    		$this->Mail->AltBody = "";
	    	}
	    	$this->Mail=null;
		} 
		catch (Exception $e)
		{	
			throw $e;
		}
		$this->sendMailCounter = $counter;
		if ($counter>0)
			echo("<br />Poslano vseh: $counter<br>");
    }
    
    /**
     * vrne število uspešno poslanih mailov
     */
    public function SendMailCounter()
    {
    	return $this->sendMailCounter;
    	$this->lastMail;
    	
    }
    

     /**
   	 * Zamenja prostor za parametre uporabnika s parametri
   	 * za indetifikacijo uporabnika, ala:
   	 * http://domena/stran/identimage.php?usrid=mail@domena2.si
   	 * 
   	 * @param   string	$text			besedilo, kejer se išče text 
   	 * @param   string	$replaceWith	url parametri uporabnika 
   	 * 
   	 */
    private function ReplaceUserID($text, $replaceWith)
    {   	
    	// $currentMailBody = $this->defaultMailBody;
    	return str_ireplace($this->userReplaceString, $replaceWith, $text);
    }
	
     /**
   	 * Funkcija izdela image s parametri uporabnika, ala:
   	 * <img src="http://domena/stran/identimage.php?usrid=mail@domena2.si"/>
   	 * 
   	 * @param   string	$replaceWith	url parametri uporabnika 
   	 * @return  string  htmlImage <img src="http://.."/>
   	 */
    private function identImageReplace($replaceWith)
	{
		$myImg = "";	
		//$this->userReplaceString
		if (strlen($this->mailIdentImage)>0)
			$myImg = str_ireplace($this->userReplaceString, $replaceWith, $this->mailIdentImage);
		return $myImg;
	}
   
	public function __destruct()
	{
		$this->Mail=null;
	}
	
	/**
	 * samo testira, če se PHPmailer instancira ok!!!
	 */
	public function  TestMailer()
    {
    	echo ("<b>Testiram PHPmailer:</b><br />");
    	$this->Mail = new PHPMailer();
		echo ("this->Mail = new PHPMailer()....OK!<br />");		
		$this->Mail->CharSet 	= "iso-8859-1";
		echo ("PHPMailer OK!<br />");	
		$this->Mail=null;
    }
    
   	private function EchoDebug2($title, $myobj)
   	{
   		echo ("<b>$title:</b><br>");
		echo ("<textarea rows=\"12\" cols=\"100\">");		
		echo($myobj);
		echo ("</textarea>");	
   	}
   	
}



?>