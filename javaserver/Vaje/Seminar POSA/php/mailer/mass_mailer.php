<?

/**
 * Page for sending on mailing list - last news
 * @remake for BE2:  Anze Robida, 29.10.2007
 *
*/

$start = microtime();

include "../../inc/config.inc";
include "../../inc/template.inc";
include "../../inc/db/db.inc";
include "../../inc/db/sql.inc";
include "../../inc/html/html_functions.inc";
include "../../inc/date_functions.inc";
include "../../inc/string_functions.inc";
include "../../inc/ui_messages.inc";
include "../../inc/message.inc";
include "../../inc/mime_mail.inc";
include "../../inc/template_db.inc";
include "../../inc/template.class.inc";
include "../../inc/frontpage.inc";
include "../../inc/article.inc";
include "../../inc/fs/filesystem_func.inc";

session_start();
session_unregister("maillist_sent_time");
session_register("messages");

// $mail->AddEmbeddedImage("rocks.png", "my-attach", "rocks.png");
// $mail->Body = 'Embedded Image: <img alt="PHPMailer" src="cid:my-attach"> Here is an image!';

//include "class/test.class.php";
//$xtest = new testClass("hojlala");

function addError($tx)
{
	global $myMAILER_Errorstr;
	if (strlen($myMAILER_Errorstr)>0) $myMAILER_Errorstr .="<br/ >";
	$myMAILER_Errorstr .= " - ".$tx;
}

$myMAILER_POSTBACK="";
$myMAILER_Errorstr="";
if(!empty($_POST))
{
	$myMailSender  		= $_POST["mass_mailer_sender_name"];
	$myMailSender_mail  = $_POST["mass_mailer_sender_email"];
	
	$myMailSubject 		= $_POST["mass_mailer_subject"];
	$myMailContent 		= stripslashes($_POST["mass_mailer_content"]);
	$myMailContentTxt 	= stripslashes($_POST["mass_mailer_txtcontent"]);
	
	$myMailUserParam 	= $_POST["mass_ident_param"];
	$myMailIdentImage 	= $_POST["mass_ident_image"];	
	
	if (strlen($myMailSender)>3 && strlen($myMailSender_mail)>3 && strlen($myMailSubject)>3 && strlen($myMailContent)>3 && strlen($myMailUserParam)>0)
	{
		include "class/mailParser.class.php";
		include "class/csvfile_parser.class.php";
		include "class/mass_mail_sender.class.php";
			
		$myMAILER_POSTBACK = "123123postback";
		$totalMailCount=0;
		// priprava maila za pošiljanje
	    $myMail = new mailParser($myMailContent);
	    $myMail->Sender($myMailSender, $myMailSender_mail);
		$myMail->Subject($myMailSubject);

		if (strlen($myMailContentTxt)>0)
				$myMail->TextMailBody($myMailContentTxt);
				
		$listIsSql 		= $_POST['mass_mailer_receivers_sql'];
		// mass mail sender
		$mSender = new mass_mail_sender($myMail->GetMail());
		$mSender->MailIdentImage($myMailIdentImage);
		$mSender->UserIdentParam($myMailUserParam);
		//$mSender->TestMailer();
		if ($listIsSql)
	    {
	    	// funkcije za pridobivanje array tabele
	    	// iz sql
			$sql_MailList=array(array());
			// tukaj se priredi listo pridobljeno z sql-om 
		    // $mSender->MailReceiversList($sql_MailList);
		    // Pošiljanje!!!
		    //$mSender->START_SENDING();
	    }
	    else
	    {
		    $fileName 		= $_FILES['mass_mailer_receivers_cvs_file']['name'];
		    $fileName_tmp 	= $_FILES['mass_mailer_receivers_cvs_file']['tmp_name'];     
		    $fileError 		= $_FILES['mass_mailer_receivers_cvs_file']['error'];
	    	if (file_exists($fileName_tmp))
		    {
		    	$cvsParser = new csvfile_parser($fileName_tmp);
		    	if($cvsParser->Parse()>0)
		    	{	
		    		$totalMailCount =$cvsParser->Count();
		    		$mSender->MailReceiversList($cvsParser->GetData());
		    		// $mSender->DontSend();
		    		// Pošiljanje!!!
		    		$mSender->START_SENDING();
		    	}
		    	else
		    	{
		    		addError("napaka okrog datoteke!");
		    		$myMAILER_POSTBACK = "";
		    	}
		    }
	    	else
	    	{
	    		addError("ne najdem datoteke");
	    	}
	    	
	    	$countXmails 	= $mSender->SendMailCounter();
	    	if ($totalMailCount>$countXmails)
	    	{
	    		$lastMail 		= $mSender->LastMail();
		    	$errorMails 	= $mSender->MailFailed();
	    		addError("Vseh mailov: ".$totalMailCount);
	    		addError("Uspešno poslanih: ".$countXmails);
	    		addError("Zadnji email: ".$lastMail);
	    	}

	    }
	}
	else
	{
	    if (strlen($myMailSender)<4)  addError("Manjka Posiljatelj!!!");
	    if (strlen($myMailSender_mail)<4)  addError("Manjka Email pošiljatelja!!!");
	    if (strlen($myMailSubject)<4)	addError("Manjka Zadeva ali Subject!!!");
	    if (strlen($myMailContent)<4)	addError("Manjka Vsebina maila!!!!");
	    if (strlen($myMailUserParam)<1) addError("Manjka Url user parameter {za indetifikacijo prejemnika}");
	    $myMAILER_POSTBACK = "";
	}

}




//if there`s no uniq id i wont let him use maillist gui//
/*
if (!$uniq) {
	mime_report_maillist_abuse();
	message_push("missing uniq");
	header("Location: /main/main.php");
	exit;
}
*/

// i check if the uniq id is allready in the db and if then i redirect him back to index//
/*
$db=new DB_Sql();
$db->query("select * from maillist_log where uniq='$uniq'");
$c=$db->affected_rows();
if ($c!=0) {
	mime_report_maillist_abuse();
	message_push("uniq already sent");
	header("Location: /main/main.php");
	exit;
}
*/
//open template
$t = new cls_Template($TEMPLATE_B2_DIR,"mailinglist/mass_mailer.tpl");
$t->add_hf(true);
$t->open();

$row = $t->repeater("MESSAGE");
if ($messages) {
	$t->row("message",message_list(),$row);
	$t->replace("MESSAGE",$row);
} else $t->replace("MESSAGE","");


$t->replace("HIDDEN",html_hidden("uniq",$uniq));
$t->replace("BODY",$body);
$t->replace("SUB_FORM",$sub_form);
$t->replace("PAGE_TIME", date_page_time($start));
$t->replace("HEADER_USER", $_SESSION["user_title"]);
$t->replace("HEADER_TITLE", $_["MAILINGLISTS"].": ".$_["U_LASTNEWS"]);

// 123
$t->replace("SENDER", $_['SENDER']);

$t->replace("CONTENT", $_['CONTENT']);
$t->replace("MAILINGLISTS", $_['MAILINGLISTS']);
$t->replace("MASS_MAILER_CSV", $_['U_EMAIL']." | ".$_['U_NAME']." | ID = opcijsko");
$t->replace("SEND", $_["SEND"]);

$_['MAIL_SUBJECT']                                      = 'Zadeva';
$_['MAIL_CONTENT']                                      = 'HTML Vsebina';
$_['MAIL_CONTENT_TXT']                                  = 'Textovna vsebina';
$_['MAIL_SEND']                                      	= 'Masovno pošiljanje';
$_['MAILINGRECEIVERS']                                  = 'Prejemniki';
$_['MAILURLTRACKPIC']                                   = 'Mail tracking slika (pikica)';
$_['MAILURLTRACKPARAM']                                  = 'Url user parameter';


$t->replace("MAILINGRECEIVERS", $_['MAILINGRECEIVERS']);
$t->replace("MAIL_SUBJECT", $_['MAIL_SUBJECT']);
$t->replace("MAIL_CONTENT", $_['MAIL_CONTENT']);
$t->replace("MAIL_CONTENT_TXT", $_['MAIL_CONTENT_TXT']);
$t->replace("MAIL_SEND", $_['MAIL_SEND']);
$t->replace("MAIL_CONTENT", $_['MAIL_CONTENT']);
$t->replace("MAILURLTRACKPIC", $_['MAILURLTRACKPIC']);
$t->replace("U_NAME", $_['U_NAME']);
$t->replace("U_EMAIL", $_['U_EMAIL']);
$t->replace("MAILURLTRACKPIC", $_['MAILURLTRACKPIC']);
$t->replace("MAILURLTRACKPARAM", $_['MAILURLTRACKPARAM']);

//self 
$t->replace("MASSMAILING", $_['MASSMAILING']);


$t->replace("MASSMAILERFORMSELF", $_SERVER['PHP_SELF']);
$t->replace("MAILER_POSTBACK", $myMAILER_POSTBACK);
if (strlen($myMAILER_Errorstr)>0) 
{
	$t->replace("MAIL_ERRORS", "$myMAILER_Errorstr<br />");
	$t->replace("MAIL_ERRORS_NOTICE", "Napaka!"); 
}

// postback
//$t->replace("mass_mailer_content", $myMailContent);

$t->write();




?>
