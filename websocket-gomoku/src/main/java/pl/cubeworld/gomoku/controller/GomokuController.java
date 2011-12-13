package pl.cubeworld.gomoku.controller;

import java.io.IOException;
import java.util.Stack;

import net.tootallnate.websocket.WebSocket;
import pl.cubeworld.gomoku.entity.Connect;
import pl.cubeworld.websocket.WebsocketReply;
import pl.cubeworld.websocket.WebsocketResource;

@WebsocketResource(path="/path")
public class GomokuController {
	private Stack<WebsocketReply> waitingUsers = new Stack<WebsocketReply>();
	
	public void connect(Connect connect, WebsocketReply reply){
		System.out.println("Game controller connect: " + connect);
		waitingUsers.push(reply);
		
		if(waitingUsers.size() >= 2){
			startGame(waitingUsers.pop(), waitingUsers.pop(), null);
		}
	}

	//TODO private method should not be find by annotation scanner
	private void startGame(WebsocketReply user1, WebsocketReply user2, WebSocket current) {
		System.out.println("Game started user1: " + user1 + " user2: " + user2);
		try {
			user1.replyRaw("Game is started 1");
			user2.replyRaw("Game is started 2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
