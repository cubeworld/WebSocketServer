package pl.cubeworld.websocket;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WebsocketResource {
	String path() default "";
}
