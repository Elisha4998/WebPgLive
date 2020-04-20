<?php

session_start();

$con = mysqli_connect('localhost','root','');

mysqli_select_db($con, 'userregistration');

$name = $_POST['user'];
$pass = $_POST['password'];

$s = "select * from usertable where name = '$name' && password = '$pass'";

$result = mysqli_query($con, $s);

$num = mysqli_num_rows($result);

if($num == 1){
    $_SESSION['username'] = $name;
    if($name == 'Admin' || $name == 'RISAT-1'){header('location:index.php');}
    elseif($name == 'gsat'){header('location:gsat.php');}
    else {header('location:home.php');}
}else{
   echo"<script>alert('Please register first!');window.location='login.php'</script>";
}

?>