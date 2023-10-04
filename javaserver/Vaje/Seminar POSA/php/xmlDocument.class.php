<?php

/**
 * a simple xmlDocument and xmlNode class
 * for creating xml documents
 *	
 * 
 * @author Gregor Grajzar
 * @version 1.0 2008/05/05
 */

interface ixmlDocument
{
	public function Version($newVersion="1.0");
	public function Encoding($Encoding="utf-8");
	
	public function &AppendNode(xmlNode $newNode);
	public function getXML();
	public function PrintXML();
	
	public function CountRootNodes();

}

interface ixmlNode
{
	public function SetInnerValue($value, $cData=false);
	
	public function setAttribute($name, $value);
	public function AppendAttributes(array $arguments);
	
	public function &AppendNode(xmlNode $newNode);
	public function &AppendSimpleNode($name, $value, $cData=false);
	public function &Node(xmlNode $node);
	
	public function ChildNodesCount();
	public function ChildNodesGet();
	
	public function ToString();
	
	public function PrintMe();
}

/**
 * a simple xmlNode class
 * for use in xmlDocument class
 *	2008/05/05
 * 
 * @author Gregor Grajzar
 * @version 1.0

 */
class xmlNode implements ixmlNode
{
	
	private $node;
	
	/**
	 * xml element name in constructor of this class
	 * will output like this
	 * <$name />
	 *
	 * @param string $name
	 */
	function __construct($name)
	{
		$this->node = array("element"=>$name);
	}
	
   	/**
   	 * Appendds xmlNode object as a child to a current node
   	 * returns added xmlNode object 
   	 *
   	 * @param xmlNode $newNode
   	 * @return xmlNode
   	 */
	public function &AppendNode(xmlNode $newNode)
   	{
   		$myreturn =& $this->_appendItem("_node",array($newNode));
   		return $myreturn;
   	}
   	
   	/**
   	 * Simple node "appender", will append a child node
   	 * <$name>$value</$name>
	 * if parameter  $cData is true, value will be passed as CDATA (read SetInnerValue)
   	 * 
   	 * @param string $name
	 * @param string $value
	 * @param bool $cData
   	 * @return xmlNode
   	 */
   	public function &AppendSimpleNode($name, $value, $cData=false)
   	{
   		$simpleNode = new xmlNode($name);
   		$simpleNode->SetInnerValue($value, $cData);
   		return $this->AppendNode($simpleNode);
   	}
   	
	/**
	 * apends value to a xml document as:
	 * <$name>$value</$name>
	 * 
	 * if parameter  $cData is true, value will be passed as CDATA:
	 * <![CDATA[
	 * 		$value
	 *  ]]>
	 *
	 * @param string $value
	 * @param bool $cData
	 */
   	public function SetInnerValue($value, $cData=false)
   	{
   		if($cData)
   			$value = "<![CDATA[ \n$value\n ]]>";
   		$this->_appendItem("_value",$value,false);
   	}
   	
   	/**
   	 * Appends one atribute to xml node as:
   	 * <$name $name="$value"/>
   	 *	
   	 * @param string $name
   	 * @param string $value
   	 */
   	public function setAttribute($name, $value)
   	{
   		$this->_appendItem("arguments",array($name => $value));
   	}
   	
   	/**
   	 * Appends an array of atributes to xml Node
   	 *
   	 * $arguments=array(
   	 * 		$name1 => $value1,
   	 * 		$name2 => $value2,...
   	 * );
   	 * 
   	 * @param array $arguments
   	 */
   	public function AppendAttributes(array $arguments)
   	{
   		$this->_appendItem("arguments",$arguments);
   	}
   	
   	
	/**
	 * Enter description here...
	 *
	 * @param unknown_type $name
	 * @param unknown_type $items
	 * @param unknown_type $append
	 * @return unknown
	 */   	
   	private function &_appendItem($name, $items, $append=true)
   	{
   		$i=0;
   		try
   		{
   			if (isset($this->node[$name])&&  $append)
   			{
   				// echo("if.. $name <br />");
   				$i=count($this->node[$name]);
   				$temp = array_merge($this->node[$name],$items);
   				$this->node[$name]=$temp;
   				return $this->node[$name][$i];
   			}
   			else if (isset($this->node[$name]))
   			{
   				$this->node[$name]=$items;
   				return $this->node[$name][0];
   			}		
   			else
   			{
   				// echo("else $name <br />");
   				$this->node = array_merge($this->node,array($name=>$items));
   			}
   		}
   		catch (Exception $e)
		{	
			//throw $e;
		}
   			
   	}
 	/**
 	 * Returns a count of a child nodes
 	 *
 	 * @return int
 	 */
   	public function ChildNodesCount()
   	{
   		$i=0;
   		try
   		{
   			$i=count($this->node["_node"]);
   		}
   	   	catch (Exception $e)
		{	
			//throw $e;
		}
   		return $i;
   	}
   	
   	/**
 	* Returns an array of a child nodes
   	*
   	* @return array
   	*/
	public function ChildNodesGet()
	{
		$n=NULL;
   		try
   		{
   			$n=$this->node["_node"];
   		}
   	   	catch (Exception $e)
		{	
			//throw $e;
		}
   		return $n;
	}
	
   	/**
   	 * returns xmlNode as string
   	 * will return all child nodes as well in a string format
   	 * 
   	 * function can be ussed to retrive xml as string 
   	 * (without xml header element)
   	 *
   	 * @return string
   	 */
   	public function ToString()
   	{
   		
   		$nodeName="";
   		$nodeString="";
   		$argumentsString="";
   		// loops trought all 
   		foreach ($this->node as $key => $value)
   		{
   			switch($key)
   			{
   				case "element":
   					$nodeName=$value;
   					//echo("node $nodeName");
   					break;
   				case "_value":
   					// èe je array
   					$nodeString.=$value;
   					break;
   				case "arguments":
   					$argumentsString=$this->get_arguments_as_string($value);
   					break;
   				case "_node":
   					// executes toString method on all child nodes
   					$nodeString.=$this->get_inner_nodes_string($value);
   					break;
   					
   				}
   		}
   		$s="<".$nodeName.$argumentsString;
   		if (strlen($nodeString)>0)
   		{
   			$s.=">".$nodeString."</$nodeName>\n";
   		}
   		else
   		{
   			$s.=" />\n";
   		}
   		return $s;
   	}
   	
   	/**
   	 * returns a string of child xml node
   	 *
   	 * @param array $innerNodes
   	 * @return string
   	 */
   	private function get_inner_nodes_string($innerNodes)
   	{
   		$s="";
   		// executes toString method on each child nodes
   		foreach ($innerNodes as $xnode)
   		{
   			$s.="\t".$xnode->ToString();
   		}
   		return $s;
   	}
   	
   	/**
   	 * returns arguments as string
   	 *
   	 * @param array $args
   	 * @return string
   	 */
   	private function get_arguments_as_string($args)
   	{
   		$s="";
   		foreach ($args as $key => $value)
   		{
   			$s.= sprintf(' %s="%s"',$key ,$value);
   		}
   		//echo("argumenti: $s<br>");
   		return $s;
   	}
   	
   	/**
   	 * returns an instance of object
   	 *
   	 * @param xmlNode $node
   	 * @return xmlNode
   	 */
   	public function &Node(xmlNode $node)
 	{
 		return $node;
 	}

   	/**
   	 * 	Prints object
   	 * 	<i>xmlNode:</i><br />
   	 *	<pre>$currentNode</pre>
   	 */
   	public function PrintMe()
   	{
   		echo ("<i>xmlNode:</i><br />");
		echo("<pre>");
		print_r($this->node);
		echo ("</pre><br />"); 
   	}
}



/**
 * a simple xmlDocument class
 *
 *	2008/05/05
 * 
 * @author Gregor Grajzar
 * @version 1.0
 */
class xmlDocument implements ixmlDocument{

	private $ver = "1.0";
	private $enc = "utf-8";
	
	private $xnodes=array();


    /**
     * Appends xmlNode to root of the document
     * 
     * !! structure of xml document shoulds be 
     * !! appended to node
     *
     * @param xmlNode $newNode
     * @return xmlNode
     */	
 	public function &AppendNode(xmlNode $newNode)
 	{
 		$i = count ($this->xnodes);
 		$this->xnodes[]=$newNode;
 		//$this->EchoMe();
 		return $this->_node($this->xnodes[$i]);
 	
 	}
 	
 	/**
 	 * Returns a count of nodes at
 	 * root position
 	 *
 	 * @return int
 	 */
 	public function CountRootNodes()
 	{
 		return count($this->xnodes);
 	}
 	
 	/**
 	 * First root node can be added in 
 	 * this constructor
 	 *
 	 * @param xmlNode $newNode
 	 */
	function __construct(xmlNode $newNode=NULL)
	{
		if (!is_null($newNode))
			$this->AppendNode($newNode);
   	}
 	
 	private function &_node(xmlNode $Node)
 	{
 		return $Node;
 	}
 	
	/**
 	 * returns xml document as string
 	 *
 	 * @return string
 	 */  	
   	public function getXML()
   	{
   		$s= "<?xml version=\"$this->ver\" encoding=\"$this->enc\" ?>\n";   		
   		foreach($this->xnodes as $xnode)
   		{
   			$s.=$xnode->ToString();
   		}
   		return $s;  		
   	}
   	
   	/**
   	 * prints xml document
   	 *
   	 */
   	public function PrintXML()
   	{
   		echo $this->getXML();
   	}
  	
   	
   	/**
   	 * Sets a new xml Version 
   	 * default is 1.0
	 *
	 * @param string $newVersion
	 * @return string
	 */
   	public function Version($newVersion="1.0")
   	{
   		$this->ver = $newVersion;
   		return $this->ver;
   	}
   	
	/**
   	 * Sets a new xml Encoding
   	 * default is utf-8
   	 *
   	 * @param string $Encoding
	 * @return string
   	 */
   	public function Encoding($Encoding="utf-8")
   	{
   		$this->enc = $Encoding;
   		return $this->enc;
   	} 
   	
   	function __destruct()
   	{
   		 
   	}
}

interface ixhtml_Element
{
	public function css_class($myclass);
	public function Value($value);
	
	public function ToString();
	public function Render();
}


?>