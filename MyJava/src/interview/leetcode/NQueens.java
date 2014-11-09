package interview.leetcode;

import java.util.ArrayList;
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
			if (isValid(board, start, i)) {	//pruning
				board[start] = i;
				solve(start + 1, n, board, res);
			}
		}
	}
	
	/**
	 * Check if the column and two diagonal has something in the way 
	 * Time: O(n)
	 * 
	 * @param colIndice, the board, value is column index, index is row index
	 * @param startRow, the row index that is going to check.
	 * @param startCol, the column index that is going to check
	 * @return
	 */
	private boolean isValid(int[] colIndice, int startRow, int startCol) {
		for (int rowIdx = startRow - 1; rowIdx >= 0; rowIdx--) {
			if(colIndice[rowIdx]==startCol)	// check column validity
				return false;
			int dist = startRow-rowIdx;	// column diagonal distance from startRow to rowIdx
			if(colIndice[rowIdx]==startCol-dist || colIndice[rowIdx]==startCol+dist)
				return false;	//check top left and right diagonal validity
		}
		return true;
	}
	
	
	/**
	 * Iterative solution, to be continued
	 */
	public List<String[]> solveQueens(int n){
		List<String[]> res = new ArrayList<String[]>();
		int board[] = new int[n];
		for(int row=0; row<n; row++){
			for(int col=0; col<n; col++){
				if(isValid(board, row, col)){
					board[row] = col;
					break;
				}
			}
		}
		return res;
	}
}
