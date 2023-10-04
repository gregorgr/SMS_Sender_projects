

<?





class csvfile_parser
{
	private $_rowCounter;
	private $_mainFile;
	private $_delimiter;
	private $_csv_array=array();
	
	public function Count()	{
		return $this->_rowCounter;		
	}
	
	public function Delimiter($delimiter){		
		$this->_delimiter=$delimiter;	
	}
	
	/**
	*   @return	array	descriptionVrne array "tabelo" sparsane csv datoteke
	* 
	* 	array(
	* 		array(1,2,3....),
	* 		array(1,2,3....),
	* 		....
	* 		array(1,2,3....)
	* )
	*/
	public function GetData()
	{
		return $this->_csv_array;	
	}
	
	public function Parse(){
		
		if (file_exists($this->_mainFile))	{			
			$h=fopen($this->_mainFile, "r");
			$cline="";
			while (!feof($h)) {
				
				$cline = fgets($h, 4096);
				
				if (strlen(trim($cline))>0) {
					$curentRow=@explode($this->_delimiter , $cline);
					$this->_csv_array[] = $curentRow;
					//$this->_rowCounter++;									
				}				
			}
			fclose($h);
			$this->_rowCounter=count($this->_csv_array);
			return $this->_rowCounter;
		}		
	}
	
	/**
	* konstruktor nastavi osnovne parametre:
	* - csv datoteka
	* - default delimiter |
	* 
	* 	* @param  	string	$file	datoteka
	*/
	function __construct($file) {
		
		$this->_rowCounter	= 0;
		$this->_mainFile	= $file;
      	$this->_delimiter	= "|";	
   }
	
	
	
	
	
}



?>