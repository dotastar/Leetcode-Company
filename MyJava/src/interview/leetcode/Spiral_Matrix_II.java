package interview.leetcode;

import java.util.Arrays;

/**
 * Given an integer n, generate a square matrix filled with elements from 1 to
 * n2 in spiral order.
 * 
 * For example, Given n = 3,
 * 
 * You should return the following matrix: [ [ 1, 2, 3 ], [ 8, 9, 4 ], [ 7, 6, 5
 * ] ]
 * 
 * 1 2 3 4 12 13 14 5 11 16 15 6 10 9 8 7
 * 
 * 1 2 3 4 5 16 17 18 19 6 15 24 25 20 7 14 23 22 21 8 13 12 11 10 9
 * 
 * @author yazhoucao
 * 
 */
public class Spiral_Matrix_II {

	public static void main(String[] args) {
		int[][] mat = generateMatrix(5);
		for (int i = 0; i < mat.length; i++)
			System.out.println(Arrays.toString(mat[i]));
	}

	/**
	 * Solution more readable and easier to memory than below solution
	 * 
	 * x, y are pointers walk spirally through the matrix
	 */
	public int[][] generateMatrix2(int n) {
		int[][] mat = new int[n][n];
		int num = 1;
		for (int i = 0; i < n / 2; i++) {
			int x = i, y = i;
			while (y < n - 1 - i)
				mat[x][y++] = num++;
			while (x < n - 1 - i)
				mat[x++][y] = num++;
			while (y > i)
				mat[x][y--] = num++;
			while (x > i)
				mat[x--][y] = num++;
		}
		if (n % 2 == 1)
			mat[n / 2][n / 2] = num;
		return mat;
	}

	/**
	 * My first solution, harder to read and understand than above solution
	 * 
	 */
	public static int[][] generateMatrix(int n) {
		int[][] mat = new int[n][n];
		int num = 1;
		for (int margin = 0; margin < n / 2; margin++) {
			// fill top
			for (int i = margin; i < n - margin - 1; i++)
				mat[margin][i] = num++;

			// fill right
			for (int i = margin; i < n - margin - 1; i++)
				mat[i][n - 1 - margin] = num++;

			// fill bottom
			for (int i = n - 1 - margin; i >= margin + 1; i--)
				mat[n - 1 - margin][i] = num++;

			// fill left
			for (int i = n - 1 - margin; i >= margin + 1; i--)
				mat[i][margin] = num++;
		}
		if (n % 2 == 1)
			mat[n / 2][n / 2] = num;
		return mat;
	}
}
