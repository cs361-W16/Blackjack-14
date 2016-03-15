//Image size and gap css constants
const IMAGE_HEIGHT = 200;
const IMAGE_GAP = 40;

//HTML element id constants
const PLAYER_BETTING_STATUS_ID = "PlayerBettingStatus";
const GAME_OPTIONS_ID = "GameOptions";
const DEALER_ID = "Dealer";
const PLAYER_ID = "Player";

//Game constants
const DELAY = 1000;

//Game variables
var blackjack;
var dealersTurn; //Dealer's turn looping function
window.onload=function(){
    blackjack = "";
    dealersTurn = "";

    $.getJSON("blackjackInitialization", function(data) {
        blackjack = data;
        displayGame();
    });
};

//Display the game
function displayGame(){
    displayPlayerBettingStatus();
    displayGameOptions();
    displayDealerHand();
    displayPlayerHands();

    //Status/resolution messages...

    //Error states...
    if(blackjack.errorState){
        $('#errorStateModal').modal('show');
    }

    if(blackjack.dealerTurnInProgress){
        dealersTurn = setInterval(function() {dealerAction();}, DELAY);
    }
}

//Display Player betting status horizontally
function displayPlayerBettingStatus(){
    //Get containing element and reset inner html
    var playerBettingStatus = document.getElementById(PLAYER_BETTING_STATUS_ID);
    //Add balance
    var balance = "<span class='pull-left'>Balance: " + blackjack.playerBalance + "</span>";
    playerBettingStatus.innerHTML = balance;
    //Add a bet for each hand
    var bet = "";
    for(var i = 0; i < blackjack.playerHands.length; i++){
        bet = "<span class='pull-left'>Bet: " + blackjack.playerHands[i].bet + "</span>";
        playerBettingStatus.innerHTML += bet;
    }
}

//Display game options vertically
function displayGameOptions(){
    //Get containing element and reset inner html
    var gameOptions = document.getElementById(GAME_OPTIONS_ID);
    gameOptions.innerHTML = "";
    //Add each available game option
    var option = "";
    for(var i = 0; i<blackjack.gameOptions.length;i++){
        var obj = blackjack.gameOptions[i];
        option = "<button type=\"button\" class=\"btn btn-primary col-sm-12\" onclick=\"gameAction(\'" + obj.action + "\')\">" + obj.label + "</div>";
        gameOptions.innerHTML += option;
    }
}

//Display dealer hand
function displayDealerHand(){
   //Get containing element and reset inner html
   var dealer = document.getElementById(DEALER_ID);
   var html = "";
   html += "<div class='col-sm-12 col-centered hand'>";
   html += "<h1 class='display-1'>" + blackjack.dealerHand.status + "</h1>";
   //display cards
   html += generateHandHTML(blackjack.dealerHand);
   html += "</div>";
   dealer.innerHTML = html;
}

//Display player hands
function displayPlayerHands(){
   //Get containing element and reset inner html
   var player = document.getElementById(PLAYER_ID);
   var html = "";
   //Create a container for each hand
   for(var i = 0; i < blackjack.playerHands.length; i++){
       html += "<div class='col-xs-12 col-sm-6 col-md-6 col-lg-3 col-centered hand'>";
       html += generatePlayerOptionsHTML(blackjack.playerHands[i].handOptions, i);
       html += generateHandHTML(blackjack.playerHands[i]);
       html += "</div>";
   }
   player.innerHTML = html;
}

//Display player options horizontally
function generatePlayerOptionsHTML(options, handIndex){
    var html = "<div class='row row-centered playerOptions'>";
    for(var i = 0; i < options.length; i++){
        var obj = options[i];
        html += "<button type='button' class='btn btn-secondary btn-sm' onclick=\"playerAction(\'" + obj.action + "\',\'" + handIndex + "\')\">" + obj.label + "</button>";
    }
    html += "</div>";
    return html;
}

//Display hand
function generateHandHTML(hand){
   var numberOfGaps = hand.cards.length-1;
   var totalHeight = IMAGE_HEIGHT + numberOfGaps*IMAGE_GAP;
   var html = "<div class='row' style='height:"+totalHeight+"px'>";
   html += "<div class='col-lg-12'>";
   html += "<h1>" + hand.status + "</h1>";
   for(var i = 0; i < hand.cards.length; i++){
       html += "<div class='row card_container'><img src='" + hand.cards[i].visibleImageURL + "'></img></div>";
   }
   html += "</div></div>";
   return html;
}

//Game action
function gameAction(option){
    var newDeal = "newDeal";
    if(newDeal.localeCompare(option) && blackjack.dealerTurnInProgress == false){
        $.ajax({
            type: "POST",
            url: option,
            data: JSON.stringify(blackjack),
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            success: function(data, status){
                blackjack = data;
                displayGame()
            }
        });
    }
}

//Dealer action
function dealerAction(){
    //End the loop
    clearInterval(dealersTurn);

    $.ajax({
        type: "POST",
        url: "dealerAction",
        data: JSON.stringify(blackjack),
        contentType:"application/json; charset=utf-8",
        dataType:"json",
        success: function(data, status){
            blackjack = data;
            displayGame()
        }
    });
}

//Player action
function playerAction(action, handIndex){
    $.ajax({
        type: "POST",
        url: action + "/" + handIndex,
        data: JSON.stringify(blackjack),
        contentType:"application/json; charset=utf-8",
        dataType:"json",
        success: function(data, status){
            blackjack = data;
            displayGame();
        }
    });
}