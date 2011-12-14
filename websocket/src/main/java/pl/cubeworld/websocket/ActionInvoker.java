package pl.cubeworld.websocket;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionInvoker {
	private static final Logger logger = LoggerFactory.getLogger(ActionInvoker.class);

	private final Map<Type, Action> actions;

	public ActionInvoker(Map<Type, Action> actions) {
		this.actions = actions;
	}

	public void invoke(Object entity, WebsocketReply reply) {
		Action action = actions.get(entity.getClass());
		try {
			logger.info("Invoking action for entity: " + entity.getClass().getName());
			Object result = action.getMethod().invoke(action.getObject(), entity, reply);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		
	}
}
