<?php
// Verificăm dacă există un parametru "code" în URL
if (isset($_GET["code"])) {
  $verificationCode = $_GET["code"];

  // Verificăm codul de verificare în baza de date (modificați cu detaliile dvs.)
  $servername = "localhost";
  $username = "root";
  $dbname = "Lab7";
  $conn = new mysqli($servername, $username, $password, $dbname);

  // Verificăm conexiunea la baza de date
  if ($conn->connect_error) {
    die("Conexiunea la baza de date a eșuat: " . $conn->connect_error);
  }

  // Construim interogarea SQL pentru verificarea codului de verificare
  $sql = "SELECT * FROM utilizatori WHERE verification_code = '$verificationCode'";

  $result = $conn->query($sql);

  if ($result->num_rows > 0) {
    // Actualizăm starea utilizatorului ca fiind verificat
    $row = $result->fetch_assoc();
    $email = $row["email"];

    $sql = "UPDATE utilizatori SET verified = 1 WHERE email = '$email'";
    if ($conn->query($sql) === TRUE) {
      echo "Înregistrarea a fost finalizată cu succes. Vă puteți autentifica.";
    } else {
      echo "A apărut o eroare la finalizarea înregistrării. Vă rugăm să încercați din nou.";
    }
  } else {
    echo "Codul de verificare nu este valid.";
  }

  // Închidem conexiunea la baza de date
  $conn->close();
} else {
  echo "Nu s-a furnizat un cod de verificare.";
}
?>
