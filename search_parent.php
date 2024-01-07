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

// Get search term from query string
$Name = $_GET['Name'];
$roll = $_GET['roll'];

// Get matched records from the 'details' table
$sqldetails = "SELECT *
            FROM parent
          WHERE parent.Name = '$Name' or parent.roll = '$roll'";
    


$resultdetails = $conn->query($sqldetails);



// Output JSON
$output = array();

// Check if there are results in the 'details' table
if ($resultdetails->num_rows > 0) {
    $detailsData = array();
    while ($row = $resultdetails->fetch_assoc()) {
        $detailsData[] = $row;
    }
    $output['details'] = $detailsData;


}

// Output JSON
if (!empty($output)) {
    echo json_encode($output);
} else {
    echo "No results found";
}

$conn->close();
?>
