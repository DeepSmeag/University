<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "web";

$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// Determine the starting position based on the POST data
$start = intval($_POST["position"]);

// Fetch the next three records from the database
$sql = "SELECT * FROM persoana LIMIT $start, 3";
$result = $conn->query($sql);

// Convert the results to an array of associative arrays
$records = array();
if ($result->num_rows > 0) {
  while($row = $result->fetch_assoc()) {
    $record = array(
      "Nume" => $row["nume"],
      "Prenume" => $row["prenume"],
      "Telefon" => $row["telefon"],
      "Email" => $row["email"]
    );
    array_push($records, $record);
  }
}

// Return the results as JSON data
echo json_encode($records);

// Close the database connection
$conn->close();

?>
