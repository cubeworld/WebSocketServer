package pl.cubeworld.gomoku;

import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameController {
	private static final Logger logger = LoggerFactory.getLogger(GomokuClient.class);
	
	private static final GameController instance = new GameController();
	
	private Queue<GomokuClient> waitingClients = new LinkedList<GomokuClient>();
	
	private GameController(){
	}
	
	public static GameController getInstance() {
		return instance;
	}
	
	public void joinGame(GomokuClient client){
		if(waitingClients.size() >= 1){
			GomokuClient client1 = waitingClients.poll();
			GomokuClient client2 = client;
			logger.debug("Creating new game client1: " + client1 + " and client2: " + client2);
			GomokuGame game = new GomokuGame(Player.WHITE,10,10);
			client1.startGame(game, client2.getClient(), Player.WHITE);
			client2.startGame(game, client1.getClient(), Player.BLACK);
			return;
		}
		waitingClients.offer(client);
		logger.debug("Client: " + client + " added to waiting list");
	}
}
