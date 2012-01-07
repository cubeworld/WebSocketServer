package pl.cubeworld.gomoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.cubeworld.gomoku.entity.Move;
import pl.cubeworld.gomoku.entity.NewGame;
import pl.cubeworld.websocket.Client;

import java.io.IOException;

public class GomokuClient {
    private static final Logger logger = LoggerFactory.getLogger(GomokuClient.class);

    private GomokuGame gomokuGame;
    private final Client me;
    private Client oponent;

    private Player player;

    public GomokuClient(Client client) {
        this.me = client;
    }

    public void startGame(GomokuGame gomokuGame, Client oponent, Player player) {
        this.gomokuGame = gomokuGame;
        this.oponent = oponent;
        this.player = player;
        try {
            NewGame newGame = new NewGame();
            newGame.setId("newGame");
            newGame.setPlayer(player);
            me.send(newGame);
//		if(player == Player.WHITE){
//			me.sendRaw("New game. You have first move");
//		} else {
//			me.sendRaw("New game. You have to wait for openent move");
//		}
        } catch (Exception ex) {
            logger.error("Cannot start game", ex);
        }
    }

    public void move(int x, int y) {
        try {
            //oponent.sendRaw("Move on x="+x + " y="+y);
            Move move = new Move();
            move.setId("move");
            move.setX(x);
            move.setY(y);
            oponent.send(move);
        } catch (IOException e) {
            logger.error("Can't send move message to oponent");
        }
    }

    public Client getClient() {
        return me;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "GomokuClient [me=" + me + "]";
    }
}
