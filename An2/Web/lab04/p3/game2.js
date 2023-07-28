var names = ['grape', 'watermelon', 'mango', 'strawberry', 'kiwi', 'lemon', 'apple', 'pear'];
var memArray = [];
var memValues = [];
var memTileId = [];
var tilesFlipped = 0;

$(document).ready(function () {
    initMemory();
    genBoard();
});

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
};

function genBoard() {
    tilesFlipped = 0;
    var output = '';
    memArray.shuffle();
    for (var i = 0; i < memArray.length; i++) {
        output += '<div class="image" id="covered"></div>';
    }
    $('#memGame').html(output);
    $('.image').click(function () {
        memFlipTile(this, memArray[$('.image').index(this)]);
    });
}

function memFlipTile(tile, val) {
    if (tile.id === "covered" && memValues.length < 2) {
        $(tile).attr('id', val);
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
                        $('#memGame').html("");
                        genBoard();
                    }
                    setTimeout(reset, 500);
                }
            } else {
                function flip2Back() {
                    var tile_1 = document.getElementById(memTileId[0]);
                    var tile_2 = document.getElementById(memTileId[1]);
                    $(tile_1).attr('id', 'covered');
                    $(tile_2).attr('id', 'covered');
                    memValues = [];
                    memTileId = [];
                }
                setTimeout(flip2Back, 700);
            }
        }
    }
}