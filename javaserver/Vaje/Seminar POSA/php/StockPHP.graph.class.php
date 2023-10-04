<?php
/**
*	@package StockPHP 1.0
*	@author Gregor Grajzar, 2007
*
*/

class StockPHPgraph
{
	private $tempdata;
	private $Img_Stock;
	private $Img_A1;
	private $Img_A2;
	private $Img_Location;
	private $_stockquotes;
	private $_stockName;
	
	private $_DateStart;
	private $_DateEnd;
	
	private $MainGraphType;
	
// colors main graph
	private $col_back;
	
	private $col_mline;
	private $col_mvolume;
	
	private $col_step_width;
	
	private $col_border;
	private $col_trends;
	private $col_R;
	private $col_R_fill;
	
	// main graph dimension
	private $dim_x;
	private $dim_y;
	private $dim_x_step;
	private $Period;
	
	private $ShowR;
	
	public function ShowR($ShowBool, $Period)
	{
		$this->Period=$Period;
		$this->ShowR=$ShowBool;
	}
	
	public function SetColors( $Background,$Borders)
	{	
		$this->col_back=$Background;	
		$this->col_border=$Borders;
			
	}
	
	public function Quotes()
	{
		return $this->_stockquotes;
	}
	
	public function SetColorsR($MainLine, $FillCollor)
	{
		$this->col_R=$MainLine;
		$this->col_R_fill=$FillCollor;	
	}
	
	
	public function DateBorder( $StartDate, $EndDate)
	{
		$this->_DateStart=$StartDate;
		$this->_DateEnd=$EndDate;
	}
	public function GettempData()
	{
		return $this->tempdata;
	}
	public function MainGraphInit($dim_x,$dim_y)
	{
		$this->dim_x=$dim_x;
		$this->dim_y=$dim_y;
		//		$this->Img_Stock=@imagecreate($dim_x, $dim_y)	
		//		$this->Img_Stock=@imagecreatetruecolor($dim_x, $dim_y)	
		$this->Img_Stock=@imagecreate($dim_x, $dim_y)	
    		or die("Cannot Initialize new GD image stream");
    	$background_color = imagecolorallocate($this->Img_Stock, $this->col_back["r"], $this->col_back["g"], $this->col_back["b"]);
		
    	
	}
	
	public function CreateAnalizeGraph($stockName, $graphType, $startDate, $endDate, $days)
	{
		$stockimgname="stockAnalise_".$stockName."_".$graphType.".png";
		if (strlen($this->Img_Location)>0)
			$stockimgname=$this->RelativeUrlConcat($this->Img_Location,$stockimgname);
		//$this->dim_x=$dim_x;
		$this->dim_y=110;
		$this->Img_A1=@imagecreate($this->dim_x, $this->dim_y)
			or die("Cannot Initialize new GD image stream");
    	$background_color = imagecolorallocate($this->Img_A1, $this->col_back["r"], $this->col_back["g"], $this->col_back["b"]);
		$atable=array();
    	switch (strtolower($graphType))
		{
			case "rsi":
				$this->CreateRSITemplate($this->Img_A1,"$stockName $graphType ($days)");				
				$atable = $this->CalculateRSI($stockName,$startDate, $days);
				$this->PrintGraphLine($this->Img_A1,$atable,$startDate,$stockName,"RSI",1);			
				break;
			case "wr":					
				$graphType="Williams %R";
				$this->CreateWRTemplate($this->Img_A1,"$stockName $graphType ($days)");
				$atable = $this->CalculateWR($stockName,$days);
				$this->PrintGraphLine($this->Img_A1,$atable,$startDate,$stockName,"WR",-100);
				break;
			case "macd":
				$graphType="$stockName MACD ($days)";
				$this->CreateMACDTemplate($this->Img_A1,$graphType);	
					break;
		}
		
    	$this->tempdata=$atable;
    //	echo("<br>Analiza:<br><pre>");		
   		//	 print_r($atable);
   	//		echo("</pre>");	

    	$xvalues=array($days,2);
    	
		imagepng($this->Img_A1,$stockimgname); 
		imagedestroy($this->Img_A1);
		return $stockimgname;
	}
	
	function PrintGraph($image, $table, $startDate, $StockName, $RowValue,$factory)
	{
		
	}
	
	function PrintGraphLine($image, $table, $startDate, $StockName, $RowValue,$factory)
	{
		
		// DrawSquare($img,50,5,$this->dim_x-44,105,$this->col_border);
		$Color=imagecolorallocate ($image, 255,0,0);
		
		for($n=0;$n<count($table);$n++)
		{			
			$date=$table[$n]["date"];
				if($date>= $startDate)
				{
					$xstep=	$this->col_step_width;//round(($this->dim_x-94)/(count($table)-$n));
					$ystep=$factory;
					$old_x=0;
					$old_y=0;
					for($i=$n;$i<count($table);$i++)
					{
						$date=$table[$i]["date"];
						$val=$ystep*$table[$i][$RowValue];
						if($i>$n)
						{
							$valxn=round($table[$i][$RowValue],3);
							imageline ($image,$old_x,$old_y+5,$old_x+$xstep,5+$val,$Color);
							
						//	echo("val($i): $valxn - x0:$old_x, y0:$old_y, x1:".$old_x+$xstep." y1:$val   <br>");
							$old_x+=$xstep;
						}
						else
						{
							$old_x=53;
							
						}
						$old_y=$val;
					}
					break;
				}
				
			
					
		}
		
		
	}
	
	function CalculateRSI($stockName,$startDate, $days)
	{
		$ResoultTable=$this->GenerateRSI_table($stockName, $days);
   		return $ResoultTable;		
	}
	
	function GenerateRSI_table($StockName, $days)
	{
		$ResoultTable=array();
		
		$StockName=strtoupper($StockName);
		$n_count=0;
		for($i=1;$i<$this->_stockquotes["rowscount"];$i++)
		{
			$stock=strtoupper($this->_stockquotes["table"][$i]["stock"]);	
			if ($StockName==$stock)
			{
				$date=$this->_stockquotes["table"][$i]["date"];
				$val=(double) $this->_stockquotes["table"][$i]["price"];
				$val0=$this->GetPreviousValue($StockName,$i);
				$U=0;
				$D=0;
				if ($val>$val0)
				{
					$U=($val-$val0);
				}
				elseif ($val<$val0)
				{
					$D=$val0-$val;
				}
				$rsi_array = array(	"stock"=>$StockName,
									"date"=>$date,
									"value"=>$val,
									"WR"=>0,
									"cal"=>"$val0 - $val",
									"U"=>(double)$U,
									"D"=>(double)$D,
									"RSI"=>0,
									"type"=>gettype ($val));
				array_push($ResoultTable,$rsi_array);
				$n_count++;
			}
		}
		$d=$days-1;
		for($n=($n_count-1);$n>=0;$n--)
		{
			// printanje flowa$Ech="";
			$k=0;
			$j=0;
			$EMAn_U=0;
			$EMAn_D=0;
			if($n<$d)	$d=$n;

			for($m=$n;$m>=($n-$d);$m--)
			{
				$EMAn_U+=$ResoultTable[$m]["U"];
				$EMAn_D+=$ResoultTable[$m]["D"];
				$j++;
				$k+=$j;
				// printanje flowa $Ech.="$m,";
			}
			$EMAn_U="$EMAn_U/$k";
			$EMAn_D="$EMAn_D/$k";
			$RSI = 100*(1/(1+($EMAn_U/$EMAn_D)));
			
			$ResoultTable[$n]["RSI"]=$RSI;
			// printanje flowa echo("$n - ($Ech) <br>");
		}
		return $ResoultTable;
	}
	
	function GetPreviousValue($StockName, $i)
	{
		$value=0;	
		$resoult=0;
		$i--;
		while ($resoult==0 && $i>=0)
		{
			$stock=strtoupper($this->_stockquotes["table"][$i]["stock"]);
			if ($StockName==$stock)
			{
				$value=(double)$this->_stockquotes["table"][$i]["price"];
				$resoult=1;
			}
			else
			{
				$i--;
			}
		}
		
		
		return $value;
	}
	
	function CalculateWR($StockName, $days)
	{
		/*
			%R=100*(CLOSE_today - HIGH_n_days)/(HIGH_n_days - LOW_n_days) 
		 */
		$ResoultTable=array();
		$StockName=strtoupper($StockName);
		for($i=0;$i<$this->_stockquotes["rowscount"];$i++)
		{	
			$stock=strtoupper($this->_stockquotes["table"][$i]["stock"]);	
			if ($StockName==$stock)
			{
				$date=$this->_stockquotes["table"][$i]["date"];
				$val=(double)$this->_stockquotes["table"][$i]["price"];
				if($i>1)
				{
					$minMax_n = $this->GetMinMaxValue($i,$val,$days);
				}
				else
				{
					$minMax_n=array("min"=>$val,"max"=>$val);
				}			
					
				$wr = round(100*($val-$minMax_n["max"])/($minMax_n["max"]-$minMax_n["min"]),3);
				$wrarray = array("stock"=>$StockName,"date"=>$date,"value"=>$val,"WR"=>$wr);
				$wrarray=array_merge($wrarray,$minMax_n);
				array_push($ResoultTable,$wrarray);
			}
		}
		return $ResoultTable;
		
	}
	
	function GetMinMaxValue ($i,$value,$days)
	{
		$min=$value;
		$max=$value;
		if(($i+1)<=$days)
		{
			$days=$i;
		}
		
		for($n=$i-$days;$n<($i+1);$n++)
		{
			$val=$this->_stockquotes["table"][$n]["price"];
			if ($min>$val)$min=$val;
			if ($val>$max)$max=$val;
		}
		$minMax=array("min"=>$min,"max"=>$max);
		return $minMax;
	}
	
	function CreateMACDTemplate($img, $text)
	{
		$this->DrawSquare($img,50,5,$this->dim_x-44,105,$this->col_border);
		$txCol=imagecolorallocate ($img, $this->col_border["r"],$this->col_border["g"], $this->col_border["b"]);
		imagestring($img, 2, 70,10, $text, $txCol);
		
		imagestring($img, 2, 30,$this->dim_y-20, "-5", $txCol);
		imagestring($img, 2, 25,4, "15", $txCol);
		$y_len =25;
		$y=80;		
		imageline ($img,53,$y,$this->dim_x-49,$y,$txCol);
		imagestring($img, 2, 25,$y-5, "0", $txCol);
		$y1=81-5*$y/15;	
		imageline ($img,53,$y1,$this->dim_x-49,$y1,$txCol);
		imagestring($img, 2, 25,$y1-5, "5", $txCol);
		$y1=81-10*$y/15;	
		imageline ($img,53,$y1,$this->dim_x-49,$y1,$txCol);
		imagestring($img, 2, 25,$y1-5, "10", $txCol);
		return $img;
	}

	function CreateRSITemplate($img, $text)
	{
		$this->DrawSquare($img,50,5,$this->dim_x-44,105,$this->col_border);
		$txCol=imagecolorallocate ($img, $this->col_border["r"],$this->col_border["g"], $this->col_border["b"]);
		imagestring($img, 2, 70,10, $text, $txCol);
		
		imagestring($img, 2, 30,$this->dim_y-20, "0", $txCol);
		imagestring($img, 2, 25,4, "100", $txCol);
		//50%
		$y=5+($this->dim_y-10)*(0.5);
		imageline ($img,55,$y,$this->dim_x-48,$y,$txCol);
		
		//30$
		$y=5+($this->dim_y-10)*(1-0.3);
		$txColB=imagecolorallocate ($img, 120,140, 253);
		imageline ($img,55,$y,$this->dim_x-48,$y,$txColB);
		imagestring($img, 2, 25,$y-5, "30", $txColB);
		
		//70%
		$y=5+($this->dim_y-10)*(1-0.7);
		$txColR=imagecolorallocate ($img, 253,140, 120);
		imageline ($img,55,$y,$this->dim_x-48,$y,$txColR);
		imagestring($img, 2, 25,$y-5, "70", $txColR);
		return $img;
	}
	
	function CreateWRTemplate($img, $text)
	{
		$this->DrawSquare($img,50,5,$this->dim_x-44,105,$this->col_border);	
		$txCol=imagecolorallocate ($img, $this->col_border["r"],$this->col_border["g"], $this->col_border["b"]);
		$values=array(0,-20,-40,-60,-80,-100);
		imagestring($img, 2, 70,10, $text, $txCol);
		for($i=0;$i<count($values);$i++)
		{
			$y=5+($this->dim_y-10)*((-1)*$values[$i]/100);
			if ($values[$i]>(-100) && $values[$i]<0)
				imageline ($img,55,$y,$this->dim_x-48,$y,$txCol);
			imagestring($img, 2, 25,$y-5, $values[$i], $txCol);
		}
		return $img;
	}
	
	public function CreateMainGraphChart($stockName, $startDate, $endDate, $color)
	{
		$this->col_mline=$color;
		$this->_stockName=$stockName;
		$this->DateBorder($startDate,$endDate);			
		$stockimgname="stockgraph_".$stockName.".png";
		if (strlen($this->Img_Location)>0)
			$stockimgname=$this->RelativeUrlConcat($this->Img_Location,$stockimgname);
		$startx=0;
		$max_y=0;
		$max_vol=0;
		$min_y=0;
		
		$count=0;
		$oldVal=0;
		$totalVol=0;
		$volAvg=0;
		$diff_rate=0;
		$firstCol=0;
		for($i=0;$i<$this->_stockquotes["rowscount"];$i++)
		{
			$val=$this->_stockquotes["table"][$i]["price"];			
			$vol=$this->_stockquotes["table"][$i]["volume"];			
			$date=$this->_stockquotes["table"][$i]["date"];
			
			$count++;
			$totalVol+=	$vol;
			$volAvg=$totalVol/$count;
			if ($oldVal!=0)
			{
				$diff_rate=100*$val/$oldVal-100;
			}
			$oldVal=$val;
			
			$addData=array(
				"change"=>$diff_rate,
				"volumenavg"=>$volAvg
			);
			$this->_stockquotes["table"][$i]=array_merge($this->_stockquotes["table"][$i],$addData);
			
			if ($max_y<$val)	$max_y=$val;
			if ($max_vol<$vol)	$max_vol=$vol;
			
			if($i==0)
			{
				$min_y=$val;
			}
			elseif ($min_y>$val)
			{
				$min_y=$val;
			}	
			if($date<=$startDate)
			{
				$startx=$i;
			}		
		}	
			
		$this->dim_x_step=$this->DateDaysDiffStep($startDate,$endDate,5);

		//echo("step: $this->dim_x_step<br> max: $max_y min: $min_y vol: $max_vol<br>");
		$this->DrawSquare($this->Img_Stock,50,30,$this->dim_x-44,$this->dim_y-27,$this->col_border);	
		
		$this->_CreateGraphGraph($startDate, $endDate,10,$startx, $max_y,$min_y,$max_vol);
		
		imagepng($this->Img_Stock,$stockimgname); 
		imagedestroy($this->Img_Stock);	
		return $stockimgname;
	}

	
	function _CreateGraphGraph ($startDate, $endDate, $border,$startx, $max, $min, $volmax)
	{	
		$valuearray=array();
		$vol_array=array();	
		
		$sCol=$this->ColorDarken($this->col_mline,3/4);
		$grColor=imagecolorallocate($this->Img_Stock, $this->col_mline["r"], $this->col_mline["g"], $this->col_mline["b"]);
		$grColor2=imagecolorallocate($this->Img_Stock, $sCol["r"],$sCol["g"], $sCol["b"]);
		$txCol=imagecolorallocate ($this->Img_Stock, $this->col_border["r"],$this->col_border["g"], $this->col_border["b"]);

		$blue 	=imagecolorallocate($this->Img_Stock, 240,154, 154);
		$red	=imagecolorallocate($this->Img_Stock, 154,154, 240);
		
		
		$x1=53;
		$y1=31;
		$x2=$this->dim_x-52;
		$y2=$this->dim_y-100;
		
		$maxvol=round(($this->dim_y-100)/8);
		$maxvol=200;
		
		$y_len=$y2-$y1;
		$topBorder=round($y_len*(100-$border)/100);
		$x_count= $this->_stockquotes["rowscount"]-$startx;
		$colwidth=($x2-$x1)/($x_count-1);
		$this->col_step_width=$colwidth;

		$x_stepno=1;
		//$alldays=$this-> DaysBetween($startDate,$endDate);
		if($this->dim_x_step>$x_count)
		{
			$xdif=$this->dim_x_step-$x_count;
			//echo("razlika je:$xdif <br>");
		}
		else
		{
			//echo("dni je :".$this->dim_x_step ."<br>");
		}
		
		$max_y=$max;
		$min_y=$min;
		
		//echo("startx:$startx $x_count<br>");
		$x_i=$x1;
		$y_i=0;
		$xt=0;
		$oldDate="";
		$oldDatex=0;
		$oldval=0;
		// izpis ravnila tecaja
		for($i=0;$i<$this->_stockquotes["rowscount"];$i++)
		{
			$val=(double)$this->_stockquotes["table"][$i]["price"];			
			array_push($valuearray,$val);
		}		
		sort($valuearray);		
		$valold=0;
		imagestring($this->Img_Stock, 1, 15,20, "[Euro]", $txCol);
		for($n=0;$n<count($valuearray);$n++)
		{
			$y_i=$y2-round(($topBorder*($valuearray[$n]-$min)/($max-$min)),0);
			if($valold==0)
			{
				imagestring($this->Img_Stock, 1, 10,$y_i-4, $valuearray[$n], $txCol);					
				imageline ($this->Img_Stock,55,$y_i,$this->dim_x-50,$y_i,$txCol);
				$valold=$y_i;
			}
			else if(($n+1)==count($valuearray))
			{
				$zcorr=($valold>$y_i?11:4);			
				imagestring($this->Img_Stock, 1, 10,$y_i-$zcorr, $valuearray[$n], $txCol);	
				imageline ($this->Img_Stock,55,$y_i,$this->dim_x-50,$y_i,$txCol);
			}
			else if  (($valold-15)>$y_i)
			{
				imagestring($this->Img_Stock, 1, 10,$y_i-4, $valuearray[$n], $txCol);	
				//imageline ($this->Img_Stock,55,$y_i,90,$y_i,$txCol);
				imageline ($this->Img_Stock,55,$y_i,$this->dim_x-50,$y_i,$txCol);
				$valold=$y_i;
			}			
		}
		
		for($i=0;$i<$this->_stockquotes["rowscount"];$i++)
		{			
			$date=$this->_stockquotes["table"][$i]["date"];	

			if($date>=$this->_DateStart)
			{
				$m=round(substr($date,4,2));
				$d=round(substr($date,6,5));
				
				$val=(double)$this->_stockquotes["table"][$i]["price"];			
				$vol=(int)$this->_stockquotes["table"][$i]["volume"];					
				array_push($vol_array,$vol);				
				$y_i=$y2-round(($topBorder*($val-$min)/($max-$min)),0);

				// writing date marks
				if (($i-$startx)==0 ||($oldDatex+40)<$x_i || ($i+1)==$this->_stockquotes["rowscount"])
				{
					$xt=$x1;
					$yt=1;
					$oldDatex=$x_i;
					if (($i-$startx)==0) 
					{
						$xt-=6;
						
					}
					else if(($i+1)==$this->_stockquotes["rowscount"])
					{
						$xt=$x2+5;
						$yt=2;
					}
					else
					{
						$yt=2;
						if ($xt>round(0.95*$x2))$yt=0;		
					}
				
					if ($yt>0) 
					{
						imagestring($this->Img_Stock, 2, $xt-10, $this->dim_y-25,  "$d.$m.", $txCol);
						// horizontalne datumske èrte
						if($yt==2)
							imageline ($this->Img_Stock,$x_i,$y_i-20,$x_i,$y_i+20,$txCol);						
					}
					$oldDate=$date;	
					$oldval=$val;			
				}			
				// risanje volumna v lotih				
				$voly=round($maxvol*$vol/$volmax);
				
				// shranjevanje vrednosti Z osi za javascript
				$valZArray=array("zval"=>$x_i,"zvol"=>$voly);
				$this->_stockquotes["table"][$i]=array_merge($this->_stockquotes["table"][$i],$valZArray);
				//echo($volmax.", ");
				$volum_collor=($oldval>=$val?$blue:$red);
				imageline ($this->Img_Stock,$x_i,$y2+70,$x_i,$y2-$voly+70,$volum_collor);
				imageline ($this->Img_Stock,$x_i+1,$y2+70,$x_i+1,$y2-$voly+70,$volum_collor);

					
				//risanje crte grafa
				if($i>$startx+1)
				{	
					imageline ($this->Img_Stock,$x1,$y1,$x_i,$y_i,$grColor);
					imageline ($this->Img_Stock,$x1,$y1-1,$x_i,$y_i-1,$grColor);					
				}
				$x1=$x_i;
				$y1=$y_i;			
				$x_i+=$colwidth;
			}
		}	

		// izpis ravnila volumna
		$vol_old=0;
		sort($vol_array);
		imagestring($this->Img_Stock, 1, 670,20, "lotov", $txCol);	
		for($m=0;$m<count($vol_array);$m++)
		{
			$voly=round(40*($volmax/$vol_array[$m]));
			if($vol_old==0)
			{
				imagestring($this->Img_Stock, 1, 675,$y2-$voly+70, $vol_array[$m], $txCol);	
				$vol_old=$voly;	
			}
			elseif($voly>($vol_old+2))
			{			
				imagestring($this->Img_Stock, 1, 675,$y2-$voly+70, $vol_array[$m], $txCol);	
				$vol_old=	$voly;	
			}
		}
	}
	
	function ColorDarken($color,$factor)
	{
		$scol=array();
		foreach ($color as $key => $val)
		{
			//if ($val>0)$val=round($val+2*(255-$val)/3);
			if ($val>0)$val=round($factor*$val);
			$colx=array($key=>$val);
			$scol=array_merge($scol,$colx);
		}		
		return $scol;
	}
	
	function DateDaysDiffStep($date1,$date2,$workdays)
	{		
		$i=$this-> DaysBetween($date1,$date2);
		$w = floor ($i/7);
		$i=$w*$workdays;	
			$w=floor ($this->dim_x/$i);
		return $w;
	}
	
	public function DrawSquare ($img, $start_x,$start_y,$end_x,$end_y, $color)
	{
		$imgColor=imagecolorallocate($img, $color["r"], $color["g"], $color["b"]);
		imageline ($img,$start_x,$start_y,$end_x,$start_y,$color);
		imageline ($img,$start_x,$end_y,$end_x-1,$end_y,$color);
	
		imageline ($img,$start_x,$start_y,$start_x,$end_y,$color);
		imageline ($img,$end_x,$start_y,$end_x,$end_y,$color);
	}
	function DaysBetween($startDate, $endDate)
	{
	    // get the number of days between the two given dates.
	    $days = (strtotime($endDate) - strtotime($startDate)) / 86400 + 1;
    	return $days;   
	}
	
	public function GraphA1Init($dim_x,$dim_y)
	{
		
	}
	
	public function GraphA2Init($dim_x,$dim_y)
	{
	
	}
	
	public function SetImageLocation($newLocation)
	{
		$this->Img_Location=$newLocation;
	}
	
	function RelativeUrlConcat($dir,$file)
	{
		$newurl=$dir;
		if(substr($dir,1,1)!='/')
		{ 
			$newurl= "/".$dir;
		}
		if(substr($dir,-1,1)!='/')
		{ 
			$newurl.= "/";
		}
		$newurl.=$file;
		return $newurl;
	}
	public function __construct( $StockQutasArray)
	{	
		$this->_stockquotes=$StockQutasArray;				
		//$this->_img=$Image;
$this->Img_Location="";
	}
	
	
}
?>