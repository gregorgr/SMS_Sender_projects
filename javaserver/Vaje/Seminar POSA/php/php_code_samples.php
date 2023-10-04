
<?php 
// ***** 
// samples from:
// http://www.sitepoint.com/forums/showthread.php?t=456441


// SELECT * FROM myTable WHERE age > 20 ORDER BY RAND() LIMIT1

// Quote variable to make safe 
function quote_smart($value) 
{ 
   // Stripslashes 
   if (get_magic_quotes_gpc()) { 
       $value = stripslashes($value); 
   } 
   // Quote if not a number or a numeric string 
   if (!is_numeric($value)) { 
       $value = "'" . mysql_real_escape_string($value) . "'"; 
   } 
   return $value; 
} 

// Connect 
$link = mysql_connect('mysql_host', 'mysql_user', 'mysql_password') 
   OR die(mysql_error()); 


// Make a safe query 
$query = sprintf("SELECT * FROM users WHERE user=%s AND password=%s", 
           quote_smart($_POST['username']), 
           quote_smart($_POST['password']));

mysql_query($query); 

/*
When using mysql_real_escape_string() or addslashes() it changes ' and " to \' and \" which we all know, right? 
But when adding the variables to the mysql database it doesnt actually put the backslashes into the table. 
You DONT need to use strip slashes when bringing data out of the table because the backslashes arent added!

Well you must be using php automatic magic quotes thingy and addslashes/mysql_real_escape_string. 
You should never have backslashes in the actual mysql table

*/



// If the variable is from a link
$blah=$_GET["blah"]; 
// If the variable is from a form
$blah=$_POST["blah"]; 
// If the variable is from a server  environment
$blah=$_SERVER["blah"];

Variables:
$var_name="supervar";
$$var_name="Variable content.";
// This will set $supervar="Variable content." 


$fetch = 'mysql_fetch_assoc';
$arr = $fetch($result); // Will do the same as mysql_fetch_assoc 


$some_really_long_variable = 'some value';
$s = 'some_really_long_variable';
echo $$s;
// Notice 2 dollar signs! -- Outputs some value



$y = 'hello';
${'data_' . $y} = 'test';
// $data_hello has been set to 'test'
echo $data_hello . ' ' . ${'data_' . $y};
// Outputs test test 




$funct_name="louis";
function $funct_name {
//...
}

//This is the same as :
function louis {
//...
} 


Headers:

<?php
header("Location: index.html");
?>
// pazi na presledke spredaj!!!!!


// debug - prints a nice formatted array, only need to pass it the name of the array 
function print_format_array($array_name) { 
  echo '<pre>'; 
  ksort($array_name); 
  print_r($array_name); 
  echo '</pre>'; 
} // end func 

// This method copies a bunch of stock super global variables into just one associative array variable
function getGlobals () { 
        // if any POST values are issued, they are dealt with here... 
        if (!empty($_POST)) { 
            $this->arrayActions = Index::ValidateUserSessionTime($_POST); 
        // ... else, if any GET values are issued... 
        } elseif (!empty($_GET)) { 
            $this->arrayActions = Index::ValidateUserSessionTime($_GET); 
        // ... else, if any COOKIE values are issued... 
        } elseif (!empty($_COOKIE)) { 
            $this->arrayActions = Index::ValidateUserSessionTime($_COOKIE); 
        } // end if 
    } // end function getGlobals 

	
	//prevent files over-writing
//The name of the file passed from the form 
$file_name = $_FILES['userfile']['name']; 
//A random 6 digit number 
$random_digit=rand(000000,999999); 
$new_file_name=$random_digit.$file_name; 
$path= "quotes/".$new_file_name; 

//if there is a file 
if($userfile !=none) 
{ //open if statement 

//Copy it to the path 
	if(copy($_FILES['userfile']['tmp_name'], $path)) 
	{//open if statement 
	echo $new_file_name; 
	} //close if statement 
	else 
	{ //open else statement 
	echo "Error"; 
	} // close else statement 
} // close if statement 


namespaces?!

class GB 
{
//PHP self does not allow using real namespaces, but with this method, you can simulate them.
  function getEntries() 
  {     .. 
  } 
  function display($rows) 
  { 
    .. 
  } 
} 
$rows = GB::getEntries(); 
GB::display($rows); 



class db
{
    /*var $link = null;
    function db($host, $user, $pwd, $db, $charset = 'utf8')
    {
        $this->link = mysql_connect( $host, $user, $pwd );
        if ( !$this->link )
            return false;
        mysql_select_db( $db );
        mysql_query( 'set names '.$charset );
        return true;
    }*/

    function q()
    {
        $args = func_get_args();
        $qry = $args[0];
        $out = array();
        $pos = 0;
        $i = 0;
        $len = strlen($qry);
        while ( ($pos = strpos($qry,'%',$pos+1)) !== false )
        {
            $i++;
            switch ( $qry[$pos+1] )
            {
                case 'n':
                    $out[] = $args[$i];
                    break;
                case 's':
                    $out[] = "'".($pos+2<$len && $qry[$pos+2]=='e'? mysql_real_escape_string($args[$i]):$args[$i])."'";
                    break;
            }
        }
        return mysql_query( vsprintf( str_replace(array('%n','%se'),array('%s','%s'),$qry), $out )/*, $this->link*/ );
    }
} 
// This function eliminates the need for adding mysql_real_escape_string() or quotes ('') every time they are required.
// q('insert into some_table values(null, %se, %s, %n)', $name, $type, $size); 
//becomes:
//mysql_query('insert into some_table values(null,\''.mysql_real_escape_string($name).'\', \''.$type.'\', '.$size.' )'); 
//    * %s -> a normal string, contained in single quotes ('')
//    * %se -> an escaped string, escaped with mysql_real_escape_string() and contained in ''
//    * %n -> a numerical value, not escaped and not contained in ''


// in conjunction with the above db class 
class debug
{
    function explain($var, $echo = true)
    {
        $out = '<table border="1" cellspacing="0" cellpadding="3" style="text-align:left;margin:1em;">';
        $type = gettype($var);

        switch ($type)
        {
            case 'boolean':
                $var = ($var?'true':'false');
            case 'integer':
            case 'double':
            case 'NULL' :
            case 'string':
                $out .= '<tr><th style="color:#a71">'.$type.' :</th><td>'.htmlspecialchars($var).'</td></tr>';
                break;
            case 'array':
                $out .= '
                    <tr>
                        <th colspan="2" style="color:#a71">ARRAY</th>
                    </tr>
                    <tr style="color:#196">
                        <th>KEY</th>
                        <th>VALUE</th>
                    </tr>';
                foreach ( $var as $key => $value )
                {
                    $out .= '<tr><th>'.htmlspecialchars($key).'</th><td>'.nl2br(htmlspecialchars($value)).'</td></tr>';
                }
                break;
            case 'unknown type':
                $out .= '<tr><th colspan="2" style="color:#a71">'.$type.' :</th></tr>';
                break;
            case 'object':
                $className = get_class($var);
                $vars = get_class_vars($className);
                $methods = get_class_methods($className);
                $out .= '
                <tr>
                    <th colspan="2" style="color:#a71; text-align:center;">'.$type.'</th>
                </tr>
                <tr>
                    <th>instance of :</th>
                    <td>'.$className.'</td>
                </tr>
                <tr>
                    <th colspan="2" style="color:#966;">PROPERTIES :</th>
                </tr>
                <tr>
                    <td colspan="2">'.(empty($vars)?'none':debug::explain($vars,false)).'</td>
                </tr>
                <tr>
                    <th colspan="2" style="color:#36b;">METHODS :</th>
                </tr>
                <tr>
                    <td colspan="2">'.(empty($methods)?'none':debug::explain($methods,false)).'</td>
                </tr>
                ';
                break;
            case 'resource':
                $out .= '
                <tr>
                    <th colspan="2" style="color:#a71;text-align:center;">'.$type.'</th>
                </tr>
                <tr>
                    <th>'.get_resource_type($var).' :</th>
                    <td>'.$var.'</td>
                </tr>';
                break;
        }
        if ( $type == 'string' && function_exists($var) )
            $out .= '<tr><th colspan="2" style="color:#36b">also a function name</th></tr>';
        if ( $type == 'string' && class_exists($var) )
            $out .= '<tr><th colspan="2" style="color:#36b">also a class name</th></tr>';
        $out .= '</table>';
        if ( $echo )
            echo $out;
        return $out;
    }

    function dbgWriteDb($msg)
    {
        db::q( 'insert into debug values(null, %se)', $msg );
    }

    function showMysqlError($where = 'screen')
    {
        $err = mysql_error();
        if ( $where == 'db' )
            debug::dbgWriteDb('last mysql error : '.$err);
        else
            echo $err;
    }
	
	
	/*
	The explain() function is a nice replacement for native functions like var_dump() etc. It can explain all type of variables in a better format. 
	It returns the output and if $echo is true, which is the default value, it also prints to the screen.

	dbgWriteDb() -> This one obviously writes the input value to a table including two columns : an id and a message of type text.

	And the showMysqlError(). You will need the function mysql_error() frequently in case of errors. And there can be times that it is impossible 
	to output the error to the screen. One example is a custom class that uses the database as the session storage instead of the default file system. 
	Since some session job have to happen before any output sent, if there is a database error, you cannot see it on your screen. Instead, you can 
	direct it to a database table. This function writes the error either to the screen (default) or to the database.
	
	debug::explain($_SERVER); 
	debug::showMysqlError('db'); 

	
	*/
} 

//http://www.roscripts.com/PHP_login_script-143.html
CREATE TABLE `users` (
  `ID` int(11) NOT NULL auto_increment,
  `Username` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Temp_pass` varchar(55) default NULL,
  `Temp_pass_active` tinyint(1) NOT NULL default '0',
  `Email` varchar(255) NOT NULL,
  `Active` int(11) NOT NULL default '0',
  `Level_access` int(11) NOT NULL default '2',
  `Random_key` varchar(32) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `Username` (`Username`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=MyISAM;


?>

<script language="JavaScript" type="text/javascript">

var url_params_names = 0;
var url_params_value = 0;
	
	// reads url parameter from array
function get_url_parameter(name)
{
	var i = 0;
	for (i=0;i<url_params_names.length;i++)
		if (url_params_names[i]==name)
			return url_params_value[i];
	return 0;	
}

// reads all url parameters ans adds them to array's url_params_names & url_params_value
function read_url_params()
{
	var sURL = window.document.URL.toString();
	if (sURL.indexOf("?") > 0)
	{
		var arrParams = sURL.split("?");
		var arrURLParams = arrParams[1].split("&");	
		url_params_names = new Array(arrURLParams.length);
		url_params_value = new Array(arrURLParams.length);		
		var i = 0;
		for (i=0;i<arrURLParams.length;i++)
		{
			var sParam =  arrURLParams[i].split("=");
			url_params_names[i] = sParam[0];
			if (sParam[1] != "")
				url_params_value[i] = unescape(sParam[1]);
			else
				url_params_value[i] = "";//No Value
		}
	}
}
// on load function
read_url_params();

</script>
