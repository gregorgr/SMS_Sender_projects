<?php

////
//!checks if source is allready there (by it`s name)
//it returns true if source exsists and false if there is no source by that name
//@author Samo Gabrovec (root@velenje.cx)
//@param  name   
//@return id of source or false
//@globs   none
function sql_check_source_by_name($name) {
        $db= new DB_Sql();
        $db->query("select source_id as id from source where name='$name'");
        $db->next_record();
        $id=$db->f("id");
        if ($id) {
                return $id;
        }else {
                return false;
        }
}

////
//!inserts source into DB
//@author Samo Gabrovec (root@velenje.cx)
//@param source type,source name, memo,media_id of present   
//@return id of entry
//@globs   none
function sql_insert_source($type,$name,$memo,$media_id) {
        $db= new DB_Sql();
        if (!$media_id) {
                $media_id=-1;
        }
        $db->query("insert into source (type,name,memo,media_id) values
                   ('$type','$name','$memo','$media_id')");
   
        $id=$db->insert_id();
        return $id;
}


////
//!deletes media from the database [you have to delete it from the disk some other way   
//@author Samo Gabrovec (root@velenje.cx)
//@param  media_id  
//@return true
//@globs   none
function sql_delete_media($media_id) {
        $db=new DB_Sql();
        $db->query("delete from media where media_id='$media_id'");
        return true;
}



////
//!it deletes media from all related tables that can be in//
//Why? when you delete the media from the db it cant be used by an article..
//@author Samo Gabrovec (root@velenje.cx)
//@param  media_id   
//@return true or false
//@globs   none
function sql_delete_media_from_tables($media_id) {
        if (!$media_id) {
                return false;
        }
        $db=new DB_Sql();
        $db->query("delete  from article_media  where media_id='$media_id'");
        $db->query("update source set media_id='-1' where media_id='$media_id'");
        return true;
}


////
//!get`s source from id and returns all it settings
//in an array [name,memo,type,media_name(this is the media name which is in the media table)]/
//@author Samo Gabrovec (root@velenje.cx)
//@param  source_id  
//@return array of all source data
//@globs   none
function sql_get_source($id) {
        $db=new DB_Sql();
        $db->query("select name,source.type as type,memo,source.media_id as med_id,filename from source left outer join media on 
                   (source.media_id=media.media_id) where source.source_id='$id'");
        $db->next_record();
        $out[0]=$db->f("name");
        $out[1]=$db->f("memo");
        $out[2]=$db->f("type");
        $out[3]=$db->f("filename");
        $out[4]=$db->f("med_id");
        if ($out[0]=="") {
                return false;
        }else {
                return $out;
        }


}

////
//!it deletes picture from media and sets media_id for source to -1 
//not realy used anymore since images are now separeted [if source is deleted media [image]
//stays
//@author Samo Gabrovec (root@velenje.cx)
//@param  media_id  
//@return true or false
//@globs   none
function sql_delete_pic_source($id){
        $db= new DB_Sql();
        $db->query("select  media_id from source where source_id='$id'");
        $db->next_record();
        $media_id=$db->f("media_id");
        if ($media_id) {
                $db->query("update source set media_id='-1' where source_id='$id'");
                //$db->query("delete from media where media_id='$media_id'");
                return true;
        }
        return false;

}

////
//!deletes source from source table 
//suply it wiht what else than a source_id
//@author Samo Gabrovec (root@velenje.cx)
//@param  source_id  
//@return true false
//@globs   none
function sql_delete_source($source_id) {
        $db=new DB_Sql();
        if ($source_id) {
                $db->query("delete from source where source_id='$source_id'");
                return true;
        } else {
                return false;
        }

}



////
//!it deletes source from all the tables that can be in 
//needed is source_id
//@author Samo Gabrovec (root@velenje.cx)
//@param  source_id  
//@return true or false
//@globs   none
function sql_delete_source_from_tables($source_id) {
        $db=new DB_Sql();
        if ($source_id) {
                $db->query("delete from article_source where source_id='$source_id'");
                //hmm i wont delete the sources from media or shoud i ? 

                return true;
        } else {
                return false;
        }

}


////
//!returns picture name from given source id [dont know if used anymore]
//@author Samo Gabrovec (root@velenje.cx)
//@param source id   
//@return picture name from given source id 
//@globs   none
function sql_get_pic_source($id_source) {
        $db= new DB_Sql();
        $db->query("select filename from media, source 
                   where source.media_id=media.media_id and source.source_id='$id_source'");
        $db->next_record();
        $picname=$db->f("filename");
        if ($picname) {
                return $picname;
        } 
        return false;
}
////
//!inserts media into the db and returns inserted id [needed for the naming of files]
//@author Samo Gabrovec (root@velenje.cx)
//@param  title,desc,keywords,date,enter_date,filename,source,type,media_comment  
//@return inserted id
//@globs   none
//*@edited arobida 2007-07-25, add parameter days_valid
function sql_enter_media($title,$desc,$keywords,$date,$enter_date,$filename,$source,$type,$media_comment,$section,$web_descr,$category_id,$can_archive,$subtype='',$status=3,$published_from=0,$days_valid=0) {
        global $LF_SITE_ID,$TEMP_DIR;

        if (!$published_from = (int)$published_from)
                $published_from = $enter_date;

        if (!$filename) {
                echo "ni fajla";
                return false;
        }
        $db=new DB_Sql();
	
	if (!$days_valid) $days_valid = "null";
	
	if ($type == 1) {
		//find dimension of the pic
		list($width, $height, $type_temp, $attr_temp) = getimagesize($TEMP_DIR.$filename);      
	} else {
		$width = "0";
		$height = "0";
	}
	
        $db->query("insert into media (title,description,keywords,date,enter_date,source_id,type,comment,section_id, web_description, category_id, can_archive,subtype,status,published_from,site_id,days_valid,image_width,image_height)
                   values('$title','$desc','$keywords','$date','$enter_date','$source','$type','$media_comment','$section', '$web_descr', $category_id, ".(int)$can_archive.",'$subtype',$status,$published_from,'$LF_SITE_ID',$days_valid,$width,$height)");
        $id=$db->insert_id();

        if ($id) {
                $sufix=str_file_sufix($filename);
                if (!$sufix) {
                        ///only images don`t have sufixes set!//
                        $sufix="jpg";
                }
                $filenew=$id.".".$sufix;
                //audio, video and docs are wo/ sufix
                if ($type==2 or $type==3) {
                        $filenew=explode(".",$filenew);
                        $filenew=$filenew[0];
                }
                if (($LF_SITE_ID=="#7#") || ($LF_SITE_ID=="#20#") || ($LF_SITE_ID=="#25#")) { //sz2 (damo prefix za slikce)//
                        $uniq=ereg_replace("[^[:alnum:]]","",uniqid(md5(time())));
                        for($x=0;$x<10;$x++) {
                                $u_tmp.=$uniq[$x];
                        }
                        $uniq=$u_tmp."_";
                        $filenew=$uniq.$id.".".$sufix;
                        $db->query("update media set filename='$filenew' where media_id='$id'");
                        return $filenew;
                }
        
                $db->query("update media set filename='$filenew' where media_id='$id'");

                return $id;
        }
        return false;
}
////
//!inserts article into the database
//@author Samo Gabrovec (root@velenje.cx)
//@param  title,subtitle,place,date,summary,body,keywords,enter_date,status,source_name  
//@return  inserted id
//@globs   $user
function  sql_insert_article($title,$subtitle,$place,$date,$summary,$body,$keywords,$enter_date,$status,$source_name){
        global $user;
        $db=new DB_Sql();
        $db->query("insert into article (title,subtitle,place,date,summary,body,keywords,enter_time,status,source_name,enteredby) values
                   ('$title','$subtitle','$place','$date','$summary','$body','$keywords','$enter_date','$status','$source_name','$user')");
        $id=$db->insert_id();
        return $id;

}
////
//!updates article with article_id in the db..
//@author Samo Gabrovec (root@velenje.cx)
//@param title,subtitle,place,date,summary,body,keywords,enter_date,status,source_name    
//@return 
//@globs   $user
function  sql_update_article($article_id,$title,$subtitle,$place,$date,$summary,$body,$keywords,$enter_date,$status,$source_name){
        global $user;
        $tb_article="article_edit_tmp";    
        $db=new DB_Sql();
        if ($status!="") {
                $db->query("update $tb_article set title='$title',subtitle='$subtitle',place='$place',date='$date',summary='$summary',
                           body='$body',keywords='$keywords',enter_time='$enter_date',status='$status',source_name='$source_name',changedby='$user' 
                           where article_id='$article_id'");
                return true;
        } else {
                $db->query("update $tb_article set title='$title',subtitle='$subtitle',place='$place',date='$date',summary='$summary',
                           body='$body',keywords='$keywords',enter_time='$enter_date',source_name='$source_name',changedby='$user' 
                           where article_id='$article_id'");
                return true;
        }
        return false;
}

////
//!it updates publish date to article if an global value of STATUS is set then the status of aricle 
//will be set to it
//@author Samo Gabrovec (root@velenje.cx)
//@param article_id, day_from, day_to    
//@return true or false 
//@globs   none
function sql_update_article_set_publish($article_id,$day_from,$day_to){
        global $STATUS; 
        $db=new DB_Sql();
        if (!$STATUS) {
                $STATUS=1;
        }
        $db->query("update article_edit_tmp set published_from='$day_from',published_to='$day_to',status='$STATUS'
                   where article_id='$article_id'");
     
        return true;
}


////
//!it adds section to the article id [it`s an update]
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id section    
//@return true
//@globs   none
function sql_add_section($article_id,$section) {
        $db=new DB_Sql();
        $db->query("update article_edit_tmp set section_id='$section' where article_id='$article_id'");
        // echo "update article set section_id='$section' where article_id='$article_id'";
        return true;
}

////
//!adds source to article 
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id, source_id, ??type of source????   
//@return true
//@globs   none
function sql_add_article_source($article_id,$source_id,$type) {
        $db=new DB_Sql();
        if (!$type) {
                $type='1';
        }
        $db->query("insert into article_source (article_id,source_id,type) values ('$article_id','$source_id','$type')"); 
        //echo "<br />insert into article_source (article_id,source_id,type) values ('$article_id','$source_id','$type')";
        return true;

}

////
//!It checks article by id 
//returns true or false (if article exsists or not)
//@author Samo Gabrovec (root@velenje.cx)
//@param  article id   
//@return true/false
//@globs   none
function check_article_by_id($id) {
        $db=new DB_Sql();
        $db->query("select article_id from article where article_id='$id'");
        $db->next_record();
        $id=$db->f("article_id");
        if (!$id) {
                return false;
        }
        return true;

}
////
//!it gives you array of fields that article has
//input is id of article output is array of fields that article of that id has or false if there`s no such article//
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id    
//@return array of fields[title, subtitle ...]
//@globs  $ARTICLE_IN_TEMP,$ARTICLE_IN_TMP_TIMEOUT
function sql_get_article_by_id($article_id) {
        global $ARTICLE_IN_TEMP,$ARTICLE_IN_TMP_TIMEOUT;
	global $ARTICLE_DATE_SHOWN;

        $db=new DB_Sql();
        if ($ARTICLE_IN_TEMP) {
                $db->query("select * from article_edit_tmp where article_id='$article_id'");
                $time=time();
                if (!$db->affected_rows()==0) {
                        //then it`s in and i need to check the last change date and if exceded it will re-write it//
                        $db->query("select lastchanged from article_edit_tmp where article_id='$article_id'");
                        $db->next_record();
                        $ctime=$db->f(0);
                        if (($ctime+$ARTICLE_IN_TMP_TIMEOUT)<$time) {
                                //then i rewrite it//
                                $db->query("delete from article_edit_tmp where article_id='$article_id'");
                                $db->query("insert into article_edit_tmp select * from article where article_id='$article_id'");
                                $db->query("update article_edit_tmp set lastchanged='$time' where article_id='$article_id'");
                        }
                        $db->query("select * from article_edit_tmp where article_id='$article_id'");
                } else {
                        $db->query("insert into article_edit_tmp select * from article where article_id='$article_id'");
                        $db->query("update article_edit_tmp set lastchanged='$time' where article_id='$article_id'");
                        $db->query("select * from article_edit_tmp where article_id='$article_id'");
                }
    
        } else {
                //it must be called for generating the articles for front since the variable *ARTICLE_IN_TEMP* isnt set..
                $db->query("select * from article where article_id='$article_id'");
        }
        //$db->query("select * from article where article_id='$article_id'");
        // echo "select * from article where article_id='$article_id'";
        $count=$db->affected_rows();
        $db->next_record();
        $out[0]=$db->f("title");
        $out[1]=$db->f("subtitle");
        $out[2]=$db->f("summary");
        $out[3]=$db->f("body");
	$out[4]=$db->f($ARTICLE_DATE_SHOWN);
        $out[5]=$db->f("description");
        $out[6]=$db->f("keywords");
        $out[7]=$db->f("published_from");
        $out[8]=$db->f("published_to");
        $out[9]=$db->f("section_id");
        $out[10]=$db->f("place");
        $out[11]=$db->f("source_name");
        $out[12]=$db->f("style");
        $out[13]=$db->f("has_comments");
        $out[14]=$db->f("has_banners");
        $out[15]=$db->f("article_reload");
        $out[16]=$db->f("has_votes");
        $out[17]=$db->f("article_reload_time");
        if ($count==0) {
                return false;
        }
        return  $out;
     
}

////
//!get`s article sources from article_id, returns array of source_id`s or false
//@author Samo Gabrovec (root@velenje.cx)
//@param article_id    
//@return array of sources which article holds
//@globs   none
function sql_get_article_sources($id) {
        $db=new DB_Sql();
        $db->query("select * from article_source where article_id='$id'");
        $x=0;
        while ($db->next_record()) {
                $out[$x]=$db->f("source_id");
                $x++;
        }
        if (is_array($out)) {
                return $out;
        }
        return $false;
}

////
//!it gives you sources by names
//just needs the article_id
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id   
//@return array of source names of certain article
//@globs   none
function sql_get_article_sources_names($id) {
        $db=new DB_Sql();
        $db->query("select name, url from article_source,source where article_source.article_id='$id' and
                   article_source.source_id=source.source_id");
        $x=0;
        while ($db->next_record()) {
		$out[$x]['name']=$db->f("name");
		$out[$x]['url']=$db->f("url");
                $x++;
        }
        if (is_array($out)) {
                return $out;
        }
        return $false;
}

////
//!it gives you sources by names from article array 
function sql_get_article_sources_names_from_arr($article) {
        $db=new DB_Sql();
    
        if (!is_array($article)) return "";
        if (!is_array($article["sources"])) return "";
    
        $x=0;
        foreach ($article["sources"] AS $source) {
                $sql = "SELECT name FROM source WHERE source_id=" . $source;
                $db->query($sql);
        
                if ($db->next_record()) {
			$out[$x]['name']=$db->f("name");
			$out[$x]['url']=$db->f("url");
                        $x++;        
                }
        }
        if (is_array($out)) {
                return $out;
        }
        return $false;
}

////
//!Deletes all sources which are set to certain article
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id   
//@return true
//@globs   none
function sql_delete_article_sources($article_id) {
        $db=new DB_Sql();
        $db->query("delete from article_source where article_id='$article_id'");
        return true;

}

////
//This function returns query which you can use then to search. before you call this function you must set 
//$first_part of the query exsample $first_part="select * from article where "; $keywords_array, $title_array. 
//the arrays contains search words ... you must asure that there`s no empty one !! $title_array=str_get_keywords_array($title," "); 
//<< this gives you an good array which is exploded on " " [space] and doesnt contain empty arrays etc etc..
//If you have a date in the where part then set that too other wise it`ll be without date. 
//@author Samo Gabrovec (root@velenje.cx)
//@param  none   
//@return query
//@globs  keywords_array,$title_array,$date[optional],$first_part of the query

function search_query_on_keys_title() {
        global $keywords_array;
        global $title_array;
        global $date;
        global $first_part;
        if (!$keywords_array and !$title_array) {
                return false;
        }

        if ($keywords_array and !$title_array) {
                $num=count($keywords_array);
                for($x=0;$x<$num;$x++) {
                        unset($key);
                        $key=$keywords_array[$x];
                        $sec_part.="keywords like '%$key%'";
                        if ($x<($num-1)) {
                                $sec_part.=" and ";
                        }
                }    

        }

        if (!$keywords_array and $title_array) {
                $num=count($title_array);
                for($x=0;$x<$num;$x++) {
                        unset($key);
                        $key=$title_array[$x];
                        $sec_part.="title like '%$key%'";
                        if ($x<($num-1)) {
                                $sec_part.=" and ";
                        }
                }    

        }

        if ($keywords_array and $title_array) {
                $num=count($keywords_array);
                for($x=0;$x<$num;$x++) {
                        unset($key);
                        $key=$keywords_array[$x];
                        $sec_part_k.="keywords like '%$key%'";
                        if ($x<($num-1)) {
                                $sec_part_k.=" and ";
                        }
                }    
                $num=count($title_array);
                for($x=0;$x<$num;$x++) {
                        unset($key);
                        $key=$title_array[$x];
                        $sec_part_t.="title like '%$key%'";
                        if ($x<($num-1)) {
                                $sec_part_t.=" and ";
                        }
                }    

                $sec_part=$sec_part_k." and ".$sec_part_t;

        }
        if ($date) {
                $middle_part="date >='$date' and (";
        }else {
                $middle_part=" (";
        }
        $query=$first_part.$middle_part.$sec_part.")";
        return $query;

}

/* Na osnovi podatkov po potrditvi forme za vpis keywords in titles nam vrne
pogoj za pripravo SQL stavka */
function search_query_KT() {
        global $title,$keywords,$day,$month,$year;
        global
        $title_array,$keywords_array,$date,$first_part,$sec_part,$act;

        $title_array = str_get_keywords_array($title," ");
        $keywords_array = str_get_keywords_array($keywords,",");
        if ($day) {
                $date = stringtotimestamp($day.".".$month.".".$year);
        }
        $first_part="";
        $sec_part = "";

        return search_query_on_keys_title(); 
}


////
//!it returns array. [0] is filename [1] is type of media [2] is enter_date
//needs media_id;
//@author Samo Gabrovec (root@velenje.cx)
//@param  media_id   
//@return array[0=fname of media,1 type of media,2= enter_date]
//@globs   none
function sql_get_media_filename($id) {
        global $BASE_DIR;
        include_once($BASE_DIR."/core/inc/string_functions.inc");
        //include_once "../../inc/string_functions.inc"; //!!!! Jure Pranjic 
        
	if (!$id) {
                return false;
        }

        $db=new DB_Sql();
        $db->query("select filename,type,enter_date,title,date,subtype from media where media_id='$id'");
        $count=$db->affected_rows();
        if ($count==0) {
	        return false;
        }
	
        $db->next_record();
        $fname=$db->f("filename");
        $type=$db->f("type");
        $enter_date=$db->f("enter_date");
        $date=$db->f("date");
        $title=$db->f("title");
        $title=str_clean_up($title, FALSE, FALSE, FALSE, TRUE ); 
	$subtype=$db->f("subtype");
        $out=array();
        $out[0]=$fname;
        $out[1]=$type;
        $out[2]=$enter_date;
        $out[3]=$title;
        $out[4]=$date;
	$out[5]=$subtype;
        return $out;
}

/**
 * function returnes medi_mime for asked media_id
 * @return media_mime int or false if no media_id
 * @param $media_id int
 */
function sql_get_media_mime($media_id) {
        if (!$media_id)
                return false;

        $d = new db_SQL();
        $d->query(sprintf("SELECT media_mime FROM media WHERE media_id=%d", $media_id));
        if ( $d->next_record() )
                return $d->f('media_mime');
        return false;
}

////
//! adds media to article id suplied 
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id, media_id, type 
//@return true or IN [if allready in]
//@globs   none
function  sql_media_add_to_article($article_id,$media_id,$type) {
        $db=new DB_Sql();
        $db->query("select * from article_media where article_id='$article_id' and media_id='$media_id'");
        $count=$db->affected_rows();
        if ($count>0) {
                $out="IN";
                return $out;
        }
        $db->query("select max(seqno) from article_media where article_id='$article_id' and type='$type'");
        $db->next_record();
        $MAX=$db->f(0);
        if ($MAX=="") {
                $MAX=0;
        }
        $seqno=$MAX+1;
        $db->query("insert into article_media (article_id,media_id,type,seqno,show_at) values ('$article_id','$media_id','$type','$seqno','2')");
        return true;

}

////
//!deletes media from article by id
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id, media_id   
//@return true or false
//@globs   none
function  sql_delete_media_from_article($article_id,$media_id) {
        $db=new Db_Sql();
        if ($article_id and $media_id) {
                $db->query("delete from article_media where article_id='$article_id' and media_id='$media_id'");
                return true;
        } else {
                return false; 
        }
}

////
//!adds comment to the media that has been asign to the article [it`s allways an sql update]
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id, media_id, value [comment it self]   
//@return  true
//@globs   none
function sql_article_media_update_comment($article_id,$media_id,$value){
        if (!$article_id or !$media_id) {
                return false;
        }
        $db=new DB_Sql();
        $db->query("update article_media set comment='$value' 
                   where article_id='$article_id' and media_id='$media_id'");
        return true;
}

////
//!it returs array of image tags for certain article
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id   
//@return array of tags for article
//@globs   none
function sql_get_image_tags($article_id) {
	global $TAG_PICTURE, $TAG_QUOTE;
	
        $db=new DB_Sql();
        $db->query("select * from article_media where article_id='$article_id' and type='1' ");
        $x=1;
        $y=0;
        while ($db->next_record()) {
                $out[$y]="<".$TAG_PICTURE."_".$x.">";
                $x++;
                $y++;
        }
        return $out;



}


////
//!it returs array of video tags for certain article
//@author Matija Abram
//@param  article_id   
//@return array of tags for article
//@globs   none
function sql_get_video_tags($article_id) {
	global $TAG_VIDEO;
	
        $db=new DB_Sql();
        $db->query("select * from article_media where article_id='$article_id' and type=3");
        $x=1;
        $y=0;
        while ($db->next_record()) {
                $out[$y]="<".$TAG_VIDEO."_".$x.">";
                $x++;
                $y++;
        }
        return $out;

}



////
//!it returns array of quote tags for article_id
//@author Samo Gabrovec (root@velenje.cx)
//@param article_id    
//@return array of quote tags  
//@globs   none
function sql_get_quote_tags($article_id) {
	global $TAG_PICTURE, $TAG_QUOTE;
	
	$db=new DB_Sql();
        $db->query("select * from article_quote where article_id='$article_id' order by seqno ASC");
        $x=1;
        $y=0;
        while ($db->next_record()) {
                $out[$y]="<".$TAG_QUOTE."_".$x.">";
                $x++;
                $y++;
        }
        return $out;


}

////
//!add quote to article
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id and quote   
//@return true
//@globs   none
function sql_article_add_quote($article_id,$quote) {
        if (!$article_id or !$quote) {
                return false;
        }
        $db=new DB_Sql();
        $db->query("insert into article_quote (article_id,body) values ('$article_id','$quote')");
        return true;
}
////
//!Deletes a quote from the article_quote table
//needs article_id and seqno of quote [right now it would be enough to have just seqno but you never know
//what we will change.
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id, quote_id   
//@return true
//@globs   none
function  sql_delete_media_quote($article_id,$quote_id) {
        $db= new DB_Sql();
        $db->query("delete from article_quote where seqno='$quote_id' and article_id='$article_id'");
        return true;
}

////
//!returns all related articles that article_id has
//suply it with article_id and you`ll get the array of related articles out or false if there is no any.
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id   
//@return array of related_articles for article which id you suplied or false if there aren`t any
//@globs   none
function sql_get_related_articles($article_id) {
        if (!$article_id) {
                echo "Ni ID-ja";
                return false;
        }
        $db=new DB_Sql();
        $db->query("select * from related_article where article_id='$article_id'");
        $count=$db->affected_rows();
        if ($count==0) {
                //echo "Ni resultata";
                return false;
        }
        $x=0;
        while ($db->next_record()){
                $out[$x]=$db->f("related_article");
                $x++;
        }
        return $out;
}
////
//!inserts article to the related article :)
//suply the article_id which is the article to wich many diff related articles belong to and the related_id which is
//article_id of the related article which you want to add to the article with the article_id.
//you may suply the array of articles that are allready in the db so that it checks for it and not try to 
//insert it if it`s in
//it returns true or false;
//@author Samo Gabrovec (root@velenje.cx)
//@param article_id,related_id,related[array]    
//@return true/false
//@globs   none
function sql_add_related_article($article_id,$related_id,$related) {
        if (!$article_id or !$related_id) {
                return false;
        }
        if ($related!="") {
                foreach ($related as $in_db) {
                        if ($in_db==$related_id) {
                                //then it`s in!!
                                // echo "je Ze noter!";
                                return true;
                        }
                }
        }
        $db=new DB_Sql();
        $db->query("insert into related_article (article_id,related_article) values ('$article_id','$related_id')");
        return true;
}


////
//!delets an related article
//suply the article_id of article and the related_article and it will be deleted
//@author Samo Gabrovec (root@velenje.cx)
//@param article_id,related_id    
//@return true/false 
//@globs   none
function  sql_delete_related_article($article_id,$related_id) {
        if (!$article_id or !$related_id) {
                return false;
        }
        $db=new DB_Sql();
        $db->query("delete from related_article where article_id='$article_id' and related_article='$related_id'");
        return true;

}

////
//!Id delets article from a certain story
//you need to suply it with article id and story id.
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id,story_id   
//@return  false/true
//@globs   none
function sql_delete_article_from_story($article_id,$story_id) {
        if (!$article_id or !$story_id) {
                return false;
        }
        $db= new DB_Sql();
        $db->query("delete from article_story where article_id='$article_id' and story_id='$story_id'");
        return true;
}

////
//!it returns array of stories in which article_id is in
//suply the article_id and you`re set :)
//@author Samo Gabrovec (root@velenje.cx)
//@param article_id    
//@return array of stories [storie_id] that article_id is in
//@globs   none
function sql_get_stories_articles($article_id) {
        if (!$article_id) {
                echo "Ni ID-ja";
                return false;
        }
        $db=new DB_Sql();
        $db->query("select * from article_story where article_id='$article_id'");
        $count=$db->affected_rows();
        if ($count==0) {
                //echo "Ni resultata";
                return false;
        }
        $x=0;
        while ($db->next_record()){
                $out[$x]=$db->f("story_id");
                $x++;
        }
        return $out;
}


////
//!It adds article to a story
//give it article_id story_id and array of stories which are allready in for this article 
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id,story_id stories[optional]    
//@return true/false
//@globs   none
function sql_add_stories_article($article_id,$story_id,$stories) {
        if (!$article_id or !$story_id) {
                return false;                     
        }
        if ($stories!="") {
                foreach ($stories as $in_db) {
                        if ($in_db==$story_id) {
                                return true;
                        }
                }
        }
        $db=new DB_Sql();
        $db->query("insert into article_story (article_id,story_id) values ('$article_id','$story_id')");
        return true;
}

////
//!It deletes article from article table
//needs only article_id
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id   
//@return true/false
//@globs   none
function sql_delete_article($article_id) {
        if (!$article_id) {
                return false;
        }
        $db= new DB_Sql();
        $db->query("delete from article where article_id='$article_id'");
        return true;
}

////
//!it deletes aritcle id from all the tables
//needs article id. I hope i`ll find all the accurences of article_id :)
//@author Samo Gabrovec (root@velenje.cx)
//@param  article_id   
//@return true/false
//@globs   none
function sql_delete_article_from_tables($article_id) {
        if (!$article_id) {
                return false;
        }
        $db= new DB_Sql();
        $db->query("delete from article_story where article_id='$article_id'");
        $db->query("delete from related_article where article_id='$article_id' or related_article.related_article='$article_id'");
        $db->query("delete from article_source where article_id='$article_id'");
        $db->query("delete from article_conf where article_id='$article_id'");
        $db->query("delete from article_link where article_id='$article_id'");
        $db->query("delete from article_media where article_id='$article_id'");
        $db->query("delete from article_quote where article_id='$article_id'");
        $db->query("delete from front_article where article_id='$article_id'");
        return true;
}

////
//!it adds an array of conferences to the article_id or is it the other way arround ... it adds article to 
//an  array of the conferences id`s :) never mind give it article_id and array of selected conf id`s
// and i`ll enter it all in
//@author Samo Gabrovec (root@velenje.cx)
//@param article_id,conf_id_Array    
//@return  true/false
//@globs   none
function sql_add_article_to_conference($article_id,$conf_id_array) {
     
        if (!$article_id) {
                return false;
        }

        $db= new DB_Sql();
        if (!$conf_id_array or !is_array($conf_id_array)) {
                $db->query("delete  from article_conf where article_id='$article_id'");
                //then it was not selected and i delete them all//
                return true;
        }
    
        $db->query("delete  from article_conf where article_id='$article_id'");
        foreach ($conf_id_array as $conf_id) {
                $db->query("insert into article_conf (article_id,conf_id) values ('$article_id','$conf_id')");
        
        }
        return true;

}


////
//!it returns values from the certain conference id in an array
//suply it with conference id and expect conf data from it :)
//@author Samo Gabrovec (root@velenje.cx)
//@param  conference_id   
//@return array of values that conference holds
//@globs   none
function sql_get_conference($conf_id) {
        if (!$conf_id) {
                return false;
        }
        $db= new DB_Sql();
        $db->query("select * from conf where conf_id='$conf_id'");
        $count=$db->affected_rows();
        if ($count==0) {
                //then there is no such conference//
                return false;
        }
        $db->next_record();
        $out[0]=$db->f("topic");
        $out[1]=$db->f("body");

        return $out;

}

////
//!it inserts conference 
//needed fields are topic and body
//@author Samo Gabrovec (root@velenje.cx)
//@param  topic,body status   
//@return true/false
//@globs   none
function sql_insert_conf($topic,$body,$status,$conf_type=0,$conf_date=0) {
        global $site;
        if (!$topic or !$body) {
                return false;
        }
    
        if (!$status) {
                $status=1;
        }
        $db=new DB_Sql();
        if (!$site) {
                $db->query("insert into conf (topic,body,status,conf_type,conf_date) values ('$topic','$body','$status','$conf_type','$conf_date')");
        } else {
                $db->query("insert into conf (topic,body,status,site_id,conf_type,conf_date) values ('$topic','$body','$status','#$site#','$conf_type','$conf_date')");
        }
        return true;
}
////
//!It s conference
//just topic and body and conference id are needed
function sql_update_conf($conf_id,$topic,$body,$conf_date=0) {
        if (!$conf_id or !topic or !$body) {
                return false;
        }
        $db=new DB_Sql();
        $db->query("update conf set topic='$topic',body='$body',conf_date='$conf_date' where conf_id='$conf_id'");
        return true;

}


////
//!It changes status of the conference
//suply conference id and status
//@author Samo Gabrovec (root@velenje.cx)
//@param conf_id,status    
//@return  true/false
//@globs   none
function sql_conf_change_status($conf_id,$status) {
        if (!$conf_id or !$status) {
                return false;
        }
        $db=new DB_Sql();
        $db->query("update conf set status='$status' where conf_id='$conf_id'");
        return true;
}



////
//!It deletes old search results from the HEAP table
//it uses global TIME for deletion or the value that you suply. It`s in seconds compared to NOW
//so sql_delete_records_from_heap(30) would delete all records from the heap table which have 
//been writen more than 30sec ago
//@author Samo Gabrovec (root@velenje.cx)
//@param time offset    
//@return true
//@globs  $DELETE_TIME
function sql_delete_records_from_heap($time,$table_name=FALSE) {
        global $DELETE_TIME;
    
        if (!$table_name) 
                $TMP_HEAP="query_table_temp";
        else 
                $TMP_HEAP=$table_name;

        if ($time) {
                $DELETE_TIME=$time;
        }
        if (!$DELETE_TIME) {
                return false;
        }
        $time=time();
        $time=$time-$DELETE_TIME;

        $db=new DB_Sql("","24ur_write");
        $db->query("SHOW TABLES");
        while ($db->next_record()) {
                $tb_name=$db->f(0);
                if (!strcasecmp($tb_name,$TMP_HEAP)) {
                        $db->query("delete from $TMP_HEAP where timestamp < $time");
                        return true;
                }
        }
        return false;
}

////
//!It deletes old search results from the HEAP table from 24URNIK database
//it uses global TIME for deletion or the value that you suply. It`s in seconds compared to NOW
//so sql_delete_records_from_heap(30) would delete all records from the heap table which have 
//been writen more than 30sec ago
//@author Samo Gabrovec (root@velenje.cx)
//@modified by Marko 
//@param time offset    
//@return true
//@globs  $DELETE_TIME
//@globs  $DB24URNIK
function sql_delete_records_from_heap_24urnik($time) {
        global $DB24URNIK;
        global $DELETE_TIME;
    
        $TMP_HEAP="query_table_temp";
    
        if ($time) {
                $DELETE_TIME=$time;
        }
        if (!$DELETE_TIME) {
                return false;
        }
        $time=time();
        $time=$time-$DELETE_TIME;

        $db=new DB_Sql("","24urnik_write");
        $db->query("SHOW TABLES");
        while ($db->next_record()) {
                $tb_name=$db->f(0);
                if (!strcasecmp($tb_name,$TMP_HEAP)) {
                        $db->query("delete from $TMP_HEAP where timestamp < $time");
           
                        return true;
                }
        }
        return false;
}



////
//!it changes the "status" of how the media is shown on front if at all
//@author Samo Gabrovec (root@velenje.cx)
//@param article_id   
//@return true
//@globs  media_on_front

function sql_put_media_on_front($article_id){
        global $media_on_front;
        $db3= new DB_Sql();
        $db= new DB_Sql();
    
        //echo "a:".$article_id." m: ".$media_on_front;
        if ($article_id) {  //first i strip all 1 bits of the media`s//
                // echo "vnasam 2"; 
                $db3->query("select (show_at & 1) as bit,article_id,media_id,show_at from article_media where article_id='$article_id' 
                            and type='1'");
                while ($db3->next_record()) {
                        $bit=$db3->f("bit");
                        if ($bit=="0") {
                                //then the front show bit  is not set
                                continue;
                        } else {
                                $media_id=$db3->f("media_id");
                                $show_at=$db3->f("show_at");
                                $show_at=$show_at-1; //i remove the 1 bit//
                                $db->query("update article_media set show_at='$show_at' where article_id='$article_id' and media_id='$media_id'");
                        }
                }
        }
        if (is_array($media_on_front) and $article_id) {
                foreach ($media_on_front as $media_id) 
                        $db3->query("update article_media set show_at=show_at +1 where article_id='$article_id' and media_id='$media_id' and type='1'");
        }
}


////
//!it changes the "status" of how the media is shown in this case to a bigger size
//@author Samo Gabrovec (root@velenje.cx)
//@param article_id   
//@return true
//@globs  media_is_big

function sql_media_is_big($article_id){
        global $media_is_big;
        $db= new DB_Sql(); 
        $db2= new DB_Sql();
        //first i remove from all media that is entered to this article the 4 bit
        $db->query("select (show_at & 4) as bit,article_id,media_id,show_at from article_media where article_id='$article_id' 
                   and type='1'");
        while ($db->next_record()) {
                $bit=$db->f("bit");
                if ($bit=="0") {
                        //then the front show bit  is not set//
                        continue;
                } else {
                        $media_id=$db->f("media_id");
                        $show_at=$db->f("show_at");
                        $show_at=$show_at-4; //i remove the 4 bit//
                        $db2->query("update article_media set show_at='$show_at' where article_id='$article_id' and media_id='$media_id'");
                
                }
        }
        //now if i have anything selected i add it the 4 bit//
        if (is_array($media_is_big) and $article_id) {
                foreach ($media_is_big as $media_id) {
                        //echo "<br> dodajam 4 h medi_id".$media_id;
                        $db->query("update article_media set show_at=show_at +4 where article_id='$article_id' and 
                                   media_id='$media_id' and type='1'");
                        //and to create the image ...
                        image_create_extra_size($media_id);
                }
       
        }
}


////
//!It returns all wheater data for a day
//give it the timestamp
//@author Samo Gabrovec (root@velenje.cx)
//@param timestamp   
//@return array of wheater data
//@globs  none
function sql_get_wheater_data($timestamp) {
        if (!$timestamp) {
                return false;
        }
        $db= new DB_Sql();
        $db->query("select * from wheater where timestamp='$timestamp'");
        $db->next_record();
        $out[0]=$db->f("timestamp");
        $out[1]=$db->f("low");
        $out[2]=$db->f("high");
        $out[3]=$db->f("image");
        return $out;
}

////
//!it update`s an article and sets the style
//
//@author Samo Gabrovec (root@velenje.cx)
//@param ar_id,style   
//@return true/false
//@globs  none
function sql_add_style_to_article($article_id,$style){
        $db=new DB_Sql();
    
        if (!$article_id or !$style) {
        
                return false;
        }
        $db->query("update article_edit_tmp set style='$style' where article_id='$article_id'");
        return true;

}


////
//!it returns conference data (main) 
//
//@author Samo Gabrovec (root@velenje.cx)
//@param conference_id,
//@return array of data 0=title,1=body or false
//@globs  none
function sql_get_conf_data($conf_id) {

        $conf_id=ereg_replace("[^[:digit:]]","",$conf_id);
        if ($conf_id) {
                $db=new DB_Sql();
       
                $db->query("select * from conf where (conf.status='3' or conf.status='9') and conf_id='$conf_id'");
                $count=$db->affected_rows();
                if ($count==0) {
                        return false; 
                }
                $db->next_record();
                $out[0]=$db->f("topic");
                $out[1]=$db->f("body");
                $out[2]=$conf_id;
                $out[3]=$db->f("media_id");
                return $out;
        }
        return false;
}

////
//!it enters post into the conference 
//
//@author Samo Gabrovec (root@velenje.cx)
//@param conf_id,author_name,timestamp,message
//@return true/false
//@globs  none
function sql_conference_add_post($conf_id,$author_name,$timestamp,$message,$ip,$id=0){
   
        if ($conf_id and $message) {
                $db=new DB_Sql("","24ur_write");
                $db->query("select post_id from conf_post where body='$message' limit 1");
                if ($db->next_record()) return(3);
        
                $db->query("insert into conf_post (conf_id,author_name,enter_date,body,status,ip,author_id) values
			('$conf_id','$author_name','$timestamp','$message','1','$ip',$id)");
                return(2);
        } 
    
        return false;
}
////
//!it changes status of conference post 
//
//@author Samo Gabrovec (root@velenje.cx)
//@param post_id,status
//@return true/false
//@globs  none
function sql_conf_post_change_status($post_id,$status) {
        if (!$post_id or !$status) {
                return false;
        }
        $db=new DB_Sql();
        $db->query("update conf_post set status='$status' where post_id='$post_id'");
        return true;
}


////
//!it adds an array of chats to the article_id or is it the other way arround ... it adds article to 
//an  array of the chats id`s :) never mind give it article_id and array of selected chat id`s
// and i`ll enter it all in
//@author Samo Gabrovec (root@velenje.cx)
//@param article_id,chat_id_Array    
//@return  true/false
//@globs   none
function sql_add_article_to_chat($article_id,$chat_id_array) {
     
        if (!$article_id) {
                echo "Ni clank id-ja";
                return false;
        }

        $db= new DB_Sql();
        if (!$chat_id_array or !is_array($chat_id_array)) {
                $db->query("delete  from article_chat where article_id='$article_id'");
                //then it was not selected and i delete them all//
                return true;
        }
    
        $db->query("delete  from article_chat where article_id='$article_id'");
        foreach ($chat_id_array as $chat_id) {
                $db->query("insert into article_chat (article_id,chat_id) values ('$article_id','$chat_id')");
        
        }
        return true;

}

////
//!It get`s the tv guides dates for wich we have them
//Note: if you have guide for 3 tv`s and one has it entered for a week, the other two for a couple
//of days more... It will show all guides just for a week.
//@author Samo Gabrovec (root@velenje.cx)
//@param  TV_id, tv_name, from date    
//@return array of dates and filenames
//@globs  $DBTVGUIDES
function sql_get_tv_guide_dates($tv,$tv_name,$timestamp=false) {
        global $DBTVGUIDES;
        
        $HOUR= date("H");
        $SIMULATING = false;
        
        $db=new DB_Sql("",$DBTVGUIDES);
        if (!$timestamp && $HOUR>=4) {
                $timestamp=todaytimestamp("");
        }
        // ce je ura manj kot 4, simuliramo, da smo se v prejsnjem dnevu
        else if (!$timestamp && $HOUR<4) {
                $timestamp=mktime(0,0,0,date("m"),date("d")-1,date("Y"));
                $SIMULATING = true;
        }
	$next_week = $timestamp + 7*24*60*60;

        if ($tv and $tv_name) {
                $tv_name=ereg_replace("[^[:alnum:]]","",$tv_name);
                $tv_name=strtolower($tv_name);
                $tv_name=$tv_name."_##datum##.php";

                $db->query("select datum from oddaja where tv='$tv' and datum>=$timestamp and datum <= $next_week and status=3 group by datum");

                while ($db->next_record()){
                        $date=$db->f(0);
                        $dates=$dates."#".$date; //to know which ones to generate ..
                        $files=$files."#".str_replace("##datum##",$date,$tv_name);
                        $slodan=date_slo_day_short(date("D",$date));
                        $datum=date("j.n",$date);
                
                        if (
                            ( !$SIMULATING && date("j.n.Y")==date("j.n.Y",$date) ) ||
                            (  $SIMULATING && date("j.n.Y",$timestamp) == date("j.n.Y",$date) )
                           )
                        {
                                $date_names=$date_names."#<b>Danes</b>";
                        } else {
                                $date_names=$date_names."#<b>".$slodan."</b> ".$datum;
                        }
                }
                $dates[0]=" ";
                $files[0]=" ";
                $date_names[0]=" ";
                ///
                $dates=trim($dates);
                $files=trim($files);
                $date_names=trim($date_names);
                ///
                $out[0]=$dates;
                $out[1]=$files;
                $out[2]=$date_names;
                return $out;
        }
        return false;
}

////It moves article from the temporary table to the real one and writes who did it.. 
//!Suply the article id
//@author Samo Gabrovec (root@velenje.cx)
//@param  none    
//@return true/false
//@globs  none
function sql_move_article_from_tmp_table($article_id) {
        global $user;
    
        if (!$article_id) {
                return false;
        }
        $db=new DB_Sql();
        $db->query("select * from article_edit_tmp where article_id='$article_id'");
        if ($db->affected_rows()==0) {
                return false;
        }
        //now let`s make a query :))
        $q="update article set ";
        $db->next_record();
        $count=count($db->Record);
        $x=0;
        foreach ($db->Record as $k => $v) {
                if ($x<($count/2)) {
                        //echo "<br>".$k." : ".$v;
                        for($y=0;$y<3;$y++) {
                                $v=stripslashes($v);
                        }
                        $v=addslashes($v);
                        if (stristr($k,"generated")) {
                                //ce je polje generated potem dam na value na 0//
                                $q.=$k."="."'0',";
                        } else {
                                $q.=$k."='".$v."',";
                        }
                        $x++;
                } else {
                        break;
                }

        }
        $lenght=strlen($q);
        $q[$lenght-1]=" ";
        $q.=" where article_id='$article_id'";
        $db->query($q);
        //now i delete the old one from temp table...
        $db->query("delete from article_edit_tmp where article_id='$article_id'");
    
        //now write who did the changes..
        $db->query("INSERT INTO `article_history` ( `id` , `user` , `article_id` , `date` ) 
                   VALUES (
                   '', '$user', '$article_id', '".time()."')");
        return true;
}

////
//!it adds location for 24urnik. if allready in it will just return the id out
//@author Samo Gabrovec (root@velenje.cx)
//@param  parent_id(if empty then it`s the parent),name,and long name     
//@return id
//@globs  $DB24URNIK
function sql_add_location($parent_id,$name,$long_name) {
        global $DB24URNIK;
    
        if (!$name or !$long_name) {
                return false;
        }

        if (!$parent_id) {
                $parent_id=0;
                $type=1;
        } else {
                $type=2;
        }

        $db=new DB_Sql("",$DB24URNIK);
        $name=str_clean_up($name);
        $long_name=str_clean_up($long_name);
        //Spremenil Marko    $db->query("select * from location where name='$name' and long_name='$long_name' and parent_location='$parent_id'");
        $db->query("select * from location where name='$name'");

        $st=$db->affected_rows();
        if ($st==0) {
                $db->query("insert into location (name,long_name,parent_location,type) values ('$name','$long_name','$parent_id','$type')");
                $out_id=$db->insert_id();
        } else {
                $db->next_record();
                $out_id=$db->f("location_id");
        }

        return $out_id;
}
////
//!It adds an event to 24urnik, and returns it`s id
//@author Samo Gabrovec (root@velenje.cx)
//@param  location_id,title,subticle,url,summary,body,type,keywords,parent,status,gtype,optiona force write     
//@return id
//@globs  $DB24URNIK
function sql_add_event($title,$subtitle,$url,$summary,$body,$type,$keywords,$parent,$status,$gtype,$force=false) {
        global $DB24URNIK;    
        if (!$title or !$type) {
                return false;
        }
        $db=new DB_Sql("",$DB24URNIK);
        $title=str_clean_up($title);
        $subtitle=str_clean_up($subtitle);
        $summary=str_clean_up($summary);
        $body=str_clean_up($body);
        if (!$force) {
                $db->query("select event_id from event where title='$title'");

                $st=$db->affected_rows();
                if ($st==0) {
                        $db->query("insert into event (title,subtitle,url,summary,body,keywords,parent,status,global_type) values ('$title','$subtitle',
                                   '$url','$summary','$body','$keywords','$parent','$status','$gtype')");
                        $out_id=$db->insert_id();
                } else {
                        $db->next_record();
                        $out_id=$db->f("event_id");
                }
        } else {
                //force is on and i insert it even tho it might be allready in (dunno why would this be good for)
                $db->query("insert into event (title,subtitle,url,summary,body,keywords,parent,status,global_type) values ('$title','$subtitle',
                           '$url','$summary','$body','$keywords','$parent','$status','$gtype')");
                $out_id=$db->insert_id();
        }
        return $out_id;
}

////
//!It inserts prices and start times for an event (24urnik)
//@author Samo Gabrovec (root@velenje.cx)
//@param  location_id,event_id,price_array,time_array     
//@return true/false
//@globs  $DB24URNIK
function sql_add_time_price($location_id,$event_id,$prices,$times,$timestamp) {
        global $DB24URNIK;
        if (!$location_id and !$event_id and !$prices and !$times) {
                //  echo "Pri cenah neki manjka";
                return false;
        }
        echo "cene : ". $p_count=count($prices) ."\n";
        echo "time : ". $t_count=count($times)."\n";
        echo "timestamp: ".date("d.n.y h:i",$timestamp)."\n";
        echo "location/event: $location_id / $event_id \n";
        if ($p_count!=$t_count) {
                // echo "Razlicno stevilo casa in cen";
                return false;
        }
   
        $db=new DB_Sql("",$DB24URNIK);
        $db2=new DB_Sql("",$DB24URNIK);
        //first i`ll just delete all prices and times for this event//
        $db->query("select schedule_id from schedule where location_id='$location_id' and event_id='$event_id' and from_unixtime(time,'%Y%m%d')=from_unixtime($timestamp,'%Y%m%d')");
        while ($db->next_record()) {
       
                $db2->query("delete from schedule where schedule_id='".$db->f(0)."'");
                $db2->query("delete from price where schedule_id='".$db->f(0)."'");
        }
        for ($x=0;$x<$t_count;$x++) {
                unset($time);
                echo "\n na zacetku:: ".$times[$x];
                $h=explode(":",$times[$x]);
                $hour=$h[0];
                $min=$h[1];
                $time=$timestamp+($hour*3600)+($min*60);
                echo "<br>\n".date("h:i",$time);
                $db->query("insert into schedule (time,event_id,location_id) values ('$time','$event_id','$location_id')");
                $id=$db->insert_id();
                $db->query("insert into price (price,schedule_id) values ('$prices[$x]','$id')");
        }
        return true;

}
////
//!it adds people in the db (for 24urnik)
//@author Samo Gabrovec (root@velenje.cx)
//@param  event_id,role,names_array     
//@return true/false
//@globs  $DB24URNIK
function sql_add_people_roles($event_id,$role,$names) {
        global $DB24URNIK;
        if (!is_array($names) or !$event_id or !$role) {
                return false;
        }
        $db=new DB_Sql("",$DB24URNIK);
        $x=0;
        foreach ($names as $name) {
                $db->query("select artist_id from artist where name='$name'");
                $db->next_record();
                if ($id_tmp=$db->f(0)=="") {
                        //ga vnesem//
                        $db->query("insert into artist (name) values('$name')");
                        $id[$x]=$db->insert_id();
                        $x++;
                } else {
                        $id[$x]=$db->f(0);
                        $x++;
                }
        } //end foreach//
        //now i have all actors id`s and i just link them if needed.What do i mean if needed? well i`m going to check 
        //if they are all ready entered for this event and if i wont delete them and add them back each time//
        $db->query("select artist_id from role where event_id='$event_id' and artist_role='$role'");
        // echo "\nselect artist_id from role where event_id='$event_id' and artist_role='$role'";
        if ($db->affected_rows()>0) {
                $id_db=array();
                $x=0;
                while($db->next_record()) {
                        $id_db[$x]=$db->f(0);
                        $x++;
                }
        }
        $c1=count($id);$c2=count($id_db);
        if ($c1!=$c2) {
                //then i just delete them since the count of each doesnt match//
         
                echo "\n<<<<<<<<<<<<<<$c1 brisem $c2>>>>>>>>>>>>>>>>>>>>>\n";
                $db->query("delete from role where event_id='$event_id' and artist_role='$role'");
                $found=false;
        } else {
                //the counts match, i`ll check if they realy "match"//
                foreach ($id as $new) {
                        $found=false;
                        foreach($id_db as $old) {
                                if ($new==$old) {
                                        $found=true;
                                }
                        }
                        //if the found is still set to false i breack the for and delete them from db//
                        if ($found!=true) {
                                echo "\n<<<<<<<<<<<<<<brisem>>>>>>>>>>>>>>>>>>>>>\n";
                                $db->query("delete from role where event_id='$event_id' and artist_role='$role'");
                                break;
                        }
                }
        }
        ///if all matched the found is still set to true other wise it`s set to false, and if false i finaly enter new ones in...
        if ($found!=true) {
                //echo  "\n".str_repeat("><",5)."Vstavljam".str_repeat("><",5);
                for($x=0;$x<count($id);$x++) {
                        $db->query("insert into role (artist_role,artist_id,event_id) values ('$role','$id[$x]','$event_id')");
                        //echo "\ninsert into role (artist_role,artist_id,event_id) values ('$role','$id[$x]','$event_id')";
                }
        }
        return true;
}

////
//!It adds duration to the event
//@author Samo Gabrovec (root@velenje.cx)
//@param  event_id,role,names_array     
//@return true/false
//@globs  $DB24URNIK
function sql_add_event_duration($event_id,$dolzina) {
        global $DB24URNIK;
   
        $db=new DB_Sql("",$DB24URNIK);   
        if (!$event_id or !$dolzina) {
                return false;
        }

        $db->query("update schedule set duration='$dolzina' where event_id='$event_id'");
        return true;
}

  
////
//!It adds event types  to the event 
//@author Samo Gabrovec (root@velenje.cx)
//@param  event_id,$event_type_array   
//@return true/false
//@globs  $DB24URNIK
function sql_add_event_type($event_id,$zanr,$parent_type= false) {
        global $DB24URNIK;
        if (!$parent_type) {
                $parent_type=1;
        }

        if (!$event_id) {
                return false;
        }
        //ce nima zanra film bo pac film//
        if (!$zanr) {
                $zanr=array();
                array_push($zanr,"Film");
        }
        $db=new DB_Sql("",$DB24URNIK);
        //first i delete all the types for this event in the db//
        $db->query("delete from event_type where event_id='$event_id'");
        //i get the parent name//
        $db->query("select name from type where type_id='$parent_type'");
        $c=$db->affected_rows();
        if ($c==0) {
                echo "No parent type with id $parent_type";
                return false;
        }
        $db->next_record();
        $parent_name=$db->f(0);
     
        foreach($zanr as $type_name) {
                $db->query("select * from type where name='$type_name'");
                $c=$db->affected_rows();
                if ($c!=0) {
                        $db->next_record();
                        $type_id=$db->f("type_id");
                        $db->query("insert into event_type (event_id,type_id) values('$event_id','$type_id')");
                } else  {
                        //then it was not found and i need to enter it in first//
                        $long_name=$parent_name." - ".$type_name;
                        $name=$type_name;
                        $db->query("insert into type (parent_type,name,long_name) values ('$parent_type','$name','$long_name')");
                        // echo "<br> insert into type (parent_type,name,long_name) values (".$parent_type.",".$zanr[$x].",".$parent_name." - ".$zanr[$x].")";
                        $type_id=$db->insert_id();
                        //echo "<br>ty_id ha ha ha  VNESEN:".$type_id;
                        $db->query("insert into event_type (event_id,type_id) values('$event_id','$type_id')");
                }
                //now to unset the name and long_name//
                unset($long_name);
                unset($name);
     
        }
}
////
//!It returns array which holds in the source id`s and names
//@author Samo Gabrovec (root@velenje.cx)
//@param  optional status
//@return Array[0]=names,Array[1]=ids
//@globs  $MAKE_EMPTY
function sql_get_source_array($status=false) {
        global $MAKE_EMPTY;
        $db=new DB_Sql();

        if (!isset($status)) {
                $status=3;
        }
        if ($status=="all") {
                $db->query("select source_id,name from source");
        } else {
                $db->query("select source_id,name from source where status=$status");
        }
        $out=array();
        if ($MAKE_EMPTY) {
                $x=1;
                $out[0][0]="-1";
                $out[1][0]="-=Prazno=-";
        } else {
                $x=0;
        }
        $c=$db->affected_rows();
        if ($c==0) return false;
        while ($db->next_record()) {
                $out[0][$x]=$db->f("source_id");
                $out[1][$x]=$db->f("name");
                $x++;
        }
        return $out;
}


////
//!returns dvodimenzionalni array: 1: id-ji kategorij, 0: imena kategorij
//@author Ratko
//@return Array[0]=names,Array[1]=ids
//@globs  $MAKE_EMPTY
function sql_get_categories_array() {
        global $MAKE_EMPTY;
        $db=new DB_Sql();

        $db->query("select category_id,name from media_categories");
        $out=array();

        $x=1;
        $out[0][0]="-1";
        $out[1][0]="-=Prazno=-";

        $c=$db->affected_rows();

        if ($c==0) return $out;

        while ($db->next_record()) {
                $out[0][$x]=$db->f("category_id");
                $out[1][$x]=$db->f("name");
                $x++;
        }
    
        return $out;
    
}

////
//!Vrne dvodimenzionalni array: 1: id-ji sekcij, 0: imen sekcije (slovenia,sport)
//@author Samo
//@return Array[0]=names,Array[1]=ids
//@globs  $MAKE_EMPTY
function sql_get_section_array() {
        global $MAKE_EMPTY;
        global $site;
    
    
        $db=new DB_Sql();
        if (!$site) {
                $db->query("select section_id,topic from section where type='1 and can_select=1'");
        } else {
                $db->query("select section_id,topic from section where type='1' and site_id like '%#$site#%' and can_select=1");
        }
       
        $out=array();

        $x=1;
        $out[0][0]="-1";
        $out[1][0]="-=Prazno=-";

        $c=$db->affected_rows();

        if ($c==0) return $out;

        while ($db->next_record()) {
                $out[0][$x]=$db->f("section_id");
                $out[1][$x]=$db->f("topic");
                $x++;
        }
    
        return $out;
    
}

?>
