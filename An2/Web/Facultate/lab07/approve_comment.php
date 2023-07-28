<?php
// Connect to the database (modify with your database credentials)
$servername = "localhost";
$username = "root";
$dbname = "Lab7";
$conn = new mysqli($servername, $username, $password, $dbname);

// Check the database connection
if ($conn->connect_error) {
  die("Conexiunea la baza de date a eșuat: " . $conn->connect_error);
}

// Check if the comment ID is provided via POST
if (isset($_POST["comment"])) {
  $comment = $_POST["comment"];
  $name = $_POST["name"];

  // Update the comment status to approved in the database
  $sql = "UPDATE comments SET valid = '1' WHERE name = '$name' AND comment = '$comment'";

  if ($conn->query($sql) === TRUE) {
    echo "Comentariul a fost aprobat cu succes.";
    header("Location: admin.php");
  } else {
    echo "A apărut o eroare la aprobarea comentariului. Vă rugăm să încercați din nou.";
  }
} else {
  echo "Nu s-a furnizat un ID valid pentru comentariu.";
}

// Close the database connection
$conn->close();
?>
