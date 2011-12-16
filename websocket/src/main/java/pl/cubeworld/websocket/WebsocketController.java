package pl.cubeworld.websocket;

import java.util.Set;

public class WebsocketController {

	private Set<Client>  clients;

	public void setClients(Set<Client>  clients) {
		this.clients = clients;
	}

	public Set<Client>  getClients() {
		return clients;
	}
}
