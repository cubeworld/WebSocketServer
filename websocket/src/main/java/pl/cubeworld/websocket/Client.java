package pl.cubeworld.websocket;

import java.io.IOException;

import net.tootallnate.websocket.WebSocket;

public class Client {

	private final WebSocket webSocket;

	public Client(WebSocket webSocket) {
		this.webSocket = webSocket;
	}
	
	public void sendRaw(String msg) throws IOException{
		webSocket.send(msg);
	}

	@Override
	public String toString() {
		return "Client [webSocket=" + webSocket + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((webSocket == null) ? 0 : webSocket.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (webSocket == null) {
			if (other.webSocket != null)
				return false;
		} else if (!webSocket.equals(other.webSocket))
			return false;
		return true;
	}
}
