<?
// <input type=button value="Back" onClick="history.back()">
$formconfirmation=false;
		
$myxPHPForms=array(
	array("ID"=>"submitev", 	"default"=>false,
		"requiresconfirmation"=> true,		
		"postdestination" =>"forms/submitevent.php",		
		"originalobject"=>$userformnavigator,
		"form"=>""),
	array("ID"=>"submitli", 	"default"=>false,
		"requiresconfirmation"=> false,
		"postdestination" =>"forms/submit.php",
		"originalobject"=>$userformnavigator,
		"form"=>""),
	array("ID"=>"subscribe", 		"default"=>true,
		"requiresconfirmation"=> false,
		"postdestination" =>"./xbase/submit.php",
		"originalobject"=>$userformnavigator,
		"form"=>"forms/subscribeemail.php",
		"form1"=>"./xbase/tangoinfoform.php"),
	array("ID"=>"login", 	"default"=>false,
		"requiresconfirmation"=> false,
		"postdestination" =>"./xbase/submit.php",
		"originalobject"=>$userformnavigator,
		"form"=>"")
);


$entersecurecode=true;
LoadCommonControls();


function LoadCommonControls()
{
	global $subdir;
	include $subdir."commoncontrols.php";

}	



function GetObjectValue($object,$key,$value)
{
	$result=false;
	if(count($object)>0)
	{
		for($i=0;$i<count($object);$i++)
		{
			if($object[$i][$key]==$value)
			{
				$result=$object[$i];
			}
		}
	}	
	return $result;
}

function PrintFormField($fields, $name, $child=false)
{
	echo "<tr><td> $name </td></tr>";
}



	


?>