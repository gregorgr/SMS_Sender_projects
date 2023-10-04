<?PHP
include_once "template_compiler.class.php";

$t = new template_compiler('page_sample.template.xml');
$t->show_compile_date();
$t->save();

?>