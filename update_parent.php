<?php

// Your database credentials
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "account_management";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
  $Name =$_POST['Name'];
    $father =$_POST['father'];
    $mother = $_POST['mother'];
    $address =$_POST['address'];
    $phone =$_POST['phone'];
    $occupation =$_POST['occupation'];
    $income =$_POST['income'];
    $roll = $_POST['roll'];

    require_once 'connect.php';

    // Assuming 'id' is the primary key for your records
    $roll = $_POST['roll'];

$sql ="UPDATE parent SET Name='$Name',father='$father',mother='$mother',address='$address',phone='$phone',occupation='$occupation',income='$income' WHERE roll='$roll'";

    $iquery = mysqli_query($conn, $sql);

    $response = array();

    if ($iquery) {
        $response["success"] = true;
        $response["message"] = "Details updated successfully";
    } else {
        $response["success"] = false;
        $response["message"] = "Failed to update details: " . mysqli_error($conn);
    }

    // Send the response as JSON
    echo json_encode($response);

    mysqli_close($conn);
}
?>