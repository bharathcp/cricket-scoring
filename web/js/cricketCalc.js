var scorecard="";
var score=0

/**
    @param {String} key
    Appends the key to the scorecard, and adds it to the total score.
    The key must be numeric or a '.'.
*/
function enter(key) { 
    scorecard = scorecard + key;
    document.getElementById('scorecard').innerHTML = scorecard
    if (key != '.') score = score + key;
    document.getElementById('score').innerHTML = score;
}

/**
    Removes the last entry from  the scorecard, and subtracts it from the total score.
*/
function backspace() {
    var previousBall = scorecard.charAt(scorecard.length - 1);
    score = score - previousBall;
    scorecard = scorecard.substring(0, scorecard.length);
    document.getElementById('scorecard').innerHTML = scorecard;
    document.getElementById('score').innerHTML = score;
}

function reset() {
    scorecard = '';
    score = 0;
    document.getElementById('scorecard').innerHTML = scorecard;
    document.getElementById('score').innerHTML = score;
}