<?php
/******************************************************************************************
 *		CENTRAL :
 ******************************************************************************************/
function box_central_section_archive_link()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $SECTION_ID, $URL_PREFIX_ARCHIVE;

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/section_archive_link.tpl");
	$t->open();
	
	$s = MicrositeSection::open((int)$SECTION_ID);
	$s->url_prefix = $URL_PREFIX_ARCHIVE;
	$t->replace("LINK", $s->url);

	return $t->get();
}

function box_central_section_archive()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $default_section_id, $REC_ON_PAGE_ARHIV, $APP_LANG;
	global $section_id, $URL_PREFIX_ARCHIVE;

	$db = new DB_Sql();
	$db1 = new DB_Sql();

	$section_id = (int)$section_id;
	if (!$section_id)
		$section_id = $default_section_id;

	if (!$REC_ON_PAGE_ARHIV)
		$REC_ON_PAGE_ARHIV = 50;

	$page = $_GET['page'];
	if (!$page)
		$page = 1;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR,"box/central/section_archive.tpl");
	$t->open();

	$section = MicrositeSection::open();
	$section->url_prefix = $URL_PREFIX_ARCHIVE;

	$all = $section->getCountArticles(9); //show only archived articles
	if ($all == 0) {
		header("Location: /");
		die();
	}

	//PAGER
	$num_pages = (int)($all/$REC_ON_PAGE_ARHIV)+1;
	$t->replace("PAGES",$num_pages);

	$pager = new cls_Pager($all, $REC_ON_PAGE_ARHIV);
	$pager->href(rtrim($section->url, '/')."?");
	$t->replace("PAGER",$pager->create($page));
	$t->replace("PAGE_CURRENT",$page);
	$t->replace("PAGE_ALL",$pager->max_page);
	$t->replace("SECTION_TITLE", $section->title);

	$row = $t->repeater("ARCHIVE_LINKS");
	$rows = $section->articles($row, $REC_ON_PAGE_ARHIV*($page-1), $REC_ON_PAGE_ARHIV,9); //show only archived articles
	$t->replace("ARCHIVE_LINKS", $rows);

	return $t->get();


}


function box_central_section_archive_short()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $default_section_id, $REC_ON_PAGE_ARHIV, $APP_LANG;
	global $section_id, $URL_PREFIX_ARCHIVE_SHORT;

	$db = new DB_Sql();
	$db1 = new DB_Sql();

	$section_id = (int)$section_id;
	if (!$section_id)
		$section_id = $default_section_id;

	if (!$REC_ON_PAGE_ARHIV)
		$REC_ON_PAGE_ARHIV = 50;

	$page = $_GET['page'];
	if (!$page)
		$page = 1;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR,"box/central/section_archive_short.tpl");
	$t->open();

	$section = MicrositeSection::open();
	$section->url_prefix = $URL_PREFIX_ARCHIVE_SHORT;

	$all = $section->countArticles;
	if ($all == 0) {
		header("Location: /");
		die();
	}

	//PAGER
	$num_pages = (int)($all/$REC_ON_PAGE_ARHIV)+1;
	$t->replace("PAGES",$num_pages);

	$pager = new cls_Pager($all, $REC_ON_PAGE_ARHIV);
	$pager->href($section->url."?");
	$t->replace("PAGER",$pager->create($page));
	$t->replace("PAGE_CURRENT",$page);
	$t->replace("PAGE_ALL",$pager->max_page);
	$t->replace("SECTION_TITLE", $section->title);

	$row = $t->repeater("ARCHIVE_LINKS");
	$rows = $section->articles($row, $REC_ON_PAGE_ARHIV*($page-1), $REC_ON_PAGE_ARHIV);
	$t->replace("ARCHIVE_LINKS", $rows);

	return $t->get();


}


function box_central_user_news()
{
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/user_news.tpl");
	$t->open();

	// add texts if submited
	if ( $_REQUEST['title'] )        $t->replace("##EMAIL##", $_REQUEST['email']);
	if ( $_REQUEST['email'] )        $t->replace("##TITLE##", $_REQUEST['title']);
	if ( $_REQUEST['date'] )         $t->replace("##DATE##", $_REQUEST['date']);
	if ( $_REQUEST['place'] )        $t->replace("##PLACE##", $_REQUEST['place']);
	if ( $_REQUEST['phone'] )        $t->replace("##PHONE##", $_REQUEST['phone']);
	if ( $_REQUEST['author'] )       $t->replace("##AUTHOR##", $_REQUEST['author']);
	if ( $_REQUEST['anon'] )         $t->replace("##ANON_CHECKBOX##", 'checked');
	if ( $_REQUEST['txt'] )          $t->replace("##TEXT##", $_REQUEST['txt']);
	if ( $_REQUEST['file_1'] )       $t->replace("##FILE_1##", $_REQUEST['file_1']);
	if ( $_REQUEST['file_2'] )       $t->replace("##FILE_2##", $_REQUEST['file_2']);
	if ( $_REQUEST['file_3'] )       $t->replace("##FILE_3##", $_REQUEST['file_3']);
	if ( $_REQUEST['file_4'] )       $t->replace("##FILE_4##", $_REQUEST['file_4']);
	if ( $_REQUEST['file_5'] )       $t->replace("##FILE_5##", $_REQUEST['file_5']);


	if ( (int)$_REQUEST['msg'] == 2 ) {
		$t->replace("##MSG_2##", "");
		$t->replace("##MSG_2##", "");
	}

	return $t->get();
}

/******************************************************************************************
 *				CENTRAL / MEDIA
 ******************************************************************************************/
function box_central_media_title()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/media_title.tpl");
	$t->open();

	$media = MicrositeMedia::open();
	$t->replace("TITLE", $media->title);

	$tem = $t->get();
	return $tem;


}
function box_central_media_date()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/media_date.tpl");
	$t->open();

	$media = MicrositeMedia::open();
	$t->replace("DATE", date("d.m.Y", $media->date));
	$t->replace("SOURCES", $media->sources);

	$tem = $t->get();
	return $tem;

}

function box_central_media_summary()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/media_summary.tpl");
	$t->open();

	$media = MicrositeMedia::open();
	$t->replace("SUMMARY", $media->description);

	$tem = $t->get();
	return $tem;

}

function box_central_media_image_big()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/media_image_big.tpl");
	$t->open();

	$media =  MicrositeMedia::open();

	if (!$media || $media->type != "image")
	{
		return "";
	}

	$t->replace("BIG_IMAGE", $media->getThumbnail("520xX"));

	//remove usertrack section
	$t->repeater("USERTRACK");


	$tem = $t->get();
	return $tem;
}


function box_central_media_video_player()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id, $APP_LANG, $WEBSITE_URL, $FLV_SERVER_STREAM, $FLV_DIR_CLIP;

	$db = new DB_sql();
	$db1 = new DB_sql();
	$query = "";
	$all_additional_videos_count = 0;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/media_video_player.tpl");
	$t->open();

	$media =  MicrositeMedia::open();

	if (!$media || $media->type != "video")
	{
		return "";
	}


	$t->replace('TITLE', $media->title);
	$t->replace('DATE', date("d.m.Y",$media->date));
	$t->replace('TIME', date("H:i",$media->date));
	$t->replace('DURATION', date("i:s",$media->lenght));
	$t->replace('SECTION_ID', $media->section_id);
	$t->replace("MEDIA_DIR",date("Y-m", $media->date));
	$t->replace("FILENAME",$media->filename);
	$t->replace("APP_LANG",strtolower($APP_LANG));
	$t->replace("WEBSITE_URL",$WEBSITE_URL);
	$t->replace("MICROSITE", $GLOBALS['MICROSITE']);

	// get and replace width and height
	$player_width = 520;
	if (isset($GLOBALS['PLAYER_SETTINGS']['width']))
		$player_width = $GLOBALS['PLAYER_SETTINGS']['width'];
		
	$player_height = 390;
	if (isset($GLOBALS['PLAYER_SETTINGS']['height']))
		$player_height = $GLOBALS['PLAYER_SETTINGS']['height'];

	$t->replace('PLAYER_WIDTH', $player_width);
	$t->replace('PLAYER_HEIGHT', $player_height);

	
	$db->query($query); //requery!!! -> bettter if goto previous record

	//more videos
	$more_video_box = $t->repeater('MORE_VIDEO');
	$additional_video = $t->row_repeater('ADDITIONAL_VIDEO',$more_video_box);

	$all_additional_videos = "";
	$video_index = 0;
/*	while($db->next_record())
	{
		$all_additional_videos .= $additional_video;
		$t->row('IMG_HTML', $media->getThumbnail("114x76")), $all_additional_videos);

		$t->row('IMAGE', html_return_media($db->f('image_id'), "article_additional_video"), $all_additional_videos);

		$t->row('TITLE', $db->f('title'), $all_additional_videos);
		$t->row('DURATION', date("i:s",$db->f('length')), $all_additional_videos);
		$t->row('DATE', date("d.m.Y",$db->f('date')), $all_additional_videos);
		$t->row('TIME', date("",$db->f('date')), $all_additional_videos);
		$t->row('MEDIA_ID', $db->f('video_id'), $all_additional_videos);
		$t->row('ARTICLE_ID', $article_id, $all_additional_videos);
		$t->row('VIDEO_URL', article_video_url($db->Record,$db->f('video_id')), $all_additional_videos);

		//insert clear div after 4 videos
		$clear_div = $t->row_repeater('VIDEO_CLEAR',$all_additional_videos);
		$video_index++;
		if ($video_index == 4) {
			$t->row('VIDEO_CLEAR', $clear_div, $all_additional_videos);
		} else {
			$t->row('VIDEO_CLEAR', '', $all_additional_videos);
		}

		$all_additional_videos_count++;
	}

	switch($all_additional_videos_count)
	{
		# no video at all
		case 0:
			return "";
		# one video - no "more videos" box
		case 1:
			break;
		# more than one video - add more videos box
		default:
			$t->replace('MORE_VIDEO', $more_video_box);
			$t->replace('ADDITIONAL_VIDEO', $all_additional_videos);

	}
	 */
	$t->replace('SECTION_ID', $media->section_id);

	// FLV player parameters
	if ($APP_LANG == "HR")
		$t->replace("SITE","hr");
	else
		$t->replace("SITE","24ur");

	$t->replace("STREAM_FLV_SERVER",$FLV_SERVER_STREAM); //path is: STREAM_FLV_SERVER/SECTION/MEDIA_DIR/FILENAME-2.flv
	$t->replace("SECTION",$FLV_DIR_CLIP);
	$t->replace("MEDIA_ID",$media->id);
	$t->replace("WEBSITE_URL",$WEBSITE_URL);

	//remove usertrack section
	$t->repeater("USERTRACK");
	banners_add($t);

	return $t->get("$");

}

function box_central_media_comments_send()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$media = MicrositeMedia::open();
	$tem = micro_comments_form($media->id, "media");

	return $tem;
}


function box_central_media_comments()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$page = $_GET['page'];
	$order = $_GET['order'];

	$media = MicrositeMedia::open();
	$tem = micro_comments($media->id,"media",$page,$order);
	return $tem;
}

function box_central_media_vote()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$article = MicrositeMedia::open();
	$tem = $article->vote;

	return $tem;

}

/******************************************************************************************
 *				CENTRAL / VIDEO CREATOR
 ******************************************************************************************/

function box_central_video_creator_single($id)
{
	global $DBconfig, $TEMPLATE_DIR, $FRONTEND_DIR, $site, $_PARAMS;
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, 'box/central/video_creator_single.tpl');
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


function box_central_video_creator_vote()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$vc = MicrositeVideoCreator::open();
	$tem = $vc->vote;

	return $tem;

}

function box_central_video_creator_comments()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$page = $_GET['page'];
	$order = $_GET['order'];

	$vc = MicrositeVideoCreator::open();
	$tem = micro_comments($vc->id,"videocreator",$page,$order);

	return $tem;
}

function box_central_video_creator_comments_send()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$media = MicrositeVideoCreator::open();
	$tem = micro_comments_form($media->id, "videocreator");

	return $tem;
}

/**
 * render video creator box
 *
 * @return unknown
 */
function box_central_video_creator()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/video_creator.tpl");
	$t->open();
	return $t->get();
}
/**
 * renders video creator list
 *
 * @param unknown_type $info
 * @return string
 */
function box_central_video_creator_view($info)
{
	global $REC_ON_PAGE, $TEMPLATE_DIR, $FRONTEND_DIR, $site;

	if(!$_GET['page'])
		$page = 1;
	else
		$page = $_GET['page'];

	$REC_ON_PAGE = 5;

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/video_creator_view.tpl");
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

	$db->query("select count(*) as cnt from micro_video_creator where status=3");
	$db->next_record();
	$all_rec = $db->f("cnt");

	$pager = new cls_Pager($all_rec, $REC_ON_PAGE);
	$t->replace("PAGE_NAVIGATOR", $pager->create($page) );


	return $t->get();
}

/******************************************************************************************
 *				CENTRAL / ARTICLE
 ******************************************************************************************/
function box_central_() {}

function box_central_article_title()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/article_title.tpl");
	$t->open();

	$article = MicrositeArticle::open();
	$t->replace("ARTICLE_TITLE", $article->title);

	$tem = $t->get();
	return $tem;


}


function box_central_article_date()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/article_date.tpl");
	$t->open();

	$article = MicrositeArticle::open();
	$t->replace("ARTICLE_DATE", $article->date);
	$t->replace("ARTICLE_SOURCES", $article->sources);
	$t->replace("ARTICLE_PLACE", $article->place);
	$tem = $t->get();
	return $tem;

}

function box_central_article_summary()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/article_summary.tpl");
	$t->open();

	$article = MicrositeArticle::open();
	$t->replace("ARTICLE_SUMMARY", $article->summary);

	$tem = $t->get();
	return $tem;

}

function box_central_article_video()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id, $APP_LANG, $WEBSITE_URL;

	$article = MicrositeArticle::open();
	
	$t = new cls_Template;
	$t->_template = $article->video;
	$t->replace('##MICROSITE##', $GLOBALS['MICROSITE']);
	
	// get and replace width and height
	$player_width = 520;
	if (isset($GLOBALS['PLAYER_SETTINGS']['width']))
		$player_width = $GLOBALS['PLAYER_SETTINGS']['width'];
		
	$player_height = 390;
	if (isset($GLOBALS['PLAYER_SETTINGS']['height']))
		$player_height = $GLOBALS['PLAYER_SETTINGS']['height'];

	$t->replace('PLAYER_WIDTH', $player_width);
	$t->replace('PLAYER_HEIGHT', $player_height);
	
	return $t->get();

}

function box_central_article_gallery()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$article = MicrositeArticle::open();

	$tem = $article->gallery;

	return $tem;

}

function box_central_article_body()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/article_body.tpl");
	$t->open();

	$article = MicrositeArticle::open();
	$t->replace("ARTICLE_BODY", $article->body);

	$tem = $t->get();
	return $tem;

}

function box_central_article_related()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$article = MicrositeArticle::open();
	$tem = $article->related;

	return $tem;

}


function box_central_article_vote()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$article = MicrositeArticle::open();
	// we show vote box if so
	if($article->has_votes)
		$tem = $article->vote;

	return $tem;

}

function box_central_article_comments()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$page = $_GET['page'];
	$order = $_GET['order'];

	$article = MicrositeArticle::open();
	// we show comment box if so
	if($article->has_comments)
		$tem = micro_comments($article->id,"article",$page,$order);
		
	return $tem;
}

function box_central_article_comments_send()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$article = MicrositeArticle::open();
	// we show comment box if so
	if($article->has_comments)
		$tem = micro_comments_form($article->id,"article",$page,$order);
		
	return $tem;
}

function box_central_article_banner()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	$t = new cls_Template($TEMPLATE_DIR . $FRONTEND_DIR, "box/central/article_banner.tpl");
	$t->open();
	banners_add($t);

	$tem = $t->get();
	return $tem;

}

/*****************************************************************************************************
 *  	CENTRAL / FULL-PAGE BOXES
 *****************************************************************************************************/

function box_central_search_results()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $front_id,  $section_id,  $site, $_PARAMS;
	global $MIN_CHARS, $MAX_SEARCH_WORDS, $URL_PREFIX_SEARCH;
	global $SEARCH_ON_FORUM,$FORUM_SECTION_ID;

	$keyword = $_PARAMS[0];
	$uniq_id = $_PARAMS[1];
	$sort    = $_PARAMS[2];
	$page	 = $_PARAMS[3];
	$secid	 = $_PARAMS[4];

	$MIN_CHARS=3;
	$MAX_SEARCH_WORDS=7;

	// needed in str function bellow
	//$MIN_CHARS = 4;
	//$MAX_SEARCH_WORDS = 10;

	if (!$keyword) {
		return false;
	}

	$db=new DB_Sql();
	$db2=new DB_Sql();

	//clean up of the HEAP table//
	sql_delete_records_from_heap(3600);


	$t = new cls_Template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/search_results.tpl");
	$t->open();

	// REPLACE NEEDED PARAMS
	if ($t->tag_exists("DATE_CURRENT")) $t->replace("DATE_CURRENT",date("d.m.Y"));
	if ($t->tag_exists("TIME_CURRENT")) $t->replace("TIME_CURRENT",date("H:i"));
	if ($t->tag_exists("SECTION_ID")) $t->replace("SECTION_ID", $section_id);

	/*
	* We make unique id, if user doesen't have it yet for this search
	*/
	if (!$uniq_id) {
			list($usec, $sec) = explode(' ', microtime());
			srand((float) $sec + ((float) $usec * 100000));
			$uniq_id = md5(uniqid(rand(),1));
	}

	$query = $keyword;

	# let's strip some tags and script shit - pljubic
	$search = array
			(
				'@<script[^>]*?>.*?</script>@si', # strip out javascript
				'@<style[^>]*?>.*?</style>@siU'  # strip out style
			);

	$query = preg_replace($search, '', $query);
	$uniq_id = preg_replace($search, '', $uniq_id);

	$query = strip_tags($query);
	$query = trim($query);

	$tmp=str_ready_for_search_w_quotes($query);

	if ($tmp) {
		$show_search_words=implode(" ",$tmp);
		if ($t->tag_exists("##ARTICLE_SEARCH_INFO##"))  $t->replace("##ARTICLE_SEARCH_INFO##",$query);

		$sWords=implode(" ",$tmp);
		if (strlen($sWords)>250) $sWords = substr($sWords, 0, 250);

		/*
		* We insert search words in search_words table.
		*/
		$sql = "INSERT INTO search_words (words, date) VALUES ('".$sWords."', ".time().") ";
		$db2->query($sql);

		if (!$site) $site=1;
		$query=("select article_id,title,site_id,article.section_id,date,article.status, match (title,keywords) against
				('$sWords') AS score from article left join section on article.section_id=section.section_id
				where section.site_id like '%#$site#%'
				and (article.status='3' or article.status='9') and match (title,keywords) against ('$sWords')");

		$db2->query("create table if not exists query_table_temp(
			uniq_id varchar(80) NOT NULL,
			article_id int(11) DEFAULT '0' NOT NULL,
			title varchar(255) NOT NULL,
			score float(15) DEFAULT '0' NOT NULL,
			timestamp int(11) DEFAULT '0' NOT NULL,
			section_id int(11) NOT NULL,
			date int(11) NOT NULL
			) DEFAULT CHARSET=utf8");

		$db2->query("select count(uniq_id) as stevec from query_table_temp where uniq_id='$uniq_id'");
		$db2->next_record();
		$count=$db2->f("stevec");
		if ($count!=0) {
			$db2->query("delete from query_table_temp where uniq_id='$uniq_id'");
		}

		$db->query($query);

		// we need for stars
		$max_score = 0.0;

		while ($db->next_record()) {
			unset($score);
			$status=$db->f("status");
			if ($status != 3 and $status !=9 ) {
				continue;
			}
			$id=$db->f("article_id");
			$title=$db->f("title");
			$title=addslashes($title);
			$body=$db->f("body");
			$keywords=$db->f("keywords");
			$section_id=$db->f("section_id");
			$date=$db->f("date");
			$score=$db->f("score");

			$time=time();
			$db2->query("insert into query_table_temp (uniq_id,article_id,title,score,timestamp,section_id,date) values
						('$uniq_id','$id','$title','$score','$time','$section_id','$date')");

		}
		
		//check if search is needed on forum
		if ($SEARCH_ON_FORUM) {
	    	
			$sql = "SELECT fc.cat_id,fp.post_id,ft.topic_id, fp.title, fp.text, fp.date, 
				ft.title AS topic_title, MATCH(fp.title, fp.text) AGAINST ('$sWords') AS score 
				FROM forum_post fp 
				INNER JOIN forum_topic ft ON fp.topic_id = ft.topic_id AND ft.status = 3 
				INNER JOIN forum_category fc ON fc.cat_id = ft.cat_id 
				INNER JOIN forum f ON f.forum_id = fc.forum_id AND site_id = ".$site."  
				WHERE fp.status = 3 
				AND MATCH(fp.title, fp.text) AGAINST ('$sWords')";
			    	
			$db->query($sql);
		
			while ($db->next_record()) {
	
			        $id=$db->f("post_id");
			        $title=$db->f("topic_title");
		        	$title=addslashes($title);
				$body=$db->f("text");
		        	$section_id=$FORUM_SECTION_ID;
		        	$date=$db->f("date");
		        	$score=$db->f("score");
		
				$db2->query("insert into query_table_temp (uniq_id,article_id,title,score,timestamp,section_id,date) values
					('$uniq_id','$id','$title','$score','$time','$section_id','$date')");
			}	
		}

	}

	//now the showing part//
	$db2=new DB_Sql("","24ur_write");

	$secid = (int)str_clean_up($secid);
	$sort = str_clean_up($sort);
	// we show records by sections
	$db2->query("select t1.section_id,t2.topic,count(t1.article_id) as stevilo from query_table_temp t1 left join section t2 using (section_id) where uniq_id='$uniq_id' group by section_id");

	$table = "";
	$row = $t->repeater("ARTICLE_SEARCH_SEC");
	while ($db2->next_record()) {
			$table .= $row;
			$t->row("##section_id##",$db2->f("section_id"), $table);
			$t->row("##topic##",$db2->f("topic"), $table);
			$t->row("##stevilo##",$db2->f("stevilo"), $table);
			$search_url = $URL_PREFIX_SEARCH."$keyword/$uniq_id/".($sort=="date"?"date":"relavance")."/1/".$db2->f('section_id');
			$t->row("##URL##", $search_url, $table);
	}
	if ($t->tag_exists("##ARTICLE_SEARCH_SEC##"))      $t->replace("ARTICLE_SEARCH_SEC",$table);

	// i get max_score, that i can draw stars correctly
	$db2->query("select max(score) from query_table_temp where uniq_id='$uniq_id' ".($secid?"and section_id=$secid ":""));
	$db2->next_record();
	$max_score = $db2->f(0);

	// i get the number of records
	$db2->query("select count(uniq_id) as stevec from query_table_temp where uniq_id='$uniq_id' ".($secid?"and section_id=$secid ":""));
	$db2->next_record();
	$ALL=$db2->f("stevec");
	$no_hits        = $t->repeater('ARTICLE_SEARCH_NO');
	$row            = $t->repeater("ARTICLE_SEARCH");

	if ($ALL==0) {
		//then there`s no hits!!//
		if ($t->tag_exists("##ARTICLE_SEARCH_NO##"))  $t->replace('ARTICLE_SEARCH_NO', $no_hits);
		if ($t->tag_exists("##ARTICLE_SEARCH##"))  $t->replace('ARTICLE_SEARCH', "");

		//remove pagers
		$page_minus = $t->repeater("IF_PAGE_MINUS");
		$page_plus = $t->repeater("IF_PAGE_PLUS");

	} else {
			$page_navig=page_navigator($ALL,$URL_PREFIX_SEARCH.urlencode($keyword)."/$uniq_id/".($sort=="date"?"date":"relavance")."/?##PAGE##/");
			$query="select article_id,title,section_id,date, score from query_table_temp where uniq_id='$uniq_id' ".($secid?"and section_id=$secid ":"")."ORDER by ".($sort=="date"?"date":"score")." DESC";
			global $rec_no, $REC_ON_PAGE;
			$query=$query." LIMIT ".$rec_no.",".$REC_ON_PAGE;
			$db2->query($query);
			$table = "";

			while ($db2->next_record()) {
					$table .= $row;

					$article = MicrositeArticle::open((int)$db2->f("article_id"));
					if ($FORUM_SECTION_ID == $db2->f("section_id")) {
						//built url to forum
                				$t->row("##ARTICLE_URL##", "/forum?post_id=".$db2->f("article_id")."#".$db2->f("article_id"),$table);
                				$t->row("##title##",$db2->f("title"),$table);
					} else
						$t->row("##ARTICLE_URL##", $article->url,$table);
						
					$t->row("##title##",$article->title,$table);
					$t->row("##date##",date("j.n.Y",$article->date),$table);
					if ($max_score)
							$score = 5 * ($db2->f('score') / $max_score);
					else
							$score = 0;

					$t->row("##HIT_STARS##",search_show_stars($score),$table);

					//some more replacement for UA site
			/*			if ($t->row_tag_exists("IMAGE",$table)) {

						$t->row("DATE",date("d.m.Y",$date),$table);
						$t->row("TIME",date("H:i",$date),$table);
						$t->row("TITLE",$title,$table);

						//find image
						$sql = "SELECT am.media_id, a.* FROM article a
							LEFT OUTER JOIN article_media am ON a.article_id = am.article_id
							WHERE a.article_id = ".$article_id." AND am.show_at & 1 = 1 LIMIT 1";
						$db1 = new DB_Sql();
						$db1->query($sql);
						if ($db1->next_record())
							$t->row("IMAGE",html_return_media($db1->f("media_id"),'archive_page'),$table);


						$t->row("SUMMARY",$db1->f("summary"),$table);
						$t->row("ARTICLE_ID",$article_id,$table);
						$t->row("ARTICLE_URL",article_url($db1->Record),$table);

						//check if video,gallery,...
						article_choose_template_part_temp($table,$article_id);
						$t->row("VIDEO_URL",article_video_url($db1->Record,""),$rows);
						$t->row("GALLERY_URL",article_gallery_url($db1->Record,""),$rows);
					}
			*/
			}
			if ($t->tag_exists("##ARTICLE_SEARCH##"))       $t->replace("ARTICLE_SEARCH",$table);
			if ($ALL>$REC_ON_PAGE) {
				if ($t->tag_exists("##PAGE_NAVIGATOR##"))       $t->replace("##PAGE_NAVIGATOR##",$page_navig);
			}

		$num_pages = (int)($ALL/$REC_ON_PAGE)+1;

		if (!$page)
			$p = 1;

		//replace tags in navigation buttons
		if ($p == 1) $t->replace("PAGE_MINUS",1); else $t->replace("PAGE_MINUS",$p-1);
		if ($p == $num_pages) $t->replace("PAGE_PLUS",$num_pages); else $t->replace("PAGE_PLUS",$p+1);

		//check if posible click on page- or page+
		$page_minus = $t->repeater("IF_PAGE_MINUS");
		$page_plus = $t->repeater("IF_PAGE_PLUS");
		if ($p != 1)
			$t->replace("IF_PAGE_MINUS",$page_minus);
		if ($p != $num_pages)
			$t->replace("IF_PAGE_PLUS",$page_plus);

	}
	//====================//
	$sort_by_relevance      = $URL_PREFIX_SEARCH."$keyword/$uniq_id/relavance/1";
	$sort_by_date_url       = $URL_PREFIX_SEARCH."$keyword/$uniq_id/date/1";

	if ($t->tag_exists("##SORTBYRELEVANCE##"))      $t->replace("##SORTBYRELEVANCE##", $sort_by_relevance);
	if ($t->tag_exists("##SORTBYDATEURL##"))        $t->replace("##SORTBYDATEURL##",$sort_by_date_url);
	if ($t->tag_exists("##ARTICLE_SEARCH_INFO##"))  $t->replace("##ARTICLE_SEARCH_INFO##",$query);



	return $t->get();
}



/**
 * Box rendering forum front
 */
function box_central_forum_front() {

	global $BB_SECTION_ID, $config, $lang, $TEMPLATE_DIR, $FRONTEND_DIR, $site;

	if (!$front_id = (int)$front_id)
		$front_id = (int)front_active($BB_SECTION_ID, 3);

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, 'box/central/forum.tpl');
	$t->open();

	$t->replace("FORUM_INSERT", forum());

	return $t->get();
}

function box_central_static()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $_PARAMS;

	$keyword = $_PARAMS[0];
	$db = new db_sql();
	$db->query("SELECT * FROM content WHERE keyword='$keyword' AND site_id='$site' AND status=3 LIMIT 1");
	if (!$db->next_record())
	{
		header("Location: /404");
		die();
	}

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/static.tpl");
	$t->open();

	$t->replace("TITLE", $db->f("title"));
	$t->replace("TEXT", $db->f("text"));

	DEFINE('WINDOW_TITLE', $db->f('title'));

	return $t->get();
}


function box_central_polls()
{
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;
	global $URL_PREFIX_POLL;
	$POLLS_PER_PAGE=10;
	$db = new DB_Sql();

    $t = new cls_Template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/polls.tpl");
	$t->open();

	if ($_GET['poll_id'])
	{
		$condition = "AND poll_id='$poll_id'";
		$start=0;
		$POLLS_PER_PAGE=1;

		//remocep page navigation
		$page_minus = $t->repeater("IF_PAGE_MINUS");
		$page_plus = $t->repeater("IF_PAGE_PLUS");
		$page_minus = $t->repeater("IF_PAGE_MINUS_B");
		$page_plus = $t->repeater("IF_PAGE_PLUS_B");
	}
	else
	{
		$condition = '';
		// PAGER
		$db->query("SELECT count(*) as count FROM poll2 WHERE status=3 AND site_id = $site $condition");
		$db->next_record();

		if ($_GET['page'])
			$p = $_GET['page'];
		else
			$p = 1;

		$POLLS_PER_PAGE = 15;
		$pager = new cls_Pager($db->f('count'), $POLLS_PER_PAGE);
		$pager->href($URL_PREFIX_POLL."##PAGE##?");

		$t->replace("PAGE_NAVIGATOR", $pager->create($p) );

		$num_pages = (int)($db->f('count')/$POLLS_PER_PAGE)+1;

		//replace tags in navigation buttons
		if ($p == 1) $t->replace("PAGE_MINUS",1); else $t->replace("PAGE_MINUS",$p-1);
		if ($p == $num_pages) $t->replace("PAGE_PLUS",$num_pages); else $t->replace("PAGE_PLUS",$p+1);

		//check if posible click on page- or page+
		$page_minus = $t->repeater("IF_PAGE_MINUS");
		$page_plus = $t->repeater("IF_PAGE_PLUS");
		if ($p != 1)
			$t->replace("IF_PAGE_MINUS",$page_minus);
		if ($p != $num_pages && $num_pages > 1)
			$t->replace("IF_PAGE_PLUS",$page_plus);

		$page_minus = $t->repeater("IF_PAGE_MINUS_B");
		$page_plus = $t->repeater("IF_PAGE_PLUS_B");
		if ($p != 1)
			$t->replace("IF_PAGE_MINUS_B",$page_minus);
		if ($p != $num_pages && $num_pages > 1)
			$t->replace("IF_PAGE_PLUS_B",$page_plus);

		$start = $p*$POLLS_PER_PAGE-$POLLS_PER_PAGE;
	}
	$db->query("SELECT *, date_format(created,'%d.%m.%Y') as f_created, date_format(archived,'%d.%m.%Y') as f_archived FROM poll2 WHERE status=3 AND site_id = $site $condition ORDER BY poll_id DESC LIMIT $start,$POLLS_PER_PAGE");

	$poll = $t->repeater("POLLS_LIST");
	$polls = '';

	// no polls
	if (!$db->num_rows())
		$t->_template="";
	else
	{
		//process each poll with poll_ext function
		while ($db->next_record())
			$polls .= poll_ext($poll, $db);
	}

	$t->replace("POLLS_LIST",$polls);

	$tem = $t->get();
	return $tem;

}

/*************************************************************************************
 * 		CENTRAL / FRONT
 ************************************************************************************/
function box_central_main_news()
{
	global $ARTICLE_COMMENTS_COUNT;
	global $front_id, $section_id, $TEMPLATE_DIR, $FRONTEND_DIR, $site;
	global $MEDIA_IMAGE_URL;
	
	if (!$id = micro_get_article_id($front_id))
		return false;

	$tpl = "box/central/main_news.tpl";

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, $tpl);
	$t->open();

	$article = MicrositeArticle::open((int)$id);
	$section = MicrositeSection::open((int)$article->section_id);

	$t->replace("article_url", $article->url);
	$t->replace("section_title", $section->title);
	$t->replace("section_url", $section->url);
	$t->replace("title", $article->title);
	$t->replace("source", $article->sources);
	$t->replace("summary", $article->summary);
	$t->replace("date", $article->date);
	$t->replace("IMAGE300x200",  html_return_media($article->frontImage, "300x200"));

	if ($t->tag_exists("IF_COMMENTS_START")) {
		$tmp = $t->repeater("IF_COMMENTS");
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
		$t->replace("IF_COMMENTS",$tmp);
	} 
	
	
	//get image pool 	 
         if ($t->tag_exists("IF_IMAGE_POOL_START")) { 	 
  	 
                 $image_pool = $t->repeater("IF_IMAGE_POOL"); 	 
  	 
                 // escape double quotes in media title for AddToPool javascript to work properly 	 
                 $db = new DB_Sql(); 	 
                 $db->query("select t1.*, REPLACE(t1.title, '\"', '\\\\\"') AS title, t3.name from media t1 inner join article_media t2 using (media_id) left join source t3 on t1.source_id=t3.source_id where (t2.article_id=".(int)$id.") and ((t2.show_at&1)=1) and (t2.type=1) order by t2.seqno"); 	 
  	 
                 $image = $t->row_repeater("POOL",$image_pool); 	 
                 $images = ""; 	 
                 $index = 0; 	 
                 while ($db->next_record()) { 	 
                         $images .= $image; 	 
  	 
                         $pic = sql_get_media_filename($db->f("media_id")); 	 
                         $dir_sufix=date_dirname_to_timestamp($pic[2]); 	 
  	 
                         $url = $MEDIA_IMAGE_URL."300x200/".$dir_sufix."/".$pic[0]; 	 
                         $t->row("INDEX",$index++,$images); 	 
                         $t->row("IMAGE_URL",$url,$images); 	 
                         $t->row("IMAGE_TITLE",$db->f("title"),$images); 	 
                 } 	 
                 $t->row("POOL",$images,$image_pool); 	 
                 $t->replace("IF_IMAGE_POOL",$image_pool); 	 
         }	
	
	article_choose_template_part_temp($t->_template, $article->id);

	$tem = $t->get();
	return $tem;

}

function box_central_list_news()
{
	global $front_id, $section_id, $TEMPLATE_DIR, $FRONTEND_DIR, $site;
	global $ARTICLE_COMMENTS_COUNT;

	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/list_news.tpl");
	$t->open();

	$news = $t->repeater("LEFT");
	$news_all = "";
	$left = "";
	$right = "";
	$i = 0;

	while ($id = micro_get_article_id($front_id))
	{
		$i++;

		$tmpnews = $news;

		$article = MicrositeArticle::open((int)$id);

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

	}

	if (!$left && !$right)
		return false;

	$t->replace("LEFT", $left);
	$t->replace("RIGHT", $right);

	$tem = $t->get();
	return $tem;

}


function box_central_image_list()
{
	global $_;
	return box_central_media_list(false, 14, $_['PHOTO']);
}

function box_central_video_list()
{
	global $_, $BOX_CENTRAL_VIDEO_LIST_BOXTITLE;
	return box_central_media_list(false, 11, $BOX_CENTRAL_VIDEO_LIST_BOXTITLE);
}


function box_central_media_list($info, $showas, $boxtitle,  $page=1, $t=null)
{
	global $front_id, $SECTION_ID, $TEMPLATE_DIR, $FRONTEND_DIR, $site;

	$perpage=7;
	if ($t==null)
	{
		$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/media_list.tpl");
		$t->open();
	}

	$t->replace("BOXTITLE",$boxtitle);
	$t->replace("SHOWAS", $showas);

	// PAGER
	$all = micro_get_media_numb($front_id, $showas);
	$pager = new cls_Pager($all, $perpage);
	$pager->href("");
	$pager->set_additional_params("onclick='returnContent(\"box_central_video_list_$showas\", \"/bin/ajax_media_list.php?showas=$showas&page=##PAGE##&boxtitle=$boxtitle&section_id=".$SECTION_ID."\"); this.blur(); return false;'");
	$pagerhtml = $pager->create($page);
	$lstart = ($page-1)*$perpage;
	$lstop  = $perpage;

	if ($pager->max_page<=1)
		$pagerhtml ="";

	$t->replace("PAGER", $pagerhtml);

	// BIG VIDEO
	if ($id = micro_get_media_id($front_id, $showas, "fr.seqno ASC, fr.media_id", $lstart.",".$lstop))
	{
		$video = MicrositeMedia::open((int)$id);
		$t->replace("TITLE", $video->title);
		$t->replace("DATE", date("d.m.Y", $video->date));
		$t->replace("DESCRIPTION", $video->description);
		$t->replace("ID", $video->id);
		$t->replace("URL", $video->url);
		$t->replace("THUMB", $video->getThumbnail("160x120"));

	}
	else
		return "";

	// EXTRA VIDEOS
	$extra = $t->repeater("EXTRA");
	$extra_all = "";
	$i = 0;

	while ($id = micro_get_media_id($front_id, $showas, "fr.seqno ASC, fr.media_id", $lstart.",".$lstop))
	{
		
		$i++;
		if ($i>=$perpage)
			break;

		$tmpextra = $extra;

		$video = MicrositeMedia::open((int)$id);

		$t->row("EXTRA_TITLE", $video->title, $tmpextra);
		$t->row("EXTRA_DATE", date("d.m.Y",$video->date), $tmpextra);
		$t->row("EXTRA_DESCRIPTION", $video->description, $tmpextra);
		$t->row("EXTRA_ID", $video->id, $tmpextra);
		$t->row("EXTRA_URL", $video->url, $tmpextra);
		$t->row("EXTRA_THUMB", $video->getThumbnail("78x52"), $tmpextra);

		$extra_all .= $tmpextra;
	}


	$t->replace("EXTRA", $extra_all);

	$tem = $t->get();
	return $tem;

}

function box_central_tags()
{
	global $front_id, $section_id, $TEMPLATE_DIR, $FRONTEND_DIR, $site;
	global $URL_PREFIX_SEARCH;

	$tem = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/tags.tpl");
	$tem->open();

	//variable for saving key words
	$count = 30;
	$allKeys = array();
	$time_keys = array();

	$today = time();

	$db = new db_sql();

	$add = "";
	if ($section_id)
		$add = " (article.section_id = " . $section_id . " OR section.parent=".$section_id.") AND ";

	$sql = "SELECT
			article_id,
			keywords,
			article.enter_time AS time
		FROM
			article
		LEFT JOIN
			section ON section.section_id = article.section_id
		WHERE
			article.status = 3 AND
			" . $add . "
			article.keywords != '' AND
			article.published_to >= '" . $today . "' AND
			article.published_from <= '" . $today . "' AND
			section.site_id = '#" . $site . "#'
		ORDER BY
			article.article_id DESC";
	$db->query($sql);

	while ($db->next_record())
	{
		//we change collumns names into variable names (article_id comes $article_id)m because i need them later
		foreach ($db->Record as $k=>$v)
			$$k = $v;

		$keywords = str_replace(" ", "", $keywords);
		$keys = explode("##", $keywords);

		//we loop all keywords for article
		foreach ($keys as $key)
		{
			if (mb_strlen($key) >= 4)
			{
				$tmpkey = strtolower($key);
				$allKeys[] = $tmpkey;
				if (in_array($tmpkey, $time_keys))
				{
					if ($time > $time_keys[$tmpkey])
						$time_keys[$tmpkey] = $time;
				}
				else
					$time_keys[$tmpkey] = $time;
			}
		}
	}

	//block in template for keywords of all articles
	$sKey = $tem->repeater("KEYWORDS", $tem);
	$sKeys = "";

	$order = 1;

	$keyz = array_count_values($allKeys);

	if($order == 1)
		arsort($keyz);
	if($order == 2)
		asort($keyz);

	//we take the most frequent keywords
	$usekeys = array();
	$i = 1;

	$keywords_that_size = 5;
	$size = $count / $keywords_that_size;

	foreach ($keyz as $k=>$v)
	{
		$usekeys[$i][$k] = $size;
		if ($i % $keywords_that_size == 0)
			$size--;
		$i++;
		if ($size == 0)
			break;
	}

	$temp_keys = array();
	foreach ($usekeys as $k=>$v)
	{
		foreach ($v as $key=>$value)
		{
			$temp_keys[$time_keys[$key]][$key] = $value;
		}
	}

	krsort($temp_keys);

	$usekeys = array();
	$i = 0;
	foreach ($temp_keys as $k=>$v)
	{
		foreach ($v as $key=>$value)
		{
			$usekeys[$i][$key] = $value;
			$i++;
		}
	}

	shuffle($usekeys);

	$keyz = array();
	$i = 0;
	foreach ($usekeys as $k=>$v)
	{
		foreach ($v as $key=>$value)
			$keyz[$key] = $value;
	}


	$size = $count / $keywords_that_size;

	//we loop through article keys
	$i = 1;
	$return_keys    = array();
	foreach ($keyz as $k=>$v)
	{
		$sKeys .= $sKey;
		$tem->row("KEYWORD", $k, $sKeys);
		$tem->row("KEYWORDLINK",$URL_PREFIX_SEARCH.$k,$sKeys);
		$return_keys[]  = $k;
		//we edit size of font beware of number of found keys
		if (($v < $size && $v > 0))
			$tem->row("SIZE", $v, $sKeys);
		else if ($v == 0 || empty($v))
			$tem->row("SIZE", "x", $sKeys);
		else
			$tem->row("SIZE", $size, $sKeys);
		if($i == $count)
			break;
		$i++;
	}
	$tem->replace("KEYWORDS", $sKeys);

	$return = $tem->get();
	return $return;
}

/**
 * render comic creator comments box
 *
 * @return string
 */
function box_central_comic_creator_comments(){
	$type="comiccreator";
	$id=(int)$_GET["id"];
	if(!$id){
		$regex ="/([\/]{1}[\d]{1,})/" ;
		if(preg_match($regex, $_SERVER['REQUEST_URI'], $result)){
			$id = (int)str_replace("/","",$result[0]);
		}
	}
	$page=(int)$_GET["page"];
	$order=(strtoupper($_GET["order"])=="DESC"?"DESC":"ASC");
	
	$tem = micro_comments($id, $type, $page, $order);
	return $tem;
}

/**
 * render comic creator comments send box
 *
 * @return string
 */
function box_central_comic_creator_comments_send(){
	$type="comiccreator";
	$id=(int)$_GET["id"];
	if(!$id){
		$regex ="/([\/]{1}[\d]{1,})/" ;
		if(preg_match($regex, $_SERVER['REQUEST_URI'], $result)){
			$id = (int)str_replace("/","",$result[0]);
		}
	}
	$page=(int)$_GET["page"];
	$order=(strtoupper($_GET["order"])=="ASC"? "ASC":"DESC");
	if ($id){
		$media = MicrositeComicCreator::open($id);
		$tem = micro_comments_form($media->id, "comiccreator");
	}
	return $tem;
}

/**
 * render comic creator vote box
 *
 * @return string
 */
function box_central_comic_creator_vote()
{
	$type="comiccreator";

	//read strip id if posted
	$id=(int)$_GET["id"];
	if(!$id){
		$regex ="/([\/]{1}[\d]{1,})/" ;
		if(preg_match($regex, $_SERVER['REQUEST_URI'], $result)){
			$id = (int)str_replace("/","",$result[0]);
		}
	}

	if ($id){
		$media = MicrositeComicCreator::open($id);
		$tem = $media->vote;
		return $tem;
	}
	else{
		//can't render comic_creator_vote
		return "";
		
	}
}

/**
 * box for live stream (in article)
 */
function box_central_live_stream() {
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site;
	global $VIDEO_CLANEK_STYLE;
	global $MICROSITE;
	
	$t = new cls_Template($TEMPLATE_DIR.$FRONTEND_DIR, 'box/central/live_stream.tpl');
	$t->open();
	
	//check if article has right style
	$article = MicrositeArticle::open();
	if ($article->style != $VIDEO_CLANEK_STYLE ." - 1" && $article->style != $VIDEO_CLANEK_STYLE ." - 2")
		return "";
	
	//fetch right stream (field seqno in table style)
	$db = new DB_Sql();
	$sql = "SELECT seqno FROM style WHERE style = '".$article->style."' AND site_id = '#".$site."#'";
	$db->query($sql);
	if (!$db->next_record())
		return "";
	
	$t->replace("MEDIA_ID",$db->f("seqno"));
	$t->replace("MICROSITE",$MICROSITE);
	
	return $t->get();  	
}


function box_central_article_send($data)
{
	//global $TEMPLATE_DIR, $FRONTEND_DIR, $site, $front_id;

	//$article = MicrositeArticle::open();
	return box_central_article_send_to($data);
//	return $article->send;
}


/**
 * Article send to friend functions
 *
 * @param int $form_results userform data
 * @return string
 */
function box_central_article_send_to($data, $form_results=0){
	global $TEMPLATE_DIR, $FRONTEND_DIR, $site;
	global $MICROSITE;
	
	switch($form_results){
		case 0:
			// we continue with the flow
			break;
		case 1:
			return "ERR";
			break;
		case 3:
			return "OK";
			break;
		default:
			return "ERR: ".$form_results;
			break;
	}
	
	$t = new cls_Template($TEMPLATE_DIR.$FRONTEND_DIR, 'box/central/article_send_to.tpl');
	$t->open();

	// box was called by ajax function
	$r = $t->repeater("MAIN_FORM"); 
	if ($_GET["ajax"])
		$t->replace("MAIN_FORM","##ARTICLE_SEND_FORM##");
	else{
		$t->replace("MAIN_FORM",$r);
		// non ajax - we fill other box data
	}
		
	$r = $t->repeater("INNER_FORM"); 
	$t->replace("ARTICLE_SEND_FORM",$r);
		
	$form_data = array(
			'article_id'=> cls_Input::post('article_id','int'),
			'email_from'=> cls_Input::post('email_from','string'),
			'email_to' 	=> cls_Input::post('email_to','string'),
			'body'	 	=> cls_Input::post('body','string'),
			'uniq_id' 	=> cls_Input::post('uniq_id','int'),
			);
		
	if($data['email_from'])
			$t->replace("EMAIL_FROM", $data['email_from']);			
		else
			$t->replace("EMAIL_FROM_ERROR", "");//*
					
	if(!$data['email_to'])
			$t->replace("EMAIL_TO_ERROR", "");//*
					
	if($data['body'])
		$t->replace("BODY", $data['body']);			
	else
		$t->replace("BODY_ERROR", "");//*
			
	//var_dump($form_data);
	
	return $t->get();
}


/**
 * temp box parameters can be later passed in backend
 *  
 * @return unknown
 */
function box_central_article_top_rated_list(){
	
		// subsection for toprated articles --> recipies
		$TOP_RATED_ARTICLES = array('min_votes'=>0,
									'limit_top'=>10,  // limit top no
									'sub_section'=>10977); // limit to section
		// box_central_article_top_rated_list_render
		
		return box_central_article_top_rated_list_render($TOP_RATED_ARTICLES);
}


/**
 * Returns box with top rated articles
 * ither within section or subsection if set in:
 * $params array(	'min_votes'=>no, 
 * 					'limit_top'=>no, 
 * 					'subsection'=>xxx)
 *
 * @param array $params  array('min_votes'=>no,'limit_top'=>no,'subsection'=>xxx)
 * @return string html
 */
function box_central_article_top_rated_list_render($params){
	
	global $front_id, $SECTION_ID, $TEMPLATE_DIR, $FRONTEND_DIR, $site;
	global $URL_PREFIX_SEARCH, $TOP_RATED_ARTICLES;
	
	// not implemented jet
	//return "";
	
	// open template
	$t = new cls_template($TEMPLATE_DIR.$FRONTEND_DIR, "box/central/your_recipe_topten.tpl");
	$t->open();

	// we select date limit for photos
	// fe setup delay for showing nexmonth photos
	$date_delay=3;
	$m0 = 0;
	$m1	= 0;
	if (((int) date("j"))>(14+$date_delay))
		$m1 =1;
	else
		$m0 =-1;			
	
	// start to build query select toprated articles from database within		
	$sql="SELECT a.article_id, a.vote_count, a.vote_avg 
			FROM article as a "; 
	
	// if subsection is set we limit to subsection else to current section
	if (array_key_exists('sub_section', $params)){
		$sql.=" INNER JOIN article_section as s ON a.article_id=s.article_id 
		WHERE s.section_id=$params[sub_section] ";
	}else{
		$sql.=" WHERE a.section_id=$SECTION_ID ";	
	}

	// did we set also min votes
	$cote_count_min = array_key_exists('min_votes', $params) ?
					$params['min_votes']: 1;
	// if limit of hits is set
	$show_limit = array_key_exists('limit_top', $params) ?
				$params['limit_top']: 4;
	
		$sql.=" AND a.vote_count>=$cote_count_min 
			ORDER By  a.vote_avg desc, a.vote_count desc LIMIT $show_limit";

	$db = new db_sql();
	$db->query($sql);
	// start picture repeater
	$counter=0;
	$pictures="";	
	$r = $t->repeater("TOPRATED");
	// there has to be at least 10 votes	
	while ($db->next_record() && $db->f('vote_count')>$cote_count_min)
	{
		
		$pictures.=$r;
		$id = (int)$db->f("article_id");
		$article =  MicrositeArticle::open($id);
		$t->row("TOPRATED_PHOTO", html_return_media($article->frontImage, "78x52"),$pictures);
		$t->row("TOPRATED_PHOTO_LINK",$article->url, $pictures);
		$t->row("TOPRATED_PHOTO_TITLE",$article->title,$pictures);
		$t->row("DATETIME",date("d.m.y H:i",$db->f("date")),$pictures);
		
		$t->row("VOTE_COUNT",	$db->f('vote_count'), $pictures);
		
		// first we calculate average
		$vote_avg = $db->f('vote_avg');
		$vote_avg = round($vote_avg,1 );

		$t->row("VOTE_RATING",		number_format($vote_avg,1,',','.'), $pictures);
		
		// we use avg to format it in 0.5 values starting with 1 as 3_5
		$pic_avg=(10*$vote_avg)%10;
		$pic_avg=floor($vote_avg);
		if ($pic_avg!=0)
		{
			if(((10*$vote_avg)%10)>=5)
				$pic_avg.="_5";			
		}
		else
			$pic_avg=1;

		$t->row("VOTE_STARRATING",	$pic_avg, $pictures);		
		$counter++;
	}
	// there wasn't any content to show
	if ($counter==0)
		return "";
	
	$t->replace('TOPRATED', $pictures);		
	return $t->get();
	
}



?>