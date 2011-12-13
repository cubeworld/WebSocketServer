package pl.cubeworld.gomoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.cubeworld.websocket.JsonWebSocketServer;

/**
 * Hello world!
 * 
 */
public class GomokuMain {
	private static final Logger logger = LoggerFactory.getLogger(GomokuMain.class);
	
	public static void main(String[] args) {
		int port = 8887;
		String scannedPacket = "pl.cubeworld.gomoku.controller";
		JsonWebSocketServer server = new JsonWebSocketServer(port, scannedPacket);
		server.start();
		logger.info("JsonWebSocketServer started on port: " + port);

	}
}
