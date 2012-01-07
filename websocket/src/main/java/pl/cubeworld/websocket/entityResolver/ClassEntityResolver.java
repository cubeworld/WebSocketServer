package pl.cubeworld.websocket.entityResolver;

import com.google.gson.Gson;
import pl.cubeworld.websocket.JsonEntity;

import java.lang.reflect.Type;
import java.util.Collection;

public class ClassEntityResolver implements EntityResolver {

    private final Collection<Type> objects;
    private Gson gson = new Gson();

    public ClassEntityResolver(Collection<Type> objects) {
        this.objects = objects;
    }

    @Override
    public Object parse(String json) {
        JsonEntity jsonEntity = gson.fromJson(json, JsonEntity.class);

        for (Type type : objects) {
            if (type.toString().contains(jsonEntity.getId())) {
                return gson.fromJson(jsonEntity.getData().toString(), type);
            }
        }
        throw new IllegalStateException("Parser not contain object");
    }
}
