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
$Name = isset($_GET['Name']) ? $_GET['Name'] : '';
$roll = isset($_GET['roll']) ? $_GET['roll'] : '';

// Check if Name or roll is provided
if (empty($Name) && empty($roll)) {
    die("Please provide 'Name' or 'roll' parameter");
}

// Output JSON
$output = array();

// Get matched records from the 'details' table
$sqlDetails = "SELECT
                details.Name AS Name,
                details.email AS email,
                details.phone AS phone,
                details.roll AS roll,
                details.aadhar AS aadhar,
                details.section AS section
            FROM details
            WHERE details.Name = ? OR details.roll = ?";

$stmtDetails = $conn->prepare($sqlDetails);
$stmtDetails->bind_param("ss", $Name, $roll);
$stmtDetails->execute();
$resultDetails = $stmtDetails->get_result();

// Check if there are results in the 'details' table
if ($resultDetails->num_rows > 0) {
    $detailsData = array();
    while ($row = $resultDetails->fetch_assoc()) {
        $detailsData[] = $row;
    }
    $output['details'] = $detailsData;
}

// Get matched records from the 'fees' table
$sqlFees = "SELECT
                fees.Name AS Name,
                fees.roll AS roll,
                fees.amount AS amount
            FROM fees
            WHERE fees.Name = ? OR fees.roll = ?";

$stmtFees = $conn->prepare($sqlFees);
$stmtFees->bind_param("ss", $Name, $roll);
$stmtFees->execute();
$resultFees = $stmtFees->get_result();

// Check if there are results in the 'fees' table
if ($resultFees->num_rows > 0) {
    $feesData = array();
    while ($row = $resultFees->fetch_assoc()) {
        $feesData[] = $row;
    }
    $output['fees'] = $feesData;
}

// Get matched records from the 'parent' table
$sqlParent = "SELECT
                parent.Name AS Name,
                parent.father AS father,
                parent.mother AS mother,
                parent.address AS address,
                parent.phone AS phone,
                parent.occupation AS occupation,
                parent.income AS income,
                parent.roll AS roll
            FROM parent
            WHERE parent.Name = ? OR parent.roll = ?";

$stmtParent = $conn->prepare($sqlParent);
$stmtParent->bind_param("ss", $Name, $roll);
$stmtParent->execute();
$resultParent = $stmtParent->get_result();

// Check if there are results in the 'parent' table
if ($resultParent->num_rows > 0) {
    $parentData = array();
    while ($row = $resultParent->fetch_assoc()) {
        $parentData[] = $row;
    }
    $output['parent'] = $parentData;
}

// Get matched records from the 'admission' table
$sqlAdmission = "SELECT
                admission.Name AS Name,
                admission.course AS course,
                admission.type AS type,
                admission.year AS year,
                admission.enroll AS enroll,
                admission.roll AS roll
            FROM admission
            WHERE admission.Name = ? OR admission.roll = ?";

$stmtAdmission = $conn->prepare($sqlAdmission);
$stmtAdmission->bind_param("ss", $Name, $roll);
$stmtAdmission->execute();
$resultAdmission = $stmtAdmission->get_result();

// Check if there are results in the 'admission' table
if ($resultAdmission->num_rows > 0) {
    $admissionData = array();
    while ($row = $resultAdmission->fetch_assoc()) {
        $admissionData[] = $row;
    }
    $output['admission'] = $admissionData;
}

// Output JSON
if (!empty($output)) {
    echo json_encode($output);
} else {
    echo "No results found";
}

// Close statements and connection
$stmtDetails->close();
$stmtFees->close();
$stmtParent->close();
$stmtAdmission->close();
$conn->close();
?>
