<?php
// Get the directory path from the AJAX request
$directory = $_GET["directory"];

// Get the directory contents
$contents = array();
$files = glob($directory . "*");
foreach ($files as $file) {
  if (is_dir($file)) {
    $contents[] = array("name" => basename($file), "type" => "directory");
  } else {
    $contents[] = array("name" => basename($file), "type" => "file");
  }
}

// Return the directory contents as JSON
echo json_encode($contents);
?>
