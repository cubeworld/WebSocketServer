package pl.cubeworld.websocket.controllers;

import pl.cubeworld.websocket.WebsocketReply;
import pl.cubeworld.websocket.WebsocketResource;
import pl.cubeworld.websocket.annotation.Book;
import pl.cubeworld.websocket.annotation.Person;

@WebsocketResource(path="/path")
public class TestController {
	
	public void login(Book test, WebsocketReply reply){
		System.out.println("Login invoked: " + test.toString());
	}
	
	public void logout(Person test, WebsocketReply socket){
		System.out.println("Logout invoked: " + test.toString());
	}

}
