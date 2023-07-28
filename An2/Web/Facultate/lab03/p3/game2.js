var names = ['grape', 'watermelon', 'mango', 'strawberry', 'kiwi', 'lemon', 'apple', 'pear'];
var memArray = [];
var memValues = [];
var memTileId = [];
var tilesFlipped = 0;

initMemory();
function initMemory() {
    for (var i = 0; i < 16; i++) {
        memArray[i] = names[(Math.floor(i / 2))];
    }
}

Array.prototype.shuffle = function () {
    var i = this.length, j, temp;
    while (--i > 0) {
        j = Math.floor(Math.random() * (i + 1));
        temp = this[j];
        this[j] = this[i];
        this[i] = temp;
    }
}

function genBoard() {
    tilesFlipped = 0;
    var output = '';
    memArray.shuffle();
    for (var i = 0; i < memArray.length; i++) {
        output += '<div class="image" id="covered" onclick="memFlipTile(this,\'' + memArray[i] + '\')"></div>';
    }
    document.getElementById('memGame').innerHTML = output;
}

function memFlipTile(tile, val) {
    if (tile.id === "covered" && memValues.length < 2) {
        // tile.style.background = '#FFF';
        // tile.innerHTML = '<div class="image" id="' + val + '" onclick="memFlipTile(this,\'' + memArray[i] + '\')"></div>';
        tile.id = val;
        if (memValues.length == 0) {
            memValues.push(val);
            memTileId.push(tile.id);
        } else if (memValues.length == 1) {
            memValues.push(val);
            memTileId.push(tile.id);
            if (memValues[0] == memValues[1]) {
                tilesFlipped += 2;
                memValues = [];
                memTileId = [];
                if (tilesFlipped == memArray.length) {
                    function reset() {
                        alert("Board cleared... generating new board");
                        document.getElementById('memGame').innerHTML = "";
                        genBoard();
                    }
                    setTimeout(reset, 500);
                }
            } else {
                function flip2Back() {
                    var tile_1 = document.getElementById(memTileId[0]);
                    var tile_2 = document.getElementById(memTileId[1]);
                    // tile_1.style.background = 'crimson';
                    // tile_1.innerHTML = "";
                    // tile_2.style.background = 'crimson';
                    // tile_2.innerHTML = "";
                    tile_1.id = "covered";
                    tile_2.id = "covered";
                    memValues = [];
                    memTileId = [];
                }
                setTimeout(flip2Back, 700);
            }
        }
    }
}