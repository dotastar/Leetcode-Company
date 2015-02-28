package interview.laicode;

import java.util.Comparator;
import java.util.PriorityQueue;

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Best First Search, Dijkstra's shortest path algorithm
	 * View the matrix as a graph, search the shortest path from [0, 0]
	 */
	public int kthSmallest(int[][] matrix, int k) {
		if (k == 0)
			return 0;
		PriorityQueue<Coord> minHeap = new PriorityQueue<>(k,
				new Comparator<Coord>() {
					public int compare(Coord cod1, Coord cod2) {
						return cod1.val - cod2.val;
					}
				});
		final int m = matrix.length, n = matrix[0].length;
		boolean[][] visited = new boolean[m][n];
		minHeap.add(new Coord(0, 0, matrix[0][0]));
		int res = Integer.MIN_VALUE;
		while (k > 0) {
			Coord cod = minHeap.poll();
			if (visited[cod.x][cod.y])
				continue;
			visited[cod.x][cod.y] = true;
			k--;
			res = cod.val;
			if (cod.x + 1 < m)
				minHeap.add(new Coord(cod.x + 1, cod.y, matrix[cod.x + 1][cod.y]));
			if (cod.y + 1 < n)
				minHeap.add(new Coord(cod.x, cod.y + 1,	matrix[cod.x][cod.y + 1]));
		}

		return res;
	}

	public int kthSmallest2(int[][] matrix, int k) {
		if (k == 0)
			return 0;
		PriorityQueue<Coord> minHeap = new PriorityQueue<>(k,
				new Comparator<Coord>() {
					public int compare(Coord cod1, Coord cod2) {
						return cod1.val - cod2.val;
					}
				});
		final int m = matrix.length, n = matrix[0].length;
		boolean[][] visited = new boolean[m][n];
		minHeap.add(new Coord(0, 0, matrix[0][0]));
		int res = Integer.MIN_VALUE;
		while (k > 0) {
			Coord cod = minHeap.poll();
			k--;
			res = cod.val;
			if (cod.x + 1 < m && !visited[cod.x + 1][cod.y]) {
				minHeap.add(new Coord(cod.x + 1, cod.y, matrix[cod.x + 1][cod.y]));
				visited[cod.x + 1][cod.y] = true;
			}
			if (cod.y + 1 < n && !visited[cod.x][cod.y + 1]) {
				minHeap.add(new Coord(cod.x, cod.y + 1, matrix[cod.x][cod.y + 1]));
				visited[cod.x][cod.y + 1] = true;
			}
		}

		return res;
	}

	private static class Coord {
		int x;
		int y;
		int val;

		public Coord(int x, int y, int val) {
			this.x = x;
			this.y = y;
			this.val = val;
		}
	}
}
