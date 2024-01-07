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

// Validate and sanitize user inputs
$Name = isset($_POST['Name']) ? $conn->real_escape_string($_POST['Name']) : '';
$email = isset($_POST['email']) ? $conn->real_escape_string($_POST['email']) : '';
$phone = isset($_POST['phone']) ? $conn->real_escape_string($_POST['phone']) : '';
$roll = isset($_POST['roll']) ? $conn->real_escape_string($_POST['roll']) : '';
$aadhar = isset($_POST['aadhar']) ? $conn->real_escape_string($_POST['aadhar']) : '';
$section = isset($_POST['section']) ? $conn->real_escape_string($_POST['section']) : '';
$imageData = isset($_POST['image']) ? $_POST['image'] : '';

// Decode Base64 encoded image data
$imageData = base64_decode($imageData);
// Check if the image data is not empty
if (!empty($imageData)) {
    // Specify the full path for the image directory
    $imageDirectory = "path/to/your/image/";

    // Generate a unique filename for the image
    $imageFileName = "image_" . uniqid() . ".jpg";

    // Save the image to the server
    $imagePath = $imageDirectory . $imageFileName;

    // Check if the directory exists; if not, create it
    if (!file_exists($imageDirectory)) {
        if (!mkdir($imageDirectory, 0777, true)) {
            die("Error: Unable to create the image directory.");
        }
    }

    // Check if the image is successfully saved
   if (file_put_contents($imagePath, $imageData)) {

    // Insert data into the database
    $sql = "INSERT INTO details (Name, email, phone, roll, aadhar, section, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sssssss", $Name, $email, $phone, $roll, $aadhar, $section, $imageFileName);

    // Check if the SQL query is executed successfully
    if ($stmt->execute()) {
        // Insertion successful
        echo "Success";
    } else {
        // Insertion failed
        echo "Error: Unable to insert data into the database. " . $stmt->error;
    }

    // Close the statement
    $stmt->close();
} else {
    echo "Error: Unable to save the image to the server.";
}

}
// Close the database connection
$conn->close();
?>
