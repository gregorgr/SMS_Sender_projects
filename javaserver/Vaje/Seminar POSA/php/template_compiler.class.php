<?PHP
/**
 * xPHP template
 * Template compiler class
 *
 */
class template_compiler {
	private $parser;
	private $compiled="";
	private $compile_date;
	private $template_file;
	
	private $show_date=false;
	
	private $current_element=array();
	
	
	/**
	 * Enter description here...
	 *
	 * @param string $template_file
	 */
	public function __construct($template_file){	
		// we have a template file
		if (!($fp = fopen($template_file, "r"))) {
			die("could not open XML input: $template_file");
		}
		
		$this->template_file=&$template_file;
		$this->compile_date = date("d-m-o h:i");
		
		
		$this->parser = xml_parser_create();				
		// create handlers
		xml_set_element_handler(	$this->parser,
            array(&$this,"xml_element_handler_start"),
            array(&$this,"xml_element_handler_end"));
			
		xml_set_default_handler(	$this->parser , 
			array(&$this,"xml_element_handler_default") );
		
		// read xml document
		while ($data = fread($fp, 4096)) {
			if (!xml_parse($this->parser, $data, feof($fp))) {
				die(sprintf("XML error: %s at line %d",
                   xml_error_string(xml_get_error_code($xml_parser)),
                   xml_get_current_line_number($xml_parser)));
			}
		}
		xml_parser_free($this->parser);
	}
	
	/**
	 * Prints template compilation date time at the end of compiled template
	 *
	 * @param bool $bool
	 */
	public function show_compile_date($bool=true){
	
		$this->show_date=$bool;
	}
	
	/**
	 * Saves compiled xPHP template to default location
	 *
	 */
	public function save()	{
		
		if ($this->show_date)
			$this->compiled.='<!-- xPHP template compile_date: '.$this->compile_date.' -->';
			
		$fh = fopen($this->template_file.'.php', 'w+');
		fwrite($fh, $this->compiled);
		fclose($fh);
	}
	
	
	public function echo_compiled()	{
	
		echo($this->compiled);
	
	}
	
	// xml handlers
	
	/**
	 * Default start xml handler
	 *
	 * @param xml_parser_type $parser
	 * @param string $name
	 * @param array $attrs
	 */
	private function xml_element_handler_start($parser, $name, $attrs){
		$name= strtolower($name);
		$xphp= explode(':',$name);
		if (is_array($xphp) && count($xphp)>1)
		{
			$this->xphp_element_handler_start($xphp[0], $xphp[1], $attrs);
		}
		else
		{
			// html handler
			$this->html_element_handler_start($name, $attrs);
		}
	}
	
	/**
	 * Default end xml handler
	 *
	 * @param xml_parser_type $parser
	 * @param string $name
	 */
	private function xml_element_handler_end($parser, $name){
		$name= strtolower($name);
		$xphp= explode(':',$name);
		if (is_array($xphp) && count($xphp)>1)
		{
			$this->xphp_element_handler_end($xphp[0], $xphp[1]);
		}
		else
		{
			// html handler
			$this->html_element_handler_end($name);
		}
	}
	
	
	/**
	 * Default xml handler - handels text within tags
	 *
	 * @param xml_parser_type $parser
	 * @param string $data
	 */
	private function xml_element_handler_default($parser, $data){
		$this->compiled.=$data;
	}
	
	
	/// start handlers
	
	/**
	 * PHP element start handler
	 *
	 * @param string $namespace
	 * @param string $name
	 * @param array $attrs
	 */
	private function xphp_element_handler_start($namespace, $name, $attrs){
		
		if ($name!='template')
		{
			$php_attributes="";
			$this->compiled.='<?PHP ';
			foreach ($attrs as $key => $value) {

				$php_attributes.='"'.strtolower($key).'"=>"'.$value.'",';
			}
			$this->compiled.=' $t->create_element("'.$namespace.'","'.$name.'",array('.$php_attributes.')); ';
			$this->compiled.=' ?>';
		}
	
	}
	
	/* HTML element start handler
	 *
	 * @param string $name
	 * @param array $attrs
	 */
	private function html_element_handler_start(&$name, &$attrs){
		$this->compiled.='<'.$name;
		foreach ($attrs as $key => $value) {
			$this->compiled.=' '.strtolower($key).'="'.$value.'"';
		}
		if ($name=='br')
			$this->compiled.=' />';
		else
			$this->compiled.='>';
	}
	
	
	// end handlers
	
	/**
	 * PHP element end handler
	 *
	 * @param string $namespace
	 * @param string $name
	 */
	private function xphp_element_handler_end($namespace, &$name){
	
		if ($name!='template')
			if(isset($this->current_element["name"]) && $this->current_element["name"]==$name)
			{
				// element is the same!!!
			}
			else 
				$this->compiled.='<?PHP $t->close_element("'.$namespace.'","'.$name.'"); ?>'."\n";
		$this->current_element=array();
	}
	
	/* HTML element end handler
	 *
	 * @param string $name
	 */
	private function html_element_handler_end($name){
		if($name!='br')
			$this->compiled.='</'.$name.'>';
		$this->current_element=array();
	}
	
	
}

class template_reader{
	
	/**
	 * Enter description here...
	 *
	 * @param string $template_file
	 */
	public function __construct($template_file){	
		
		
	}
	
	
	public function append_element($namespace, $name, $attributes){
		
		
		
	}
	
	public function create_element($namespace, $name, $attributes){
		
		
		
	}
	
	public function close_element($namespace, $name){
		
		
	}
}




?>