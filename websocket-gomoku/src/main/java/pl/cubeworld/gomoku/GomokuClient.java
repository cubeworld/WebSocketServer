package pl.cubeworld.gomoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.cubeworld.websocket.Client;

public class GomokuClient {
	private static final Logger logger = LoggerFactory.getLogger(GomokuClient.class);

	private GomokuGame gomokuGame;
	private final Client me;
	private Client oponent;

	public GomokuClient(Client client) {
		this.me = client;
	}

	public void startGame(GomokuGame gomokuGame, Client oponent, boolean firstMove) {
		this.gomokuGame = gomokuGame;
		this.oponent = oponent;
		try{
		if(firstMove){
			me.sendRaw("New game. You have first move");
		} else {
			me.sendRaw("New game. You have to wait for openent move");
		}
		} catch(Exception ex){
			logger.error("Cannot start game", ex);
		}
	}

	public Client getClient() {
		return me;
	}

	@Override
	public String toString() {
		return "GomokuClient [me=" + me + "]";
	}
}
