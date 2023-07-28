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

// update the data associated with the specified key in the database
$stmt = $db->prepare('UPDATE mytable SET attribute1 = :attribute1, attribute2 = :attribute2, attribute3 = :attribute3 WHERE id = :id');
$stmt->execute(array(
  'id' => $_POST['key'],
  'attribute1' => $_POST['attribute1'],
  'attribute2' => $_POST['attribute2'],
  'attribute3' => $_POST['attribute3']
));

// send a JSON response indicating success
header('Content-Type: application/json');
echo json_encode(array('message' => 'Data saved successfully.'));
?>
