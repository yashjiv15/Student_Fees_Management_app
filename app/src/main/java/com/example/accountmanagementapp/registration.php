<?php include 'dbcon.php';
if(isset($_POST['submit']))
{
  $username = mysqli_real_escape_string($con, $_POST['username']);
   $email = mysqli_real_escape_string($con, $_POST['email']);
   $password = mysqli_real_escape_string($con, $_POST['password']);
   $cpassword = mysqli_real_escape_string($con, $_POST['cpassword']);
   
   $pass = password_hash($password, PASSWORD_BCRYPT);
   $cpass = password_hash($cpassword, PASSWORD_BCRYPT);
   $emailquery = "select * from registry where email='$email' ";
   $query = mysqli_query($con, $emailquery);

   $emailcount = mysqli_num_rows($query);
  if($emailcount>0)
  {
   ?>
<script>
	alert("email already exists");
</script>
<?php
  }
 else
{
if  ($password === 	$cpassword)
    {
     $insertquery ="insert into registry( username, email, password ,cpassword) values('$username','$email','$pass' ,'$cpass')" ;
         $iquery = mysqli_query($con,$insertquery);
    
  if($iquery){	
?>
<script>
	alert("inserted successfully");
</script>
<?php
}
else{
	?>
<script>
	alert("not inserted");
</script>
<?php
}


 }else{
?>
<script>
	alert("passwod not matching");
</script>
<?php
 }
}
}

?>