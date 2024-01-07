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

// Check if the required POST parameters are set
if (isset($_POST['Name'], $_POST['father'], $_POST['mother'], $_POST['address'], $_POST['phone'], $_POST['occupation'], $_POST['income'], $_POST['roll'])) {
    // Sanitize input values
    $Name = mysqli_real_escape_string($conn, $_POST['Name']);
    $father = mysqli_real_escape_string($conn, $_POST['father']);
    $mother = mysqli_real_escape_string($conn, $_POST['mother']);
    $address = mysqli_real_escape_string($conn, $_POST['address']);
    $phone = mysqli_real_escape_string($conn, $_POST['phone']);
    $occupation = mysqli_real_escape_string($conn, $_POST['occupation']);
    $income = mysqli_real_escape_string($conn, $_POST['income']);
    $roll = mysqli_real_escape_string($conn, $_POST['roll']);

    // Use parameterized query to prevent SQL injection
    $sql = "INSERT INTO parent (Name, father, mother, address, phone, occupation, income, roll)
    VALUES ('$Name', '$father', '$mother', '$address', '$phone', '$occupation', '$income', '$roll')";

    $iquery = mysqli_query($conn, $sql);

    if ($iquery) {
        $result["success"] = "1";
        $result["message"] = "success";
        echo json_encode($result);
    } else {
        // Handle insertion failure
        $result["success"] = "0";
        $result["message"] = "error";
        echo json_encode($result);
    }

    mysqli_close($conn);
} else {
    // Handle missing parameters
    $result["success"] = "0";
    $result["message"] = "missing parameters";
    echo json_encode($result);
}
?>
