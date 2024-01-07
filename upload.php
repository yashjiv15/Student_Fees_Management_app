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
    $image_name = $_POST['image_name'];
    $image_data = file_get_contents($_FILES['image']['tmp_name']);

    $stmt = $conn->prepare("INSERT INTO images (image_name, image_data) VALUES (?, ?)");
    $stmt->bind_param("ss", $image_name, $image_data);

    if ($stmt->execute()) {
        echo "Image uploaded successfully";
    } else {
        echo "Error uploading image";
    }

    $stmt->close();
} else {
    echo "Invalid request";
}

$conn->close();
?>