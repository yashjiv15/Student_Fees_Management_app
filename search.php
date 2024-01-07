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

    // Get search term from query string
    $Name = isset($_GET['Name']) ? $_GET['Name'] : '';
    $roll = isset($_GET['roll']) ? $_GET['roll'] : '';

    // Check if the parameters are set
    if (empty($Name) && empty($roll)) {
        die("Error: Name and roll parameters are missing");
    }

    // Set content type to JSON
    header('Content-Type: application/json');

    // Get matched records from the 'details' table using prepared statements
    $sqldetails = $conn->prepare("SELECT * FROM details WHERE details.Name = ? OR details.roll = ?");
    $sqldetails->bind_param("ss", $Name, $roll);
    $sqldetails->execute();
    $resultdetails = $sqldetails->get_result();

    // Output JSON
    $output = array();

    // Check if there are results in the 'details' table
    if ($resultdetails->num_rows > 0) {
        $detailsData = array();
        while ($row = $resultdetails->fetch_assoc()) {
            // Include the image URL in the JSON response
            $row['image_url'] = "http://192.168.1.7/path/to/your/image/" . $row['image'];
            $detailsData[] = $row;
        }
        $output['details'] = $detailsData;
    } else {
        // Handle the case when no results are found
        $output['details'] = array(); // Set to an empty array if no results are found
    }

    // Output JSON
    echo json_encode($output);

    // Close the database connection
    $conn->close();
    ?>