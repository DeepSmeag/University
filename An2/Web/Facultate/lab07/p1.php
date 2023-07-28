<?php
// Verificăm dacă a fost trimis un request POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // Prelucrăm și scăpăm de caracterele nedorite înainte de a construi interogarea SQL
  $origin = sanitize_input($_POST["origin"]);
  $destination = sanitize_input($_POST["destination"]);
  $directOnly = isset($_POST["direct_only"]) ? 1 : 0;

  // Conectarea la baza de date (modificați cu detaliile dvs.)
  $servername = "localhost";
  $username = "root";
  $dbname = "Lab7";
  $conn = new mysqli($servername, $username, $password, $dbname);

  // Verificăm conexiunea la baza de date
  if ($conn->connect_error) {
    die("Conexiunea la baza de date a eșuat: " . $conn->connect_error);
  }

  // Construim interogarea SQL în funcție de opțiunile selectate
  $sql = "SELECT * FROM trenuri WHERE localitate_plecare = '$origin' AND localitate_sosire = '$destination'";
  if ($directOnly) {
    $sql .= " AND tip_tren = 'direct'";
  }

  // Executăm interogarea și obținem rezultatele
  $result = $conn->query($sql);

  // Afisam rezultatele căutării
  if ($result->num_rows > 0) {
    echo "<h2>Rezultate:</h2>";
    echo "<table>";
    echo "<tr><th>Nr. tren</th><th>Tip tren</th><th>Localitate plecare</th><th>Localitate sosire</th><th>Ora plecare</th><th>Ora sosire</th></tr>";
    while ($row = $result->fetch_assoc()) {
      echo "<tr>";
      echo "<td>".$row["nr_tren"]."</td>";
      echo "<td>".$row["tip_tren"]."</td>";
      echo "<td>".$row["localitate_plecare"]."</td>";
      echo "<td>".$row["localitate_sosire"]."</td>";
      echo "<td>".$row["ora_plecare"]."</td>";
      echo "<td>".$row["ora_sosire"]."</td>";
      echo "</tr>";
    }
    echo "</table>";
  } else {
    echo "Nu s-au găsit trenuri disponibile pentru căutarea selectată.";
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
