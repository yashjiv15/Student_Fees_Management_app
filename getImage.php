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

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['id'])) {
        $id = $_GET['id'];
        $sql = "SELECT * FROM images WHERE id = '$id'";
        
        // Use the correct variable name $conn, not $con
        $r = mysqli_query($conn, $sql);

        if ($r) {
            $result = mysqli_fetch_array($r);

            // Check if the result is not empty
            if ($result) {
                header('content-type: image/jpeg');
                echo base64_decode($result['image']);
            } else {
                echo "Image not found";
            }
        } else {
            echo "Error executing query: " . mysqli_error($conn);
        }
    } else {
        echo "ID parameter is missing";
    }

    mysqli_close($conn);
} else {
    echo "Error: Invalid request method";
}
?>
