<?php
$conn =mysqli_connect("localhost","root","");
mysqli_select_db($conn,"account_management");

$username=$_POST['username']
$email=$_POST['email']
$password=$_POST['password']
$cpassword=$_POST['cpassword']

$query="INSERT INTO 'registry' ('id','username','email','password','cpassword') VALUES (NUll,'$username','$password')";
mysqli_query($conn,$query);

echo json_encode(array('response'=>"inserted succesfully"));
mysqli_close($conn);


?>

