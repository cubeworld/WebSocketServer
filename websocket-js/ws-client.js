$(function() {
	// log("Test");
	$("#sendBtn").click(function() {
		send($("#textField").val());
		$("#textField").val("");
		$("#textField").focus();
		return false;
	});
	$("#jsonBtn").click(function() {
		var person = {
			"name" : "John Smith",
			"age" : 25,
		};
		var json_text = JSON.stringify(person, null, 2);
		send(json_text);
		return false;
	});
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

});

var ws;

var id = Math.floor(Math.random() * 1000);

function connect() {
	ws = new WebSocket($("#uri").val());
	ws.onopen = function() {
		log("[WebSocket#onopen]\n");

		var connectJson = {
			"connect" : id
		};
		var json_text = JSON.stringify(connectJson, null, 2);
		console.log(json_text);
		send(json_text);
	};

	ws.onmessage = function(e) {
		log("[WebSocket#onmessage] Message: '" + e.data);
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

function disconnect() {
	if (ws) {
		ws.close();
	}
}

function log(text) {
	$("#log").append((new Date).getTime() + ": " + text + " <br/>");
}