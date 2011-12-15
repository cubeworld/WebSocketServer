package pl.cubeworld.websocket;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Method;

import org.junit.Test;

import pl.cubeworld.websocket.annotation.Book;
import pl.cubeworld.websocket.controllers.TestController;

public class InvokerTest {

	@Test
	public void testName() throws Exception {
		// given
		TestController annotatedClassMocked = mock(TestController.class);
		Book book = new Book();

		Method method = TestController.class.getMethod("login", Book.class, WebsocketReply.class);
		Action action = new Action(annotatedClassMocked, method);
		
		WebsocketReply reply = mock(WebsocketReply.class);

		// when
		ActionInvoker invoker = new ActionInvoker(action);
		invoker.invoke(book, reply);

		// then
		verify(annotatedClassMocked).login(eq(book), any(WebsocketReply.class));
	}
}
