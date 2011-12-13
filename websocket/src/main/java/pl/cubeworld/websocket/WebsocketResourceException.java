package pl.cubeworld.websocket;

@SuppressWarnings("serial")
public class WebsocketResourceException extends Exception {

	public WebsocketResourceException() {
		super();
	}

	public WebsocketResourceException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public WebsocketResourceException(String message) {
		super(message);
	}

	public WebsocketResourceException(Throwable throwable) {
		super(throwable);
	}

}
