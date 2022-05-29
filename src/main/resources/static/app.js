var stompClient = null;
let count = 0;
let playerId = "";
let gameId = "";
let isLive = true;

function setConnected(connected) {
    $("#play").prop("disabled", connected);
    $("#end").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#moves").html("");
    isLive = connected;
    $('input[type=radio][name=mode]').prop("disabled", !connected);
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
            setTimeout(()=>{
                if(isLive) {
                    sendMove();
                }
            }, 1000); 
        });
        stompClient.subscribe('/topic/'+playerId+'/play', (response) => {
            const player = JSON.parse(response.body);
            if(player.success === false) {
                disconnect();
                showMoves("Only two player can play game at a time!");
            } else {
                gameId = player.gameId;
            }
        });
        stompClient.subscribe('/topic/'+playerId+'/status', (response) => {
            const status = JSON.parse(response.body);
            showNotification(status.message);
        });

        stompClient.subscribe('/topic/'+playerId+'/win-lose', (response) => {
            const gameOver = JSON.parse(response.body);
            isLive = false;
            showNotification(gameOver.message);
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
    $("#move").val("");
    showNotification("")
    console.log("Game Over!");
}

function sendMove() {
    if(count == 0){
        count = $("#move").val();
        $('input[type=radio][name=mode]').prop("disabled", true);
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
    $("#move").prop("disabled", true);
    playerId = generateId()
    $( "#play" ).click(function() { connect(); });
    $( "#end" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMove(); });
    $('input[type=radio][name=mode]').change((e) => {
        modechange(e.target.value);
    });
    $('input[type=radio][name=mode]').prop("disabled", true);
});

function modechange(mode) {
    console.log("mode changed to: "+mode);
    if(mode==="auto"){
        $("#move").prop("disabled", true);
    } else {
        $("#move").prop("disabled", false);
    }
}

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