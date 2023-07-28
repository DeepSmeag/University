<?php
// Verificăm dacă utilizatorul este autentificat
session_start();

if (!isset($_SESSION["user_id"])) {
  die("Acces neautorizat. Vă rugăm să vă autentificați.");
}

// Verificăm dacă a fost trimis un request POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // Verificăm dacă a fost trimis un id de poză valid
  if (isset($_POST["photoName"])) {
    $photoName = $_POST["photoName"];

    // Ștergem poza din baza de date și de pe server (modificați cu detaliile dvs.)
    $servername = "localhost";
    $username = "root";
    $dbname = "Lab7";
    $conn = new mysqli($servername, $username, $password, $dbname);

    // Verificăm conexiunea la baza de date
    if ($conn->connect_error) {
      die("Conexiunea la baza de date a eșuat: " . $conn->connect_error);
    }

    // Construim interogarea SQL pentru ștergerea pozei din baza de date
    $sql = "DELETE FROM photos WHERE name = '$photoName'";
    if ($conn->query($sql) === TRUE) {
      // Ștergem fișierul de pe server
      $uploadDir = "uploads/"; // Directorul în care se află fișierele (asigurați-vă că aveți permisiunile necesare)
      $filePath = $uploadDir . $photoName;
      if (unlink($filePath)) {
        echo "Poza a fost ștearsă cu succes.";
      } else {
        echo "A apărut o eroare la ștergerea pozei.";
      }
    } else {
      echo "A apărut o eroare la ștergerea pozei.";
    }

    // Închidem conexiunea la baza de date
    $conn->close();
  } else {
    echo "Id de poză invalid.";
  }
} else {
  echo "Acces neautorizat.";
}
?>
