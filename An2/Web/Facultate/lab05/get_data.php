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

// retrieve the data associated with the specified key from the database
$stmt = $db->prepare('SELECT attribute1, attribute2, attribute3 FROM mytable WHERE id = :id');
$stmt->execute(array('id' => $_GET['key']));
$data = $stmt->fetch(PDO::FETCH_ASSOC);

// send the data as a JSON response
header('Content-Type: application/json');
echo json_encode($data);
?>
