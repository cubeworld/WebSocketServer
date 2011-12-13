package pl.cubeworld.websocket;

import net.tootallnate.websocket.WebSocket;
import pl.cubeworld.websocket.WebsocketResource;
import pl.cubeworld.websocket.annotation.Book;
import pl.cubeworld.websocket.annotation.Person;

@WebsocketResource(path="/path")
public class AnnotatedClass {
	
	public void login(Book test, WebSocket socket){
		System.out.println("Login invoked: " + test.toString());
	}
	
	public void logout(Person test, WebSocket socket){
		System.out.println("Logout invoked: " + test.toString());
	}

}
