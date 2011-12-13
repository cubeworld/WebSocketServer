package pl.cubeworld.websocket;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Map;

public class Invoker {

	private final Map<Type, Action> actions;

	public Invoker(Map<Type, Action> actions) {
		this.actions = actions;
	}

	public void invoke(Object object, WebsocketReply reply) {
		Action action = actions.get(object.getClass());
		try {
			Object result = action.getMethod().invoke(action.getObject(), object, reply);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		
	}
	
}
