package interview.leetcode;

import java.util.Arrays;

/**
 * Determine if a Sudoku is valid, according to: Sudoku Puzzles - The Rules.
 * 
 * The rule of Sudoku:
 * 
 * 1. Each row must have the number 1-9 occuring just once.
 * 
 * 2. Each column must have the number 1-9 occuring just once.
 * 
 * 3. The number 1-9 must occur just once in each of sub-boxes of the grid.
 * 
 * The Sudoku board could be partially filled, where empty cells are filled with
 * the character '.'.
 * 
 * Note: A valid Sudoku board (partially filled) is not necessarily solvable.
 * Only the filled cells need to be validated.
 * 
 * @author yazhoucao
 * 
 */
public class Valid_Sudoku {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[][] board = new char[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[(i / 3) * 3 + j / 3][(i % 3) * 3 + j % 3] = (char) ('1' + j);
			}
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Basically just check whether those numbers are unique in the same rows,
	 * cols, cubes. The key is know how to map the i,j to the corresponding row,
	 * col, cube.
	 * 
	 * Because the number range of sudoku is 1-9, so each number in each row,
	 * col and block should be unique, then we can go through every position of
	 * given board, check if the number has been Found in current row,current
	 * column and current block. If so, return false;
	 * 
	 */
	public boolean isValidSudoku(char[][] board) {
		boolean[][] rows = new boolean[9][9];
		boolean[][] cols = new boolean[9][9];
		boolean[][] blocks = new boolean[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] != '.') {
					int val = board[i][j] - '1';
					if (rows[i][val] || cols[j][val] || blocks[(i / 3) * 3 + j / 3][val]){
						System.out.println("board["+i+"]["+j+"]: "+board[i][j]);
						return false;	
					}
					rows[i][val] = cols[j][val] = blocks[(i / 3) * 3 + j / 3][val] = true;
				}
			}
		}
		return true;
	}

	/**
	 * Improved a little in space
	 * 
	 * Traverse row_i, col_i, square_i at the same time
	 * 
	 * @return
	 */
	public boolean isValidSudoku_Improved(char[][] board) {
		boolean[] rows = new boolean[9];
		boolean[] cols = new boolean[9];
		boolean[] blocks = new boolean[9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				char c = board[i][j]; // fixed row i
				if (!checkAndSet(c, rows))
					return false;

				c = board[j][i]; // fixed column i
				if (!checkAndSet(c, cols))
					return false;

				c = board[(i / 3) * 3 + j / 3][(i % 3) * 3 + j % 3];
				if (!checkAndSet(c, blocks))
					return false;
			}
			Arrays.fill(rows, false);
			Arrays.fill(cols, false);
			Arrays.fill(blocks, false);
		}
		return true;
	}

	private boolean checkAndSet(char c, boolean[] valuesExist) {
		if (c == '.')
			return true;
		int idx = c - '1';
		if (valuesExist[idx])
			return false;
		valuesExist[idx] = true;
		;
		return true;
	}
}
