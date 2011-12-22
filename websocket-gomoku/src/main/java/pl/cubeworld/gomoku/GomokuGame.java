package pl.cubeworld.gomoku;

public class GomokuGame {
	private Player[][] board;
	
	private Player turn;

	public GomokuGame(Player player, int width, int height) {
		this.turn = player;
		this.board = new Player[width][height];
	}

	public GomokuGame(Player player, Player[][] board) {
		this.turn = player;
		this.board=board;
	}

	public boolean move(Player player, int x, int y) {
		if(player != turn) {
			return false;
		}
		if(board[x][y] != null){
			return false;
		}
		turn = opositePlayer(player);
		board[x][y] = player;
		
		return true;
	}

	public Player get(int x, int y) {
		return board[x][y];
	}
	
	private Player opositePlayer(Player player){
		if(player == Player.WHITE){
			return Player.BLACK;
		}
		return Player.WHITE;
	}

	public boolean isFinished() {
		//veritical
		for(int y=0; y<board[0].length; y++){
			if(isRowFinished(y)){
				return true;
			}
		}
		
		//horizontal
		for(int x=0; x<board.length; x++){
			if(isColFinished(x)){
				return true;
			}
		}
		
		//cross down
		for(int y=0; y<board[0].length; y++){
			if(isCrossDownFinished(0,y)){
				return true;
			}
		}
		
		for(int x=1; x<board.length; x++){
			if(isCrossDownFinished(x,0)){
				return true;
			} 
		}

		//cross up
		for(int y=0; y<board[0].length; y++){
			if(isCrossUpFinished(0,y)){
				return true;
			}
		}
		
		for(int x=1; x<board.length; x++){
			if(isCrossUpFinished(x,board[0].length-1)){
				return true;
			}
		}
		
		return false;
	}

	private boolean isCrossUpFinished(int startX, int startY) {
		int count=0;
		Player currentField = Player.WHITE;
		
		for(int x=startX, y=startY; x <board.length && y >=0; x++, y--){
			Player field = board[x][y];
			if(field == currentField){
				count++;
				if(count >= 5){
					return true;
				}
			} else if(field != null) {
				currentField = field;
				count = 1;
			}
		}
		
		return false;
	}

	private boolean isCrossDownFinished(int startX, int startY) {
		int count=0;
		Player currentField = Player.WHITE;
		
		for(int x=startX, y=startY; x <board.length && y <board[0].length; x++, y++){
			Player field = board[x][y];
			if(field == currentField){
				count++;
				if(count >= 5){
					return true;
				}
			} else if(field != null) {
				currentField = field;
				count = 1;
			}
		}
		
		return false;
	}

	private boolean isColFinished(int x) {
		int count=0;
		Player currentField = Player.WHITE;
		
		for(int y=0; y < board[0].length; y++){
			Player field = board[x][y];
			if(field == currentField){
				count++;
				if(count >= 5){
					return true;
				}
			} else if(field != null) {
				currentField = field;
				count = 1;
			}
		}
		return false;
	}

	private boolean isRowFinished(int y) {
		int count=0;
		Player currentField = Player.WHITE;
		
		for(int x=0; x < board.length; x++){
			Player field = board[x][y];
			if(field == currentField){
				count++;
				if(count >= 5){
					return true;
				}
			} else if(field != null) {
				currentField = field;
				count = 1;
			}
		}
		return false;
	}

}
