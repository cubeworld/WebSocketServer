package pl.cubeworld.websocket;

import net.tootallnate.websocket.WebSocket;
import net.tootallnate.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.cubeworld.websocket.entityResolver.ClassEntityResolver;
import pl.cubeworld.websocket.entityResolver.EntityResolver;

import java.lang.reflect.Type;
import java.util.*;

public class JsonWebSocketServer extends WebSocketServer {
    private static final Logger logger = LoggerFactory
            .getLogger(JsonWebSocketServer.class);

    private final String scannedPacket;

    private List<ActionMethod> actionMethods = new ArrayList<ActionMethod>();
    private Set<Type> entities = new HashSet<Type>();
    private Map<Client, Map<Type, Action>> clientMap = new HashMap<Client, Map<Type, Action>>();

    public JsonWebSocketServer(int port, String scannedPacket) {
        super(port);
        this.scannedPacket = scannedPacket;
    }

    @Override
    public void onClientClose(WebSocket webSocket) {
        logger.debug("onClientClose");

        clientMap.remove(new Client(webSocket));
        logger.debug("Clients number: " + clientMap.size());
    }

    @Override
    public void onClientMessage(WebSocket webSocket, String message) {
        logger.debug("onClientMessage");
        Client client = new Client(webSocket);

        if (!clientMap.containsKey(client)) {
            throw new IllegalStateException("Map of client not contains client: " + webSocket);
        }

        EntityResolver resolver = new ClassEntityResolver(entities);
        Object entity = resolver.parse(message);

        Map<Type, Action> entityMap = clientMap.get(client);
        Action action = entityMap.get(entity.getClass());

        ActionInvoker invoker = new ActionInvoker(action);
        invoker.invoke(entity, client);

    }

    @Override
    public void onClientOpen(WebSocket webSocket) {
        logger.debug("onClientOpen");

        Set<WebsocketController> controllers = EntityAction.createControllers(actionMethods, clientMap.keySet());
        Map<Type, Action> entitiesMap = EntityAction.getEntitiesMap(actionMethods, controllers);
        if (clientMap.containsKey(new Client(webSocket))) {
            throw new IllegalStateException("Map of clients already contains client: " + webSocket);
        }
        clientMap.put(new Client(webSocket), entitiesMap);
        logger.debug("Clients number: " + clientMap.size());
    }

    @Override
    public void onError(Throwable throwable) {
        logger.debug("onError");
        logger.error("onError", throwable);
    }

    @Override
    public void start() {
        WebSocketControllerScanner scanner = new WebSocketControllerScanner(
                scannedPacket);
        List<WebSocketControllerDescription> controllerDescriptions = scanner
                .scan();

        for (WebSocketControllerDescription controllerDescription : controllerDescriptions) {
            try {
                actionMethods.addAll(controllerDescription.getActionMethods());
                //prediction one entity
            } catch (Exception ex) {
                logger.warn("Controller " + controllerDescription
                        + " cannot be created");
            }
        }

        for (ActionMethod actionMethod : actionMethods) {
            entities.add(actionMethod.getEntity());
        }

        super.start();
    }

}
