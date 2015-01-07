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

		List<Integer> snake = o.findSnake(mat);
		System.out.println(snake.toString());
	}
	
	
	List<Integer> res;	

	/**
	 * Brute force, try every point
	 */
	public List<Integer> findSnake(int[][] mat) {
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
