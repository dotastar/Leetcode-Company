package interview.epi.chapter18_and_19_greedy_algorithm_and_graphs;

import interview.epi.utils.Pair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Ch19Q2_Paint_A_Boolean_Matrix {

	public static void main(String[] args) {
		Ch19Q2_Paint_A_Boolean_Matrix o = new Ch19Q2_Paint_A_Boolean_Matrix();
		Random gen = new Random();

		int n = gen.nextInt(20) + 1;

		boolean[][] A = new boolean[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				A[i][j] = gen.nextBoolean();
			}
		}
		int i = gen.nextInt(n), j = gen.nextInt(n);
		System.out.println("color = " + i + " " + j + " " + A[i][j]);
		printMatrix(A);
		o.flipColor(A, i, j);
		System.out.println("============================================");
		printMatrix(A);
	}

	/**
	 * BFS
	 */
	public void flipColor(boolean[][] A, int x, int y) {
		Queue<Pair<Integer, Integer>> q = new LinkedList<>();
		int m = A.length, n = A[0].length;
		final boolean xy = A[x][y];
		q.add(new Pair<Integer, Integer>(x, y));
		while (!q.isEmpty()) {
			Pair<Integer, Integer> coord = q.poll();
			x = coord.getFirst();
			y = coord.getSecond();
			if (x >= 0 && y >= 0 && x < m && y < n && A[x][y] == xy) {
				A[x][y] = !xy;
				q.add(new Pair<Integer, Integer>(x, y + 1));
				q.add(new Pair<Integer, Integer>(x, y - 1));
				q.add(new Pair<Integer, Integer>(x + 1, y));
				q.add(new Pair<Integer, Integer>(x - 1, y));
			}
		}
	}

	static void printMatrix(boolean[][] A) {
		for (boolean[] row : A) {
			for (boolean cell : row) {
				int cellVal = cell ? 1 : 0;
				System.out.print(cellVal + " ");
			}
			System.out.println();
		}
	}
}
