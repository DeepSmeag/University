<?php

// Get the board and player from the AJAX request
$board = $_POST['board'];
$player = $_POST['player'];

// Find all empty cells on the board
$empty_cells = array_keys($board, '');

// If there are no empty cells, the game is a tie
if (count($empty_cells) == 0) {
    echo 'tie';
    exit();
}

// Otherwise, randomly choose an empty cell and return its index
$move = $empty_cells[array_rand($empty_cells)];
echo $move;

// Update the board with the computer's move
$board[$move] = $player;

?>
