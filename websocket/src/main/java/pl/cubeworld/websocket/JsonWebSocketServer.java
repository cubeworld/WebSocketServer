package pl.cubeworld.websocket;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import net.tootallnate.websocket.WebSocket;
import net.tootallnate.websocket.WebSocketServer;

public class JsonWebSocketServer extends WebSocketServer {
	private Map<Type, Action> actions = new HashMap<Type, Action>();
	private final String scannedPacket;

	public JsonWebSocketServer(int port, String scannedPacket) {
		super(port);
		this.scannedPacket = scannedPacket;
	}

	@Override
	public void onClientClose(WebSocket arg0) {
		System.out.println("onClientClose");

	}

	@Override
	public void onClientMessage(WebSocket webSocket, String message) {
		System.out.println("onClientMessage");

		AdjustObject adjuster = new AdjustObject(actions.keySet());
		Object obj = adjuster.parse(message);
		Invoker invoker = new Invoker(actions);
		invoker.invoke(obj, webSocket);
		
//		try {
//			webSocket.send("Hello");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	@Override
	public void onClientOpen(WebSocket arg0) {
		System.out.println("onClientOpen");

	}

	@Override
	public void onError(Throwable arg0) {
		System.out.println("onError");

	}

	@Override
	public void start() {
		Reflections reflections = new Reflections(scannedPacket);
		Set<Class<?>> typeAnnotated = reflections
				.getTypesAnnotatedWith(WebsocketResource.class);
		System.out.println("Length: " + typeAnnotated.size());
		for (Class<?> clazz : typeAnnotated) {

			System.out.println(clazz.getName());
			final AnnotatedElement myClass = clazz;
			WebsocketResource annotation = myClass
					.getAnnotation(WebsocketResource.class);

			String path = annotation.path();
			System.out.println("Path: " + path);

			for (Method method : clazz.getDeclaredMethods()) {
				try {
					Type[] parameterTypes = method.getGenericParameterTypes();
					if (parameterTypes.length >= 1 && parameterTypes.length <= 2) {
						System.out.println("Method is websocket action: "
								+ method.getName());

						Object webResourceObject = clazz.getConstructor()
								.newInstance();
						Type messageType = parameterTypes[0];
						System.out.println("Message type: " + messageType);
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
