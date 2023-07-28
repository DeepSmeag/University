<?php
// Verificăm dacă a fost trimis un request POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // Prelucrăm și scăpăm de caracterele nedorite înainte de a construi interogarea SQL
  $email = sanitize_input($_POST["email"]);

  // Verificăm dacă adresa de e-mail este validă
  if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    die("Adresă de e-mail invalidă");
  }

  // Generăm un cod unic pentru înregistrare
  $verificationCode = generate_verification_code();

  // Salvăm adresa de e-mail și codul de verificare în baza de date (modificați cu detaliile dvs.)
  $servername = "localhost";
  $username = "root";
  $dbname = "Lab7";
  $conn = new mysqli($servername, $username, $password, $dbname);

  // Verificăm conexiunea la baza de date
  if ($conn->connect_error) {
    die("Conexiunea la baza de date a eșuat: " . $conn->connect_error);
  }
  

  // Construim interogarea SQL pentru inserarea datelor în baza de date
  $sql = "INSERT INTO utilizatori (email, verification_code) VALUES ('$email', '$verificationCode')";

  if ($conn->query($sql) === TRUE) {
    // Trimitem un email cu link-ul de înregistrare către utilizator
    $subject = "Finalizare înregistrare";
    $message = "Bine ai venit!\n\nPentru a-ți finaliza înregistrarea, accesează următorul link: http://localhost/verify.php?code=$verificationCode";
    $headers = "From: muryand12@gmail.com" . "\r\n";
    // echo $message;
    if (mail($email, $subject, $message, $headers)) {
      echo "Un e-mail cu instrucțiuni pentru finalizarea înregistrării a fost trimis la adresa specificată.";
    } else {
      echo "A apărut o eroare la trimiterea e-mailului de verificare. Vă rugăm să încercați din nou.";
    }
  } else {
    echo "A apărut o eroare la înregistrare. Vă rugăm să încercați din nou.";
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

// Funcție pentru generarea unui cod unic de verificare
function generate_verification_code() {
  return bin2hex(random_bytes(16));
}
?>
