<?php
// Start the session
session_start();

// Retrieve the selected user_id from the form submission
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  $user_id = $_POST["user_id"];

  // Perform validation or additional processing as needed

  // Set the user_id in the session
  $_SESSION["user_id"] = $user_id;

  // Redirect to the profile page or any other desired page
  header("Location: profilehtml.php");
  exit();
}
?>
