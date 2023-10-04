<?php

/**
 * StringBuilder

 * 
 * @author Gregor Grajzar
 *
 */
class StringBuilder {

	private $s = array();


   	/**
   	 * Sets a new param
   	 * default is 0
   	 *
   	 * @param int $newParam
   	 */
   	public function Param($newParam)
   	{
   		$this->Param = $newParam;
   	}

   	/**
   	 * Appends text to string
   	 *
   	 * @param string $text
   	 */
   	public function Append(&$text)
   	{
   		$this->s[]=$text;
   	}
   	
	function __construct($text= null)
	{
		if (!is_null($text))
			$this->Append($text);
   	}
   	
   	/**
   	 * Returns a complete string
   	 *
   	 * @return string
   	 */
   	public function ToString()
   	{
   		$str="";
   		foreach($text as $this->s)
   			$str.=$text;
   			return $str;
   	}
   	
   	/**
   	 * Returns a length of string
   	 *
   	 * @return int
   	 */
   	public function Length()
   	{
   		$len=0;
   		foreach($text as $this->s)
   			$len+= strlen($text);
   			return $len;
   	}
   	
   	function __destruct()
   	{
   		 
   	}
}
?>