<?php 

if ($_SERVER['REQUEST_METHOD'] =='POST'){

    $Name = $_POST['Name'];

   
    $roll = $_POST['roll'];
     $amount = $_POST['amount'];
     $pendingamount = $_POST['pendingamount'];
    require_once 'connect.php';

    

 $sql = "INSERT INTO fees (Name,roll,amount,pendingamount)
 VALUES ('$Name','$roll','$amount','$pendingamount')";

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




?>