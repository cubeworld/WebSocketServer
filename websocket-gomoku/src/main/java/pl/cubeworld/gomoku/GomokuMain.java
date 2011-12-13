package pl.cubeworld.gomoku;

import pl.cubeworld.websocket.JsonWebSocketServer;

/**
 * Hello world!
 * 
 */
public class GomokuMain {
	public static void main(String[] args) {
		int port = 8887;
		String scannedPacket = "pl.cubeworld.gomoku.controller";
		JsonWebSocketServer server = new JsonWebSocketServer(port, scannedPacket);
		server.start();
		System.out.println("JsonWebSocketServer started on port: " + port);

	}
}
