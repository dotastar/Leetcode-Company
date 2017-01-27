package interview.leetcode;

/**
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 * 
 * Empty cells are indicated by the character '.'.
 * 
 * You may assume that there will be only one unique solution.
 * 
 * @author yazhoucao
 * 
 */
public class Sudoku_Solver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Valid_Sudoku v = new Valid_Sudoku();
		Sudoku_Solver o = new Sudoku_Solver();

		/*** Test case 1 ***/
		String[] raw1 = { "..9748...", "7........", ".2.1.9...", "..7...24.",
				".64.1.59.", ".98...3..", "...8.3.2.", "........6", "...2759.." };
		// "519748632","783652419","426139875","357986241","264317598","198524367","975863124","832491756","641275983"]
		char[][] board1 = new char[9][9];
		for (int i = 0; i < 9; i++) {
			String s = raw1[i];
			board1[i] = s.toCharArray();
		}
		o.solveSudoku(board1);
		assert v.isValidSudoku(board1);

		/*** Test case 0 ***/
		String[] raw0 = { "53..7....", "6..195...", ".98....6.", "8...6...3",
				"4..8.3..1", "7...2...6", ".6....28.", "...419..5", "....8..79" };
		char[][] board0 = new char[9][9];
		for (int i = 0; i < 9; i++) {
			String s = raw0[i];
			board0[i] = s.toCharArray();
		}
		o.solveSudoku(board0);
		assert v.isValidSudoku(board0);
	}

	/**
	 * Second time practice, use three arrays to check the validity of board
	 */
	boolean[][] rows;
	boolean[][] cols;
	boolean[][] blocks;

	public void solveSudoku2(char[][] board) {
		rows = new boolean[9][9];
		cols = new boolean[9][9];
		blocks = new boolean[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] != '.') {
					int val = board[i][j] - '1';
					rows[i][val] = true;
					cols[j][val] = true;
					blocks[(i / 3) * 3 + j / 3][val] = true;
				}
			}
		}

		assert solve(board, 0, 0);
	}

	/**
	 * The advantage of use backtracking is that you can backward if the current
	 * path is wrong.
	 */
	public boolean solve(char[][] board, int i, int j) {
		i += j / 9;
		j = j % 9;
		if (i >= 9)
			return true;

		if (board[i][j] != '.')
			return solve(board, i, j + 1);

		for (int val = 0; val < 9; val++) {
			if (rows[i][val] || cols[j][val] || blocks[(i / 3) * 3 + j / 3][val])
				continue;
			
			board[i][j] = (char) ('1' + val);			
			rows[i][val] = cols[j][val] = blocks[(i / 3) * 3 + j / 3][val] = true;
			if (solve(board, i, j + 1))
				return true;
			rows[i][val] = cols[j][val] = blocks[(i / 3) * 3 + j / 3][val] = false;
		}
		board[i][j] = '.';
		
		return false;
	}

	/**
	 * Backtracking
	 * 
	 */
	public void solveSudoku(char[][] board) {
		fillBoard(board, 0, 0);
	}

	public boolean fillBoard(char[][] board, int row, int col) {
		if (col >= 9)
			return fillBoard(board, row + 1, 0);
		if (row == 9)
			return true;

		if (board[row][col] == '.') { // need to be filled
			for (int i = 1; i <= 9; i++) {
				board[row][col] = (char) ('0' + i);
				if (isValid(board, row, col))
					if (fillBoard(board, row, col + 1)) // go down to next
						return true;

				board[row][col] = '.'; // otherwise, backtracking, try next
			}
		} else {
			return fillBoard(board, row, col + 1);
		}
		return false;
	}

	private boolean isValid(char[][] board, int i, int j) {
		for (int k = 0; k < 9; k++) {
			if (k != j && board[i][k] == board[i][j])
				return false;
		}
		for (int k = 0; k < 9; k++) {
			if (k != i && board[k][j] == board[i][j])
				return false;
		}
		for (int row = i / 3 * 3; row < i / 3 * 3 + 3; row++) {
			for (int col = j / 3 * 3; col < j / 3 * 3 + 3; col++) {
				if ((row != i || col != j) && board[row][col] == board[i][j])
					return false;
			}
		}
		return true;
	}
}
