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
    // Correct usage of isset for each parameter
    $Name = isset($_POST['Name']) ? $_POST['Name'] : '';
    $email = isset($_POST['email']) ? $_POST['email'] : '';
    $phone = isset($_POST['phone']) ? $_POST['phone'] : '';
    $roll = isset($_POST['roll']) ? $_POST['roll'] : '';
    $aadhar = isset($_POST['aadhar']) ? $_POST['aadhar'] : '';
    $section = isset($_POST['section']) ? $_POST['section'] : '';

    require_once 'connect.php';

    // Assuming 'roll' is the primary key for your records
    $roll = $_POST['roll'];

    $sql = "UPDATE details SET Name='$Name', email='$email', phone='$phone', aadhar='$aadhar', section='$section' WHERE roll='$roll'";

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
