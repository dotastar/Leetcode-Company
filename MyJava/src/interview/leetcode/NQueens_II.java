package interview.leetcode;

import java.util.Arrays;

/**
 * Follow up for N-Queens problem.
 * 
 * Now, instead outputting board configurations, return the total number of
 * distinct solutions.
 * 
 * @author yazhoucao
 * 
 */
public class NQueens_II {

	public static void main(String[] args) {
		NQueens_II o = new NQueens_II();
		System.out.println(o.totalNQueens(4));
	}

	public int totalNQueens(int n) {
		int[] board = new int[n];
		Arrays.fill(board, -1);
		int count = 0;
		for (int i = 0; i < n / 2; i++) {
			board[0] = i;
			count += fillBoard(board, 1);
		}

		count *= 2;

		if (n % 2 == 1) {
			board[0] = n / 2;
			count += fillBoard(board, 1);
		}
		return count;

	}

	private int fillBoard(int[] board, int row) {
		if (row == board.length)
			return 1;
		int count = 0;
		for (int col = 0; col < board.length; col++) {
			if (isValid(board, row, col)) {
				board[row] = col;
				count += fillBoard(board, row + 1);
				board[row] = -1;
			}
		}
		return count;
	}

	private boolean isValid(int[] board, int row, int col) {
		assert board[row] == -1;
		for (int i = 0; i < row; i++) { // check column, and two diagonals
			if (board[i] == col || board[i] + row - i == col
					|| board[i] - (row - i) == col)
				return false;
		}
		return true;
	}
}
