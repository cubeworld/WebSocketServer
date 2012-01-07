$(function() {
	$("#connectBtn").click(function() {
		if (window.WebSocket) {
			$("#disconnectBtn").attr('disabled', false);
			$(this).attr('disabled', true);
			connect();
			log("Using browser's native WebSocket implementation");
		} else {
			log("WebSocket not supported");
		}
		return false;
	});
	$("#disconnectBtn").click(function() {
		$("#connectBtn").attr('disabled', false);
		$(this).attr('disabled', true);
		disconnect();
		log("Disconnected");
		return false;
	});

	init();
    	$("td").each(function(i) {
    		$(this).addClass("square" + i);
    		$(this).click(function() {
    		    if(isStarted && myMove){
    			    move(i);
    			    myMove=false;
    			}

    		});
    	});

});

var isStarted = false;
var mySign = "X";
var myMove = true;
var opponentSign = "O";

function init() {
	var i = 0;
	for (i = 0; i < 15*15; i++) {
		table[i] = " ";
	}
}

function move(i) {
	log("move clicked " + i);
	var move = {
	    "id" : "move",
    	"x" : i,
    	"y" : i
    };

    table[i]= mySign;
    draw();
    status("Opponent move");

	sendJson("Move", move);
}

var ws;

var id = Math.floor(Math.random() * 1000);

function connect() {
	ws = new WebSocket($("#uri").val());
	ws.onopen = function() {
		log("[WebSocket#onopen]\n");

		var connectJson = {
			"connect" : id
		};
		sendJson("Connect", connectJson);
	};

	ws.onmessage = function(e) {
		log("[WebSocket#onmessage] Message: '" + e.data);
		var msg = jQuery.parseJSON(e.data);
		console.log(msg.id);
        if(msg.id=="move"){
            table[msg.x]=opponentSign;
            draw();
            myMove=true;
            status("Your move");
        } else if(msg.id=="newGame"){
             log("new Game: " + msg.player);
             isStarted=true;
             if(msg.player == "WHITE"){
                myMove=true;
                mySign="X";
                opponentSign="O";
                status("Your move");
             } else {
                myMove=false;
                mySign="O";
                opponentSign="X";
                status("Opponent move");
             }
        }
	};

	ws.onclose = function() {
		log("[WebSocket#onclose]\n");
		$("#connectBtn").attr('disabled', false);
		$("#disconnectBtn").attr('disabled', true);
		ws = null;
	};
}

function send(text) {
	if (ws) {
		ws.send(text);
		log("[WebSocket#send]      Send:    '" + text);
	}
}

function sendJson(className, obj){
    //var objJson  = JSON.stringify(obj, null, 2);
    var entity = {
    	    "id" : className,
        	"data" : obj
        };
    var entityJson = JSON.stringify(entity, null, 2);
    send(entityJson);
}

function disconnect() {
	if (ws) {
		ws.close();
	}
}

function log(text) {
	$("#log").append((new Date).getTime() + ": " + text + " <br/>");
}

var table = new Array();

function draw() {
	var i = 0;
	for (i = 0; i < 15*15; i++) {
		var state = table[i];
		if (state == "O") {
			$(".square" + i).addClass("selectO");
		} else if (state == "X") {
			$(".square" + i).addClass("selectX");
		} else {
			$(".square" + i).removeClass("selectO selectX");
		}
	}
}

function status(msg){
    $("#info").html(msg);
}