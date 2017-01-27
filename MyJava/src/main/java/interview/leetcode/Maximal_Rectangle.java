package interview.leetcode;

import java.util.Stack;

/**
 * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle
 * containing all ones and return its area.
 * 
 * @author yazhoucao
 * 
 */
public class Maximal_Rectangle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Maximal_Rectangle obj = new Maximal_Rectangle();
		char[][] mat = new char[][] { { '1' } };
		System.out.println(obj.maximalRectangle(mat));
	}

	/**
	 * Thought: fix one side, dynamic programming-ly solve the other side.
	 * 
	 * We use each row as the base of a histogram(notice: it's base, not
	 * ceiling, this is important!), each column is a rectangle in the
	 * histogram.
	 * 
	 * We enumerate all histograms that use every rows as base and use the first
	 * row as ceiling, and record and return max.
	 * 
	 * So for each histogram, the height of each column is easy to calculate:
	 * height[j] (base row i) =
	 * 1.height[j](base row i-1)+1, if matrix[i][j]=='1';
	 * 2.0, if matrix[i][j]=='0';
	 * 
	 * Time n*(n+n) = O(n^2)
	 */
	public int maximalRectangle(char[][] matrix) {
		int m = matrix.length;
		int n = m == 0 ? 0 : matrix[0].length;
		int[] height = new int[n];
		int res = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				height[j] = matrix[i][j] == '1' ? height[j] + 1 : 0;
			res = max(res, maxHistogram2(height));
		}
		return res;
	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}

	/**
	 * Time: O(n^2), but its real running time is faster on LeetCode OJ.
	 */
	public int maxHistogram(int[] H) {
		int max = 0;
		int len = H.length;
		for (int i = 0; i < len; i++) {
			if (i != len - 1 && H[i] <= H[i + 1])
				continue;
			int height = H[i];
			for (int j = i; j >= 0; j--) {
				height = height < H[j] ? height : H[j];
				int area = (i - j + 1) * height;
				max = area > max ? area : max;
			}
		}
		return max;
	}

	/**
	 * Time: O(n), but it's slower than the above one.
	 */
	public int maxHistogram2(int[] H) {
		int max = 0;
		int len = H.length;
		Stack<Integer> left = new Stack<Integer>();
		for (int i = 0; i < len; i++) {
			while (!left.isEmpty() && H[i] <= H[left.peek()]) {
				int idx = left.pop();
				int width = left.isEmpty() ? i : i - (left.peek() + 1);
				int area = H[idx] * width;
				max = area > max ? area : max;
			}
			left.push(i);
		}
		while (!left.isEmpty()) {
			int idx = left.pop();
			int width = left.isEmpty() ? len : len - (left.peek() + 1);
			int area = H[idx] * width;
			max = area > max ? area : max;
		}
		return max;
	}
}
