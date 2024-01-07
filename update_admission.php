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
     $Name = $_POST['Name'];
     

     $course = $_POST['course'];
      $type = $_POST['type'];

    $year = $_POST['year'];
    $enroll = $_POST['enroll'];
    $roll = $_POST['roll'];
    $institute = $_POST['institute'];
   

    require_once 'connect.php';


$sql = "UPDATE admission SET Name='$Name', course='$course', type='$type',year='$year', enroll='$enroll', institute='$institute' WHERE roll='$roll'";

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