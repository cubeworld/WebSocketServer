package pl.cubeworld.websocket;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ActionMethod {
	private final Class<?> clazz;
	private final Method method;
	private final Type entity;
	
	public ActionMethod(Class<?> clazz, Method method, Type entity) {
		this.clazz = clazz;
		this.method = method;
		this.entity = entity;
	}

	public Method getMethod() {
		return method;
	}

	public Type getEntity() {
		return entity;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
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
		ActionMethod other = (ActionMethod) obj;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ActionMethod [method=" + method.getName() + ", entity=" + entity + "]";
	}
}
