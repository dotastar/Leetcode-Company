package interview.laicode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Disjoint White Objects
 * Fair
 * Data Structure
 * 
 * In a 2D black image there are some disjoint white objects with arbitrary
 * shapes, find the number of disjoint white objects in an efficient way.
 * 
 * By disjoint, it means there is no white pixels that can connect the two
 * objects, there are four directions to move to a neighbor pixel (left, right,
 * up, down).
 * 
 * Black is represented by 1’s and white is represented by 0’s.
 * 
 * Assumptions
 * 
 * The given image is represented by a integer matrix and all the values in the
 * matrix are 0 or 1
 * The given matrix is not null
 * 
 * Examples
 * 
 * the given image is
 * 
 * 0 0 0 1
 * 
 * 1 0 1 1
 * 
 * 1 1 0 0
 * 
 * 0 1 0 0
 * 
 * there are 3 disjoint white objects.
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Disjoint_White_Objects {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int whiteObjects(int[][] A) {
		int cnt = 0;
		int m = A.length, n = A.length == 0 ? 0 : A[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (A[i][j] == 0) {
					floodFill(A, i, j);
					cnt++;
				}
			}
		}
		return cnt;
	}

	private void floodFill(int[][] A, int i, int j) {
		if (A[i][j] != 0)
			return;
		int m = A.length, n = A[0].length;
		Queue<Integer> q = new LinkedList<>();
		Set<Integer> visited = new HashSet<>();
		q.add(i * n + j);
		while (!q.isEmpty()) {
			Integer point = q.poll();
			int x = point / n, y = point % n;
			if (A[x][y] == 0 && visited.add(point)) {
				A[x][y] = 2; // filling
				if (x + 1 < m)
					q.add((x + 1) * n + y);
				if (y + 1 < n)
					q.add(x * n + y + 1);
				if (x - 1 >= 0)
					q.add((x - 1) * n + y);
				if (y - 1 >= 0)
					q.add(x * n + y - 1);
			}
		}
	}
}
