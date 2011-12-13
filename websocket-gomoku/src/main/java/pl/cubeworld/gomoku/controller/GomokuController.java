package pl.cubeworld.gomoku.controller;

import java.io.IOException;
import java.util.Stack;

import net.tootallnate.websocket.WebSocket;
import pl.cubeworld.gomoku.entity.Connect;
import pl.cubeworld.websocket.WebsocketResource;

@WebsocketResource(path="/path")
public class GomokuController {
	private Stack<WebSocket> waitingUsers = new Stack<WebSocket>();
	
	public void connect(Connect connect, WebSocket conn){
		System.out.println("Game controller connect: " + connect);
		waitingUsers.push(conn);
		
		if(waitingUsers.size() >= 2){
			startGame(waitingUsers.pop(), waitingUsers.pop(), conn);
		}
	}

	private void startGame(WebSocket user1, WebSocket user2, WebSocket current) {
		System.out.println("Game started user1: " + user1 + " user2: " + user2);
		try {
			user1.send("Game is started 1");
			user2.send("Game is started 2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
