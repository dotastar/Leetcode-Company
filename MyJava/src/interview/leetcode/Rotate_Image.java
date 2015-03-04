package interview.leetcode;

import java.util.Arrays;

/**
 * You are given an n x n 2D matrix representing an image.
 * 
 * Rotate the image by 90 degrees (clockwise).
 * 
 * Follow up: Could you do this in-place?
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Rotate_Image {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] matrix = new int[4][4];
		int val = 1;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = val;
			}
			val++;
		}

		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}

		rotate2(matrix);
		System.out.println("After rotate: ");
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
	}

	public static void rotate(int[][] matrix) {
		int len = matrix.length;
		int last = len - 1; // last element index
		for (int margin = 0; margin < len / 2; margin++) {
			for (int i = margin; i < len - 1 - margin; i++) {
				int lefttop = matrix[margin][i]; // save left top
				// left to top
				matrix[margin][i] = matrix[last - i][margin];
				// bottom to left
				matrix[last - i][margin] = matrix[last - margin][last - i];
				// right to bottom
				matrix[last - margin][last - i] = matrix[i][last - margin];
				// top to right
				matrix[i][last - margin] = lefttop;
			}
		}
	}

	/**
	 * Exactly the same solution as above
	 */
	public static void rotate2(int[][] matrix) {
		int n = matrix.length;
		for (int i = 0; i < n / 2; i++) { // margin
			for (int j = i; j < n - 1 - i; j++) { // offset
				int lefttop = matrix[i][j];
				matrix[i][j] = matrix[n - 1 - j][i];
				matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
				matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
				matrix[j][n - 1 - i] = lefttop;
			}
		}
	}
}
