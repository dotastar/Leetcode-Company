package interview.company.epic;

import static org.junit.Assert.*;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * Tic Tac Toe
 * N*N matrix is given with input red or black.You can move horizontally,
 * vertically or diagonally. If 3 consecutive same color found, that color will
 * get 1 point. So if 4 red are vertically then point is 2. Find the winner.
 * 
 * @author yazhoucao
 * 
 */
public class TicTacToe {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(TicTacToe.class);
	}

	/**
	 * Traverse the board, at each point check if get the point.
	 * Time: O(mn).
	 * Return: 1 is red, 2 is black, 0 is equals.
	 * In the board, true is red, false is black.
	 */
	public int tictactoe(boolean[][] board) {
		int red = 0, black = 0;
		int m = board.length;
		int n = m==0 ? 0 : board[0].length;
		for(int i=0; i<m; i++){
			for(int j=0; j<n; j++){
				int count = 0;
				if(i+2<m && board[i][j]==board[i+1][j] && board[i][j]==board[i+2][j])
					count++;	// Vertical
				if(j+2<n && board[i][j]==board[i][j+1] && board[i][j]==board[i][j+2])
					count++;	// Horizontal
				if(j+2<n && i+2<m && board[i][j]==board[i+1][j+1] && board[i][j]==board[i+2][j+2])
					count++;	// Diagonal, bottom-right
				if(j+2<n && i-2>=0 && board[i][j]==board[i-1][j+1] && board[i][j]==board[i-2][j+2])
					count++;	//Diagonal, top-right
				
				if(board[i][j])
					red += count;
				else
					black += count;
			}
		}
		return red == black ? 0 : red > black ? 1 : 2;
	}

	@Test
	public void test1() {
		boolean[][] board = { 
				{ true, true, true, true },
				{ true, false, false, false }, 
				{ true, false, true, true } };
		
		assertTrue(tictactoe(board)==1);
	}

	@Test
	public void test2() {
		boolean[][] board = { 
				{ true, false, true }, 
				{ false, true, false },
				{ true, false, true } };
		
		assertTrue(tictactoe(board)==1);
	}

	@Test
	public void test3() {
		boolean[][] board = { 
				{ true, true, true, true },
				{ false, true, true, true }, 
				{ false, true, false, true },
				{ true, true, true, true } };
		
		assertTrue(tictactoe(board)==1);
	}
	
	@Test
	public void test4() {
		boolean[][] board = { 
				{ true, false, false }, 
				{ true, true, false },
				{ true, false, false } };
		
		assertTrue(tictactoe(board)==0);
	}
	

	@Test
	public void test5() {
		boolean[][] board = { 
				{ false, false, true, false },
				{ false, true, false, true }, 
				{ false, true, false, true },
				{ false, true, false, false } };
		
		assertTrue(tictactoe(board)==2);
	}
}
