<!DOCTYPE html>
<html>
<head>
  <title>Profilul utilizatorului</title>
  <style>
    /* Stilizare pentru aspectul paginii */
    body {
      font-family: Arial, sans-serif;
    }

    img {
      margin: 10px;
    }

    form {
      margin-top: 20px;
    }

    button {
      margin-top: 10px;
    }

    /* Ascunde formularul de încărcare inițial */
    #upload-form {
      display: none;
    }

    /* Afiseaza formularul de încărcare când butonul este apăsat */
    #upload-button:checked ~ #upload-form {
      display: block;
    }
  </style>
</head>
<body>
  <h1>Profilul utilizatorului</h1>

  <h2>Pozele Incarcate:</h2>
  <div id="photos">
    <?php
    session_start();
    // Afiseaza pozele incarcate
    $servername = "localhost";
    $username = "root";
    $dbname = "Lab7";
    $conn = new mysqli($servername, $username, $password, $dbname);

    // Verificăm conexiunea la baza de date
    if ($conn->connect_error) {
      die("Conexiunea la baza de date a eșuat: " . $conn->connect_error);
    }

    // Construim interogarea SQL pentru a prelua numele pozelor
    $userId = $_SESSION["user_id"];
    $sql = "SELECT name FROM photos WHERE user_id = '$userId'";
    echo $user_id;
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
      while ($row = $result->fetch_assoc()) {
        $photoName = $row["name"];
        echo '<div class="photo-wrapper">';
        echo '<img src="uploads/' . $photoName . '" alt="Poza">';
        echo '<form method="post" action="delete.php">';
        echo '<input type="hidden" name="photoName" value="' . $photoName . '">';
        echo '<button type="submit">Șterge</button>';
        echo '</form>';
        echo '</div>';
      }
    } else {
      echo "Nu există poze încărcate.";
    }

    // Închidem conexiunea la baza de date
    $conn->close();
    ?>
  </div>

  <input type="checkbox" id="upload-button">
  <label for="upload-button">Incarcati o poza</label>

  <form method="post" action="upload.php" enctype="multipart/form-data" id="upload-form">
    <label for="photo">Selectati o poza:</label>
    <input type="file" name="photo" id="photo" accept="image/*" required>

    <button type="submit">Incarcati</button>
  </form>
</body>
</html>
