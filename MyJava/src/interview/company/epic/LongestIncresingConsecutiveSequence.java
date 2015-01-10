package interview.company.epic;

import java.util.ArrayList;
import java.util.List;

/**
 * find longest increasing sub sequence in 2d array.
 * (bit more expl..)
 * ex: finding length of the snake in snake game
 * ---------
 * the sequence must not be diagonally.
 * but it can be any like top-bootm,bottom-left-top ........
 * increasing means one step
 * Ex: 10,11,12,13 (correct)
 * 12,14,15,20(wrong)
 * Ex: input: consider 4x4 grid
 * 2 3 4 5
 * 4 5 10 11
 * 20 6 9 12
 * 6 7 8 40
 * 
 * output : 4 5 6 7 8 9 10 11 12
 * 
 * @author yazhoucao
 * 
 */
public class LongestIncresingConsecutiveSequence {

	public static void main(String[] args) {
		LongestIncresingConsecutiveSequence o = new LongestIncresingConsecutiveSequence();
		int[][] mat = { // 4*4
		{ 6, 7, 8, 40 }, 
		{ 2, 6, 9, 12 }, 
		{ 4, 5, 10, 11 }, 
		{ 2, 3, 4, 5 } };

		List<Integer> snake = o.findSubsequence_recursion(mat);
		int length = o.findMaxLength(mat);
		assert snake.size() == length;
		System.out.println(snake.toString() + "\tmax length:" + length);
	}

	/**
	 * Dynamic Programming - for just finding the max length
	 */
	public int findMaxLength(int[][] mat) {
		int max = 0;
		int m = mat.length;
		int n = m == 0 ? 0 : mat[0].length;
		int[][] dp = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int length = maxLength(mat, i, j, dp);
				max = length > max ? length : max;
			}
		}
		return max;
	}

	private int maxLength(int[][] mat, int i, int j, int[][] dp) {
		if (dp[i][j] != 0)
			return dp[i][j];
		int up = 0, down = 0, left = 0, right = 0;
		if (i - 1 >= 0 && mat[i - 1][j] - mat[i][j] == 1)
			up = maxLength(mat, i - 1, j, dp);
		if (i + 1 < mat.length && mat[i + 1][j] - mat[i][j] == 1)
			down = maxLength(mat, i + 1, j, dp);
		if (j - 1 >= 0 && mat[i][j - 1] - mat[i][j] == 1)
			up = maxLength(mat, i, j - 1, dp);
		if (j + 1 < mat[0].length && mat[i][j + 1] - mat[i][j] == 1)
			down = maxLength(mat, i, j + 1, dp);

		int length = max(max(up, down), max(left, right)) + 1;
		dp[i][j] = length;
		return length;

	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}
	
	
	/**
	 * Brute force, try every point -- for find the sequence
	 */
	List<Integer> res;

	public List<Integer> findSubsequence_recursion(int[][] mat) {
		res = null;
		List<Integer> container = new ArrayList<>();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				findPath(mat, i, j, container);
			}
		}
		return res;
	}

	/**
	 * DFS
	 */
	private void findPath(int[][] mat, int i, int j, List<Integer> path) {
		if (i < 0 || j < 0 || i >= mat.length || j >= mat[0].length)
			return;

		path.add(mat[i][j]);
		int val = mat[i][j];
		mat[i][j] = Integer.MAX_VALUE;

		if (i - 1 >= 0 && (mat[i - 1][j] - val) == 1)
			findPath(mat, i - 1, j, path); // try up
		if (i + 1 < mat.length && (mat[i + 1][j] - val) == 1)
			findPath(mat, i + 1, j, path); // try down
		if (j - 1 >= 0 && (mat[i][j - 1] - val) == 1)
			findPath(mat, i, j - 1, path); // try left
		if (j + 1 < mat[0].length && (mat[i][j + 1] - val) == 1)
			findPath(mat, i, j + 1, path); // try right

		// update result
		if (res == null || path.size() > res.size())
			res = new ArrayList<Integer>(path);

		mat[i][j] = val;
		path.remove(path.size() - 1);

	}
}
