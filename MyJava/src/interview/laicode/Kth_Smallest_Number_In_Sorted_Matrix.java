package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.Test;

/**
 * 
 Kth Smallest Number In Sorted Matrix
 * Fair
 * Data Structure
 * 
 * Given a matrix of size N x M. For each row the elements are sorted in
 * ascending order, and for each column the elements are also sorted in
 * ascending order. Find the Kth smallest number in it.
 * 
 * Assumptions
 * 
 * the matrix is not null, N > 0 and M > 0
 * K > 0 and K <= N * M
 * 
 * Examples
 * 
 * { {1, 3, 5, 7},
 * 
 * {2, 4, 8, 9},
 * 
 * {3, 5, 11, 15},
 * 
 * {6, 8, 13, 18} }
 * 
 * the 5th smallest number is 4
 * the 8th smallest number is 6
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Kth_Smallest_Number_In_Sorted_Matrix {
	private static Class<?> c = Kth_Smallest_Number_In_Sorted_Matrix.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Best First Search, Dijkstra's shortest path algorithm
	 * View the matrix as a graph, search the shortest path from [0, 0]
	 */
	public int kthSmallest(int[][] matrix, int k) {
		int M = matrix.length, N = matrix.length == 0 ? 0 : matrix[0].length;
		PriorityQueue<Tuple> minHeap = new PriorityQueue<>(k,
				new Comparator<Tuple>() {
					@Override
					public int compare(Tuple t1, Tuple t2) {
						return t1.val - t2.val;
					}
				});
		boolean[][] visited = new boolean[M][N];
		minHeap.add(new Tuple(0, 0, matrix[0][0]));
		int res = 0;
		while (!minHeap.isEmpty() && k > 0) {
			Tuple tup = minHeap.poll();
			int x = tup.x, y = tup.y;
			if (visited[x][y]) // De-dup, Important!
				continue;

			visited[x][y] = true;
			res = tup.val;
			k--;
			if (x + 1 < M)
				minHeap.add(new Tuple(x + 1, y, matrix[x + 1][y]));
			if (y + 1 < N)
				minHeap.add(new Tuple(x, y + 1, matrix[x][y + 1]));
		}
		return res;
	}

	public static class Tuple {
		int x;
		int y;
		int val;

		public Tuple(int x, int y, int val) {
			this.x = x;
			this.y = y;
			this.val = val;
		}
	}

	@Test
	public void test1() {
		int[][] mat = { { 1, 3, 5, 7 }, { 2, 4, 8, 9 }, { 3, 5, 11, 15 },
				{ 6, 8, 13, 18 } };
		int k = 5;
		int res = kthSmallest(mat, k);
		int ans = 4;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		int[][] mat = { { 1, 3, 5, 7 }, { 2, 4, 8, 9 }, { 3, 5, 11, 15 },
				{ 6, 8, 13, 18 } };
		int k = 6;
		int res = kthSmallest(mat, k);
		int ans = 5;
		assertTrue("Wrong: " + res, res == ans);
	}
}
