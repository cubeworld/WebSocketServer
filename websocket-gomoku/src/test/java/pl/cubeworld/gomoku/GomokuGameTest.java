package pl.cubeworld.gomoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GomokuGameTest {

	@Test
	public void shouldMoveToEmptyField() {
		// given
		GomokuGame game = new GomokuGame(Player.WHITE, 10, 10);
		int moveX = 5;
		int moveY = 5;

		// when
		game.move(Player.WHITE, moveX, moveY);
		Player field = game.get(moveX, moveY);

		// then
		assertEquals(Player.WHITE, field);
	}

	@Test
	public void shouldNotMoveToBecauseNowIsBlackTurn() {
		// given
		GomokuGame game = new GomokuGame(Player.BLACK, 10, 10);
		int moveX = 5;
		int moveY = 5;

		// when
		boolean moved = game.move(Player.WHITE, moveX, moveY);
		Player field = game.get(moveX, moveY);

		// then
		assertFalse(moved);
		assertNull(field);
	}

	@Test
	public void shoulMoveWhiteBlackAndWhite() {
		// given
		GomokuGame game = new GomokuGame(Player.WHITE, 10, 10);
		int move1X = 5;
		int move1Y = 5;

		int move2X = 5;
		int move2Y = 6;

		int move3X = 6;
		int move3Y = 5;

		// when
		boolean moved1 = game.move(Player.WHITE, move1X, move1Y);
		assertTrue(moved1);

		boolean moved2 = game.move(Player.BLACK, move2X, move2Y);
		assertTrue(moved2);

		boolean moved3 = game.move(Player.WHITE, move3X, move3Y);
		assertTrue(moved3);
	}

	@Test
	public void shoulNotMoveWhiteBlackAndBlack() {
		// given
		GomokuGame game = new GomokuGame(Player.WHITE, 10, 10);
		int move1X = 5;
		int move1Y = 5;

		int move2X = 5;
		int move2Y = 6;

		int move3X = 6;
		int move3Y = 5;

		// when
		boolean moved1 = game.move(Player.WHITE, move1X, move1Y);
		assertTrue(moved1);

		boolean moved2 = game.move(Player.BLACK, move2X, move2Y);
		assertTrue(moved2);

		boolean moved3 = game.move(Player.BLACK, move3X, move3Y);
		assertFalse(moved3);
	}

	@Test
	public void shouldNotMove2TimesOnTheSamePlace() {
		// given
		GomokuGame game = new GomokuGame(Player.WHITE, 10, 10);
		int moveX = 5;
		int moveY = 5;

		// when
		boolean moved1 = game.move(Player.WHITE, moveX, moveY);
		assertTrue(moved1);

		boolean moved2 = game.move(Player.BLACK, moveX, moveY);
		assertFalse(moved2);
	}

	@Test
	public void shouldReturnTrueBecauseGameIsNotFinished() {
		// given
		int[][] board = {
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 1, 1, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } 
				};

		// when
		Player[][] transformedBoard = transformToPlayer(board);
		GomokuGame game = new GomokuGame(Player.WHITE, transformedBoard);
		boolean finished = game.isFinished();

		// then
		assertFalse(finished);

	}

	@Test
	public void shouldReturnTrueBecauseVeriticalGameIsFinished() {
		// given
		int[][] board = {
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
		// when
		Player[][] transformedBoard = transformToPlayer(board);
		GomokuGame game = new GomokuGame(Player.WHITE, transformedBoard);
		boolean finished = game.isFinished();

		// then
		assertTrue(finished);
	}

	@Test
	public void shouldReturnTrueBecauseHorizontalGameIsFinished() {
		// given
		int[][] board = { 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 1, 1, 1, 1, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

		// when
		Player[][] transformedBoard = transformToPlayer(board);
		GomokuGame game = new GomokuGame(Player.WHITE, transformedBoard);
		boolean finished = game.isFinished();

		// then
		assertTrue(finished);

	}
	
	@Test
	public void shouldReturnTrueBecauseCrossUpLeftToDownRightGameIsFinished() {
		// given
		int[][] board = { 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 1, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }
		};

		// when
		Player[][] transformedBoard = transformToPlayer(board);
		GomokuGame game = new GomokuGame(Player.WHITE, transformedBoard);
		boolean finished = game.isFinished();

		// then
		assertTrue(finished);

	}
	
	
	@Test
	public void shouldReturnTrueBecauseCrossDownLeftToUpRightGameIsFinished() {
		// given
		int[][] board = { 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 1, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }
		};

		// when
		Player[][] transformedBoard = transformToPlayer(board);
		GomokuGame game = new GomokuGame(Player.WHITE, transformedBoard);
		boolean finished = game.isFinished();

		// then
		assertTrue(finished);

	}

	private Player[][] transformToPlayer(int[][] board) {
		Player[][] transformedBoard = new Player[board.length][board[0].length];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				if (board[x][y] == 1) {
					transformedBoard[y][x] = Player.WHITE;
				} else if (board[x][y] == 2) {
					transformedBoard[y][x] = Player.BLACK;
				}
			}
		}
		return transformedBoard;
	}

}
