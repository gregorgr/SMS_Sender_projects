<?php

/**
 * Function tries to populate main news box with two articles
 * If there are not 2 articles with seqno=1 linked, then opens normal main news box
 */
function box_central_main_news_2()
{
	global $front_id, $section_id, $TEMPLATE_DIR, $FRONTEND_DIR, $site;
	
	//check if there are 2 linked articles with seqno = 1 (left and right)

	// build exclude list
	$exclude = "";
	$articles = MicrositeArticle::get_articles();
	foreach ($articles as $article)
	{
		if ($exclude) $exclude .= ",";
		$exclude .= $article;
	}

	$db = new DB_Sql();
	$sql = "SELECT a.* 
		FROM front_article af, article a 
		WHERE af.front_id = " . $front_id . " 
		AND af.article_id = a.article_id 
		AND af.show_as = 1 
		AND af.seqno = 1";
	if ($exclude) $sql .= " AND af.article_id NOT IN ($exclude)";	
	$db->query($sql);
	
	//return ordinary main news box
	if ($db->num_rows() < 2)
		return box_central_main_news();
	
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/main_news_2.tpl");
	$t->open();
	
	$row = $t->repeater("LEFT");
	$tmpnews = "";
	for ($i=0;$i<2;$i++) {
		$db->next_record();
		
		$tmpnews = $row;
		
		$article = MicrositeArticle::open((int)$db->f("article_id"));
		
		$t->row("TITLE", $article->title, $tmpnews);
		$t->row("DATE", $article->date, $tmpnews);
		$t->row("SOURCE", $article->sources, $tmpnews);
		$t->row("SUMMARY", $article->summary, $tmpnews);
		$t->row("ID", $article->id, $tmpnews);
		$t->row("URL", $article->url, $tmpnews);
		$t->row("FRONTIMAGE", html_return_media($article->frontImage, "114x76"), $tmpnews);

		//get image pool 	 
		if ($t->row_tag_exists("IF_IMAGE_POOL_START",$tmpnews)) { 

			$t->row("GALLERY_ID",$i,$tmpnews);

			$image_pool = $t->row_repeater("IF_IMAGE_POOL",$tmpnews); 	 
			
			// escape double quotes in media title for AddToPool javascript to work properly 	 
			$db1 = new DB_Sql(); 	 
			$db1->query("select t1.*, REPLACE(t1.title, '\"', '\\\\\"') AS title, t3.name from media t1 inner join article_media t2 using (media_id) left join source t3 on t1.source_id=t3.source_id where (t2.article_id=".(int)$db->f("article_id").") and ((t2.show_at&1)=1) and (t2.type=1) order by t2.seqno"); 	 
			$image = $t->row_repeater("POOL",$image_pool); 	 
			$images = "";
			global $MEDIA_IMAGE_URL;
			$index = 0;	
			while ($db1->next_record()) { 	 
				$images .= $image; 	 
				
				$pic = sql_get_media_filename($db1->f("media_id")); 	 
				$dir_sufix=date_dirname_to_timestamp($pic[2]); 	 

				$url = $MEDIA_IMAGE_URL."114x76/".$dir_sufix."/".$pic[0]; 	 
				$t->row("INDEX",$index++,$images); 	 
				$t->row("IMAGE_URL",$url,$images); 	 
				$t->row("IMAGE_TITLE",$db1->f("title"),$images); 	 
			}
			$t->row("POOL",$images,$image_pool); 	 
			$t->row("IF_IMAGE_POOL",$image_pool,$tmpnews); 	 
		}	
		
		if ($db->f("style") == 'Člani družine') {
			$t->replace("LEFT",$tmpnews);
		} elseif ($db->f("style") == 'Izzivalci') {
			$t->replace("RIGHT",$tmpnews);
		}
	         
		article_choose_template_part_temp($t->_template, $article->id);
	}
	
	return $t->get();
}


/**
 * Funtion populate box with family members
 * 
 */
function box_central_players()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR;
	global $_PARAMS;
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/players.tpl");
	$t->open();
	
	$row = $t->repeater("PLAYER");
	$rows = "";
	
	$index = 1;

	$section = MicrositeSection::open();
	$t->replace("SECTION_NAME",$section->topic);
	
	//with which parameters box should be populated
	if ($_PARAMS[0] == "clani_druzine")
		$status = "3,6,9";
	elseif ($_PARAMS[0] == "izzivalci")
		$status = "5,6,7";
	else
		return "";

	$db = new DB_Sql();
	$sql = "SELECT * FROM ". $GLOBALS["TABLE_PREFIX"] ."player WHERE show_in_header = 1 AND status IN (" . $status . ") ORDER BY status,`order`";
	
	$db->query($sql);
	while ($db->next_record()) {
		$rows .= $row;
		
		$section = MicrositeSection::open((int)$db->f("section_id"));
		$section->url_prefix = $GLOBALS["URL_PREFIX_SECTION2"];
		$t->row("URL",$section->url,$rows);
		
		if ($db->f("status") == 7 || $db->f("status") == 9)
			//player is out
			$t->row("IMAGE",str_replace("kmetija2_player","bw_kmetija2_player",html_return_media($db->f("profile_media_id"),"small")),$rows);
		else
			//player is active
			$t->row("IMAGE",html_return_media($db->f("profile_media_id"),"small"),$rows);
					
		
		$t->row("NAME",$db->f("name"),$rows);
		$t->row("NICK",$db->f("nick"),$rows);
		$t->row("AGE",$db->f("age"),$rows);
		$t->row("ZODIAC",$db->f("zodiac"),$rows);
		$t->row("CITY",$db->f("city"),$rows);
		
		//what status the player has
		$status_3 = $t->row_repeater("IF_COMPETE",$rows);
		$status_5 = $t->row_repeater("IF_CHALLENGER",$rows);
		$status_9 = $t->row_repeater("IF_DROP",$rows);
		if ($db->f('status') == 3 || $db->f('status') == 6)
			$t->row('IF_COMPETE',$status_3,$rows);
		elseif ($db->f('status') == 5)
			$t->row('IF_CHALLENGER',$status_5,$rows);
		else
			$t->row('IF_DROP',$status_9,$rows);
		$t->row('IF_COMPETE',"",$rows);
		$t->row('IF_CHALLENGER',"",$rows);
		$t->row('IF_DROP',"",$rows);
		
		$left = $t->row_repeater("LEFT_POSITION",$rows);
		$right = $t->row_repeater("RIGHT_POSITION",$rows);
		if ($index++ & 1 == 1) {
			$t->row("LEFT_POSITION",$left,$rows);			
			$t->row("RIGHT_POSITION","",$rows);			
		} else {
			$t->row("LEFT_POSITION","",$rows);			
			$t->row("RIGHT_POSITION",$right,$rows);			
		}
	
	}
	$t->replace("PLAYER", $rows);
	
	return $t->get();
}

/**
 * Function populates page with data of one player
 *
 */
function box_central_player_info()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $INNER_SECTION_ARTICLE_MAPPING;
	global $_PARAMS;
	global $SECTION_ID;
	
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/main_news.tpl");
	$t->open();

	// lets not override section id for other content on page
	$box_section_id = (int)$SECTION_ID;
	// do we have inner section mapping for this box?
	foreach ($INNER_SECTION_ARTICLE_MAPPING as $section_key => $section_value)
		if($section_key==$box_section_id)
			$box_section_id=$section_value;
	// we use new section, only for this box
			
	$sql = "SELECT article_id FROM article 
		WHERE status = 3
		AND section_id = " . $box_section_id . " 
		ORDER BY article_id DESC LIMIT 1";
	$db = new DB_Sql();
	$db->query($sql);
	if (!$db->next_record()) 
		return "";
		
	//open article with players section
	$article = MicrositeArticle::open((int)$db->f("article_id"));
	
	$t->replace("title", $article->title);
	$t->replace("summary", $article->summary);
	$t->replace("article_url", $article->url);
	$t->replace("IMAGE300x200", html_return_media($article->frontImage, "300x200"), $tmpnews);

	article_choose_template_part_temp($t->_template, $db->f("article_id"));
	$temp = $t->repeater("IF_GALLERY");
	$temp = $t->repeater("IF_VIDEO");
	
	return $t->get();
}

/**
 * populates page with tabbed video creator box
 *
 * @return string
 */
function box_central_video_creator_tabs(){

	global $TEMPLATE_DIR, $FRONTEND_DIR;
	global $_PARAMS, $site, $_;
	$media_width=533;
	$media_height=400;
	
	$tab_group_id = "videocreator_tabs";
	
	$content_count=0;
	
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/media_creator_tabs.tpl");
	$t->open();
	$t->replace('MEDIA_GROUP_TITLE',$_['FARM_VIDEO_CREATOR']); 
	
	$db = new DB_Sql();
	// sql for content use
	$sql = "SELECT id FROM micro_video_creator WHERE status=3 AND site_id='$site' ORDER By %s DESC LIMIT 1;";
	

	$tab_content="";
	$r = $t->repeater("GROUP");
	
	$i=1;
	// lets make first tab
	// get last added video by date
	$db->query(sprintf($sql,'date'));
	if ($db->next_record()) {
		$content_count++;
		$tab_content.=$r;			
		$t->row("TAB_ID",	$i,	$tab_content);	
		$t->row("IF_RIGHT_START",	"",	$tab_content);	
		$t->row("ID",	$db->f('id'),	$tab_content);
		$t->row("MEDIA_LINK", '/moj_video/'.$db->f('id'), $tab_content);
	}
	else{
		// remove tab, since it has no content
		$r1 = $t->repeater("TAB1");
		$t->replace('TAB1','');
	}
	
	// lets make top rated tab
	$i++;
	// get top rated video
	$db->query(sprintf($sql,'(vote_avg * vote_count)'));
	if ($db->next_record()) {
		$content_count++;
		$tab_content.=$r;	
		$t->row("TAB_ID",	$i,	$tab_content);	
		$t->row("IF_RIGHT_START",	'style="display:none;"',	$tab_content);
		$t->row("ID",	$db->f('id'),	$tab_content);
		$t->row("MEDIA_LINK", '/moj_video/'.$db->f('id'), $tab_content);
	}	else{
		// remove tab, since it has no content
		$r1 = $t->repeater("TAB2");
		$t->replace('TAB2','');
	}
	
	// lets make last  tab
	$i++;
	// most commented
	$sql = "SELECT DISTINCT v.id, count(1) as c FROM micro_video_creator as v 
			INNER JOIN micro_video_creator_comment as c 
			ON c.video_id=v.id WHERE v.status=3 AND site_id='$site' GROUP By v.id Order by c DESC limit 1;";
	$db->query($sql);
	if ($db->next_record()) {
		$content_count++;
		$tab_content.=$r;	
		$t->row("TAB_ID",	$i,	$tab_content);	
		$t->row("IF_RIGHT_START",	'style="display:none;"',	$tab_content);
		$t->row("ID",	$db->f('id'),	$tab_content);
		$t->row("MEDIA_LINK", '/moj_video/'.$db->f('id'), $tab_content);
	}	else{
		// remove tab, since it has no content
		$r1 = $t->repeater("TAB3");
		$t->replace('TAB3','');
	}
	
	// replace default box parameters
	$t->replace('GROUP',$tab_content);
	$t->replace('GROUP_ID',$tab_group_id);

	// we replace media player
	$t->replace("MEDIA_JS", "add_video_object");
	$t->replace("MEDIA_SWF", "video_creator_view.swf?src=");
	
	// clear comic link
	$r = $t->repeater('REDIRECTCOMICLINK');
	$t->replace('REDIRECTCOMICLINK','');
	// dimensions are back
	if($t->tag_exists('MEDIA_WIDTH'))
		$t->replace('MEDIA_WIDTH',$media_width);
	if($t->tag_exists('MEDIA_HEIGHT'))
		$t->replace('MEDIA_HEIGHT',$media_height);
	
	
	return $t->get();
}

/**
 * populates page with tabbed comics creator box
 *
 * @return string
 */
function box_central_comic_creator_tabs(){

	global $TEMPLATE_DIR, $FRONTEND_DIR, $TABLE_PREFIX;
	global $_PARAMS, $_;
	$media_width=513;
	$media_height=220;
	
	$tab_group_id = "comiccreator_tabs";
	
	$content_count=0;
	
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/media_creator_tabs.tpl");
	$t->open();
	$t->replace('MEDIA_GROUP_TITLE',$_['FARM_COMIC_CREATOR']);
	
	$db = new DB_Sql();
	
	$tab_content="";
	$r = $t->repeater("GROUP");
	
	$i=1;
	// lets make first tab

	
	// get last added comic by date
	$sql = "SELECT id FROM micro_comic_creator WHERE status=3 ORDER By date DESC LIMIT 1;";
	$db->query($sql);
	if ($db->next_record()) {
		$content_count++;
		$tab_content.=$r;		
		$t->row("TAB_ID",	$i,	$tab_content);	
		$t->row("IF_RIGHT_START",	"",	$tab_content);	
		$t->row("ID", $db->f('id'), $tab_content);
		$t->row("MEDIA_LINK", '/moj_strip/'.$db->f('id'), $tab_content);
	}	else{
		// remove tab, since it has no content
		$r1 = $t->repeater("TAB1");
		$t->replace('TAB1','');
	}
	
	
	// lets make next tab
	$i++;
	// top rated
	$sql = "SELECT DISTINCT s.id, count(1) as c FROM micro_comic_creator as s
	INNER JOIN micro_comic_creator_vote as v ON s.id=v.comic_id WHERE s.status=3 GROUP By s.id ORDER By c DESC LIMIT 1;";
	$db->query($sql);
	if ($db->next_record()) {
		$content_count++;
		$tab_content.=$r;	
		$t->row("TAB_ID", $i, $tab_content);	
		$t->row("IF_RIGHT_START",	'style="display:none;"',	$tab_content);	
		$t->row("ID", $db->f('id'),	$tab_content);
		$t->row("MEDIA_LINK", '/moj_strip/'.$db->f('id'), $tab_content);
	}	else {
		// remove tab, since it has no content
		$r1 = $t->repeater("TAB2");
		$t->replace('TAB2','');
	}
	
	// lets make last  tab
	$i++;
	// most commented
	$sql = "SELECT DISTINCT s.id, count(1) as c FROM micro_comic_creator as s
	INNER JOIN micro_comic_creator_comment as v ON s.id=v.comic_id WHERE s.status=3 GROUP By s.id ORDER By c DESC LIMIT 1;";

	$db->query($sql);
	if ($db->next_record()) {
		$content_count++;
		$tab_content.=$r;	
		$t->row("TAB_ID", $i, $tab_content);	
		$t->row("IF_RIGHT_START", 'style="display:none;"', $tab_content);	
		$t->row("ID", $db->f('id'), $tab_content);
		$t->row("MEDIA_LINK", '/moj_strip/'.$db->f('id'), $tab_content);
	} else{
		// remove tab, since it has no content
		$r1 = $t->repeater("TAB3");
		$t->replace('TAB3','');
	}
	//if (!$content_count)
	//	return "";
		
	// replace default box parameters
	$t->replace('GROUP',$tab_content);
	$t->replace('GROUP_ID',$tab_group_id);
	
	// add bottom link
	$tab_content="";
	// clear video link
	$r = $t->repeater('REDIRECTVIDEOLINK');
	$t->replace('REDIRECTVIDEOLINK','');

	// we replace media player
	$t->replace("MEDIA_JS", "add_comic_object");
	$t->replace("MEDIA_SWF", "sg-pop-view.swf?id=");
	// dimensions are back
	if($t->tag_exists('MEDIA_WIDTH'))
		$t->replace('MEDIA_WIDTH',$media_width);
	if($t->tag_exists('MEDIA_HEIGHT'))
		$t->replace('MEDIA_HEIGHT',$media_height);
	
	return $t->get();
}

/**
 * Univerzal function for populating monthly calendar
 * @param string $val what is the type of calendar (daily,weekly)
 * @param array $enabled_days which days are enabled (clickable)
 * @return calendar tpl 
 */
function box_monthly_calendar($type = "daily",$enabled_days = Array())
{
	global $TEMPLATE_DIR, $FRONTEND_DIR;
	global $_PARAMS;
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/monthly_calendar.tpl");
	$t->open();
	// var_dump($enabled_days);
	$db = new DB_Sql();
	$sql = "SELECT MIN(time_start), MAX(time_end) FROM ".$GLOBALS["TABLE_PREFIX"]."weeks";
	$db->query($sql);
	if (!$db->next_record())
		return "";
	
	$time_start = $db->f(0);
	$time_end = $db->f(1);
	
	//loop through months
	$row = $t->repeater("MONTH_LIST");
	$rows = "";
	
	$index = 0;
	
	//loop through months
	for ($i=date("m",$time_start);$i<=date("m",$time_end);$i++) {
		$rows .= $row;
		$t->row("MONTH",date_month_slo($i),$rows);
		$t->row("MONTHID",$i,$rows);
		$t->row("NUMBER",$index++,$rows);
		if ($i == date("m",$time_start))
			//for first name
			$t->row("DAYMONTH","'".date("d",$time_start).str_pad($i,2,"0",STR_PAD_LEFT)."'",$rows);
		elseif (mktime(0,0,0,$i,1,date("Y",$time_end))+86400*31 > $time_end)
			//for last one -> there is less than 31 days to finish of the show
			$t->row("DAYMONTH","'".date("d",$time_end-86400*31).date("m",$time_end-86400*31)."'",$rows);
		else
			$t->row("DAYMONTH","'01".str_pad($i,2,"0",STR_PAD_LEFT)."'",$rows);

		$temp = $t->row_repeater("IF_SELECTED",$rows);
		if ($i==date("m")) {
			$t->row("IF_SELECTED",$temp,$rows);
			$t->replace("MONTHID",$i);
		} else
			$t->row("IF_SELECTED","",$rows);
		
	}
	$t->replace("MONTH_LIST",$rows);
	
	//loop through weeks/days
	$week = $t->repeater("WEEK");
	$weeks = "";
	$weeknum = 1;

	//loop through weeks
	for ($i=$time_start;$i<=$time_end;$i+=86400*7) {
		$weeks .= $week;
		
		$t->row("WEEK_NUM",$weeknum++,$weeks);
		$t->row("WEEK_URL",$i,$weeks);
		
		//check if week is clickable
		$week_url = $t->row_repeater("IF_WEEK_URL",$weeks);
		$week_nourl = $t->row_repeater("IF_WEEK_NOURL",$weeks);
		if ($type == "weekly" && in_array(date("ymd",$i),$enabled_days))
			$t->row("WEEK",$week_url,$weeks);
		else
			$t->row("WEEK",$week_nourl,$weeks);
		
		//extract repeaters for days (day, dayname, cliackable)
		$dayname1 = $t->row_repeater("IF_DAYNAME_1",$weeks);
		$dayname2 = $t->row_repeater("IF_DAYNAME_2",$weeks);
		$day1 = $t->row_repeater("IF_DAY_1",$weeks);
		$day2 = $t->row_repeater("IF_DAY_2",$weeks);
		$dayurl1 = $t->row_repeater("IF_DAY_URL_1",$weeks);
		$dayurl2 = $t->row_repeater("IF_DAY_URL_2",$weeks);
		$today = $t->row_repeater("IF_TODAY",$weeks);
		
		$dayname = "";
		$day = "";
		
		//loop through days in current week
		for ($j=0;$j<7;$j++) {
			if (date("N",$i+86400*$j) == 6 || date("N",$i+86400*$j) == 7) {
				//is weekend	
				$dayname .= $dayname2;
				if (in_array(date("ymd",$i+86400*$j),$enabled_days) && $type == "daily")
					$day .= $dayurl2;
				else	
					$day .= $day2;
			} else {
				$dayname .= $dayname1;
				if (in_array(date("ymd",$i+86400*$j),$enabled_days) && $type == "daily")
					$day .= $dayurl1;
				else	
					$day .= $day1;
							}
			if (date("dmy",$i+86400*$j) == date("dmy"))
				$t->row("TODAY",$today,$day);
			else
				$t->row("TODAY","",$day);
							
			$t->row("DAY",date("d",$i+86400*$j),$day);
			$t->row("DAY_URL",$i+86400*$j,$day);
			$t->row("DAYMONTH",date("dm",$i+86400*$j),$dayname);
			$t->row("DAY_LABEL",date_week_short_slo(date("N",$i+86400*$j)),$dayname);
					
		}
		$t->row("DAYNAME",$dayname,$weeks);
		$t->row("DAY",$day,$weeks);
	
	}
	$t->replace("WEEK",$weeks);
			
	return $t->get();

}

/**
 * Function renders monthly calendar with daily links
 *
 */
function box_central_daily_monthly_calendar() {
	
	global $VIDEO_DAILY_SECTION_ID, $FOTO_DAILY_SECTION_ID, $ARTICLE_SECTION_ID, $site;
	
	$enabled_days = Array();
	
	$section = MicrositeSection::open();
	
	
	
	if (in_array($section->section_id,$VIDEO_DAILY_SECTION_ID) || 
		in_array($section->section_id,$FOTO_DAILY_SECTION_ID)) {
			
		// lets setup media type
		$media_type = in_array($section->section_id,$FOTO_DAILY_SECTION_ID) ? 1 : 3;
			
		//find enabled days for videos
		$sql = "SELECT DISTINCT DATE_FORMAT(FROM_UNIXTIME(published_from),'%y%m%d') 
			FROM media 
			WHERE type = $media_type AND status = 3   
			AND section_id IN (".$section->section_id.")  
			AND published_from < UNIX_TIMESTAMP()";
	} else {
		//find enabled days for article
		$sql = "SELECT DISTINCT DATE_FORMAT(FROM_UNIXTIME(date),'%y%m%d')  
			FROM article 
			WHERE (status = 3 OR status = 9)   
			AND section_id IN (".implode(",",$ARTICLE_SECTION_ID).")    
			AND published_from < UNIX_TIMESTAMP()";
	}

	
	$db = new DB_Sql();

	$db->query($sql);
	while ($db->next_record())
		$enabled_days[] = $db->f(0);
	
	return box_monthly_calendar("daily",$enabled_days);
}

/**
 * Function renders monthly calendar with weekly links
 *
 */
function box_central_weekly_monthly_calendar() {
	
	global $VIDEO_WEEKLY_SECTION_ID, $ARTICLE_WEEKLY_SECTION_ID, $section_id;

	$db = new DB_Sql();
	$index = 0;
	//fetch days, when week starts
	$sql = "SELECT DISTINCT DATE_FORMAT(FROM_UNIXTIME(time_start-86400*28),'%y%m%d'),DATE_FORMAT(FROM_UNIXTIME(time_end-86400*28),'%y%m%d')
		FROM ".$GLOBALS["TABLE_PREFIX"]."weeks";
	$db->query($sql);
		
	while ($db->next_record()) {
		$weeks[$index]["from"] = $db->f(0);
		$weeks[$index++]["to"] = $db->f(1);
	}

	$section = MicrositeSection::open();
	// are we dealing with video or article
	if (in_array($section->section_id,$VIDEO_WEEKLY_SECTION_ID)){
		$sql = "SELECT DISTINCT DATE_FORMAT(FROM_UNIXTIME(published_from),'%y%m%d') 
			FROM media 
			WHERE type = 3 AND status = 3   
			AND section_id IN (".implode(",",$VIDEO_WEEKLY_SECTION_ID).")  
			AND published_from < UNIX_TIMESTAMP()";
	}
	else{
		//find enabled days for article
		$sql = "SELECT DISTINCT DATE_FORMAT(FROM_UNIXTIME(date),'%y%m%d')  
			FROM article 
			WHERE (status = 3 OR status = 9)   
			AND section_id IN (".$section->section_id.")    
			AND date < UNIX_TIMESTAMP()";
	}
	$db->query($sql);
	while ($db->next_record()) {
		foreach($weeks as $week)
			if ($db->f(0)>=$week["from"] && $db->f(0)<$week["to"] )
				$enabled_days[] = $week["from"];
	}		
	
	return box_monthly_calendar("weekly",$enabled_days);
}



/**
 * Function populates video archive page
 *
 */
function box_central_video_archive()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site;
	global $VIDEO_NOLIMIT_SECTION_ID,$VIDEO_WEEKLY_SECTION_ID, $FOTO_DAILY_SECTION_ID;
	global $_PARAMS;
	
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/video_archive.tpl");
	$t->open();
	

	//replace section's parameters
	if (cls_Input::request("section_id"))
		$section = MicrositeSection::open(cls_Input::request("section_id"));
	else
		$section = MicrositeSection::open();
	$section_id = $section->section_id;
	$t->replace("SECTION_TITLE",$section->topic);
	$t->replace("SECTION_ID",$section->section_id);
	
	// lets setup media type
	$media_type = in_array($section_id,$FOTO_DAILY_SECTION_ID) ? 1 : 3;
	
	$row = $t->repeater("VIDEO");
	$rows = "";
	
	//input filters are: day
	$day = cls_Input::request("day","string");
	$interval = cls_Input::request("interval","string");
	
	if (!$day)
		$day = date_zero_hour_form_date(time());

	// is this sevtion in week list
	$is_weekly_section = in_array($section_id,$VIDEO_WEEKLY_SECTION_ID);
	$day_from = $day;
	
	// if interval is not set set it for 7 or 1 day
	if (!$interval && $is_weekly_section){
		$interval = 7;
	}else if(!$interval){
		$interval = 1;
	}	
		
	if (isset($interval) && $interval>1){
		// we want monday
		$day_from = date_get_monday_from_weekday($day);	
	}	
	$day_to = $day_from + 86400*($interval-1);
	
	$db = new DB_Sql();
	
	//get all videos within selected filters
	$sql0 = "SELECT media_id,published_from FROM media 
		WHERE type = $media_type AND status = 3
		AND site_id = '#" . $site . "#'  
		AND published_from < " . time() . " ";
	if ($section_id)
		$sql0 .= "AND section_id = " . (int)$section_id . " ";
	else		
		//if section is not set, then show all videos without section defined	
		$sql0 .= "AND (section_id = '' OR section_id IS NULL OR section_id = 0) ";
	$sql = $sql0;
	if ($day_from && $day_to && !in_array($section_id,$VIDEO_NOLIMIT_SECTION_ID))
		$sql .= " AND DATE_FORMAT(FROM_UNIXTIME(published_from),'%Y%m%d') BETWEEN " . date("Ymd",$day_from) . " AND " . date("Ymd",$day_to) . " ";
	$sql .= "ORDER BY published_from DESC";
	
	$index = 1;
	$db->query($sql);		
	
	
	if($db->num_rows()<=0){
		// when no hits we move to last period with content
		$sql = $sql0 . " ORDER BY published_from DESC LIMIT 1;";
		$db->query($sql);
		$date=0;
		if ($db->next_record()){
			$date=$db->f("published_from"); // unix date
		}
		// find the day that we use in date period
		$day_from = $interval==1 ? 	date_zero_hour_form_date($date) : 
									date_get_monday_from_weekday($date);
		$day_to = $day_from + 86400*($interval-1);
		$sql = $sql0;
		$sql .= " AND DATE_FORMAT(FROM_UNIXTIME(published_from),'%Y%m%d') BETWEEN " . 
		date("Ymd",$day_from) . " AND " . date("Ymd",$day_to) . " ";
		$sql .= " ORDER BY published_from DESC";
		$db->query($sql);
		
	}		
	
	while ($db->next_record()) {
		$rows .= $row;
		
		$media =  MicrositeMedia::open((int)$db->f("media_id"));
		$t->row("IMAGE",$media->getThumbnail("114x76"),$rows);
		$t->row("URL",$media->url,$rows);
		$t->row("TITLE",$media->title,$rows);
		$t->row("DATETIME",date("d.m.y H:i",$db->f("published_from")),$rows);
		
		$left = $t->row_repeater("LEFT_POSITION",$rows);
		$right = $t->row_repeater("RIGHT_POSITION",$rows);
		if ($index++ & 1 == 1) {
			$t->row("LEFT_POSITION",$left,$rows);			
			$t->row("RIGHT_POSITION","",$rows);			
		} else {
			$t->row("LEFT_POSITION","",$rows);			
			$t->row("RIGHT_POSITION",$right,$rows);			
		}		
	}
	$t->replace("VIDEO",$rows);
	if (cls_Input::request("ajax"))
		return $t->repeater("AJAX");
	else	
		return $t->get();
}

/**
 * Function populates news archive page
 *
 */
function box_central_news_archive()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $ARTICLE_WEEKLY_SECTION_ID;
	global $_PARAMS;

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/news_archive.tpl");
	$t->open();

	//replace section's parameters
	if (cls_Input::request("section_id"))
		$section = MicrositeSection::open((int)cls_Input::request("section_id"));
	else
		$section = MicrositeSection::open();

	$section_id = $section->section_id;
		
	$t->replace("SECTION_TITLE",$section->topic);
	$t->replace("SECTION_ID", $section_id );
	
	$row = $t->repeater("ARTICLE");
	$rows = "";
	
	//input filters are: day
	$day = cls_Input::request("day","int");
	$interval = cls_Input::request("interval","int");

	if (!$day)
		$day = date_zero_hour_form_date(time());
	
	// is this sevtion in week list
	$is_weekly_section = in_array($section_id,$ARTICLE_WEEKLY_SECTION_ID);
	$day_from = $day;
	
	// if interval is not set set it for 7 or 1 day
	if (!$interval && $is_weekly_section){
		$interval = 7;
	}else if(!$interval){
		$interval = 1;
	}	
	
	if (isset($interval) && $interval>1){
		// we want monday
		$day_from = date_get_monday_from_weekday($day);
	

	}	
	$day_to = $day_from + 86400*($interval-1);

	$db = new DB_Sql();
	
	//get all videos within selected filters
	$sql0 = "SELECT article_id, date FROM article  WHERE (status = 3 OR status = 9) ";
	// do we have a valid section id
	if($section_id)		
		$sql0 .= " AND section_id=$section->section_id ";
	
	//
	$sql = $sql0;
	if ($day_from && $day_to)
		$sql .= " AND DATE_FORMAT(FROM_UNIXTIME(date),'%Y%m%d') BETWEEN " . 
		date("Ymd",$day_from) . " AND " . date("Ymd",$day_to) . " ";
	$sql .= " ORDER BY date DESC";
	
	$index = 1;
	$db->query($sql);
	
	// no articles so we select last
	if($db->num_rows()<=0){
		// when no hits we move to last period with content
		$sql = $sql0 . " ORDER BY date DESC LIMIT 1;";
		$db->query($sql);
		$date=0;
		if ($db->next_record()){
			$date=$db->f("date"); // unix date
		}
		// find the day that we use in date period
		$day_from = $interval==1 ? 	date_zero_hour_form_date($date) : 
									date_get_monday_from_weekday($date);
		
		$day_to = $day_from + 86400*($interval-1);
		
		$sql = $sql0;
		$sql .= " AND DATE_FORMAT(FROM_UNIXTIME(date),'%Y%m%d') BETWEEN " . 
		date("Ymd",$day_from) . " AND " . date("Ymd",$day_to) . " ";
		$sql .= " ORDER BY date DESC";
		$db->query($sql);
	}
	
	
	while ($db->next_record()) {
		$rows .= $row;
		
		$article =  MicrositeArticle::open((int)$db->f("article_id"));
		$t->row("IMAGE",html_return_media($article->frontImage, "114x76"),$rows);
		$t->row("URL",$article->url,$rows);
		$t->row("TITLE",$article->title,$rows);
		$t->row("DATETIME",date("d.m.y H:i",$db->f("date")),$rows);

		$left = $t->row_repeater("LEFT_POSITION",$rows);
		$right = $t->row_repeater("RIGHT_POSITION",$rows);
		if ($index++ & 1 == 1) {
			$t->row("LEFT_POSITION",$left,$rows);			
			$t->row("RIGHT_POSITION","",$rows);			
		} else {
			$t->row("LEFT_POSITION","",$rows);			
			$t->row("RIGHT_POSITION",$right,$rows);			
		}		
	}
	$t->replace("ARTICLE",$rows);

	if (cls_Input::request("ajax"))
		return $t->repeater("AJAX");
	else	
		return $t->get();
}

/**
 * Function renders weekly roels central box
 *
 * @return template string
 */
function box_central_weekly_roles()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR;
	
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/weekly_roles.tpl");
	$t->open();
	
	$row = $t->repeater("WEEKLY_ROLES");
	$rows = "";
	$curr_week = 999;
	
	$db = new DB_Sql();

	//weekly roles contants (yes, I know, should be in config ;))
	$roles[1] = "Dekla";	
	$roles[2] = "Dvobojevalec";	
	$roles[3] = "Glava družine";	
	$roles[4] = "Hlapec";	
	$roles[5] = "Izzivalec";
	$roles["extra"] = "Dvobojevalec";
	
	//classes for weekly roles images
	$role_class[1] = $t->repeater("IF_ROLE_1");
	$role_class[4] = $t->repeater("IF_ROLE_2");
	$role_class[2] = $t->repeater("IF_ROLE_3");
	$role_class[3] = $t->repeater("IF_ROLE_4");
	
	
	//find all weeks
	$sql = "SELECT pwl.week, p.name, pwl.role, pwl.extra, p.status, p.profile_media_id, p.section_id	
		FROM ".$GLOBALS["TABLE_PREFIX"]."player_weekly_roles  pwl
		INNER JOIN ".$GLOBALS["TABLE_PREFIX"]."player p ON pwl.player_id = p.player_id
		WHERE pwl.role <> 0 
		ORDER BY pwl.week DESC";
	$db->query($sql);
	while ($db->next_record()) {
		
		//create new week and clean-up data from previous week
		if ($curr_week != $db->f("week")) {
			
			//clean-up data from previous week
			$t->row("PLAYER",$subrows,$rows);
			
			$rows .= $row;
			
			$t->row("WEEK",$db->f("week"),$rows);

			//take sub repeater from week
			$subrow = $t->row_repeater("PLAYER",$rows);
			$subrows = "";
			$index = 1;
			
			$curr_week = $db->f("week");
		}
		
		$subrows .= $subrow;
		
		$t->row("NAME",$db->f("name"),$subrows);
		
		//write down right role class
		$t->row("ROLE_CLASS",$role_class[$db->f("role")],$subrows);
		
		if ($db->f("extra") == 1)
			$t->row("ROLE_EXTRA",$roles["extra"],$subrows);
		else
			$t->row("ROLE_EXTRA","",$subrows);
			
		$t->row("ROLE",$roles[$db->f("role")],$subrows);
		
		$section = MicrositeSection::open((int)$db->f("section_id"));
		$section->url_prefix = $GLOBALS["URL_PREFIX_SECTION2"];
		$t->row("URL",$section->url,$subrows);		
		
		if ($db->f("status") == 7 || $db->f("status") == 9)
			//player is out
			$t->row("IMAGE",str_replace("kmetija2_player","small_bw_kmetija2_player",html_return_media($db->f("profile_media_id"),"small")),$subrows);
		else
			//player is active
			$t->row("IMAGE",str_replace("kmetija2_player","small_kmetija2_player",html_return_media($db->f("profile_media_id"),"small")),$subrows);
			
		$sep = $t->row_repeater("IF_CLEAR",$subrows);
		if ($index++ & 4) {
			$index = 1;
			$t->row("IF_CLEAR",$sep,$subrows);
		} else
			$t->row("IF_CLEAR","",$subrows);
	
	}
	$t->row("PLAYER",$subrows,$rows);
	$t->replace("WEEKLY_ROLES",$rows);

	return $t->get();
}

/**
 * Function return box if live video show is on
 *
 * @return tempalate
 */
function box_right_live_stream_promo()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $URL_PREFIX_SEARCH;
	
	//check if show is going on
	if (!tv_is_show_on(1,5,false,0,true))
		return "";
	
	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/right/live_stream_promo.tpl");
	$t->open();

	return $t->get();

}


/**
 * shows box your recepie
 *
 * @return string
 */
function box_central_your_recepie(){
	global $TEMPLATE_DIR, $FRONTEND_DIR, $URL_PREFIX_SEARCH;
	
	// lets open this simple template
	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/your_recepie_user_news.tpl");
	$t->open();
	
	// sended data
	$author = strip_tags( cls_Input::request('author', 'clean'));
	if ($t->tag_exists('AUTHOR') && $author)
		$t->replace('AUTHOR',$author);
	
	$email	= strip_tags( cls_Input::request('email', 'clean'));
	if ($t->tag_exists('EMAIL') && $email)
		$t->replace('EMAIL',$email);
	
	$phone 	= strip_tags( cls_Input::request('phone', 'clean'));
	if ($t->tag_exists('PHONE') && $phone)
		$t->replace('PHONE',$phone);
		
	$title 	= strip_tags( cls_Input::request('title', 'clean'));
	if ($t->tag_exists('TITLE') && $title)
		$t->replace('TITLE',$title);
		
	$txt 	= strip_tags( cls_Input::request('txt', 'clean'));
	if ($t->tag_exists('TEXT') && $txt)
		$t->replace('TEXT',$txt);
	
	// is error acoured, client might not have javascript
	if (isset($_GET['missing_data'])){
		$r = $t->repeater("ERRORSTR");
		$t->replace("ERRORSTR","");
				$r = $t->repeater("ERRORSTR2");
		$t->replace("ERRORSTR2","");
	}
	
	return $t->get();
}


/**
 *  shows box your with link user form
 *
 * @return string
 */
function box_right_your_recepie_shoutbox(){
	global $TEMPLATE_DIR, $FRONTEND_DIR, $URL_PREFIX_SEARCH;
	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/right/your_recepie_shoutbox.tpl");
	$t->open();
	
	return $t->get();

}

/**
 *  shows box your with link user form
 *
 * @return string
 */
function box_central_your_recepie_shoutbox(){
	global $TEMPLATE_DIR, $FRONTEND_DIR, $URL_PREFIX_SEARCH;
	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/your_recepie_shoutbox.tpl");
	$t->open();
	
	return $t->get();

}

/**
 * shows box your with link to section
 *
 * @return string
 */
function box_right_your_recepie_link(){
	global $TEMPLATE_DIR, $FRONTEND_DIR, $URL_PREFIX_SEARCH;
	
	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/right/your_recepie_section_add.tpl");
	$t->open();
	
	return $t->get();
}

/**
 * Renders media for current section; order by date of the media
 *
 * @return template
 */
function box_central_media_list_section()
{
	global $SECTION_ID, $TEMPLATE_DIR, $FRONTEND_DIR, $site;
	global $BOX_CENTRAL_VIDEO_LIST_BOXTITLE;
	global $VIDEO_SECTION_ID;

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/media_list.tpl");
	$t->open();

	$t->replace("BOXTITLE",$BOX_CENTRAL_VIDEO_LIST_BOXTITLE);

	$db = new DB_Sql();
	
	$page = cls_input::request("page");
	if ($page == 0)
		$page = 1;
	
	if (cls_Input::request("section_id"))
		$section = MicrositeSection::open(cls_Input::request("section_id"));
	else
		$section = MicrositeSection::open();		
		
	//get videos of selected player
	$sql = "SELECT SQL_CALC_FOUND_ROWS media_id FROM media m 
		WHERE m.type = 3 AND m.status = 3 
		AND m.section_id = " . $VIDEO_SECTION_ID . "  
		AND m.published_from < " . time() . " 
		AND EXISTS (SELECT 1 FROM media_section ms 
			WHERE m.media_id = ms.media_id 
			AND ms.section_id = " . (int)$section->section_id . ")
		ORDER BY m.published_from DESC LIMIT ".(($page-1)*7).",7";
	$db->query($sql);
	while ($db->next_record())
		$arr_media[] = $db->Record;
	
	$index = 0;
	
	// PAGER
	$db->query("SELECT found_rows() AS num_rows");
	$db->next_record();
	$all = $db->f("num_rows");
	$pager = new cls_Pager($all, 7);
	$pager->href("");
	$pager->set_additional_params("onclick='returnContent(\"box_central_video_list_\", \"/lbin/ajax_media_list_section.php?&page=##PAGE##&section_id=".$section->section_id."\"); this.blur(); return false;'");
	$pagerhtml = $pager->create($page);

	if ($pager->max_page<=1)
		$pagerhtml ="";

	$t->replace("PAGER", $pagerhtml);	
		
	// BIG VIDEO
	if (isset($arr_media[$index]))
	{
		$video = MicrositeMedia::open((int)$arr_media[$index]["media_id"]);
		$t->replace("TITLE", $video->title);
		$t->replace("DATE", date("d.m.Y", $video->date));
		$t->replace("DESCRIPTION", $video->description);
		$t->replace("ID", $video->id);
		$t->replace("URL", $video->url);
		$t->replace("THUMB", $video->getThumbnail("160x120"));
		
		$index++;

	}
	else
		return "";

	// EXTRA VIDEOS
	$extra = $t->repeater("EXTRA");
	$extra_all = "";

	while (isset($arr_media[$index]))
	{		
		$tmpextra = $extra;
		$video = MicrositeMedia::open((int)$arr_media[$index]["media_id"]);

		$t->row("EXTRA_TITLE", $video->title, $tmpextra);
		$t->row("EXTRA_DATE", date("d.m.Y",$video->date), $tmpextra);
		$t->row("EXTRA_DESCRIPTION", $video->description, $tmpextra);
		$t->row("EXTRA_ID", $video->id, $tmpextra);
		$t->row("EXTRA_URL", $video->url, $tmpextra);
		$t->row("EXTRA_THUMB", $video->getThumbnail("78x52"), $tmpextra);

		$extra_all .= $tmpextra;
		$index++;
	}

	$t->replace("EXTRA", $extra_all);

	$tem = $t->get();
	return $tem;
}


/**
 * Renders news list box
 *
 * @return template
 */
function box_central_news_list_section()
{
	global $front_id, $section_id, $TEMPLATE_DIR, $FRONTEND_DIR, $site;
	global $NEWS_ARTICLE_SECTION_ID;
	global $ARTICLE_COMMENTS_COUNT;
	global $BOX_CENTRAL_NEWS_LIST_BOXTITLE;

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/list_news.tpl");
	$t->open();
	
	$t->replace("BOXTITLE",$BOX_CENTRAL_NEWS_LIST_BOXTITLE);

	$db = new DB_Sql();
	
	$page = cls_input::request("page");
	if ($page == 0)
		$page = 1;
	
	if (cls_Input::request("section_id"))
		$section = MicrositeSection::open(cls_Input::request("section_id"));
	else
		$section = MicrositeSection::open();		
		
	$news = $t->repeater("LEFT");
	$news_all = "";
	$left = "";
	$right = "";
	$i = 0;
	
	//get all article, connected to current section
	$sql = "SELECT SQL_CALC_FOUND_ROWS a.article_id FROM article a 
		WHERE (a.status = 3 OR a.status = 9)
		AND a.section_id = " . $NEWS_ARTICLE_SECTION_ID . "  
		AND a.published_from < " . time() . " 
		AND EXISTS (SELECT 1 FROM article_section ass 
			WHERE a.article_id = ass.article_id 
			AND ass.section_id = " . (int)$section->section_id . ")
		ORDER BY a.published_from DESC LIMIT ".(($page-1)*6).",6";
	$db->query($sql);
	while ($db->next_record())
		$arr_article[] = $db->Record;
	
	$index = 0;
	
	// PAGER
	$db->query("SELECT found_rows() AS num_rows");
	$db->next_record();
	$all = $db->f("num_rows");	
	$pager = new cls_Pager($all, 6);
	$pager->href("");
	$pager->set_additional_params("onclick='returnContent(\"box_central_news_list\", \"/lbin/ajax_news_list_section.php?&page=##PAGE##&section_id=".$section->section_id."\"); this.blur(); return false;'");
	$pagerhtml = $pager->create($page);

	if ($pager->max_page<=1)
		$pagerhtml ="";

	$t->replace("PAGER", $pagerhtml);	
	
	while (isset($arr_article[$index]))
	{
		$i++;

		$tmpnews = $news;

		$article = MicrositeArticle::open((int)$arr_article[$index]["article_id"]);

		$t->row("TITLE", $article->title, $tmpnews);
		$t->row("DATE", $article->date, $tmpnews);
		$t->row("SOURCE", $article->sources, $tmpnews);
		$t->row("SUMMARY", $article->summary, $tmpnews);
		$t->row("ID", $article->id, $tmpnews);
		$t->row("URL", $article->url, $tmpnews);
		$t->row("FRONTIMAGE", html_return_media($article->frontImage, "114x76"), $tmpnews);

		if ($t->row_tag_exists("IF_COMMENTS_START",$tmpnews)) {
			$tmp = $t->row_repeater("IF_COMMENTS",$tmpnews);
			if ($ARTICLE_COMMENTS_COUNT) {
				$count = $article->commentsCount;
				if ($count) {
					$t->row("ARTICLE_COMMENT_NUMBER",$article->commentsCount,$tmp);
				} else {
					$tmp = "";
				}
			} else {
				$tmp = "";
			}
			$t->row("IF_COMMENTS",$tmp,$tmpnews);
		} 
		
		article_choose_template_part_temp($tmpnews, $article->id);

		if ($i%2!==0)
			$left .= $tmpnews;
		else
			$right .= $tmpnews;
		
		$index++;
	}

	if (!$left && !$right)
		return false;

	$t->replace("LEFT", $left);
	$t->replace("RIGHT", $right);

	$tem = $t->get();
	return $tem;

}


/**
 * farm video kreator box
 *
 * @return string
 */
function box_top_farm_video_creator()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/top/farm_video_creator.tpl");
	$t->open();
	return $t->get();
}

/**
 * farm video kreator user video list
 *
 * @return string
 */
function box_top_farm_video_creator_view(){
	
		global $REC_ON_PAGE, $TEMPLATE_DIR, $FRONTEND_DIR, $site;

	if(!$_GET['page'])
		$page = 1;
	else
		$page = $_GET['page'];

	$REC_ON_PAGE = 5;

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/top/farm_video_creator_view.tpl");
	$t->open();

	$rec_start = ($page-1)*$items;
	$limit = " LIMIT $rec_start, $REC_ON_PAGE ";

	$db = new DB_Sql();

	$video = $t->repeater("VIDEO", $tem);

	$video_list = "";

	$list = MicrositeVideoCreator::getList($page, $REC_ON_PAGE, "desc");
	foreach ($list as $id)
	{
		$video_list .= $video;
		$t->row("##ID##", $id, $video_list);
	}

	$t->replace("##VIDEO##", $video_list);

	$db->query("SELECT count(*) as cnt FROM micro_video_creator WHERE status=3 AND site_id=$site;");
	$db->next_record();
	$all_rec = $db->f("cnt");
	$pager = new cls_Pager($all_rec, $REC_ON_PAGE);
	$pager->href("?");
	$t->replace("PAGE_NAVIGATOR", $pager->create($page) );


	return $t->get();
	
}


/******************************************************************************************
 *				CENTRAL / VIDEO CREATOR
 ******************************************************************************************/

function box_central_farm_video_creator_single($id)
{
	global $DBconfig, $TEMPLATE_DIR, $FRONTEND_DIR, $site, $_PARAMS;
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, 'box/central/farm_video_creator_single.tpl');
	$t->open();
	if (!$vc = MicrositeVideoCreator::open())
	{
		header("Location: /bin/404.php");
		die();
	}
	$t->replace("##NAME##", $vc->author);
	$t->replace("ID", $vc->id);

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// vrnem html
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	return $t->get();
}

/**
 * function renders in flash header players box
 * @author benjamin.povirk@pop-tv.si, 23.9.08
 * @return string
 */
function box_top_flash_header() {
	global $TEMPLATE_DIR, $FRONTEND_DIR;
	
	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/top/flash_header.tpl");
	$t->open();
	
	return $t->get();
}

/**
 * function renders in results of the eleet game in header
 * @author benjamin.povirk@pop-tv.si, 24.9.08
 * @return string
 */
function box_central_flash_eleet_score() {
	global $TEMPLATE_DIR, $FRONTEND_DIR;
	
	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/flash_eleet_score.tpl");
	$t->open();

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Retrieve scores and show them
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	$db = new DB_Sql();

	$sql = "SELECT username, score, usertext, DATE_FORMAT(FROM_UNIXTIME(time), '%d.%c.%y') as time FROM kmetija2_game ORDER BY score DESC LIMIT 50";
	$db->query($sql);

	$table = $t->repeater("TR");
	$table_all = "";

	$i=1;
	while ($db->next_record()) {
		$table_all .= $table;
		$t->row('POSITION',$i,$table_all);
		$t->row('NICK',shorten_smarter($db->f('username'),50),$table_all);
		$t->row('SCORE',$db->f('score'),$table_all);
		$t->row('DATE',$db->f('time'),$table_all);
		$t->row('TEXT',shorten_smarter($db->f('usertext'),198),$table_all);
		$i++;
	}

	$t->replace('DATA', $table_all);

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Vrnem html
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	return $t->get();
}

/**
 * Renders right box with last video from Spela videoblog section
 *
 * @return unknown
 */
function box_right_kmetija2_last_media_spela() {
	return get_last_media(10971,3);
}


/**
 * Renders right box with last video from Mama Manka section
 *
 * @return unknown
 */
function box_right_kmetija2_last_media_manka() {
	return get_last_media(10972,3);
}

/**
 * Renders right box with last video from Foto section
 *
 * @return unknown
 */
function box_right_kmetija2_last_media_foto() {
	return get_last_media(10974,1);
}

/**
 * Renders right box with last video from Foto section
 *
 * @return unknown
 */
function box_right_kmetija2_last_article_naloga() {
	
	return get_last_article(10918);
}



/**
 * renders pratika page
 *
 * @return string
 */
function box_top_kmetija2_pratika()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR;

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/top/kmetija2_pratika.tpl");
	$t->open();
	return $t->get();
}

?>
