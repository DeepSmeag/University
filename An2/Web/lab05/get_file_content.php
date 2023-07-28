<?php
// Get the file path from the AJAX request
$file = $_GET["file"];

// Get the file content
$content = file_get_contents($file);

// Return the file content
echo $content;
?>
