package interview.leetcode;


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
		// TODO Auto-generated method stub

	}

    public int totalNQueens(int n) {
        int[] board = new int[n];
        int half = 0;
        for(int i=0; i<n/2; i++){
        	board[0] = i;
        	half += solve(1, board);
        }
        half *= 2;
        if(n%2==1){
        	board[0] = n/2;
        	half += solve(1, board);
        }
        return half;
    }

	/**
	 * Backtracking, recusively try every possible solution, if failed on a
	 * point, stop recurison down and backtrack to previous point, and then try
	 * next possible point
	 */
	public int solve(int startRow, int[] board) {
		if (startRow == board.length) {
			return 1;
		}
		
		int solutions = 0;
		for (int i = 0; i < board.length; i++) {
			if (checkValid(startRow, i, board)) { // if valid, recursively go down
				board[startRow] = i;
				solutions += solve(startRow + 1, board);
			} // otherwise try next branch
		}
		return solutions;
	}

	/**
	 * It is valid if colj and diagnol has no other queens
	 * 
	 * Time O(n)
	 * 
	 */
	private boolean checkValid(int rowi, int colj, int[] columnPos) {
		for (int i = 0; i < rowi; i++) { // only check rows from 0 to rowi-1
			int currCol = columnPos[i];
			if (currCol == colj)// check column validity
				return false;

			int distance = rowi - i; // column diagnol distance from i to rowi
			if (currCol - distance == colj || currCol + distance == colj)
				return false; // bottom left and right diagnol
		}

		return true;
	}
}
