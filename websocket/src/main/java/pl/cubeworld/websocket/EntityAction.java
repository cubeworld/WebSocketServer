package pl.cubeworld.websocket;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityAction {
	private static final Logger logger = LoggerFactory
			.getLogger(EntityAction.class);
	
	public static Set<WebsocketController> createControllers(List<ActionMethod> actionMethods, Set<Client> clients){
		Set<Class<?>> controllerClasses = new HashSet<Class<?>>();
		for(ActionMethod actionMethod : actionMethods){
			controllerClasses.add(actionMethod.getClazz());
		}
		
		Set<WebsocketController> controllers = new HashSet<WebsocketController>();
		for(Class<?> controllerClass : controllerClasses){
			try {
				WebsocketController controller = createObject(controllerClass);
				controller.setClients(clients);
				controllers.add(controller);
			} catch (InstanceObjectException e) {
				logger.warn("Controller " + controllerClass.getName() + " cannot be created");
			}
		}
		return controllers;
	}

	private static WebsocketController createObject(Class<?> controllerClass) throws InstanceObjectException {
		try {
			return (WebsocketController)controllerClass.getConstructor().newInstance();
		} catch (IllegalArgumentException e) {
			throw new InstanceObjectException("Object of class " + controllerClass.getName() + " cannot be created",e);
		} catch (SecurityException e) {
			throw new InstanceObjectException("Object of class " + controllerClass.getName() + " cannot be created",e);
		} catch (InstantiationException e) {
			throw new InstanceObjectException("Object of class " + controllerClass.getName() + " cannot be created",e);
		} catch (IllegalAccessException e) {
			throw new InstanceObjectException("Object of class " + controllerClass.getName() + " cannot be created",e);
		} catch (InvocationTargetException e) {
			throw new InstanceObjectException("Object of class " + controllerClass.getName() + " cannot be created",e);
		} catch (NoSuchMethodException e) {
			throw new InstanceObjectException("Object of class " + controllerClass.getName() + " cannot be created",e);
		}
	}
	
	public static Map<Type, Action> getEntitiesMap(List<ActionMethod> actionMethods, Set<WebsocketController> controllers){
		Map<Type, Action> entities = new HashMap<Type, Action>();
		for(ActionMethod actionMethod : actionMethods){
			Object controller = getController(actionMethod.getClazz(), controllers);
			entities.put(actionMethod.getEntity(), new Action(controller, actionMethod.getMethod()));
		}
		
		return entities;
	}

	private static Object getController(Class<?> clazz, Set<WebsocketController> controllers) {
		for(Object controller : controllers){
			if(controller.getClass() == clazz){
				return controller;
			}
		}
		throw new IllegalStateException("Controller for class: " + clazz.getName() + " does not exist");
	}
}
