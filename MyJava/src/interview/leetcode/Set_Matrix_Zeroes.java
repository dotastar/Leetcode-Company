package interview.leetcode;

/**
 * Given a m x n matrix, if an element is 0, set its entire row and column to 0.
 * Do it in place.
 * 
 * Follow up:
 * 
 * Did you use extra space? A straight forward solution using O(mn) space is
 * probably a bad idea. A simple improvement uses O(m + n) space, but still not
 * the best solution. Could you devise a constant space solution?
 * 
 * @author yazhoucao
 * 
 */
public class Set_Matrix_Zeroes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Space O(m+n)
	 * 
	 * @param matrix
	 */
	public void setZeroes(int[][] matrix) {
		if (matrix.length == 0)
			return;
		int leni = matrix.length;
		int lenj = matrix[0].length;
		int[] row = new int[leni];
		int[] col = new int[lenj];

		for (int i = 0; i < leni; i++) {
			for (int j = 0; j < lenj; j++) {
				if (matrix[i][j] == 0) {
					row[i] = 1;
					col[j] = 1;
				}
			}
		}

		for (int i = 0; i < leni; i++) {
			if (row[i] == 1)
				for (int j = 0; j < lenj; j++)
					matrix[i][j] = 0;
		}

		for (int j = 0; j < lenj; j++) {
			if (col[j] == 1)
				for (int i = 0; i < leni; i++)
					matrix[i][j] = 0;
		}
	}

	/**
	 * Constant Space solution
	 * 
	 * This problem can solve by following 4 steps:
	 * 
	 * 1.check if first row and column are zero or not
	 * 
	 * 2.mark zeros on first row and column
	 * 
	 * 3.use mark to set elements
	 * 
	 * 4.set first column and row by using marks in step 1
	 * 
	 * @param matrix
	 */
	public void setZeroes_Improved(int[][] matrix) {
		if (matrix.length == 0)
			return;
		int leni = matrix.length;
		int lenj = matrix[0].length;

		boolean firstrow = false;
		boolean firstcol = false;

		// check if first row and col has 0
		for (int i = 0; i < leni; i++)
			if (matrix[i][0] == 0) {
				firstcol = true;
				break;
			}

		for (int j = 0; j < lenj; j++)
			if (matrix[0][j] == 0) {
				firstrow = true;
				break;
			}

		// mark rows and cols that has 0
		for (int i = 1; i < leni; i++) {
			for (int j = 1; j < lenj; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][0] = 0;
					matrix[0][j] = 0;
				}
			}
		}

		// set matrix rows and cols to 0 except the first
		for (int i = 1; i < leni; i++) {
			for (int j = 1; j < lenj; j++) {
				if (matrix[i][0] == 0 || matrix[0][j] == 0) {
					matrix[i][j] = 0;
				}
			}
		}

		// set first row and col to 0
		if (firstrow)
			for (int j = 0; j < lenj; j++)
				matrix[0][j] = 0;
		if (firstcol)
			for (int i = 0; i < leni; i++)
				matrix[i][0] = 0;
	}
}
