<?php 
$services = getenv("VCAP_SERVICES")
$services_json = json_decode($services,true);
$mysql_config = $services_json["mysql-5.5"][0]["credentials"]; 
$db = $mysql_config["d464afbad3af14930846ac5d26f2bd9a8"]; 
$host = $mysql_config["192.155.247.251"]; 
$port = $mysql_config["3307"]; 
$username = $mysql_config["uAo7T0YkLzkQZ"];
$password = $mysql_config["pptLxpY9pdgAI"];

$conn = mysql_connect($host . ':' . $port, $username, $password);
 if(! $conn ) { die('Could not connect: ' . mysql_error()); } mysql_select_db($db);

$name=$_POST['name'];
$mobile=$_POST['mobile'];
$deal=$_POST['deal'];
$salon=$_POST['salon'];
 
mysql_query("insert into newtable(name, mobile,deal,salon) values('{$name}','{$mobile}','{$deal}','{$salon}')");
mysql_close($conn);
?>
