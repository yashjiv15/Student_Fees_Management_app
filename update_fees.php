<?php
if ($_SERVER['REQUEST_METHOD'] =='POST'){
    $Name = $_POST['Name'];
    $roll = $_POST['roll'];
    $amount = $_POST['amount'];
    
    require_once 'connect.php';

    $sql = "UPDATE fees SET amount = '$amount' WHERE Name = '$Name' AND roll = '$roll'";

    $iquery = mysqli_query($conn, $sql);

    if ($iquery) {
        $result["success"] = "1";
        $result["message"] = "success";
        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["success"] = "0";
        $result["message"] = "error";
        echo json_encode($result);
        mysqli_close($conn);
    }
}
?>
