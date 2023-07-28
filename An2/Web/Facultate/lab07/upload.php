<?php
// Verificăm dacă utilizatorul este autentificat
session_start();

if (!isset($_SESSION["user_id"])) {
  die("Acces neautorizat. Vă rugăm să vă autentificați.");
}

// Verificăm dacă a fost trimis un request POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // Verificăm dacă a fost încărcat un fișier valid
  if (isset($_FILES["photo"]) && $_FILES["photo"]["error"] == UPLOAD_ERR_OK) {
    // Verificăm tipul de fișier
    $fileType = $_FILES["photo"]["type"];
    if ($fileType == "image/jpeg" || $fileType == "image/png") {
      // Prelucrăm și scăpăm de caracterele nedorite înainte de a construi interogarea SQL
      $userId = $_SESSION["user_id"];
      $photoName = sanitize_input($_FILES["photo"]["name"]);

      // Generăm un nume de fișier unic pentru a evita conflictele
      $fileName = uniqid() . "_" . $photoName;

      // Salvăm fișierul pe server
      $uploadDir = "uploads/"; // Directorul în care vor fi salvate fișierele (asigurați-vă că aveți permisiunile necesare)
      $uploadPath = $uploadDir . $fileName;
      if (move_uploaded_file($_FILES["photo"]["tmp_name"], $uploadPath)) {
        // Salvăm numele fișierului în baza de date (modificați cu detaliile dvs.)
        $servername = "localhost";
        $username = "root";
        $dbname = "Lab7";
        $conn = new mysqli($servername, $username, $password, $dbname);

        // Verificăm conexiunea la baza de date
        if ($conn->connect_error) {
          die("Conexiunea la baza de date a eșuat: " . $conn->connect_error);
        }

        // Construim interogarea SQL pentru inserarea datelor în baza de date
        $sql = "INSERT INTO photos (user_id, name) VALUES ('$userId', '$fileName')";
        if ($conn->query($sql) === TRUE) {
          echo "Poza a fost încărcată cu succes.";
        } else {
          echo "A apărut o eroare la încărcarea pozei. Vă rugăm să încercați din nou.";
        }

        // Închidem conexiunea la baza de date
        $conn->close();
      } else {
        echo "A apărut o eroare la încărcarea pozei. Vă rugăm să încercați din nou.";
      }
    } else {
      echo "Tip de fișier neacceptat. Vă rugăm să încărcați doar fișiere de tip JPEG sau PNG.";
    }
  } else {
    echo "A apărut o eroare la încărcarea pozei. Vă rugăm să încercați din nou.";
  }
}

// Funcție pentru eliminarea caracterelor nedorite și prevenirea atacurilor de tip SQL injection
function sanitize_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>
