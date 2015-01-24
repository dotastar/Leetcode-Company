package interview.epi.chapter17_dynamic_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Let A be an n*m boolean 2D array. Design efficient algorithms for the
 * following two problems:
 * What is the largest 2D subarray containing only 1s?
 * What is the largest square 2D subarray containing only 1s?
 * 
 * @author yazhoucao
 * 
 */
public class Q23_Find_The_Maximum_2D_Subarray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private static class MaxHW {
		public int h, w;

		public MaxHW(int h, int w) {
			this.h = h;
			this.w = w;
		}
	}

	public static int maxRectangleSubmatrix(ArrayList<ArrayList<Boolean>> A) {
		// DP table stores (h, w) for each (i, j).
		MaxHW[][] table = new MaxHW[A.size()][A.get(0).size()];
		for (int i = A.size() - 1; i >= 0; --i) {
			for (int j = A.get(i).size() - 1; j >= 0; --j) {
				// Find the largest h such that (i, j) to (i + h - 1, j) are feasible.
				// Find the largest w such that (i, j) to (i, j + w - 1) are feasible.
				table[i][j] = A.get(i).get(j) ? new MaxHW(
						i + 1 < A.size() ? table[i + 1][j].h + 1 : 1, j + 1 < A.get(i).size() ? table[i][j + 1].w + 1 : 1)
						: new MaxHW(0, 0);
			}
		}
		int maxRectArea = 0;
		for (int i = 0; i < A.size(); ++i) {
			for (int j = 0; j < A.get(i).size(); ++j) {
				// Process (i, j) if it is feasible and is possible to update maxRectArea.
				if (A.get(i).get(j)
						&& table[i][j].w * table[i][j].h > maxRectArea) {
					int minWidth = Integer.MAX_VALUE;
					for (int a = 0; a < table[i][j].h; ++a) {
						minWidth = Math.min(minWidth, table[i + a][j].w);
						maxRectArea = Math.max(maxRectArea, minWidth * (a + 1));
					}
				}
			}
		}
		return maxRectArea;
	}

	public static int maxSquareSubmatrix(ArrayList<ArrayList<Boolean>> A) {
		// DP table stores (h, w) for each (i, j).
		MaxHW[][] table = new MaxHW[A.size()][A.get(0).size()];
		for (int i = A.size() - 1; i >= 0; --i) {
			for (int j = A.get(i).size() - 1; j >= 0; --j) {
				// Find the largest h such that (i, j) to (i + h - 1, j) are feasible.
				// Find the largest w such that (i, j) to (i, j + w - 1) are feasible.
				table[i][j] = A.get(i).get(j) ? new MaxHW(
						i + 1 < A.size() ? table[i + 1][j].h + 1 : 1, j + 1 < A.get(i).size() ? table[i][j + 1].w + 1 : 1)
						: new MaxHW(0, 0);
			}
		}
		// A table stores the length of largest square for each (i, j).
		int[][] s = new int[A.size()][A.get(0).size()];
		int maxSquareArea = 0;
		for (int i = A.size() - 1; i >= 0; --i) {
			for (int j = A.get(i).size() - 1; j >= 0; --j) {
				int side = Math.min(table[i][j].h, table[i][j].w);
				if (A.get(i).get(j)) {
					// Get the length of largest square with bottom-left corner (i, j).
					if (i + 1 < A.size() && j + 1 < A.get(i + 1).size()) {
						side = Math.min(s[i + 1][j + 1] + 1, side);
					}
					s[i][j] = side;
					maxSquareArea = Math.max(maxSquareArea, side * side);
				}
			}
		}
		return maxSquareArea;
	}

	public static int maxRectangleSubmatrix_Improve(
			ArrayList<ArrayList<Boolean>> A) {
		Integer[] table = new Integer[A.get(0).size()];
		int maxRectArea = 0;
		// Find the maximum among all instances of the largest rectangle.
		for (int i = A.size() - 1; i >= 0; --i) {
			for (int j = 0; j < A.get(i).size(); ++j) {
				table[j] = A.get(i).get(j) ? i + 1 < A.size() ? table[j] + 1 : 1 : 0;
			}
			maxRectArea = Math.max(maxRectArea,calculateLargestRectangle(Arrays.asList(table)));
		}
		return maxRectArea;
	}

	public static int calculateLargestRectangle(List<Integer> A) {
		LinkedList<Integer> s = new LinkedList<>();
		int maxArea = 0;
		for (int i = 0; i <= A.size(); ++i) {
			while (!s.isEmpty() && (i == A.size() || A.get(i) < A.get(s.peek()))) {
				int height = A.get(s.peek());
				s.pop();
				maxArea = Math.max(maxArea, height * (s.isEmpty() ? i : i - s.peek() - 1));
			}
			s.push(i);
		}
		return maxArea;
	}
}
