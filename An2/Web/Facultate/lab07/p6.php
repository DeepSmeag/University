<!DOCTYPE html>
<html>
<head>
  <title>Articol</title>
  <style>
    /* Stilizare pentru aspectul paginii */
    body {
      font-family: Arial, sans-serif;
    }

    label {
      display: block;
      margin-top: 10px;
    }

    button {
      margin-top: 10px;
    }

    .comment {
      margin-top: 20px;
      border: 1px solid #ccc;
      padding: 10px;
    }
  </style>
</head>
<body>
  <h1>Articol</h1>

  <!-- Afisare articol -->
  <div>
    <h2>Titlul articolului</h2>
    <p>Continutul articolului.</p>
  </div>

  <!-- Formular pentru adaugarea unui comentariu -->
  <h2>Adauga un comentariu</h2>
  <form method="post" action="post_comment.php">
    <label for="name">Nume:</label>
    <input type="text" name="name" id="name" required>

    <label for="comment">Comentariu:</label>
    <textarea name="comment" id="comment" rows="4" required></textarea>

    <button type="submit">Posteaza comentariu</button>
  </form>

  <!-- Afisarea comentariilor aprobate -->
  <h2>Comentarii</h2>
  <div id="comments">
    <?php include 'display_comments.php'; ?>
  </div>

</body>
</html>
