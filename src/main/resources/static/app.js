var stompClient = null;
let count = 0;
let playerId = "";
let gameId = "";

function setConnected(connected) {
    $("#play").prop("disabled", connected);
    $("#end").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#moves").html("");
}

function connect() {
    var socket = new SockJS('/game');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/'+playerId+'/move', function (response) {
            const move = JSON.parse(response.body)
            count = move.next;
            showMoves("current - "+ move.curr+" next - "+ move.next+" operation - "+move.operation);
            $("#send").prop("disabled", false);
        });
        stompClient.subscribe('/topic/'+playerId+'/play', (response) => {
            const player = JSON.parse(response.body);
            if(player.success === false) {
                disconnect();
                showMoves("Only two player can play game at a time!");
            } else {
                gameId = player.gameId;
                if(player.count==2)
                    showNotification("Player 2 Joined!");
                else
                    showNotification("Player 1 Joined! Waiting for 2nd Player to Join");
            }
        });
        stompClient.subscribe('/topic/'+playerId+'/status', (response) => {
            const status = JSON.parse(response.body);
            let msg = "";
            if(status.win === true){
                msg = "You win!🎉"
            } else {
                msg = "You lose!😕"
            }
            showNotification(msg);
        });
    });
    setTimeout(() => {
        startPlay();
    }, 500);
    
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    count = 0;
    gameId = "";
    setConnected(false);
    console.log("Game Over!");
}

function sendMove() {
    if(count == 0){
        count = $("#move").val();
    } 
    stompClient.send(
                    "/app/move", 
                    {}, 
                    JSON.stringify({'curr': count, 'playerId': playerId, 'gameId': gameId}));
    $("#send").prop("disabled", true);
}

function startPlay(){
    stompClient.send("/app/play", {}, playerId);
}

function showNotification(msg) {
    $("#notification").empty();
    $("#notification").append("<div>"+msg+"<div>");
}

function showMoves(move) {
    $("#moves").append("<tr><td>" + move + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    playerId = generateId()
    $( "#play" ).click(function() { connect(); });
    $( "#end" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMove(); });
});

function generateId() {
    var result, i, j;
    result = '';
    for(j=0; j<32; j++) {
        if( j == 8 || j == 12 || j == 16 || j == 20)
        result = result + '-';
        i = Math.floor(Math.random()*16).toString(16).toUpperCase();
        result = result + i;
    }
    return result;
}