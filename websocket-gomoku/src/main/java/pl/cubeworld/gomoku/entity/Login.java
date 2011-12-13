package pl.cubeworld.gomoku.entity;

public class Login {
	private final String login;
	private final String password;
	
	public Login(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "Login [login=" + login + ", password=" + password + "]";
	}
	
	
}
