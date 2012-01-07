package pl.cubeworld.gomoku.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.cubeworld.gomoku.GameController;
import pl.cubeworld.gomoku.GomokuClient;
import pl.cubeworld.gomoku.entity.Connect;
import pl.cubeworld.gomoku.entity.Move;
import pl.cubeworld.websocket.Client;
import pl.cubeworld.websocket.WebsocketController;
import pl.cubeworld.websocket.WebsocketResource;

@WebsocketResource(path = "/path")
public class GomokuController extends WebsocketController {
    private static final Logger logger = LoggerFactory.getLogger(GomokuController.class);

    private GomokuClient gomokuClient;

    public void connect(Connect connect, Client client) {
        logger.info("Game controller connect: " + connect);
        gomokuClient = new GomokuClient(client);
        GameController.getInstance().joinGame(gomokuClient);
    }

    public void move(Move move, Client client) {
        logger.info("Move " + move);
        gomokuClient.move(move.getX(), move.getY());
    }
}
