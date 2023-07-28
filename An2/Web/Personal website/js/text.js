// function([string1, string2],target id,[color1,color2])    
typeWriter()


function consoleText(words, id, colors) {
  if (colors === undefined) colors = ['#fff'];
  var visible = true;
  var con = document.getElementById('console');
  var letterCount = 1;
  var x = 1;
  var waiting = false;
  var target = document.getElementById(id)
  target.setAttribute('style', 'color:' + colors[0])
  window.setInterval(function() {

    if (letterCount === 0 && waiting === false) {
      waiting = true;
      target.innerHTML = words[0].substring(0, letterCount)
      window.setTimeout(function() {
        var usedColor = colors.shift();
        colors.push(usedColor);
        var usedWord = words.shift();
        words.push(usedWord);
        x = 1;
        target.setAttribute('style', 'color:' + colors[0])
        letterCount += x;
        waiting = false;
      }, 700)
    } else if (letterCount === words[0].length + 1 && waiting === false) {
      waiting = true;
      window.setTimeout(function() {
        x = -1;
        letterCount += x;
        waiting = false;
      }, 20000) /**10000 */
    } else if (waiting === false) {
      target.innerHTML = words[0].substring(0, letterCount)
      letterCount += x;
    }
  }, 50) /* 60*/ 
  window.setInterval(function() {
    if (visible === true) {
      con.className = 'console-underscore hidden'
      visible = false;

    } else {
      con.className = 'console-underscore'

      visible = true;
    }
  }, 400)
}




function typeWriter() {
  var i = 0;
  var speed = 50;
  var finished = false;
  var txt = 'Hi there! I\'m Cosmin.';
  var first_line = window.setInterval(function() {
    if (i < txt.length) {
      document.getElementById("typewriter").innerHTML += txt.charAt(i);
      i++;
      // setTimeout(typeWriter, speed);
    }
    else {
      finished = true;
      clearInterval(first_line);
    }
  }, 50)

  var second_line = window.setInterval(function() {
    if(finished === true) {
      consoleText(['What were you looking for?', 'There\'s only two buttons... <br> Not hard to pick one',
      'You know... <br> the background is interactable!',
      'Still haven\'t decided? <br> Ok, I\'ll help you out.',
      'On the left you wil find <br> things like experience, hobbies and projects...',
      'The right one goes to my blog:<br>personal lessons, things I find motivating, or general tech.'], 'text', ['lightblue','lightblue', 'lightblue', 'lightblue', 'lightblue'])
      clearInterval(second_line);
    }
  }, 60)
  return true;
  
}
