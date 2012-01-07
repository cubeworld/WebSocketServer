package pl.cubeworld.websocket.entityResolver;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;

public class AutoEntityResolver implements EntityResolver {
    private static final Logger logger = LoggerFactory.getLogger(AutoEntityResolver.class);

    private final Collection<Type> objects;
    private Gson gson = new Gson();

    public AutoEntityResolver(Collection<Type> objects) {
        this.objects = objects;
    }

    public Object parse(String json) {
        for (Type type : objects) {
            Object object = gson.fromJson(json, type);
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
                //logger.debug("Method name: " + method.getName());
                try {
                    Object result = method.invoke(object);
                    if (result == null) {
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
