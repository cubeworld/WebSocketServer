package pl.cubeworld.websocket;

import java.io.IOException;

import net.tootallnate.websocket.WebSocket;

public class WebsocketReply {
	
	private final WebSocket webSocket;

	public WebsocketReply(WebSocket webSocket) {
		this.webSocket = webSocket;
	}
	
	public void replyRaw(String msg) throws IOException{
		webSocket.send(msg);
	}
	
	public void reply(Object object) throws IOException {
		//TODO implement
		throw new UnsupportedOperationException("Method not implemented");
	}
}
