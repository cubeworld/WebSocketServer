package pl.cubeworld.websocket;

import java.lang.reflect.Type;
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

	private Map<Type, Action> actions = new HashMap<Type, Action>();
	private final String scannedPacket;

	private Set<WebSocket> webSockets = new HashSet<WebSocket>();

	public JsonWebSocketServer(int port, String scannedPacket) {
		super(port);
		this.scannedPacket = scannedPacket;
	}

	@Override
	public void onClientClose(WebSocket webSocket) {
		logger.debug("onClientClose");
		webSockets.remove(webSocket);
		logger.debug("WebSockets set size: " + webSockets.size());

	}

	@Override
	public void onClientMessage(WebSocket webSocket, String message) {
		logger.debug("onClientMessage");
		if (!webSockets.contains(webSocket)) {
			throw new IllegalStateException("Web socket " + webSocket + " should be created befere onClienetMessage method");
		}

		EntityResolver resolver = new EntityResolver(actions.keySet());
		Object entity = resolver.parse(message);

		WebsocketReply reply = new WebsocketReply(webSocket);
		
		ActionInvoker invoker = new ActionInvoker(actions);
		invoker.invoke(entity, reply);

	}

	@Override
	public void onClientOpen(WebSocket webSocket) {
		logger.debug("onClientOpen");
		if (webSockets.contains(webSocket)) {
			logger.debug("WebSocket have been already stored");
			return;
		}
		webSockets.add(webSocket);
		logger.debug("WebSockets set size: " + webSockets.size());

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
			try{
			Object webResourceObject = controllerDescription
					.getControllerClass().getConstructor().newInstance();
			for (ActionMethod actionMethod : controllerDescription
					.getActionMethods()) {
				actions.put(actionMethod.getEntity(), new Action(
						webResourceObject, actionMethod.getMethod()));
			}
			} catch (Exception ex){
				logger.warn("Controller " + controllerDescription + " cannot be created");
			}
		}

		super.start();
	}

}
