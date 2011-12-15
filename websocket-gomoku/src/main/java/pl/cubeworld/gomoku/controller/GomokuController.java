package pl.cubeworld.gomoku.controller;

import java.io.IOException;
import java.util.Stack;

import net.tootallnate.websocket.WebSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.cubeworld.gomoku.entity.Connect;
import pl.cubeworld.websocket.WebsocketReply;
import pl.cubeworld.websocket.WebsocketResource;

@WebsocketResource(path="/path")
public class GomokuController {
	private static final Logger logger = LoggerFactory.getLogger(GomokuController.class);
	
	private Stack<WebsocketReply> waitingUsers = new Stack<WebsocketReply>();
	
	private double id = Math.random();
	
	public void connect(Connect connect, WebsocketReply reply){
		logger.info("Game controller connect: " + connect);
		waitingUsers.push(reply);
		try {
			reply.replyRaw("I'm connected :) " + id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(waitingUsers.size() >= 2){
			startGame(waitingUsers.pop(), waitingUsers.pop(), null);
		}
	}

	//TODO private method should not be find by annotation scanner
	private void startGame(WebsocketReply user1, WebsocketReply user2, WebSocket current) {
		logger.info("Game started user1: " + user1 + " user2: " + user2);
		try {
			user1.replyRaw("Game is started 1");
			user2.replyRaw("Game is started 2");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
