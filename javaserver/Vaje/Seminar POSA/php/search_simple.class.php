<?PHP


/**
 * search simple class 
 * 
 * 
 * @author gregor.grajzar@pop-tv.si, 14.07.2008
 * @version 1.0 
 * @example 
 *<code>
 * 	$page=1;
 *  $search = new search_simple(1, $my_search_text);
 * 
 * 	$search->search_execute(1,$domedia);
 *  $mycount= $search->count_results();
 * 
 * 	$search->sort_by_date("ASC"); //if we need a diferent sort
 * 	$search->get_results($page);
 * 	
 * 	$sdb=$search->DB();
 * 	while ($sdb->next_record())	{
 * 
 * 		// workout your results
 *  }
 * </code>
 */

class search_simple{
	private $debug=false;
	private $ok=false;
	private $site;
	private $sectionid='';
	private $mediatype='';
	
	private $db;
	private $search_id;
	private $temp_table = "query_table_temp_searchtest";
	private $insert=' (uniq_id, id, title,  section_id, timestamp, date, type, score) ';
	private $w;
	private $qtime;
	
	// will delete records from this session in destructor --> default of
	private $delete_records=false;
	private $results_perpage=10;
	private $sort_me='';
	private $error="";
	
	/**
	 * @var int tmeout in minutes default=30 min
	 */
	private $data_timeout=15;
	

	
	/**
	 * public constructor
	 *
	 * @param unknown_type $site_id
	 * @param string $words
	 * @param string $search_id
	 */
	public function __construct($site_id, $words, $search_id="")
	{
		$this->site = &$site_id;
		$search = array
		(
			'@<script[^>]*?>.*?</script>@si', # strip out javascript
			'@<style[^>]*?>.*?</style>@siU'  # strip out style
		);  

		$words = preg_replace($search, '', $words);
		$words = strip_tags($words);
		$words = trim($words);
		$this->ok = strlen($words)>0;
		
		if($this->ok && strlen($search_id)>0){
			$this->db		=new DB_Sql();
			$this->w		=&$words;
			$this->search_id=$search_id;
		}
		else if($this->ok){
			
			$this->db		=new DB_Sql();
			$this->w		=&$words;
			list($usec, $sec) = explode(' ', microtime());
        	srand((float) $sec + ((float) $usec * 100000));
			$this->search_id= md5(uniqid(rand(),1));
		}
		else
			$error="Search string to short!";
		$this->qtime = $this->microtime_float();
		
		if ($this->debug)
			echo(": $this->ok!<br />");
	}
	
	
	/**
	 * timeout for temp data
	 * 
	 * @param int $minutes default
	 */
	public function Data_timeout($minutes=15)
	{
		$this->data_timeout=(int)$minutes;
	}
	
	public function Debug($bool)
	{
		$this->debug= $bool;
		if ($this->debug)
			echo("query: $this->w!<br />");
	}
	
	/**
	 * Returns result count
	 *
	 * @param string $type
	 * @return int
	 */
	public function count_results($type='')
	{
		$sql="SELECT count(1) as counter FROM query_table_temp_searchtest 
				WHERE uniq_id='$this->search_id'";
		if($type) $sql .= " AND type='".$type."'";
		if ($this->debug)
				echo("executing: <br />$sql<br />");
		$this->db->query($sql);
		if($this->db->next_record())
		{	
			if ($this->debug)
				echo("counter: ".$this->db->f("counter")."<br />");
			return (int)$this->db->f("counter");
		}
		else
		{
		 	return 0;
		}
	}
		
	/**
	 * creates temp search table if needed
	 *
	 */
	private function create_temp_table()
	{
		
	}
	
	
	/**
	 * returns pointer to DB_Sql object
	 * @return DB_Sql object
	 */
	public function DB()
	{
		return $this->db;
	}
	
	
	/**
	 * Enter description here...
	 *
	 * @return unknown
	 */
	public function delete_temp_data(){
		$sql="DELETE FROM query_table_temp_searchtest WHERE uniq_id='$this->search_id'";
		return $this->db->query($sql);
	}
	
	
	private function delete_old_temp_data()
	{
		//$sql="DELETE FROM query_table_temp_searchtest WHERE uniq_id='$this->search_id'";
		//return $this->db->query($sql);		
	}
	
	/**
	 * returns error message
	 * @return string
	 */
	public function get_errorr()
	{
		return $this->error;	
	}
	
	
	/**
	 * selects resultset
	 * returns result of query
	 * 
	 * @param int $page
	 * @param bool delete_records_afte
	 * @return int
	 */
	public function get_results($page, $delete_records_after=false)
	{
		// will delete records from this session in destructor --> default of
		$this->delete_records=$delete_records_after;
		$section = ($this->sectionid?"AND section_id=$this->sectionid ":'');
		
		// we dont have sort order we set default sort
		if (!$this->sort_me)
			$this->sort_by_score();
			
		$sql="SELECT id, title,section_id, date, type, score 
				FROM $this->temp_table 
				WHERE uniq_id='$this->search_id' $section
				ORDER by $this->sort_me
				LIMIT ".($this->results_perpage*($page-1)).", $this->results_perpage";
		//($secid?"and section_id=$secid ":"")
		return $this->db->query($sql);
	}
	
	
	/**
	 * returns section_id sql condition
	 *
	 * @param string $alias
	 * @return string sql condition
	 */
	private function get_section_condition($alias='')
	{		
		if ($alias)
			$alias.='.';
			
		if ($this->sectionid)
		{
			// sectionid is array
			if (is_array($this->sectionid))
			{
				$b=false;
				foreach ($this->sectionid as &$section)
				{
					if ($b)$sql.=" OR ";
					$sql.=$alias."section_id='$section'";	
					$b=true;
				}
				return " AND ($sql)";
			}
			else
			{
				return ' AND '.$alias."section_id='$this->sectionid'";
			}
		}
		else
			return '';
	}
	
	
	/**
	 * This will insert query into temp table
	 *
	 * @param sql string $sql
	 * @return bool qury_ok
	 */
	private function insert_sql(&$sql)
	{
		
		$q='INSERT INTO '.$this->temp_table.' '.$this->insert.$sql;
		if ($this->debug)
			echo("executing: <br />$q<br />");
		$this->ok= $this->db->query($q);
		if ($this->debug)
			echo("done: $this->ok!<br />");
		return $this->ok;
	}
	
	
	/**
	 * lets set media type
	 * default 3 & 9
	 *
	 * @param var $type
	 */
	public function media_type(&$type)
	{
		$this->mediatype=$type;
	}
	
	
	private function microtime_float()
	{
    	list($usec, $sec) = explode(" ", microtime());
    	return ((float)$usec + (float)$sec);
	}
	
	
	public function mili_seconds()
	{
		return $this->qtime;
	}
	
	
	/**
	 * Sets results per page default is 10
	 * @param int $count
	 */
	public function results_per_page($count){
		$this->results_perpage=$count;		
	}
	
	
	/**
	 * set section id to limit serch within some section
	 * @param var $id
	 */
	public function section_id(&$id)
	{
		$this->sectionid=$id;
	}
	
	/**
	 * creates select search str articles
	 * @return sql query string
	 */
	private function select_articles()
	{
		if ($this->sectionid)
			$sql= "AND article.section_id=".$this->sectionid;
		
		$sql=" SELECT '$this->search_id' as uniq_id,
			article_id as id,	title, article.section_id, CURRENT_TIMESTAMP timestamp,  date, 1 as type, 
			MATCH (title, keywords) AGAINST ('$this->w') AS score 
			FROM article
			LEFT JOIN section on article.section_id=section.section_id
			WHERE section.site_id like '%#$this->site#%' $sql and 
			(article.status='3' or article.status='9') and 
				MATCH (title,keywords) AGAINST ('$this->w')";
		return $sql;
	}
	
	
	/**
	 * creates select search str for media
	 * @return sql query string
	 */
	private function select_media()
	{
		$media_section_sql='';
		if ($this->mediatype)
		{
			// media type is array
			if (is_array($this->mediatype))
			{
				$b=false;
				foreach ($this->mediatype as &$media)
				{
					if ($b)$media_section_sql.=" OR";
					$media_section_sql.=" type='$media'";	
					$b=true;
				}
				$media_section_sql="($media_section_sql)";
			}
			else
			{
				$media_section_sql="type = '$this->mediatype'";
			}
		}
		else
			$media_section_sql="(type='1' or type='3')";
		
		if ($this->sectionid)
			$media_section_sql.= "AND article.section_id=".$this->sectionid;
			//(case when ISNULL(section_id)  then 0 else section_id end) as  section_id
		$sql="SELECT '$this->search_id' as uniq_id, media_id as id, title, 
			(CASE WHEN ISNULL(section_id)  THEN 0 ELSE section_id end) as  section_id,
			CURRENT_TIMESTAMP timestamp, date, type,  
			MATCH (title,description,keywords) AGAINST ('$this->w') AS score
			FROM media 
			WHERE site_id like '%#$this->site#%'
				AND (status='3' OR status='9')
				AND $media_section_sql
				AND MATCH (title,description,keywords) AGAINST ('$this->w')";		
		return $sql;
	}
	
	/**
	 * creates select search str forum
	 * @return sql query string
	 */
	private function select_forum()
	{
		$weught=2;
		$sql ="SELECT '$this->search_id' as uniq_id, 
			fp.post_id as id, ft.topic_id as parent_id,  ft.total_group, 
			date,	7 as type,	CURRENT_TIMESTAMP timestamp, 
			(($weught*ft.title_score) + (MATCH (fp.text) against ('$this->w'))) as score
			FROM forum_post fp INNER JOIN  (
				SELECT 
					forum_topic.topic_id, forum_topic.title,  forum_topic.post_count as total_group,
      				MATCH (forum_topic.title) against ('$this->w') as title_score
      			FROM forum_topic, forum, forum_category
				WHERE forum.forum_id = forum_category.forum_id
					AND forum_topic.cat_id = forum_category.cat_id
					AND forum.site_id = '$this->site' AND forum_topic.status = 3
           	 		AND MATCH (forum_topic.title) AGAINST('$this->w')
            	) AS ft
           ON ft.topic_id = fp.topic_id
           WHERE fp.status = 3
            	and MATCH (fp.text) against ('$this->w')";

			
	//	if ($this->sectionid)
	//		$sql= "AND article.section_id=".$this->sectionid;
		
		$sql="";
		
		return $sql;		
	}
	
	
	/**
	 * 	trigers search inserts into temptable
	 * 	one by one
	 *	treturns true if search ok
	 * 
	 * @param bool $do_search_articles
	 * @param bool $do_search_media
	 * @param bool $do_search_forum
	 * @return bool
	 */
	public function search_execute($do_search_articles=1, $do_search_media=0,$do_search_forum=0 )
	{
		if($this->ok)
		{
			$b=0;
			if ($do_search_articles)
				$b=$this->insert_sql($this->select_articles());
			if ($do_search_media)
				$b=$this->insert_sql($this->select_media());
			if ($do_search_forum)
				$b=$this->insert_sql($this->select_forum());
			$this->qtime = (int)(1000*($this->microtime_float()-$this->qtime));
			return 1;
		}
		else
			return 0;
	}

	
	/**
	 * 	trigers search insert into temptable
	 * 	instntly by UNION query
	 *	returns true if search ok
	 * 
	 * @param bool $do_search_articles
	 * @param bool $do_search_media
	 * @param bool $do_search_forum
	 * @return bool
	 */
	public function search_execute_once($do_search_articles=1, 
					$do_search_media=0,$do_search_forum=0 )
	{	
		
		//echo("ok: $this->ok! <br />do_search_articles:$do_search_articles <br />do_search_media:$do_search_media<br />");
		if($this->ok)
		{
			
			$sql='';
			// let's prepare UNION sql string for inserting serach
			if ($do_search_articles)
			{
				$sql='('.$this->select_articles().')';
			}
			
			if ($do_search_media)
			{
				if($sql)$sql.=' UNION ';
				$sql.='('.$this->select_media().')';

			}
			
			if ($do_search_forum)
			{
				if($sql)$sql.=' UNION ';
				$sql.='('.$this->select_forum().')';
			}
			$b=$this->insert_sql($sql);
			$this->qtime = (int)(1000*($this->microtime_float()-$this->qtime));
			return $b;
		}
		else
			return 0;
	}
	
	/**
	 * no use of temp table 
	 * works like: select * from (select ... union select ....)
	 * 
	 * @param int $page
	 * @param bool $do_search_articles
	 * @param bool $do_search_media
	 * @param bool $do_search_forum
	 * @return bool
	 */
	public function search_execute_on_the_fyl($page, $do_search_articles=1, 
					$do_search_media=0,$do_search_forum=0 )
	{
		if ($do_search_articles)
		{
			$sql='('.$this->select_articles().')';
		}
			
		if ($do_search_media)
		{
			if($sql)$sql.=' UNION ';
			$sql.='('.$this->select_media().')';
		}
			
		if ($do_search_forum)
		{
			if($sql)$sql.=' UNION ';
			$sql.='('.$this->select_forum().')';
		}
		$sql='select * from ('.$sql.') as s order by  s.'.$this->sort_me.'LIMIT ('.($this->results_perpage*($page-1)).", $this->results_perpage)";

		return  $this->db->query($sql);
	}
	/**
	 * default sort!
	 * sort results by score
	 * default sort direction is DESC
	 * @param string $direction (DESC/ASC)
	 */
	public function sort_by_score($direction="DESC")
	{
		$this->sort_me="score $direction";
	}
	
	
	/**
	 * sort results by date
	 * default sort direction is DESC
	 * @param string $direction (DESC/ASC)
	 */
	public function sort_by_date($direction="DESC")
	{
		$this->sort_me="date $direction";
	}
	
	
	/**
	 * set or get 
	 * temp table name
	 *
	 * @param string $table_name
	 */
	public function temp_table_name(&$table_name=0)
	{
		if ($table_name!=0)
		$this->temp_table = $table_name;
		return $this->temp_table;
	}
	
		
	/**
	 * will delete records from this session in destructor --> default of
	 */
	private function __destruct()
	{
		if ($this->delete_records)
			$this->delete_temp_data();
	}
	
	

}

?>