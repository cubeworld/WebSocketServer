package pl.cubeworld.websocket;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketControllerScanner {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketControllerScanner.class);
	
	private final String scannedPacket;

	public WebSocketControllerScanner(String scannedPacket) {
		this.scannedPacket = scannedPacket;
	}
	
	
	public List<WebSocketControllerDescription> scan(){
		List<WebSocketControllerDescription> controllerDescriptionList = new ArrayList<WebSocketControllerDescription>();
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
			
			controllerDescriptionList.add(new WebSocketControllerDescription(clazz, path));
		}
		return controllerDescriptionList;
	}
		
}
