package interview.company.epic;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Jumper Game 1: (horizontally or vertically)
 * A NxN grid which contains either of 0-empty, 1 - player1, 2 -player2.
 * Given a position in the grid, find the longest jump path.
 * For jump path, you can horizontally or vertically jump. If the adjacent chess
 * is opponent player’s and the spot beside that is empty, then the chess could
 * jump to that spot. No opponent cell can be jumped more than once.
 * Write a function which takes grid and a specific position in the grid, and
 * returns the longest possible number of jumps in the grid.
 * 
 * 
 * 
 * Jumper Game 2: (diagonally)
 * A NxN grid which contains either of 0-empty, 1 - player 1, 2 - player 2.
 * Given a position in the grid, find the longest jump path. For jump path, you
 * can jump only diagonally, you can jump on opponent cell and also the landing
 * cell should be empty. No opponent cell can be jumped more than once.
 * Write a function which takes grid and a specific position in the grid,
 * and returns the longest possible number of jumps in the grid.
 * For Example:
 * if grid = {
 * { 0,0,0,0,0,0 },
 * { 0,1,0,0,0,0 },
 * { 2,0,2,0,2,0 },
 * { 0,0,0,0,0,0 },
 * { 0,0,0,0,0,0 },
 * { 0,0,0,0,0,0 },
 * };
 * Answer should be 2 - (1,1) -> (3,3) -> (1,5) (start from (1,1))
 * 
 * @author yazhoucao
 * 
 */
public class JumperGame {
	static Class<?> c = JumperGame.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}
	
	/**
	 * Second time practice
	 */
	public int longestJump1(int[][] mat, int i, int j, int opponent, boolean[][] visited){
		int N = mat.length;
		if(i<0 || j<0 || i>=N || j>=N || mat[i][j]!=0 || visited[i][j])
			return -1;
		
		int length = 0;
		visited[i][j] = true;
		int up = (i-1>=0 && mat[i-1][j]==opponent) ? longestJump(mat, i-2, j, opponent, visited):-1;
		int down = (i+1<N && mat[i+1][j]==opponent) ? longestJump(mat, i+2, j, opponent, visited):-1;
		int left = (j-1>=0 && mat[i][j-1]==opponent) ? longestJump(mat, i, j-2, opponent, visited):-1;
		int right = (j+1<N && mat[i][j+1]==opponent) ? longestJump(mat, i, j+2, opponent, visited):-1;
		length = max(max(up, down), max(left, right)) + 1;
		visited[i][j] = false;
		return length;
	}
		

	/**
	 * Jump Game
	 * 
	 * DFS + Pruning
	 */
	public int longestJump(int[][] mat, int i, int j, int oppennent, boolean[][] visited) {
		int N = mat.length;
		if (i >= N || i < 0 || j >= N || j < 0 || mat[i][j]!=0 || visited[i][j])
			return -1;

		visited[i][j] = true;
		int longest = -1;
		if (i - 1 >= 0 && mat[i - 1][j] == oppennent)
			longest = max(longest, longestJump(mat, i - 2, j, oppennent, visited));
		if (i + 1 < N && mat[i + 1][j] == oppennent)
			longest = max(longest, longestJump(mat, i + 2, j, oppennent, visited));
		if (j - 1 >= 0 && mat[i][j - 1] == oppennent)
			longest = max(longest, longestJump(mat, i, j - 2, oppennent, visited));
		if (j + 1 < N && mat[i][j + 1] == oppennent)
			longest = max(longest, longestJump(mat, i, j + 2, oppennent, visited));
		visited[i][j] = false;
		return longest+1;
	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}

	public static void displayBoard(int[][] mat) {
		int N = mat.length;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println("");
		}
	}

	@Test
	public void test1() {
		JumperGame o = new JumperGame();
		int n = 5;
		boolean[][] visited = new boolean[n][n];
		int[][] board = new int[n][n];
		board[0] = new int[] { 0, 2, 0, 2, 0 };
		board[1] = new int[] { 2, 0, 0, 0, 0 };
		board[2] = new int[] { 0, 2, 0, 0, 0 };
		board[3] = new int[] { 0, 0, 0, 0, 0 };
		board[4] = new int[] { 0, 0, 0, 0, 0 };

		displayBoard(board);
		int length = o.longestJump1(board, 2, 2, 2, visited);
		System.out.println(length);
		assertTrue(length == 4);
	}

	@Test
	public void test2() {
		JumperGame o = new JumperGame();
		int n = 5;
		boolean[][] visited = new boolean[n][n];
		int[][] board = new int[n][n];
		board[0] = new int[] { 0, 2, 2, 2, 0 };
		board[1] = new int[] { 2, 0, 0, 0, 0 };
		board[2] = new int[] { 0, 0, 0, 0, 0 };
		board[3] = new int[] { 0, 0, 0, 0, 0 };
		board[4] = new int[] { 0, 0, 0, 0, 0 };

		displayBoard(board);
		int length = o.longestJump1(board, 2, 2, 2, visited);
		System.out.println(length);
		assertTrue(length == 0);
	}

	@Test
	public void test3() {
		JumperGame o = new JumperGame();
		int n = 5;
		boolean[][] visited = new boolean[n][n];
		int[][] board = new int[n][n];
		board[0] = new int[] { 2, 0, 0, 0, 0 };
		board[1] = new int[] { 2, 0, 0, 0, 0 };
		board[2] = new int[] { 0, 2, 0, 0, 0 };
		board[3] = new int[] { 1, 0, 0, 0, 0 };
		board[4] = new int[] { 1, 0, 0, 0, 0 };

		displayBoard(board);
		int length = o.longestJump1(board, 2, 2, 2, visited);
		System.out.println(length);
		assertTrue(length == 1);
	}

	@Test
	public void test4() {
		JumperGame o = new JumperGame();
		int n = 5;
		boolean[][] visited = new boolean[n][n];
		int[][] board = new int[n][n];
		board[0] = new int[] { 0, 2, 0, 2, 0 };
		board[1] = new int[] { 2, 0, 0, 0, 2 };
		board[2] = new int[] { 0, 2, 0, 0, 0 };
		board[3] = new int[] { 2, 0, 0, 0, 2 };
		board[4] = new int[] { 0, 2, 0, 2, 0 };

		displayBoard(board);
		int length = o.longestJump1(board, 2, 2, 2, visited);
		System.out.println(length);
		assertTrue(length == 8);
	}
}