package interview.leetcode;

/**
 * 
 * Write an efficient algorithm that searches for a value in an m x n matrix.
 * This matrix has the following properties:
 * 
 * Integers in each row are sorted from left to right. The first integer of each
 * row is greater than the last integer of the previous row. For example,
 * 
 * Consider the following matrix:
 * 
 * [[1, 3, 5, 7],
 * 
 * [10, 11, 16, 20],
 * 
 * [23, 30, 34, 50] ]
 * 
 * Given target = 3, return true.
 * 
 * @author yazhoucao
 * 
 */
public class Search_a_2D_Matrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] mat = new int[2][1];
		mat[0][0] = 1;
		mat[1][0] = 3;
		System.out.println(searchMatrix2(mat, 3));
	}

	public static boolean searchMatrix(int[][] matrix, int target) {
		if (matrix.length == 0)
			return false;
		return searchMatrix(matrix, target, 0, 0);
	}

	/**
	 * The route can be decided O(m+n)
	 * 
	 * @return
	 */
	public static boolean searchMatrix(int[][] matrix, int target, int m, int n) {
		if (m >= matrix.length || n >= matrix[0].length)
			return false;
		if (matrix[m][n] == target)
			return true;

		if (m + 1 < matrix.length) {
			if (target >= matrix[m + 1][n])
				return searchMatrix(matrix, target, m + 1, n);
			else
				return searchMatrix(matrix, target, m, n + 1);
		} else
			return searchMatrix(matrix, target, m, n + 1);
	}

	/**
	 * Binary Search to decide which row, then bsearch that row
	 * 
	 * Time: O(lg(m) + lg(n))
	 * 
	 */
	public static boolean searchMatrix2(int[][] matrix, int target) {
		int m = matrix.length;
		int n = m == 0 ? 0 : matrix[0].length;
		int row = -1;
		int l = 0;
		int r = m - 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (mid==m-1 ||(target >= matrix[mid][0] && target < matrix[mid + 1][0])) {
				row = mid;
				break;
			} else if (target < matrix[mid][0])
				r = mid-1;
			else
				l = mid+1;
		}
		if (row < 0)
			return false;
		l = 0;
		r = n - 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (target == matrix[row][mid])
				return true;
			else if (target > matrix[row][mid])
				l = mid + 1;
			else
				r = mid - 1;
		}

		return false;
	}
}
