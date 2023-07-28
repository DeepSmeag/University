<?php
// Verificăm dacă a fost trimis un request POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // Prelucrăm și scăpăm de caracterele nedorite înainte de a construi interogarea SQL
  $email = sanitize_input($_POST["email"]);

  // Verificăm dacă adresa de e-mail este validă
  if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    die("Adresă de e-mail invalidă");
  }

  // Verificăm dacă utilizatorul există în baza de date (modificați cu detaliile dvs.)
  $servername = "localhost";
  $username = "root";
  $dbname = "Lab7";
  $conn = new mysqli($servername, $username, $password, $dbname);

  // Verificăm conexiunea la baza de date
  if ($conn->connect_error) {
    die("Conexiunea la baza de date a eșuat: " . $conn->connect_error);
  }

  // Construim interogarea SQL pentru verificarea utilizatorului în baza de date
  $sql = "SELECT * FROM utilizatori WHERE email='$email'";
  $result = $conn->query($sql);

  if ($result->num_rows > 0) {
    // Utilizatorul există în baza de date, redirecționăm către pagina de profil
    session_start();
    $_SESSION["user_id"] = $email;
    alert("Autentificare reușită!");
    exit();
  } else {
    echo "Autentificare eșuată. Vă rugăm să verificați adresa de e-mail.";
  }

  // Închidem conexiunea la baza de date
  $conn->close();
}

// Funcție pentru eliminarea caracterelor nedorite și prevenirea atacurilor de tip SQL injection
function sanitize_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>
