package pl.cubeworld.websocket;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.Gson;

public class AdjustObject {

	private final Collection<Type> objects;
	private Gson gson = new Gson();

	public AdjustObject(Collection<Type> objects) {
		this.objects = objects;
	}

	public Object parse(String json) {
		for (Type type : objects) {
			Object object = gson.fromJson(json, type);
			//System.out.println(object);
			if (!isNull(object)) {
				return object;
			}
		}
		throw new IllegalStateException("Parser not contain object");
	}

	private boolean isNull(Object object) {
		Method[] methods = object.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getParameterTypes().length == 0 && method.getName().startsWith("get")) {
				//System.out.println("Method name: " + method.getName());
				try {
					Object result = method.invoke(object);
					//System.out.println("Method result " + result);
					if(result==null){
						return true;
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}
}
