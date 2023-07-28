<?php
// Verificăm dacă utilizatorul este autentificat
session_start();

if (!isset($_SESSION["user_id"])) {
  die("Acces neautorizat. Vă rugăm să vă autentificați.");
}

// Obținem pozele utilizatorului din baza de date și le afișăm
$userId = $_SESSION["user_id"];

// Accesați baza de date și obțineți pozele utilizatorului (modificați cu detaliile dvs.)
$servername = "localhost";
  $username = "root";
  $dbname = "Lab7";
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificăm conexiunea la baza de date
if ($conn->connect_error) {
  die("Conexiunea la baza de date a eșuat: " . $conn->connect_error);
}

// Construim interogarea SQL pentru a obține pozele utilizatorului
$sql = "SELECT * FROM photos WHERE user_id = $userId";

$result = $conn->query($sql);

// Verificăm dacă există poze în baza de date
if ($result->num_rows > 0) {
  while ($row = $result->fetch_assoc()) {
    $photoName = $row["name"];
    echo "<img src='data:image/jpeg;base64," . base64_encode($row["data"]) . "' alt='$photoName' width='200'>";
    echo "<form method='post' action='delete_photo.php'>";
    echo "<input type='hidden' name='photo_id' value='" . $row["id"] . "'>";
    echo "<button type='submit'>Ștergeți</button>";
    echo "</form>";
  }
} else {
  echo "Nu există poze încărcate.";
}

// Închidem conexiunea la baza de date
$conn->close();
?>
