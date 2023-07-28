<?php
// Connect to database
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "web";
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
	die("Connection failed: " . $conn->connect_error);
}

// Get column and filter values from AJAX request
$column = $_POST["column"];
$filter = $_POST["filter"];

// Build SQL query based on selected column and filter value
$sql = "SELECT * FROM products WHERE $column LIKE '%$filter%'";

// Execute SQL query and display results
$result = $conn->query($sql);
if ($result->num_rows > 0) {
	while($row = $result->fetch_assoc()) {
		echo "<div>";
		echo "<h2>" . $row["name"] . "</h2>";
		echo "<p>" . $row["description"] . "</p>";
		echo "<p>Manufacturer: " . $row["manufacturer"] . "</p>";
		echo "<p>Processor: " . $row["processor"] . "</p>";
		echo "<p>Memory: " . $row["memory"] . "</p>";
		echo "<p>Capacity: " . $row["capacity"] . "</p>";
		echo "<p>Graphics card: " . $row["gpu"] . "</p>";
		echo "</div>";
	}
} else {
	echo "<p>No results found.</p>";
}

// Close database connection
$conn->close();
?>
