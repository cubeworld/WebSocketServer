package pl.cubeworld.websocket;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import static org.mockito.Matchers.*;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import net.tootallnate.websocket.WebSocket;

import org.junit.Test;

import pl.cubeworld.websocket.annotation.Book;
import pl.cubeworld.websocket.model.TestController;

public class InvokerTest {

	@Test
	public void testName() throws Exception {
		// given
		TestController annotatedClassMocked = mock(TestController.class);
		Book book = new Book();

		Method method = TestController.class.getMethod("login", Book.class, WebSocket.class);
		Map<Type, Action> actions = new HashMap<Type, Action>();
		actions.put(Book.class, new Action(annotatedClassMocked, method));

		// when
		Invoker invoker = new Invoker(actions);
		invoker.invoke(book, null);

		// then
		verify(annotatedClassMocked).login(eq(book), any(WebSocket.class));
	}
}
