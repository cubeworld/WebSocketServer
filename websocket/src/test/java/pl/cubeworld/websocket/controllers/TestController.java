package pl.cubeworld.websocket.controllers;

import pl.cubeworld.websocket.Client;
import pl.cubeworld.websocket.WebsocketResource;
import pl.cubeworld.websocket.annotation.Book;
import pl.cubeworld.websocket.annotation.Person;

@WebsocketResource(path="/path")
public class TestController {
	
	public void login(Book test, Client client){
		System.out.println("Login invoked: " + test.toString());
	}
	
	public void logout(Person test, Client client){
		System.out.println("Logout invoked: " + test.toString());
	}

}
