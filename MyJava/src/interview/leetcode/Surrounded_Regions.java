package interview.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a 2D board containing 'X' and 'O', capture all regions surrounded by
 * 'X'.
 * 
 * A region is captured by flipping all 'O's into 'X's in that surrounded
 * region.
 * 
 * For example,
 * 
 * X X X X X O O X X X O X X O X X
 * 
 * After running your function, the board should be:
 * 
 * X X X X X X X X X X X X X O X X
 * 
 * @author yazhoucao
 * 
 */
public class Surrounded_Regions {

	public static void main(String[] args) {
		Surrounded_Regions obj = new Surrounded_Regions();
		String[] sarr = { "OXXOX", "XOOXO", "XOXOX", "OXOOO", "XXOXO" };
		char[][] board1 = new char[sarr.length][sarr[0].length()];
		for (int i = 0; i < sarr.length; i++)
			board1[i] = sarr[i].toCharArray();

		for (int i = 0; i < sarr.length; i++)
			System.out.println(new String(board1[i]));
		System.out.println("After solve():");
		obj.solve(board1);
		for (int i = 0; i < sarr.length; i++)
			System.out.println(new String(board1[i]));

	}

	/**
	 * BFS
	 * 
	 * @param board
	 */
	public void solve(char[][] board) {
		int m = board.length;
		int n = m == 0 ? 0 : board[0].length;
		for (int i = 0; i < n; i++) {
			floodfill(board, 0, i); 	// top
			floodfill(board, m - 1, i); // bottom
		}
		for (int i = 1; i < m - 1; i++) {
			floodfill(board, i, 0); 	// leftmost
			floodfill(board, i, n - 1); // rightmost
		}
		// check all cells
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == '#') // not surrounded
					board[i][j] = 'O';
				else
					board[i][j] = 'X';
			}
		}
	}

	/**
	 * BFS fasion, flood fill, begin from elements of four borders
	 * 
	 * see flood fill: http://zh.wikipedia.org/wiki/Flood_fill
	 * 
	 * Another trick is how to use one integer to represent a coordinate,
	 * 
	 * because for a matrix[m][n], j must < n, so i*n could be the tens digit,
	 * so ( i*n + j ) can represent a 2-D coordinate.
	 */
	private void floodfill(char[][] board, int i, int j) {
		if (board[i][j] != 'O')
			return;
		int m = board.length;
		int n = m == 0 ? 0 : board[0].length;
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(i * n + j);
		while (!q.isEmpty()) {
			int pos = q.poll();
			int x = pos / n;
			int y = pos % n;
			if (x >= 0 && x < m && y >= 0 && y < n && board[x][y] == 'O') {
				board[x][y] = '#';	//mark it as not surrounded
				q.add((x + 1) * n + y);
				q.add((x - 1) * n + y);
				q.add(x * n + y + 1);
				q.add(x * n + y - 1);
			}
		}
	}

	/**
	 * DFS search, this will be stack overflow
	 */
	protected void floodfill_DFS(char[][] board, int i, int j) {
		int m = board.length;
		int n = m == 0 ? 0 : board[0].length;
		if (i < 0 || j < 0 || i >= m || j >= n)
			return;
		if (board[i][j] == 'O') {
			board[i][j] = '#'; // mark as not surrounded
			floodfill(board, i + 1, j);
			floodfill(board, i - 1, j);
			floodfill(board, i, j + 1);
			floodfill(board, i, j - 1);
		}
	}

}
