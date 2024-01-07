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
    // Get the roll number from the POST parameters
    $roll = $_POST['roll'];

    // Assuming 'id' is the primary key for your records
    $sql = "DELETE FROM fees WHERE roll='$roll'";

    $iquery = mysqli_query($conn, $sql);

    $response = array();

    if ($iquery) {
        $response["success"] = true;
        $response["message"] = "Details deleted successfully";
    } else {
        $response["success"] = false;
        $response["message"] = "Failed to delete details: " . mysqli_error($conn);
    }

    // Send the response as JSON
    echo json_encode($response);

    mysqli_close($conn);
}
?>
