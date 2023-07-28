<!DOCTYPE html>
<html>
<head>
  <title>Pagina administrator</title>
  <style>
    /* Stilizare pentru aspectul paginii */
    body {
      font-family: Arial, sans-serif;
    }

    h1 {
      margin-bottom: 20px;
    }

    .comment {
      margin-bottom: 20px;
      border: 1px solid #ccc;
      padding: 10px;
    }

    .comment .name {
      font-weight: bold;
    }

    .comment .content {
      margin-top: 5px;
    }

    .approval-form {
      margin-top: 20px;
    }

    .approval-form input[type="hidden"] {
      display: none;
    }

    .approval-form input[type="submit"] {
      margin-top: 10px;
    }
  </style>
</head>
<body>
  <h1>Pagina administrator</h1>

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

  // Retrieve unapproved comments
  $sql = "SELECT * FROM comments WHERE valid = '0'";
  $result = $conn->query($sql);

  if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
      echo '<div class="comment">';
      echo '<div class="name">' . htmlspecialchars($row["name"]) . '</div>';
      echo '<div class="content">' . htmlspecialchars($row["comment"]) . '</div>';
      echo '<form class="approval-form" method="post" action="approve_comment.php">';
      echo '<input type="hidden" name="name" value="' . $row["name"] . '">';
      echo '<input type="hidden" name="comment" value="' . $row["comment"] . '">';
      echo '<input type="submit" value="Aproba comentariu">';
      echo '</form>';
      echo '</div>';
    }
  } else {
    echo 'Nu există comentarii de aprobat.';
  }

  // Close the database connection
  $conn->close();
  ?>

</body>
</html>
