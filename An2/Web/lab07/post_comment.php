<?php
// Verificăm dacă a fost trimis un request POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // Prelucrăm și scăpăm de caracterele nedorite înainte de a construi interogarea SQL
  $name = sanitize_input($_POST["name"]);
  $comment = sanitize_input($_POST["comment"]);

  $servername = "localhost";
  $username = "root";
  $dbname = "Lab7";
  $conn = new mysqli($servername, $username, $password, $dbname);

  // Verificăm conexiunea la baza de date
  if ($conn->connect_error) {
    die("Conexiunea la baza de date a eșuat: " . $conn->connect_error);
  }

  // Construim interogarea SQL pentru verificarea utilizatorului în baza de date
  $sql = "INSERT INTO comments (name, comment) VALUES ('$name', '$comment')";
  
  if ($conn->query($sql) === TRUE) {
    echo "Comentariul a fost adăugat cu succes!";
  } else {
    echo "Eroare: " ;
  }

  // Închidem conexiunea la baza de date
  $conn->close();

  // Store the comment in the database or any other storage medium (implement your logic here)
  // ...

  // Redirect the user back to the article page
  header("Location: p6.php");
  exit();
} else {
  die("Acces neautorizat. Vă rugăm să trimiteți formularul de comentariu.");
}

// Funcție pentru eliminarea caracterelor nedorite și prevenirea atacurilor de tip SQL injection și XSS
function sanitize_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  // Perform additional sanitization or validation as per your requirements
  return $data;
}
?>
