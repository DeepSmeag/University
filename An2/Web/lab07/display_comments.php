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

  // Retrieve approved comments
  $sql = "SELECT * FROM comments WHERE valid = 1";
  $result = $conn->query($sql);

  if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
      echo '<div class="comment">';
      echo '<div class="name">' . htmlspecialchars($row["name"]) . '</div>';
      echo '<div class="content">' . htmlspecialchars($row["comment"]) . '</div>';
      echo '</div>';
    }
  } else {
    echo 'Nu există comentarii aprobate.';
  }

  // Close the database connection
  $conn->close();
  ?>