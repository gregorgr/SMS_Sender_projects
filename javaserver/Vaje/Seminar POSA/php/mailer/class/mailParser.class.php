<?


class mailParser
{
	private $_mailString;
	private $_mailStringTxt = "";
	private $_mailSubject;
	private $_mailArray;
	private $ObjectArray = array();
	
	private $imagesArray;
	private $linksArray;
	
	private $imageReplaceString ="SLIKA";
	private $linkReplaceString 	="##USERPARAMS##";
	
	private $sender;
	private $sender_mail;
	
	public function Sender($name, $email)
	{
		$this->sender		= $name;
		$this->sender_mail	= $email;
	}

	/**
	* konstruktor izvede celotno pripravo maila
	* - poišče vse linke v mailu in jim doda {?|&}##USERPARAMS##
	*   kar se kasneje zamenja s url parametrom in uni id uporabnika za tracking 
	* - poišče vse slike in zamenja src=".." s parametrom ..SLIKA_1
	* 	preveri potencialno podvojene slike
	* 
	* 	* @param  	string	$mailString	 celoten vhodni mail string
	*/
	function __construct($mailString) 
	{
		$this->_mailString = $mailString;	
		
		// $this->_mailString = stripcslashes($mailString);
		$this->_mailArray  = array($mailString);
		
		// poišče vse linke in slike v vsebini
		$this->imagesArray=$this->FindImages();	
		$this->ResolveImages();
		
		$this->linksArray=$this->FindLinks();	
		//$this->EchoDebug("links", $this->linksArray);	
		$this->ResolveLinks();
			
		//$this->_mailString= stripslashes(trim($this->_mailString));		
		//$this->EchoDebug2("mail",$this->_mailString);		
		//$this->EchoDebug("pics", $this->imagesArray);
   	}

   	/**
	* @return 	array spremljajočih slik v datoteki
	* - poišče vse linke v mailu in jim doda {?|&}##USERPARAMS##
	*   kar se kasneje zamenja s url parametrom in uni id uporabnika za tracking 
	* - poišče vse slike in zamenja src=".." s parametrom ..SLIKA_1
	*	 array(
   	* 			array($imageUrl1, guid1, $imageNameInMail1),
   	* 			array($imageUrl2, guid2, $imageNameInMail2)
   	* 			)
	*/
   	public function Images()
   	{
   		return $this->imagesArray;
   	}

   	
	/**
	*   @return	array	vrne array s podatki o mailu
	* 
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
	*/
   	public function GetMail()
   	{
   		$mail = array(
   			"sender"		=>$this->sender,
   			"sender_mail"	=>$this->sender_mail,
   			"subject"		=>$this->_mailSubject,
   			"body"			=>$this->_mailString,
   			"txtbody"		=>$this->_mailStringTxt,
   			"images"		=>$this->imagesArray,
   			"userreplace"	=>$this->linkReplaceString  		
   		);
   		return $mail;
   	}
   	
	public function TextMailBody($text)
	{
		$this->_mailStringTxt=$text;
		if (strlen($this->_mailStringTxt)>0) $this->CreateTextMail();
	}
	
	/**
	*   @return	string  ki se kasneje zamenja s url parametrom in uni id uporabnika za tracking 	
	* 				"##USERPARAMS##" - default vrednost  
   	*/
   	public function UserParams()
   	{
   		return $this->linkReplaceString;
   	} 	
   	
   	/**
   	 * 
   	 * @param   string	$text	Subject v mailu
   	 * 
   	 */
   	public function Subject($text)
   	{
   		$this->_mailSubject=$text;
   	}
   	
    	
	/**
	* regex, za iskanje linkov 
	*/
   	private function FindLinks()
   	{ 			
   		$regex ='/\shref=\"[^\"]*\"/iU';
   		preg_match_all($regex , $this->_mailString,$result, PREG_OFFSET_CAPTURE); 
   		return $result;	
   	}
		
   	/**
	* regex, za iskanje slik 
	*/
   	private function FindImages()
   	{ 			
   		$regex ='/\ssrc=\"[^\"]*\"/iU';
   		preg_match_all($regex , $this->_mailString,$result,PREG_OFFSET_CAPTURE); 
   		return $result;	
   	}
   	
   	/**
   	 * Funkcija pripravi mail na podoben način
   	 * doda url parametre
   	 * in izvede tudi  strip_tags na vsebini
   	 */
   	private function CreateTextMail()
   	{
   		// parsanje tekstovnega maila
   		$mPosition=0;
   		$tempMailContent ="";   		
   		// $regex ='/[^\s]{1}http:\/\/.*\s/siU';
   		$regex ='/[\s]{1}http:\/\/.*\s/siU';
   		preg_match_all($regex , $this->_mailStringTxt ,$result, PREG_OFFSET_CAPTURE); 
		
   		//$this->EchoDebug("txt", $result);
   		foreach($result[0] as $currentLink)
   		{
   			if (count($currentLink)>1)
   			{
   				$xLink		= $currentLink[0];
   				$xLinkPos	= $currentLink[1]; 
   				// odrežem vsebino do linka	
   				$tempMailContent .= substr($this->_mailStringTxt,$mPosition ,$xLinkPos - $mPosition);
   				$mPosition = $xLinkPos + strlen($xLink); 
   				$xLink = trim($xLink);
   			    if(!$this->TestLink($xLink)) 
   				{
   					$xLink.="/?";
   				}
   				else if(strlen(stripos($xLink,"?"))>0) 
   				{
   					$xLink.="&";	
   				}
   				else
   				{
   					$xLink.="?";	
   				}
   				$xLink.=$this->linkReplaceString;
   				$tempMailContent .= "$xLink ";
   			}
   		}
   		$tempMailContent .= substr($this->_mailString,$mPosition,strlen($this->_mailString)-$mPosition);
   		// $this->_mailStringTxt = strip_tags($tempMailContent);
   		$tempMailContent 		= str_ireplace("<br>",   "\n", $tempMailContent);
   		$this->_mailStringTxt   = str_ireplace("<br />", "\n", $tempMailContent);
   		// $this->_mailStringTxt   = $tempMailContent;
   	}
   	
   	/**
   	 * Funkcija obdela linke in pripravi vsebino maila z linki..
   	 *
   	 */
	private function ResolveLinks()
	{
   		$mPosition=0;
   		$tempMailContent ="";    	
		foreach($this->linksArray[0] as $currentLink)
   		{
   			if (count($currentLink)>1)
   			{
   				$xLink		= $currentLink[0];
   				$xLinkPos	= $currentLink[1];  	
   				
   				//$i2=strrchr($xLink,chr(34)); 	
   				// odrežem vsebino do linka				
   				$tempMailContent .= substr($this->_mailString,$mPosition ,$xLinkPos-$mPosition);
   				$mPosition = $xLinkPos + strlen($xLink); 
   				// obdelava dodatkov za link
   				$xLink=$this->parseLink($xLink);   		

   				if(!$this->TestLink($xLink)) 
   				{
   					$xLink.="/?";
   				}
   				else if(strlen(stripos($xLink,"?"))>0) 
   				{
   					$xLink.="&";	
   				}
   				else
   				{
   					$xLink.="?";	
   				}
   				$xLink.=$this->linkReplaceString;
   				$tempMailContent .= " $xLink\"";
   			}
   		}
   		// do konca sestavim nov mail
   		$this->linksArray =array(); //resetnem  		
   		$tempMailContent .= substr($this->_mailString,$mPosition,strlen($this->_mailString)-$mPosition);
   		$this->_mailString = $tempMailContent;
	}
	
	/**
	 * testira, če link vsebuje še kak /
	 * oziroma, če je zapisan kot http://domena.com
	 * 
	 * @param string $link
	 * @return bool
	 */
	private function TestLink($link)
	{
		if (stripos(substr($link,stripos($link,"http://")+7 ),"/"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
   	private function parseLink($link)
   	{
   		if (strcmp(substr($link,strlen($link)-1,1),chr(34))==0)
   			$link = substr($link ,0, strlen($link)-1);
   		// $link = stristr(stristr($link,"http"),'"',true);
   		// $link = trim(stristr($link,"http"));
   		// if(strlen(stripos($link,'"'))>0) $link = substr($link ,0, stripos($link,'"'));;
   		// if(strlen(substr($link,strlen($link)-1,1))>0) 
		// echo("x:".stripos($link,'"')."<br>");
   		return trim($link);		
   	}
   	
   	
   	private function ResolveImages()
   	{
   		$mPosition=0;
   		$myImages = array();
   		$tempMailContent ="";  		 		
   		foreach($this->imagesArray[0] as $currentImage)
   		{
   			if (count($currentImage)>1)
   			{
   				$xImage		= $currentImage[0];
   				$xImagePos	= $currentImage[1];		  				
   				// shrani image v array
   				$myImagesX	= $this->getImageData($myImages, $xImage);
   				$myImages 	= $myImagesX[0];
   				$replaceWith= $myImagesX[1];
   				// sestavi novo vsebino maila
   				// in postavljam linke na nove slike	
   				$tempMailContent .= substr($this->_mailString,$mPosition ,$xImagePos-$mPosition);     							
   				$tempMailContent .= " src=\"cid:$replaceWith\"";
   				// nova pozicija
   				$mPosition = $xImagePos + strlen($xImage);  				
   			}	
   		}
   		// do konca sestavim nov mail
   		$this->imagesArray =$myImages;
   		
   		$tempMailContent .= substr($this->_mailString,$mPosition,strlen($this->_mailString)-$mPosition);
   		$this->_mailString = $tempMailContent;  
   		$this->ReparseImgUrl();		
   	}
   	
   	
	private function ReparseImgUrl()
	{
		for ($i=0;$i<count($this->imagesArray);$i++)
		{		
			$this->imagesArray[$i]["path"]=substr($this->imagesArray[$i]["path"], stripos($this->imagesArray[$i]["path"],"=")+2);		
		}
	}
	
    /**
	* vrne 
	* 
	*/
   	private function getImageData($myImagesArray,$xImage)
   	{
   		$xImage=$this->parseLink($xImage); // dokončno sparsaj image
   		$Image_ext = substr($xImage, strripos($xImage,"."));
   		$isMatch=false;
   		// slika + count + ext
   		$cid = $this->guid();
   		$imageName= $this->imageReplaceString.count($myImagesArray).$Image_ext;
   		
   		//$replaceWith =	$xImage;
   		$replaceWith =	$cid;//$imageName;
   		// $replaceWith = "cid:$myGuid/".$this->imageReplaceString.count($myImagesArray).$Image_ext;
   		// primerja s shranjenimi  in osdstrani duplikate		
   		foreach ($myImagesArray as $testImage)
   		{
   			// če se ujame
   			if (strcmp($testImage["path"],$xImage)==0)
   			{
   				$replaceWith = $testImage["cid"];
   				$isMatch=true;
   				break;
   			}
   		}   		
   		if(!$isMatch)
   		{
   			$myImagesArray[]=array("path"=>$xImage, "name"=> $imageName, "cid"=>$cid);
   		}
   		return array($myImagesArray, $replaceWith);
   	}
   	
   	/**
   	*  creates Guid -> cid
   	*/
   	private function guid()
   	{
   		if (function_exists('com_create_guid'))
   		{
   			return com_create_guid();
   		}
   		else
   		{
   			mt_srand((double)microtime()*10000);//optional for php 4.2.0 and up.
        	$charid = strtoupper(md5(uniqid(rand(), true)));
        	$hyphen = chr(45);// "-"
        	$uuid = chr(123)// "{"
                .substr($charid, 0, 8).$hyphen
                .substr($charid, 8, 4).$hyphen
                .substr($charid,12, 4).$hyphen
                .substr($charid,16, 4).$hyphen
                .substr($charid,20,12)
                .chr(125);// "}"
       	 	return $uuid;
   		}
   	}
   	
   	// Doddelek za testiranje
	// preg_split - dela ok
	// preg_grep
	// 	preg_match("/aa/","aaskjefc",$array); //poiščemo aa
	//	preg_match_all() // ujame vse
   	private function FindLinks1()
   	{
  	
   		$regex ='?=(href\s*=\s*"?([^")';
   		$regex ='href=\s*""';
   		
   		$results = preg_grep($regex , $this->_mailString); 	
   		return $results;
   	}

 	private function EchoDebug($title, $myobj)
   	{
   		echo ("<b>$title:</b><br>");
		if (count($myobj))
		{  		
			echo("<pre>");
			print_r($myobj);
			echo ("</pre><br />"); 
		}else{
			echo ("<textarea rows=\"10\" cols=\"60\">");
			echo($myobj);
			echo ("</textarea>");
		}    		
   	}
   	
   	private function EchoDebug2($title, $myobj)
   	{
   		echo ("<b>$title:</b><br>");
		echo ("<textarea rows=\"19\" cols=\"100\">");		
		echo($myobj);
		echo ("</textarea>");	
   	}
   	
   	public function TestRegex()
   	{  		
   		
   		echo ("izpis rezultata:<br/ >");
   		$regtest= $this->imagesArray;
   		$regtest= $this->linksArray;
   		if (is_array($regtest))
   		{
	   		echo ("<textarea rows=\"10\" cols=\"60\">");
	   		print_r($regtest);
	   		echo ("</textarea>");
   			foreach($regtest as $rez)
	   		{  			
	   			echo ("<br/ >xx. $rez<br/ >");
	   		}
   		}
   		else
   		{
   			echo ("ni array. $regtest<br/ >");
   		}
   	}
}


	

?>