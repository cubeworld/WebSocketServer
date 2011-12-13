package pl.cubeworld.websocket;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.tootallnate.websocket.WebSocket;
import net.tootallnate.websocket.WebSocketServer;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonWebSocketServer extends WebSocketServer {
	private static final Logger logger = LoggerFactory.getLogger(JsonWebSocketServer.class);
	
	private Map<Type, Action> actions = new HashMap<Type, Action>();
	private final String scannedPacket;

	public JsonWebSocketServer(int port, String scannedPacket) {
		super(port);
		this.scannedPacket = scannedPacket;
	}

	@Override
	public void onClientClose(WebSocket arg0) {
		logger.debug("onClientClose");

	}

	@Override
	public void onClientMessage(WebSocket webSocket, String message) {
		logger.debug("onClientMessage");

		AdjustObject adjuster = new AdjustObject(actions.keySet());
		Object obj = adjuster.parse(message);
		Invoker invoker = new Invoker(actions);
		WebsocketReply reply = new WebsocketReply(webSocket);
		invoker.invoke(obj, reply);

	}

	@Override
	public void onClientOpen(WebSocket arg0) {
		logger.debug("onClientOpen");

	}

	@Override
	public void onError(Throwable arg0) {
		logger.debug("onError");

	}

	@Override
	public void start() {
		Reflections reflections = new Reflections(scannedPacket);
		Set<Class<?>> typeAnnotated = reflections
				.getTypesAnnotatedWith(WebsocketResource.class);
		logger.debug("Length: " + typeAnnotated.size());
		for (Class<?> clazz : typeAnnotated) {

			logger.debug("Annotated class: " + clazz.getName());
			final AnnotatedElement myClass = clazz;
			WebsocketResource annotation = myClass
					.getAnnotation(WebsocketResource.class);

			String path = annotation.path();
			logger.debug("Path: " + path);

			for (Method method : clazz.getDeclaredMethods()) {
				try {
					Type[] parameterTypes = method.getGenericParameterTypes();
					if (parameterTypes.length >= 1 && parameterTypes.length <= 2) {
						logger.info("Method is websocket action: "
								+ method.getName());

						Object webResourceObject = clazz.getConstructor()
								.newInstance();
						Type messageType = parameterTypes[0];
						logger.info("Message type: " + messageType);
						actions.put(messageType, new Action(webResourceObject,
								method));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}

		super.start();
	}

}
