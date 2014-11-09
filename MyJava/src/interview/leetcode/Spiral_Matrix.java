package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a matrix of m x n elements (m rows, n columns), return all elements of
 * the matrix in spiral order.
 * 
 * For example, Given the following matrix:
 * 
 * [ [ 1, 2, 3 ],
 * 
 * [ 4, 5, 6 ],
 * 
 * [ 7, 8, 9 ] ]
 * 
 * You should return [1,2,3,6,9,8,7,4,5].
 * 
 * @author yazhoucao
 * 
 */
public class Spiral_Matrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] mat1 = new int[][] { { 2, 3 } };
		int[][] mat2 = new int[][] { { 2 }, { 3 } };
		int[][] mat3 = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		int[][] mat4 = new int[][] { { 1 }, { 2 }, { 3 }, { 4 }, { 5 }, { 6 },
				{ 7 }, { 8 }, { 9 }, { 10 } };
		System.out.println(spiralOrder(mat1).toString());
		System.out.println(spiralOrder(mat2).toString());
		System.out.println(spiralOrder(mat3).toString());
		System.out.println(spiralOrder(mat4).toString());
	}

	/**
	 * It is a rectangle rather than a square, be careful when dealing with both
	 * m and n.
	 * 
	 * O(n) Time
	 */
	public static List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> res = new ArrayList<Integer>();
		int m = matrix.length;
		int n = m == 0 ? 0 : matrix[0].length;
		int x = 0;
		int y = 0;
		while (m != 0 && n != 0) {
			// if one row/column left, no circle can be formed
			if (m == 1) {
				for (int i = 0; i < n; i++)
					res.add(matrix[x][y++]);
				break;
			} else if (n == 1) {
				for (int i = 0; i < m; i++)
					res.add(matrix[x++][y]);
				break;
			}

			for (int i = 0; i < n - 1; i++)
				res.add(matrix[x][y++]); // top, y++ move right
			for (int i = 0; i < m - 1; i++)
				res.add(matrix[x++][y]); // right, x++ move down
			for (int i = 0; i < n - 1; i++)
				res.add(matrix[x][y--]); // bottom, y-- move left
			for (int i = 0; i < m - 1; i++)
				res.add(matrix[x--][y]); // left, x-- move up

			x++;
			y++;
			m -= 2;
			n -= 2;
		}

		return res;
	}
}
