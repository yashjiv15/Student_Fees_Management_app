<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $cpassword = $_POST['cpassword'];
    $password = password_hash($password, PASSWORD_DEFAULT);
    $cpassword = password_hash($cpassword, PASSWORD_DEFAULT);
    require_once 'connect.php';

    




    if ($_POST['password'] === $_POST['cpassword'] ) {
        $sql = "INSERT INTO registry (username, email, password,cpassword) VALUES ('$username', '$email', '$password','$cpassword')";

$iquery =mysqli_query($conn, $sql) ;

if ($iquery) {
  $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);
}

else{

 

}

}


}

?>