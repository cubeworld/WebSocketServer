package pl.cubeworld.websocket;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionInvoker {
	private static final Logger logger = LoggerFactory.getLogger(ActionInvoker.class);

	private final Action action;

	public ActionInvoker(Action action) {
		this.action = action;
	}

	public void invoke(Object entity, WebsocketReply reply) {
		try {
			logger.info("Invoking action for entity: " + entity.getClass().getName() + " on controller: " + action.getObject().getClass().getName());
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
