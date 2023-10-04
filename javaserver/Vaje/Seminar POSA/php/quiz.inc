<?php

/**
 * This class is for working
 * quiz form
 *	
 * @author Gregor Grajzar 2008/05/13
 * @package quiz
 * 
 */


/**
 * Function updates quiz data
 *
 */
function quiz_update($serial_config) {
	global 	$quiz_id, $q_name, $q_data, $q_postdata, $q_status, $status, $site, $q_key, $q_media, $welcome_text, $general_rules_link;	
	//	global 	$keywords, $description, 
	//		$vals, $article_id, $newanswer, $answer, 
	//		 $type, $media_id;
			
	$db = NEW DB_Sql();	
	if (($quiz_id=="") || ($quiz_id == -1))
	{
		// let's insert new quiz
		$quiz_id =$q_data->insert_new_quiz($site, $q_name, $q_key, $serial_config, $q_status, $q_media, $welcome_text, $general_rules_link);
		//echo("$quiz_id");
		if ($quiz_id>0) quiz_update_questions();		
	}	
	else
	{
		$q_data->compare_quiz_row($quiz_id, $q_name,$q_key,$serial_config, $q_status,  $q_media, $welcome_text, $general_rules_link);
		$q_data->get_quiz_data();		
		quiz_update_questions();
	}		
}

/**
 * udate quiz questions
 * if any changes
 *
 */
function quiz_update_questions()
{
	global 	$quiz_id, $q_name, $q_data, $q_postdata, $q_post_images;
	

	

	$sort=1; // for default sorting as posted
	foreach ($q_postdata as $curQ)
	{
		$qid = $curQ["id"];		
		$media_id 	= $curQ['media_id']; 
		// question has some usefull length
		if (strlen($curQ['question'])>3)
		{
			if($curQ["status"]<0)
			{				
				// let's insert a question with status -1
				$qid=$q_data->insert_question_row($curQ['question'],$curQ['points'], $media_id,  $sort);//$curQ['order']);
				quiz_update_answers($qid, $curQ['answer']);		
			}
			else
			{
				// let's update a question if changed
				$q_data->compare_question_row($qid,$curQ["question"], $curQ["points"], $media_id, $sort);//$curQ["order"]);
				quiz_update_answers($qid, $curQ['answer']);
			}
			$db2=null;
		}
		$sort++;
	}

}


/**
 * This will update answers in database
 *
 * @param int $qid
 * @param array $cur_answers
 */
function quiz_update_answers($qid, $cur_answers)
{
	global 	$quiz_id, $q_name, $q_data, $q_postdata;
	$sort=1;
	foreach($cur_answers as $a)
	{
		$aid 		= $a["id"];
		$correct 	= ($a["correct"]!=null && $a["correct"]=="on"?1:0);
		// answer has some usefull length
		if (strlen($a['answer'])>0)
		{
			$db3 = NEW DB_Sql();
			if($a["status"] <0)
			{	
				// let's insert a answer with status -1			
				$aid=$q_data->insert_answer_row($qid, $a["answer"], $correct,$sort);//$a['order']);
			}
			else
			{
				// lets update answer
				$q_data->compare_answer_row($aid ,$a['answer'],$correct, $sort);//$a['order']);
			}
			$db3 = null;
		}
		$sort++;
	}
	
}


/**
 * updates result ranks for quiz
 *
 */
function  result_update()
{
	global 	$quiz_id, $q_status, $q_postdata, $q_data;
	$q_data->get_result_table();
	foreach ($q_postdata[0]['result'] as $row)
	{
		$points_low = (int)$row["points_low"];
		$points_high = (int)$row["points_high"];
		// let's update results ranking table
		if ($row["id"]<0)
		{
			// if id is -1 well add new record
			$q_data->insert_result_row($row["rank"],$points_low,$points_high );
		}
		else
		{
			$q_data->compare_result_row($row["id"],$row["rank"],$points_low,$points_high);
		}
	}
}


/**
 * delete questions ans answers from quiz
 *
 * @param array $delete_data_array
 */
function delete_from_quiz($delete_data_array)
{
	$db = NEW DB_Sql();	
	
	// let's delete questions and their answers
	foreach($delete_data_array[0]["question"] as $row)
	{	
		$del_sql="DELETE FROM quiz_questions WHERE id=".$row["id"];
		$db->query($del_sql);
		//echo("del: $del_sql<br />");
		$del_sql="DELETE FROM quiz_answers WHERE question_id=".$row["id"];
		$db->query($del_sql);	
	}
	// let's delete  answers
	foreach($delete_data_array[0]["answer"] as $row)
		$db->query("DELETE FROM quiz_answers WHERE id=".$row["id"]);		

	// let's delete  results
	foreach($delete_data_array[0]["results"] as $row)
		$db->query("DELETE FROM quiz_result WHERE id=". $row["id"]);

}


/**
*
* Renders a correct title on the top of 
* quiz right section 
* 
* @var template 	&$t
* @var int			$id
* */
function quiz_top_title(&$t, $id)
{
	$edit_new_quiz 	= "";
	$edit_quiz 		= "";
	$edit_players	= "";
	$r_edit_new_quiz 	= $t->repeater("XEDITNEWQUIZ");
	$r_edit_quiz 		= $t->repeater("XEDITQUIZ");
	$r_edit_players 	= $t->repeater("XEDITPLAYERS");
	switch($id)
	{
		case 0: // XEDITNEWQUIZ		
			$edit_new_quiz 	.= $r_edit_new_quiz;
			break;
		case 1: // XEDITQUIZ
			$edit_quiz 		.= $r_edit_quiz;
			break;
		case 2: // XEDITPLAYERS
			$edit_players	.= $r_edit_players;
			break;
		default:	
			break;
	}
	if($edit_new_quiz)	$t->replace("XEDITNEWQUIZ", $edit_new_quiz);
	if($edit_quiz) 		$t->replace("XEDITQUIZ", 	$edit_quiz);
	if($edit_players)	$t->replace("XEDITPLAYERS", $edit_players);
}



/**
 * Render form for quiz editing in backend
 *
 * @param int $quiz_id ID of quiz
 */
function quiz_show_edit($quiz_id) 
{
	global $TEMPLATE_B2_DIR, $_;
	global $site, $q_save_status, $my_last_question, $q_data, $my_last_answer, $sub_view, $no_image_src;	
	if (!$quiz_id = (int)$quiz_id)
		return FALSE;
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// i open template for editing
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	$t = new cls_Template($TEMPLATE_B2_DIR, "quiz/edit.tpl");
	$t->open();	
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// i get data about quiz
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	$my_quiz = &$q_data->get_quiz();
	
	//$db = new DB_Sql();
	//$sql = "SELECT * FROM quiz WHERE id='$quiz_id'";
	//$db->query($sql);
	
	//if ($db->next_record()) {
	if (count($my_quiz)>1) {
		
		$answerCount=0;
		$qmedia_id= $my_quiz["media_id"];
		//$qmedia_id= $db->f("media_id");
		$t->replace("QUIZ_ID", $quiz_id);
		$t->replace("Q_NAME", $my_quiz["name"]);
		//$t->replace("Q_NAME", $db->f("name"));
		
		// select dropdown status
		//switch ($db->f("status"))
		switch ($my_quiz["status"])
		{
			case 3:
				$t->replace("STATUS_3_SELECTED", " selected=\"selected\"");
				break;
			case 9:
				$t->replace("STATUS_9_SELECTED", " selected=\"selected\"");
				break; 
		}
		// select what to show next		
		if ($q_save_status<1 && !$sub_view)
		{
			quiz_top_title($t,1);
			$t->replace("QUESTION_SECTION", quiz_show_questions($quiz_id));			
			/// $t->replace("SAVE", "Shrani in nadaljuj" );
		}
		else if($sub_view=='players')
		{
			quiz_top_title($t,2);
			$t->replace("RESULT_SECTION", quiz_show_players($quiz_id));
			///	$t->replace("SAVE", "Shrani" );
		}
		else
		{
			quiz_top_title($t,1);
			// this will open RESULT_SECTION -> result ranks for quiz
			$t->replace("RESULT_SECTION", quiz_show_results($quiz_id));
			/// $t->replace("SAVE", "Shrani" );
		}	
		
		// quiz image
		$img=get_image_array($qmedia_id);		
		$t->replace("Q_MEDIAID", $qmedia_id);		
		$t->replace("QUIZ_IMAGE_SRC",$img["src"]);
		$t->replace("QUIZWELCOMETEXT",$my_quiz["welcome_text"]);
		$t->replace("QUIZGENERALRULES",$my_quiz["rules_link"]);
	}
	else 
	{
		quiz_top_title($t,0);
		/// prints out default values 
		$t->replace("QUESTION_SECTION", quiz_show_questions($quiz_id));			
		
		$t->replace("QUIZ_IMAGE_SRC",$no_image_src);	
		$t->replace("QUIZ_ID", "-1");
		$t->replace("TITLE", "");
				
		// questions
		$t->replace("QID", "0" );		
		$t->replace("SAVE", "Shrani" );
		$t->replace("VALUEPOINTS", "10" );
		$t->replace("VALUESTATUS", "-1" );
		$t->replace("VALUEORDER", "0" );

		// answer
		$t->replace("AID", "-1" );		
		$t->replace("ANSWID", "-1" );
		$t->replace("ANSTATUS", "-1" );
		$q_save_status=0;
		$my_last_question= 0;
		$my_last_answer =0;
	}		
	$t->replace("LAST_QUESTION", $my_last_question );
	$t->replace("LAST_ANSWER",   $my_last_answer );	
	$t->replace("QUIZ_SAVE_STATUS",   $q_save_status );
	
	return $t->get(FALSE);	
}

/**
 * Render search result in backend
 * We insert searcher and results for quiz into template
 *
 * @param Array $data $_REQUEST array, from where we get data about seaching
 */
function quiz_show_search( $data, $template_file = "quiz/search.tpl") {

	global $site;
	global $TEMPLATE_B2_DIR;
	global $ITEMS_ON_PAGE;

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// I open template for searcher
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	$t = new cls_Template($TEMPLATE_B2_DIR, $template_file);
	$t->open();

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// we look for quizs
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	$db = new DB_Sql();
	if (!$data['status']) {
		$data['status'] = 3;
	}

	$sql = "SELECT * FROM quiz WHERE site_id='$site' AND status='".$data['status']."'";

	if ($data['s_keywords'])
		$sql .= " AND name LIKE '%" . $data['s_keywords'] . "%'";

	$sql .= " ORDER BY created DESC ";


	// katero stran prikazujemo
	$page = (int)$_REQUEST["page"];
	if (!$page) $page = 1;

	$sql .= "LIMIT " . (($page - 1) * $ITEMS_ON_PAGE) . ", " . $ITEMS_ON_PAGE;

	$db->query($sql);

	$table = "";
	$row = $t->repeater("TR");

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// we make tabkle with results
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	if ( $db->affected_rows() === 0) {
		$t->replace("ERROR", "Nothing found");
		return $t->get(FALSE);
	}

	while ($db->next_record()) {

		$table .= $row;

		$t->row("QUIZ_ID", $db->f('id'), $table);
		$t->row("TITLE", $db->f('name'), $table);
		$t->row("TITLE_JS", addslashes($db->f("name")), $table);
		//$t->row("START_DATE", date("d.m.Y", $db->f("start")) , $table);
		$answers = $t->row_repeater('ANSWER', $t);
		$t->row_class($table);
	}

	$t->replace("TR", $table);
	$t->replace("S_KEYWORDS", $data['s_keywords']);
	$t->replace("OPTIONS_STATUS", options_quiz_status($data['status']));

	$t->replace("PAGE", $page);

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// pager for clicking by pages
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	if ((int)$_REQUEST["p_all_items"])
		$all_items = $_REQUEST["p_all_items"];
	else
		$all_items = db_number_items($sql);

	$pager = new cls_Pager($all_items, $ITEMS_ON_PAGE);
	$t->replace("SEARCH_PAGER", $pager->create() );

	return $t->get(FALSE);
}

/**
 * function renders show players 
 * form 
 *
 * @param unknown_type $quiz_id
 */
function quiz_show_players($quiz_id)
{
	global $TEMPLATE_B2_DIR;
	global $site, $q_data,$q_play_date, $q_select_one_player;		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// i open template for editing
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	$t = new cls_Template($TEMPLATE_B2_DIR, "quiz/edit_players.tpl");
	$t->open();
	
	// get play date
	if (!$q_play_date)
		$q_play_date=date('d.m.o');
	
	$t->replace("CURMAXREZ", $q_data->get_max_result());
	
	$t->replace("Q_PLAYS_DATE", $q_play_date);
	// turn to unix date
	$q_play_date=convert_to_unix_date($q_play_date);
	$q_play_date2 = strtotime("+1 day", $q_play_date); 
	//echo("$q_play_date<br>$q_play_date2");
	
	$sql ="SELECT
			p.id, q.p_date, p.name, p.surname, p.address, p.zip, p.city,  p.email, q.points
		FROM quiz_players as p
		inner join quiz_plays as q on p.play_id=q.id
		WHERE  q.quiz_id=".$quiz_id;
	$sql .=" AND q.p_date>=$q_play_date AND  q.p_date<$q_play_date2";
	if ($q_select_one_player)$sql.=" Order By RAND() LIMIT 3";
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// i get data about quiz
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	$db = new DB_Sql();
	$db->query($sql);
			
	$results ='';
	$r = $t->repeater("PLAYER");
	while ($db->next_record()) 
	{
		$results .= $r;
		$t->row("PLAYERID",			$db->f("id"),	$results);
		$t->row("PLAYER_DATE",		date('d.m.o',$db->f("p_date")),	$results);
		$t->row("PLAYER_NAME",		$db->f("name"),	$results);
		$t->row("PLAYER_SURNAME",	$db->f("surname"),	$results);
		$t->row("PLAYER_ADDRESS",	$db->f("address"),	$results);
		$t->row("PLAYER_ZIP",		$db->f("zip"),	$results);
		$t->row("PLAYER_CITY",		$db->f("city"),	$results);
		$t->row("PLAYER_EMAIL",		$db->f("email"),	$results);
		$t->row("PLAYER_POINTS",	$db->f("points"),	$results);
		
	}
	if ($results)
			$t->replace("PLAYER", $results);	
	
	return $t->get(FALSE);
}

/**
 * let's show form for
 * quiz result ranks
 *
 * @param int $quiz_id
 * @return unknown
 */
function quiz_show_results($quiz_id)
{
	global $TEMPLATE_B2_DIR;
	global $site, $q_data;		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// i open template for editing
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	$t = new cls_Template($TEMPLATE_B2_DIR, "quiz/edit_result.tpl");
	$t->open();
	$t->replace("CURMAXREZ", $q_data->get_max_result());

	// i go trough results for printing
	if ($q_data->get_result_table())
	{
		$count=0;
		$r = $t->repeater("RESULT");
		$results ='';
		$cur_rID=0;	
		
		foreach ($q_data->result_table() as $row)
		{
			$results .= $r;
			$cur_rID	=$row['id'];		
			$t->row("RESULT_ID",$cur_rID,	$results);
			$t->row("RESULT_RANK",	$row['rank'],	$results);
			$t->row("RESULT_LOW",	$row['points_low'],	$results);
			$t->row("RESULT_HIGH",	$row['points_high'],	$results);	
			$t->row("ANSTATUS",	0,	$results);
			$count++;	
		}
		if ($results)
			$t->replace("RESULT", $results);

	}
	else
	{		
		//default values
		$t->replace("RESULT_LOW", 0);
		$t->replace("RESULT_ID", -1);
		$t->replace("ANSTATUS", -1);		
	}
	$t->replace("QID", 0);

	
	return $t->get(FALSE);
}

/**
 * shows quiz questions form
 *
 * @param id $quiz_id
 * @return unknown
 */
function quiz_show_questions($quiz_id)
{
	global $TEMPLATE_B2_DIR;

	global $site, $my_last_question, $my_last_answer, $q_data, $no_image_src;	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// i open template for editing
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	$t = new cls_Template($TEMPLATE_B2_DIR, "quiz/edit_questions.tpl");
	$t->open();
	$maxID=0;
	$answerCount=0;
	// let's cruze trough questions from 
	// table stored in $q_data object
	if ($q_data->question_count())
	{
		$my_quiz = &$q_data->get_quiz();
		//list questions
		$question_count=0;
		// opens subsection in template
		$question = $t->repeater("QUESTION");
		$questions ='';
		$cur_qID=0;	
		
		foreach ( $q_data->GetQuestions() as $row)
		{
			$questions .= $question;
			$cur_qID	= $row['id'];
			$img_src	= $row['image'];
			$media_id 	= $row['media_id']; 
			if ($maxID<$cur_qID)$maxID=$cur_qID;
			/// answer subsection 
			$answers='';				
			// opens subsection in template
			$subrow = $t->row_repeater('ANSWERS', $questions);	
			// i'll get questions from  $q_data object
			$ans_arr= $q_data->get_question_answers($cur_qID,'order');
			$answerCount=0;	
			foreach($ans_arr as $arow)
			{
				$answerCount++;
				$answers.= $subrow;
				$cur_aID=$arow['id'];
				$t->row("ANSWID",	$cur_aID,	$answers);
				$t->row("AVALUE",	$arow['answer'],	$answers);			
				$correct=($arow['correct']==1? "checked":"");
				$t->row("CHVALUE",	$correct,	$answers);
				$t->row("ANSTATUS", 0,	$answers);
			}		
			$t->row('ANSWERS', $answers, $questions); 
			// back to question section
			$t->row("QID",	$cur_qID,				$questions);
			$t->row("VALUESTATUS",	0,				$questions);
			$t->row("VALUE",$row['question'],	$questions);			
			$t->row("VALUEPOINTS",$row['points'],	$questions);
			
			// quiz image
			$img=get_image_array($media_id);
			
			$t->row("MEDIAID",$media_id,	$questions);
			$t->row("QUESTION_IMAGE_SRC",$img["src"],	$questions);
			$t->row("QUESTION_IMAGE_ALT",$img["alt"],	$questions);
			
					
					
			//$t->replace("VALUESTATUS", "0" );				
			$question_count++;
		}

		if ($questions)
			$t->replace("QUESTION", $questions);	
		
		$t->replace("QUESTION_IMAGE_SRC", $no_image_src );
		
		switch ($my_quiz["status"])
		{
			case 3:
				$t->replace("STATUS_3_SELECTED", " selected=\"selected\"");
				break;
			case 9:
				$t->replace("STATUS_9_SELECTED", " selected=\"selected\"");
				break; 
		}
		
		// we read advanced settings
		$t->replace("QUIZKEY", $my_quiz["quizkey"]);
		$adv_settings = $my_quiz["q_config"];
		$t->replace("QADVSERIALIZED", $adv_settings);
		$adv_settings =unserialize($adv_settings);
		$t->replace("QSUBMITFORM", 	$adv_settings["player_submit_form"]);
		$t->replace("QAJAXTPL", 	$adv_settings["quiz_ajax_tlp"]);
		$t->replace("QAJAXREP", $adv_settings["quiz_ajax_repeater"]);
		$t->replace("QIMAGEBIG", 	$adv_settings["quiz_image_big"]);
		$t->replace("QIMAGENORMAL", $adv_settings["quiz_image_normal"]);
		
		// convert database array value to string
		$adv_submit_fields = "";	
		for ($i=0;$i<count($adv_settings["player_submit_fields"]);$i++)
		{
			if ($i>0)
				$adv_submit_fields.=",";
			$adv_submit_fields.=$adv_settings["player_submit_fields"][$i];		
		}	
		$t->replace("QSUBMITFIELDS", 	$adv_submit_fields);		
		
	}
	else
	{
		//echo("questions!!!<br>");
		// answer
		$t->replace("QID", "0" );
		$t->replace("VALUESTATUS", -1 );
		$t->replace("QUESTION_IMAGE_SRC", $no_image_src );
		$t->replace("ANSWID", "0" );
		$t->replace("ANSTATUS", "-1" );
		$t->replace("VALUEPOINTS", 10 );
		$t->replace("MEDIAID", 0 );
	}

	

	$my_last_question= ++$maxID;//$question_count;
	$my_last_answer=$answerCount;
	return $t->get(FALSE);
}

function get_image_array($media_id, $type = "small")
{
	global $PICT_DIM, $PICT_DIM_POINTER, $MEDIA_IMAGE_URL, $no_image_src;
	$img = array("src"=>$no_image_src, "alt"=>"");
	if(!is_null($media_id)&& $media_id>0)
	{		
		if ($PICT_DIM_POINTER[$type]) 
			$type = $PICT_DIM_POINTER[$type];
					
		$pic=sql_get_media_filename($media_id);
		if ($PICT_DIM[$type]['extension'])
			$pic=str_replace(".jpg","." . $PICT_DIM[$type]['extension'],$pic);
		
		$dir_sufix=date_dirname_to_timestamp($pic[2]);

		$img["src"] = $MEDIA_IMAGE_URL."/".$type."/".$dir_sufix."/".$pic[0];
		$img["alt"] = $pic[3];
	}
	return $img;		
}

/**
 * Update status of quiz
 * @param int $archive_id id of quiz
 * @param int $new_status new status to update to
 *
 */
function quiz_set_status( $archive_id = 0, $new_status = 0 ) {
	global $quiz_id,$status;

	if (!$archive_id = (int)$archive_id)
		$archive_id = $quiz_id;

	if (!$new_status = (int)$new_status)
		$new_status = $status;

	$db = new DB_Sql();

	if ($archive_id!="") {
		if ($new_status==3) {
			if (!quiz_test($archive_id)) {
				return FALSE;
			}
		}
		$db->query("update quiz set status=".$new_status." where (id=".$archive_id.")");
		message_push("quiz:status:".$new_status);
	}
}

/**
 * returns unix date int
 * from dd.mm.yyyy
 *
 * @param string $date
 * @param string $delimiter
 * @return int
 */
function convert_to_unix_date($date, $delimiter=".")
{
	$d = explode($delimiter,$date);
	return gmmktime(0,0,0,$d[1],$d[0], $d[2]);
}

/**
 * function sets up secured quiz cookie for a user which is recognized by their hash
 */
function quiz_start( $quiz_id=0 ) {
	global $QUIZ;

	if ( $quiz_id===0 )
		return false;
	//bake cookie
	$userhash = microtime();
	$cookiecrc = md5($userhash.$QUIZ['salt']);
	$cookievalue=str_rot13($userhash.'|'.$cookiecrc);
	setcookie('quiz['.$quiz_id.']',$cookievalue, time()+$QUIZ['session_time'], "/");
	$q_play_date=convert_to_unix_date(date('d.m.o'));

	$d = new db_sql();
	$sql = sprintf("INSERT INTO quiz_plays SET quiz_id=%d, p_date=%d, hash='%s'", $quiz_id, $q_play_date, $userhash);
	$d->query($sql);

	return true;
}

/**
 * function adds +1 to points for quiz player on correct answer
 */
function quiz_record_answer($quiz_id, $question_id, $correct=false) {
	if ( !quiz_check_player($quiz_id) )
		return false;

	$sql = sprintf("UPDATE quiz_plays SET question_id=%d ", $question_id);
	
	if ( $correct )
		$sql .= sprintf(", points=points+(SELECT points FROM quiz_questions where id=%d limit 1) ",$question_id );
	$sql .= sprintf(" WHERE hash='%s'", quiz_cookie_extract($quiz_id,'hash'));
	$d = new db_sql();
	$d->query($sql);

	return true;
}


/**
 * Function inserts player into database!
 * returns player points if successful!
 * 
 * will return 0 if any of strings is longer than 100 chars
 * 
 * @param int $quiz_id
 * @param string $name
 * @param string $surname
 * @param string $address
 * @param string $city
 * @param string $email
 * @return int
 */
function quiz_database_insert_player($quiz_id, $data)
{	
	global $error_msg;
	if ( !quiz_check_player($quiz_id) )
		return false;
	// we test if any of arguments is longer than 1 and shorter than 100
	$insert_fields="";
	$insert_values="";
	$error_msg.="quiz_database_insert_player: ";
	foreach ($data as $field=>$value)
	{
		
		if (strlen($value)>99 | 
			strlen($value)<1 | 
			$value==' ' |  
			$value==false){
			return 0;
		}
		else
		{
			// and construct insert statement
			if (strlen($insert_fields)>0)
			{
				$insert_fields.=", ";
				$insert_values.=", ";
			}
			$insert_fields.=$field;
			$insert_values.="'$value'";
		}		
	}
	// arguments are ok	
	$d = new db_sql();
	$sql = sprintf("SELECT id, points FROM quiz_plays WHERE hash='%s' LIMIT 1", quiz_cookie_extract($quiz_id,'hash'));
	$d->query($sql);
	if ( $d->next_record() ) {	
		$play_id =$d->f('id');
		$points =$d->f('points');
		// test if play id was allready ussed
		$sql = "SELECT id FROM  quiz_players WHERE play_id=". $play_id;
		$d->query($sql);
		if (!$d->next_record())
		{
			// we insert new  player
			$sql = "INSERT INTO quiz_players ";
			$sql .="(play_id, quiz_id,  $insert_fields) VALUES ";
			$sql .="($play_id, $quiz_id,$insert_values)";
			if ($d->query($sql))
				return $points;				
		}	
	}
	return 0;
}

/**
 * function retrieves points for quiz player
 */
function quiz_get_points($quiz_id=0) {
	if ( !quiz_check_player($quiz_id) )
		return false;

	$d = new db_sql();
	$sql = sprintf("SELECT points FROM quiz_plays WHERE hash='%s' LIMIT 1", quiz_cookie_extract($quiz_id,'hash'));
	$d->query($sql);
	if ( $d->next_record() )
		return $d->f('points');
	else
		return false;
}

/**
 * returns points array or false
 *
 * @param int $quiz_id
 * @return array("points"=>$points, "id"=>$id)
 */
function quiz_get_points_array($quiz_id)
{
	$d = new db_sql();
	$sql = sprintf("SELECT id, points FROM quiz_plays WHERE hash='%s' LIMIT 1", quiz_cookie_extract($quiz_id,'hash'));
	$d->query($sql);
	if ( $d->next_record() ) 	
		return array("points"=>$d->f('points'), "id"=>$d->f('id'));
	else
		return false;
}

/**
 * Returns rank array
 *	array("text"=>"some text...", "points"=>xx)
 * 
 * @param int $quiz_id
 * @param int $points
 * @return array
 */
function quiz_get_points_text($quiz_id, $points)
{
	$sql = sprintf("SELECT rank, points_high FROM quiz_result where quiz_id=%d and %d>=points_low and  %d<=points_high",
	$quiz_id, $points, $points);
	
	$d = new db_sql();
	$d->query($sql);
	if ( $d->next_record() )
	
		return array ("text"=>$d->f('rank'), "points"=>$d->f('points_high'));
	else
		return array ("text"=>"", "points"=>0);
}

/**
 * function retrieves possible
 */
function quiz_get_points_all($quiz_id=0) {
	$d = new db_sql();
	$sql = sprintf("SELECT MAX(points_high) AS points FROM quiz_result WHERE quiz_id=%d", $quiz_id);
	$d->query($sql);
	if ( $d->next_record() )
		return $d->f('points');
	else
		return false;
}

 /**
  * function checks cookie consistency
  */
function quiz_check_player($quiz_id=0) {
	global $QUIZ;

	if ( $quiz_id < 1 )
		return false;
	$quiz_id = (int)$quiz_id;

	if ( quiz_cookie_extract($quiz_id,'crc') === md5(quiz_cookie_extract($quiz_id,'hash').$QUIZ['salt']) )
		return true;
	else
		return false;
}

/**
 * function extracts given value from cookie
 */
function quiz_cookie_extract($quiz_id=0,$what='hash') {
	if ( $quiz_id < 1 )
		return false;
	$quiz_id = (int)$quiz_id;

	$cookievalue = str_rot13($_COOKIE['quiz'][$quiz_id]);
	$cookie = explode('|',$cookievalue);

	if ( $what === 'hash' )
		return $cookie[0];
	elseif ( $what=== 'crc' )
		return $cookie[1];
	else
		return false;

}

/**
 * Will clear repeater
 *
 * @param template &$t
 * @param string $repeter_name
 */
function quiz_clear_repeater(&$t, $repeter_name)
{
	if ($t->tag_exists($repeter_name."_START"))
	{
		$r = $t->repeater($repeter_name);
		$t->replace($repeter_name, "");
	}
}


function get_quiz_settings($var, $value)
{
	global $QUIZ_CONFIG; 
	foreach ($QUIZ_CONFIG as &$quiz_settings_row)
		foreach ($quiz_settings_row as $key=>$val)
		{
			if ($key==$var && $key!="q_config" && $val==$value)
				return $quiz_settings["q_config"];
		}
	return 0;
}

/**
 * returns serialized config array
 *
 * @return string
 */
function get_quiz_serialized_config()
{
	global $q_adv_settings;
	$config =$q_adv_settings["serialized"];
	$config_h =$q_adv_settings["serialized_h"];
	
	// serialized config has been changet we post this one
	if ($config!=$config_h)
		return $config;
	else
	{
		$q_fields= explode(',',$q_adv_settings["submit_fields"]);
		foreach ($q_fields as &$field)
		{
			$field = trim($field);
			if (strlen($field)==0)
			{
				// we should remove this field as error	empty insert		
			}
		}
		$submit_form = (string)(int)$q_adv_settings["submit_form"];
		$new_config=array(
			"quiz_image_big"		=>	$q_adv_settings["image_big"],
			"quiz_image_normal"		=>	$q_adv_settings["image_normal"],
			"quiz_ajax_tlp"			=>	$q_adv_settings["ajax_tlp"],
			"quiz_ajax_repeater"	=>	$q_adv_settings["ajax_repeater"],
			"player_submit_form"	=>	$submit_form,
			"player_submit_fields"  =>  $q_fields
		);
		return serialize($new_config);	
	}
	return 0;
}

/**
 * Returns quiz settings array
*
 *  * @param int $id
 * @return settings array
 */
function get_deserialized_settings($id)
{
	// read settings from database
	$db = new DB_Sql();
	$squl="SELECT q_config FROM quiz WHERE id='$id' AND status=3";
	
	$db->query($squl);
	if($db->next_record())
	{	
		// read serialized config from database
		return unserialize($db->f('q_config'));
	}
	else
		return "";
}


/**
 * lets send content to client...
 * and setup json response
 * @param die
 */
function quiz_json_response(&$quiz_id, &$q_response_array, $status=1)
{
	//print_r($q_response_array);
	//and clear cookie if end
	if ($status<=0)
		$q_response_array["quiz_status"]=$status;
		
	if($q_response_array["quiz_status"]<=0)
	{
		$q_response_array["not_refresh"]=0;
		setcookie('quiz['.$quiz_id.']',"", time()-1000, "/");
	}
	$json = new Services_JSON();
	echo($json->encode($q_response_array));	
	if ($q_response_array["quiz_status"]<=0)		die();
	
}

/**
 * returns quiz name
 *
 * @param int $quiz_id
 * @return string
 */
function get_quiz_name($quiz_id){
	$db = new DB_Sql();
	$db->query("SELECT name FROM quiz WHERE id=".$quiz_id);
	if ($db->next_record()){
		return $db->f('name');
	}
	else{
		return "";
	}
}


/**
 * this is universal quiz box
 * quiz_key is passed to div holder as id
 * 
 * Template if not defined should be named like:
 * 		box/central/micro_quiz.tpl --> for micro sites
 * 	or
 * 		box/central/box_quiz.tpl --> for all sites
 * 
 *
 * @param string $quiz_key {if not defined it takes last valid id according to site_id}
 * @param string $box_position {default "central" or "right"}
 * @return string
 */
function micro_quiz_box_render($quiz_key, $box_position="central"){
	
	global $FRONTEND_DIR, $TEMPLATE_DIR, $APP_LANG, $front_id, $section_id, $site, $MICROSITE;

	/* LOAD CACHE  */
	$c = new cls_cache(__FUNCTION__."_{$section_id}_{$front_id}");
	if ($cache = $c->get())
		return $cache;

	// first we have quiz_id
	$quiz_id=2;
	$quiz_config="";
	$quiz_key_id=$quiz_key;

	// first we get last quiz ID with satus 3
	$db = new DB_Sql();
	if ($quiz_key && strlen($quiz_key)>0)
		$quiz_key="AND quizkey='$quiz_key'";
	else
		$quiz_key="";
	$sql="SELECT id, name, q_config, welcome_text, rules_link FROM quiz WHERE site_id=$site AND status=3 $quiz_key Order by id DESC limit 1";
	$db->query($sql);
	if($db->next_record())
	{
		$quiz_id=$db->f('id');
		// read serialized config from database
		$quiz_config	= unserialize($db->f('q_config'));
	}
	// we prepare the temlate
	$q_template_dir=$quiz_config["quiz_ajax_tlp"];
	if (!(strlen($q_template_dir)>5 && file_exists($TEMPLATE_DIR.$FRONTEND_DIR.$q_template_dir))){
		// template file is not defined we take default
		if (isset($MICROSITE))
			$q_template_dir = "micro";
		else
			$q_template_dir = "box";
		$q_template_dir = "box/$box_position/".$q_template_dir."_quiz.tpl";
	}
	//echo($q_template_dir);
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, $q_template_dir);
	$t->open();

	// we need to know postition of box
	if($t->tag_exists("QUIZ_BOX_POSITION"))
		$t->replace("QUIZ_BOX_POSITION", $box_position);
	
	// to clear
	$quiz_ajax_repeater		= $quiz_config["quiz_ajax_repeater"];

	//setup site id and quiz id
	$t->replace("QUIZ_TITLE",$db->f('name'));
	$t->replace("QUIZ_NAME",$quiz_key_id);
	$t->replace("QUIZ_SITE_ID",$site);
	
	
	$t->replace("QUIZ_ID",$quiz_id);
	
	if ($db->f('welcome_text') && $t->tag_exists("QUIZ_WELCOME_TEXT"))
		$t->replace("QUIZ_WELCOME_TEXT", $db->f('welcome_text'));
	// if we have link to general rules, we show repeter
	if ($db->f('rules_link')){
		$t->replace("GENERAL_RULES_LINK",$db->f('rules_link'));
	}
	else{
		quiz_clear_repeater($t, "GENERALRULES");
	}
	
	// let's set up right/wrong images
	$q_answer_image = "/static/slo/";
	if (isset($MICROSITE)){
		$q_answer_image.="microsites/".strtolower($MICROSITE);
	}	
	$t->replace("QUIZ_IMAGE_WRONG",$q_answer_image."/img/buttons/icon_false.gif");
	$t->replace("QUIZ_IMAGE_RIGHT",$q_answer_image."/img/buttons/icon_true.gif");

	// if here is a ajax repeater, we cleare it for later use!!!!!
	if ($t->tag_exists($quiz_ajax_repeater."_START")){
		$r = $t->repeater($quiz_ajax_repeater);
		$t->replace($repeter_name, "");
	}

	if ($t->tag_exists("QUIZWINNERFORM_START")){
		$r = $t->repeater("QUIZWINNERFORM");
		$t->replace($repeter_name, "");
	}

	/* SAVE CACHE */
	$tem = $t->get();
	$c->save($tem);

	return $tem;
}

?>