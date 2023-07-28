// Define the size of the puzzle grid
const gridSize = 3;

// Define the game state
var puzzle = createPuzzle(gridSize);
let emptyCellRow = gridSize - 1;
let emptyCellCol = gridSize - 1;
shufflePuzzle();
console.log(puzzle);

document.addEventListener('keydown', function (event) {
    console.log('key pressed');
    handleArrowKey(event);
});

// Create the puzzle grid and add it to the web page
for (let i = 0; i < gridSize; i++) {
    for (let j = 0; j < gridSize; j++) {
        const cell = document.createElement('div');
        cell.classList.add('cell');
        cell.textContent = puzzle[i][j];
        cell.dataset.row = i;
        cell.dataset.col = j;
        // cell.addEventListener('click', handleCellClick);
        document.getElementById('puzzle-container').appendChild(cell);
    }
}

function createPuzzle(size) {
    var puzzle = [];
    var count = 1;

    for (let i = 0; i < size; i++) {
        var row = [];
        for (let j = 0; j < size; j++) {
            console.log('count:', count);
            if (i === size - 1 && j === size - 1) {
                row.push(null); // use null to represent the empty space
            } else {
                row.push(count);
                count++;
            }
        }
        puzzle.push(row);
    }

    console.log('puzzle:', puzzle);
    return puzzle;
}

// Function to handle keyboard arrow key events
function handleArrowKey(event) {
    const rowOffset = event.key === 'ArrowUp' ? -1 : event.key === 'ArrowDown' ? 1 : 0;
    const colOffset = event.key === 'ArrowLeft' ? -1 : event.key === 'ArrowRight' ? 1 : 0;

    const targetRow = emptyCellRow + rowOffset;
    const targetCol = emptyCellCol + colOffset;

    if (canMove(targetRow, targetCol)) {
        movePiece(targetRow, targetCol);
        updatePuzzleGrid();
        if (isSolved()) {
            showMessage('Congratulations, you solved the puzzle!');
        }
    }
}
// Function to check if a puzzle piece can be moved
function canMove(targetRow, targetCol) {
    return targetRow >= 0 &&
        targetRow < gridSize &&
        targetCol >= 0 &&
        targetCol < gridSize &&
        ((targetRow === emptyCellRow && Math.abs(targetCol - emptyCellCol) === 1) ||
            (targetCol === emptyCellCol && Math.abs(targetRow - emptyCellRow) === 1));
}

function movePiece(targetRow, targetCol) {
    if (targetRow < 0 || targetRow >= gridSize || targetCol < 0 || targetCol >= gridSize) {
        return; // invalid target position, do nothing
    }

    puzzle[targetRow][targetCol] = puzzle[emptyCellRow][emptyCellCol];
    puzzle[emptyCellRow][emptyCellCol] = null;
    emptyCellRow = targetRow;
    emptyCellCol = targetCol;
}

// Function to check if the puzzle is solved
function isSolved() {
    let count = 1;

    for (let i = 0; i < gridSize; i++) {
        for (let j = 0; j < gridSize; j++) {
            if (i === gridSize - 1 && j === gridSize - 1) {
                // the last cell should be null
                if (puzzle[i][j] !== null) {
                    return false;
                }
            } else {
                // the other cells should be numbered correctly
                if (puzzle[i][j] !== count) {
                    return false;
                }
                count++;
            }
        }
    }

    return true;
}

// Function to update the puzzle grid
function updatePuzzleGrid() {
    const cells = document.querySelectorAll('.cell');

    cells.forEach(cell => {
        const row = parseInt(cell.dataset.row);
        const col = parseInt(cell.dataset.col);
        cell.textContent = puzzle[row][col];
    });

}

// Function to show a message to the user
function showMessage(text) {
    document.getElementById('message').textContent = text;
}
function shufflePuzzle() {
    for (let i = 0; i < 100; i++) {
        const directions = ['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight'];
        const randomDirection = directions[Math.floor(Math.random() * directions.length)];

        const rowOffset = randomDirection === 'ArrowUp' ? -1 : randomDirection === 'ArrowDown' ? 1 : 0;
        const colOffset = randomDirection === 'ArrowLeft' ? -1 : randomDirection === 'ArrowRight' ? 1 : 0;

        const targetRow = emptyCellRow + rowOffset;
        const targetCol = emptyCellCol + colOffset;

        if (canMove(targetRow, targetCol)) {
            movePiece(targetRow, targetCol);
        }
    }
    updatePuzzleGrid();
}