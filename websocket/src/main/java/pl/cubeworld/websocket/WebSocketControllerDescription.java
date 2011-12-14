package pl.cubeworld.websocket;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketControllerDescription {
	private static final Logger logger = LoggerFactory
			.getLogger(WebSocketControllerDescription.class);

	private final Class<?> controllerClass;
	private final String path;

	public WebSocketControllerDescription(Class<?> controllerClass, String path) {
		this.controllerClass = controllerClass;
		this.path = path;
	}
	
	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public String getPath() {
		return path;
	}

	public List<ActionMethod> getActionMethods() {
		List<ActionMethod> actionMethods = new ArrayList<ActionMethod>();
		for (Method method : controllerClass.getDeclaredMethods()) {
			Type[] parameterTypes = method.getGenericParameterTypes();
			if (parameterTypes.length >= 1 && parameterTypes.length <= 2) {
				logger.info("Method is websocket action: " + method.getName());
				Type entityType = parameterTypes[0];
				actionMethods.add(new ActionMethod(method, entityType));
			}
		}
		return actionMethods;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((controllerClass == null) ? 0 : controllerClass.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebSocketControllerDescription other = (WebSocketControllerDescription) obj;
		if (controllerClass == null) {
			if (other.controllerClass != null)
				return false;
		} else if (!controllerClass.equals(other.controllerClass))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WebSocketControllerDescription [controllerClass="
				+ controllerClass.getName() + ", path=" + path + "]";
	}

}
