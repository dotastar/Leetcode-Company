package interview.laicode;

import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * N Queens
 * Fair
 * Recursion
 * 
 * Get all valid ways of putting N Queens on an N * N chessboard so that no two
 * Queens threaten each other.
 * 
 * Assumptions
 * 
 * N > 0
 * 
 * Return
 * 
 * A list of ways of putting the N Queens
 * Each way is represented by a list of the Queen's y index for x indices of 0
 * to (N - 1)
 * 
 * Example
 * 
 * N = 4, there are two ways of putting 4 queens:
 * 
 * [1, 3, 0, 2] --> the Queen on the first row is at y index 1, the Queen on the
 * second row is at y index 3, the Queen on the third row is at y index 0 and
 * the Queen on the fourth row is at y index 2.
 * 
 * [2, 0, 3, 1] --> the Queen on the first row is at y index 2, the Queen on the
 * second row is at y index 0, the Queen on the third row is at y index 3 and
 * the Queen on the fourth row is at y index 1.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class N_Queens {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(N_Queens.class);
	}

	public List<List<Integer>> nqueens(int n) {
		Integer[] board = new Integer[n];
		List<List<Integer>> res = new ArrayList<>();
		solve(res, board, 0);
		return res;
	}

	private void solve(List<List<Integer>> res, Integer[] board, int row) {
		if (row == board.length) {
			List<Integer> solu = new ArrayList<>();
			for (int num : board)
				solu.add(num);
			res.add(solu);
			return;
		}

		for (int col = 0; col < board.length; col++) {
			if (isValidBoard(board, row, col)) {
				board[row] = col;
				solve(res, board, row + 1);
				board[row] = -1;
			}
		}
	}

	/**
	 * # # # Q
	 * # Q # #
	 */
	private boolean isValidBoard(Integer[] board, int row, int col) {
		for (int i = 0; i < row; i++) {
			if (board[i] == col || board[i] == col + row - i
					|| board[i] == col - (row - i))
				return false;
		}
		return true;
	}

	@Test
	public void test1() {
		List<List<Integer>> res = nqueens(4);
		for (List<Integer> board : res)
			System.out.println(board);
	}
}
