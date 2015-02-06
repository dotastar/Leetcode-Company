package interview.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard
 * such that no two queens attack each other.
 * 
 * Given an integer n, return all distinct solutions to the n-queens puzzle.
 * 
 * Each solution contains a distinct board configuration of the n-queens'
 * placement, where 'Q' and '.' both indicate a queen and an empty space
 * respectively.
 * 
 * For example, There exist two distinct solutions to the 4-queens puzzle:
 * 
 * [
 * 
 * [".Q..", // Solution 1
 * 
 * "...Q",
 * 
 * "Q...",
 * 
 * "..Q."],
 * 
 * ["..Q.", // Solution 2
 * 
 * "Q...",
 * 
 * "...Q",
 * 
 * ".Q.."]
 * 
 * ]
 * 
 * @author yazhoucao
 * 
 */
public class NQueens {

	public static void main(String[] args) {
		NQueens o = new NQueens();
		List<String[]> res = o.solveNQueens(4);
		for (String[] board : res) {
			for (String row : board)
				System.out.println(row);
			System.out.println("---------------------");
		}
	}

	/****************** Second time ******************/
	public List<String[]> solveNQueens2(int n) {
		int[] board = new int[n];
		Arrays.fill(board, -1);
		List<String[]> res = new ArrayList<>();
		fillBoard(res, board, 0);
		return res;
	}

	private void fillBoard(List<String[]> res, int[] board, int row) {
		if (row == board.length) {
			res.add(getBoard(board));
			return;
		}

		for (int col = 0; col < board.length; col++) {
			if (isValid(board, row, col)) {
				board[row] = col;
				fillBoard(res, board, row + 1);
				board[row] = -1;
			}
		}
	}

	private String[] getBoard(int[] intboard) {
		int n = intboard.length;
		String[] board = new String[n];
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++)
			sb.append('.');
		for (int i = 0; i < n; i++) {
			int col = intboard[i];
			sb.setCharAt(col, 'Q');
			board[i] = sb.toString();
			sb.setCharAt(col, '.');
		}
		return board;
	}

	/****************** First time ******************/
	/**
	 * Assign each queen a row, so they won't be conflicted in rows, so
	 * we only have to check their column and diagonal conflicts
	 * 
	 * Time: O(n!), pruned by the isValid().
	 * Space: O(n).
	 */
	char[] template;

	public List<String[]> solveNQueens(int n) {
		List<String[]> res = new ArrayList<String[]>();
		template = new char[n];
		for (int i = 0; i < n; i++)
			template[i] = '.';
		solve(0, n, new int[n], res);
		return res;
	}

	/**
	 * Backtracking, recursively try every possible solution, if failed on a
	 * point, stop recursion down and backtrack to previous point, and then try
	 * next possible point
	 */
	public void solve(int start, int n, int[] board, List<String[]> res) {
		if (start == n) {
			String[] s = new String[n];
			for (int i = 0; i < n; i++) {
				template[board[i]] = 'Q';
				s[i] = new String(template);
				template[board[i]] = '.';
			}
			res.add(s);
		}

		for (int i = 0; i < n; i++) {
			if (isValid(board, start, i)) { // pruning
				board[start] = i;
				solve(start + 1, n, board, res);
			}
		}
	}

	/**
	 * Check if the column and two diagonal has something in the way
	 * Time: O(n)
	 */
	private boolean isValid(int[] board, int row, int col) {
		assert board[row] == -1;
		for (int i = 0; i < row; i++) { // check column, and two diagonals
			if (board[i] == col || board[i] + row - i == col
					|| board[i] - (row - i) == col)
				return false;
		}
		return true;
	}

	/**
	 * Iterative solution, to be continued
	 */
	public List<String[]> solveQueens(int n) {
		List<String[]> res = new ArrayList<String[]>();
		int board[] = new int[n];
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if (isValid(board, row, col)) {
					board[row] = col;
					break;
				}
			}
		}
		return res;
	}
}
