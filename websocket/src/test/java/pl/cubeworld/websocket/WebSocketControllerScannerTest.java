package pl.cubeworld.websocket;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import pl.cubeworld.websocket.controllers.TestController;

public class WebSocketControllerScannerTest {

	@Test
	public void shouldScannAndReturnWebSocketControllerDescriptions() {
		//given 
		String scannedPacket = "pl.cubeworld.websocket.controllers";
		List<WebSocketControllerDescription> expectedDescriptions = new ArrayList<WebSocketControllerDescription>();
		Collections.addAll(expectedDescriptions, new WebSocketControllerDescription(TestController.class ,"/path"));
		
		
		//when
		WebSocketControllerScanner scanner = new WebSocketControllerScanner(scannedPacket);
		List<WebSocketControllerDescription> descriptions = scanner.scan();
		
		//then
		assertEquals(expectedDescriptions, descriptions);
	}

}
