package pl.cubeworld.websocket;

import java.lang.reflect.Method;

public class Action {

	private final Object object;
	private final Method method;

	public Action(Object object, Method method) {
		this.object = object;
		this.method = method;
	}

	public Object getObject() {
		return object;
	}

	public Method getMethod() {
		return method;
	}
}
