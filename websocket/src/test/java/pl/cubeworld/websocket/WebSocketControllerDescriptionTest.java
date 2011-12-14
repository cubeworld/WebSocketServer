package pl.cubeworld.websocket;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import pl.cubeworld.websocket.annotation.Book;
import pl.cubeworld.websocket.annotation.Person;
import pl.cubeworld.websocket.controllers.TestController;

public class WebSocketControllerDescriptionTest {

	@Test
	public void test() {
		//given
		WebSocketControllerDescription description = new WebSocketControllerDescription(TestController.class, "/example/path");;
		
		//when
		List<ActionMethod> actionMethod = description.getActionMethods();
		
		//then
		assertEquals("login", actionMethod.get(0).getMethod().getName());
		assertEquals(Book.class, actionMethod.get(0).getEntity());
		
		
		assertEquals("logout", actionMethod.get(1).getMethod().getName());
		assertEquals(Person.class, actionMethod.get(1).getEntity());
	}

}
