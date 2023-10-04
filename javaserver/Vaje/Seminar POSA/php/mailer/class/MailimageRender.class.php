<?



class MailImageRender
{
	private $imageFile;
	private $img;
	private $imageProperties;

	
	// konstructor
	function __construct($imageFileUrl) {
		
		$this->imageFile	= $imageFileUrl;	
		$this->getImgByType();
	
		//data:image/png;base64,
		//base64_encode(image)	
		header("Content-type: image/png");			
   		return imagepng($this->img);
		
   }
   
	function __destruct()
	{
		imagedestroy($this->img);	
	}
   
   // generira image
   private function getImgByType()
   {   	
   		$this->imageProperties= getimagesize($this->imageFile) or die ("incorect file!");
   		switch($this->imageProperties[2])
	   	{
	   		case IMAGETYPE_BMP:
	   			$this->img = @imagecreatefromwbmp($this->imageFile);
	   			break;
	   		case IMAGETYPE_PNG:
	   			$this->img = @imagecreatefrompng($this->imageFile);
	   			break;
	   		case IMAGETYPE_GIF:
	   			$this->img = @imagecreatefromgif($this->imageFile);
	   			break;
	   		case IMAGETYPE_JPEG:
	   			$this->img = @imagecreatefromjpeg($this->imageFile);
	   			break;
	   		default:
	   			die("problemi s sliko");
	   	}
   }
	
	
	
}



?>