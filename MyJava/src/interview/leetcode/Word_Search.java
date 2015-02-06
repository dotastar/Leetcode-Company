package interview.leetcode;

/**
 * Given a 2D board and a word, find if the word exists in the grid.
 * 
 * The word can be constructed from letters of sequentially adjacent cell, where
 * "adjacent" cells are those horizontally or vertically neighboring. The same
 * letter cell may not be used more than once.
 * 
 * For example, Given board =
 * 
 * [ ["ABCE"],
 * 
 * ["SFCS"],
 * 
 * ["ADEE"] ]
 * 
 * word = "ABCCED", -> returns true, word = "SEE", -> returns true, word =
 * "ABCB", -> returns false.
 * 
 * @author yazhoucao
 * 
 */
public class Word_Search {

	public static void main(String[] args) {
		char[][] board = new char[][] { { 'a', 'b' }, { 'c', 'd' } };
		String word = "acdb";
		System.out.println(exist(board, word));

		Word_Search o = new Word_Search();
		String[] s = { "ABCE", "SFES", "ADEE" };
		char[][] board2 = new char[s.length][];
		for (int i = 0; i < s.length; i++)
			board2[i] = s[i].toCharArray();
		String word2 = "ABCESEEEFS";
		System.out.println(o.exist2(board2, word2));
	}

	/**
	 * Because the same cell may not be used more than once, we can only use
	 * recursion.
	 * If the same cell can be used more than once, we could use DP to optimize
	 * it, details see EPI/CH17/Q6_Search_For_A_Sequence_In_A_2D_Array
	 */

	/**
	 * Second time
	 */
	public boolean exist2(char[][] board, String word) {
		int m = board.length;
		int n = m == 0 ? 0 : board[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (exist(board, word, i, j, 0))
					return true;
			}
		}
		return false;
	}

	public boolean exist(char[][] board, String word, int i, int j, int idx) {
		if (idx == word.length())
			return true;
		int m = board.length;
		int n = m == 0 ? 0 : board[0].length;
		if (i < 0 || j < 0 || i >= m || j >= n || board[i][j] == '\0'
				|| word.charAt(idx) != board[i][j])
			return false;

		board[i][j] = '\0';
		for (int p = -1; p <= 1; p++) {
			for (int q = -1; q <= 1; q++)
				if (Math.abs(p + q) == 1
						&& exist(board, word, i + p, j + q, idx + 1))
					return true;
		}
		board[i][j] = word.charAt(idx);
		return false;
	}

	/**
	 * Backtracking, mark the route is being used to make sure it won't
	 * calculate the path already visited.
	 * 
	 * Time O(mn * 4^k)
	 * 
	 * m, n is the length of board, k is the length of word
	 * 
	 */
	public static boolean exist(char[][] board, String word) {
		int m = board.length;
		int n = m == 0 ? 0 : board[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (search(board, i, j, word, 0))
					return true;
			}
		}
		return false;
	}

	/**
	 * Recursively serach the word, DFS
	 * 
	 * Time O(4^k), k is the length of word
	 * 
	 */
	private static boolean search(char[][] board, int i, int j, String word,
			int idx) {
		if (idx == word.length())
			return true;
		int m = board.length;
		int n = m == 0 ? 0 : board[0].length;
		if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] == '#')
			return false; // back if cross boarder or visited

		if (word.charAt(idx) == board[i][j]) {
			board[i][j] = '#'; // mark as visited
			boolean res = search(board, i + 1, j, word, idx + 1)
					|| search(board, i - 1, j, word, idx + 1)
					|| search(board, i, j + 1, word, idx + 1)
					|| search(board, i, j - 1, word, idx + 1);
			board[i][j] = word.charAt(idx); // recover it
			return res;
		}
		return false;
	}
}
