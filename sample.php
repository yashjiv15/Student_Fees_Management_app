<?php
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

$Name = $_GET['Name'];
$id = $_GET['id'];

$sql = "SELECT * FROM details WHERE Name='$Name' AND id=$id";
$result = $conn->query($sql);

$rows = array();

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $rows[] = $row;
    }
    echo json_encode($rows);
} else {
    // No data found, echo an empty JSON array
    echo json_encode(array());
}

$conn->close();
?>