<?php

session_start();
if(!isset($_SESSION['username'])){
    header('lacation:login.php');
}
?>


<html>
    <head>
        <title> Home Page </title>
        <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="stylesheet" type="text/css"
    href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    
    </head>
    <body>
        
        <div class="container">
        
        <a class="float-right" href="logout.php"> LOGOUT </a>
        
        <h1> Welcome <?php echo $_SESSION['username']; ?> </h1>
        <h2 style="color: #fff;"> Ask your admin to redirect and setup project </h2>
        </div>
            
    </body>


</html>