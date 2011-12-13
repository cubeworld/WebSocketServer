package pl.cubeworld.gomoku.entity;

public class Connect {
	private int connect;

	public int getConnect() {
		return connect;
	}

	public void setConnect(int connect) {
		this.connect = connect;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (connect ^ (connect >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connect other = (Connect) obj;
		if (connect != other.connect)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Connect [connect=" + connect + "]";
	}
}
