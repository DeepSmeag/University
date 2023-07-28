<?php
// connect to the database
$dsn = 'mysql:host=localhost;dbname=web';
$username = 'root';
$password = '';
$options = array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION);
try {
  $db = new PDO($dsn, $username, $password, $options);
} catch (PDOException $e) {
  echo 'Connection failed: ' . $e->getMessage();
  exit();
}

// retrieve the keys from the database
$stmt = $db->query('SELECT id FROM mytable');
$keys = $stmt->fetchAll(PDO::FETCH_COLUMN);

// send the keys as a JSON response
header('Content-Type: application/json');
echo json_encode($keys);
?>
