package pl.cubeworld.websocket;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.tootallnate.websocket.WebSocket;
import net.tootallnate.websocket.WebSocketServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonWebSocketServer extends WebSocketServer {
	private static final Logger logger = LoggerFactory
			.getLogger(JsonWebSocketServer.class);

	private final String scannedPacket;

	private List<ActionMethod> actionMethods = new ArrayList<ActionMethod>();
	private Set<Type>  entities = new HashSet<Type>();
	private Map<WebSocket, Map<Type, Action>> clientMap = new HashMap<WebSocket, Map<Type,Action>>();


	public JsonWebSocketServer(int port, String scannedPacket) {
		super(port);
		this.scannedPacket = scannedPacket;
	}

	@Override
	public void onClientClose(WebSocket webSocket) {
		logger.debug("onClientClose");
		
		clientMap.remove(webSocket);
		logger.debug("Clients number: " + clientMap.size());
	}

	@Override
	public void onClientMessage(WebSocket webSocket, String message) {
		logger.debug("onClientMessage");
		
		if(!clientMap.containsKey(webSocket)){
			throw new IllegalStateException("Map of client not contains client: " + webSocket);
		}
		
		EntityResolver resolver = new EntityResolver(entities);
		Object entity = resolver.parse(message);
		
		Map<Type, Action> entityMap = clientMap.get(webSocket);
		Action action= entityMap.get(entity.getClass());

		WebsocketReply reply = new WebsocketReply(webSocket);
		
		ActionInvoker invoker = new ActionInvoker(action);
		invoker.invoke(entity, reply);

	}

	@Override
	public void onClientOpen(WebSocket webSocket) {
		logger.debug("onClientOpen");
		
		Set<Object> controllers = EntityAction.createControllers(actionMethods);
		Map<Type, Action> entitiesMap = EntityAction.getEntitiesMap(actionMethods, controllers);
		if(clientMap.containsKey(webSocket)){
			throw new IllegalStateException("Map of clients already contains client: " + webSocket);
		}
		clientMap.put(webSocket, entitiesMap);
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
		
		for(ActionMethod actionMethod : actionMethods){
			entities.add(actionMethod.getEntity());
		}

		super.start();
	}

}
