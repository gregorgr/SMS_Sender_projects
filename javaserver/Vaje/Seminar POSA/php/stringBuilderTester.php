<?php

include "StringBuilder.class.php";
$n= 300000;
$timer = microtime();
echo("<br>1. zaèetek<br />".$timer);

// zaèetek
$testSB = new StringBuilder();
for($i=0;$i<$n;$i++)
	$testSB->Append("12345 ");

$timer = microtime();
echo("<br>2. polno:<br />".$timer);

$test1=$testSB->ToString();
$timer = microtime();
echo("<br>3. prenešeno:<br />".$timer);
echo("<br><br>");


$timer = microtime();
echo("<br>1. zaèetek<br />".$timer);
$testSt ="";
for($i=0;$i<$n;$i++)
	$testSt .="12345 ";

$timer = microtime();
echo("<br>2. polno:<br />".$timer);

$test2 = $testSt;
echo("<br>3. prenešeno:<br />".$timer);
echo("<br><br>");

?>