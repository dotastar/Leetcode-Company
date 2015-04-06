package interview.laicode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

/**
 * Place To Put The Chair I
 * Hard
 * Graph
 * 
 * Given a gym with k pieces of equipment and some obstacles. We bought a chair
 * and wanted to put this chair into the gym such that the sum of the shortest
 * path cost from the chair to the k pieces of equipment is minimal. The gym is
 * represented by a char matrix, ‘E’ denotes a cell with equipment, ‘O’ denotes
 * a cell with an obstacle, ' ' denotes a cell without any equipment or
 * obstacle. You can only move to neighboring cells (left, right, up, down) if
 * the neighboring cell is not an obstacle. The cost of moving from one cell to
 * its neighbor is 1. You can not put the chair on a cell with equipment or
 * obstacle.
 * 
 * Assumptions
 * 
 * There is at least one equipment in the gym
 * The given gym is represented by a char matrix of size M * N, where M >= 1 and
 * N >= 1, it is guaranteed to be not null
 * If there does not exist such place to put the chair, just return null (Java)
 * empty vector (C++)
 * 
 * Examples
 * 
 * { { 'E', 'O', ' ' },
 * 
 * { ' ', 'E', ' ' },
 * 
 * { ' ', ' ', ' ' } }
 * 
 * we should put the chair at (1, 0), so that the sum of cost from the chair to
 * the two equipments is 1 + 1 = 2, which is minimal.
 * 
 * @author yazhoucao
 * 
 */
public class Place_To_Put_The_Chair_I {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Place_To_Put_The_Chair_I.class);
	}

	/**
	 * Brute Force, calculate cost at every position, Time: O(mnk(mn))
	 */
	public List<Integer> putChair(char[][] gym) {
		List<int[]> equips = new ArrayList<>();
		int M = gym.length, N = gym[0].length;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (gym[i][j] == 'E')
					equips.add(new int[] { i, j });
			}
		}

		List<Integer> res = null;
		int minCost = Integer.MAX_VALUE;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (gym[i][j] != 'E' && gym[i][j] != 'O') {
					boolean isUnreachable = false;
					int cost = 0;
					for (int[] coord : equips) {
						int dist = distance(gym, coord[0], coord[1], i, j);
						if (dist < 0) {
							isUnreachable = true;
							break;
						}
						cost += dist;
					}
					if (!isUnreachable && cost < minCost) {
						minCost = cost;
						if (res == null || res.size() == 0) {
							res = new ArrayList<>();
							res.add(i);
							res.add(j);
						} else {
							res.set(0, i);
							res.set(1, j);
						}
					}
				}
			}
		}
		return res;
	}

	/**
	 * BFS
	 * Time: O(m * n)
	 */
	private int distance(char[][] gym, int x, int y, int i, int j) {
		if (x == i && y == j)
			return 0;
		int M = gym.length, N = gym[0].length;
		Queue<Integer> q = new LinkedList<>();
		Set<Integer> visited = new HashSet<Integer>();
		q.add(x * N + y);
		int dist = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int k = 0; k < size; k++) {
				Integer coord = q.poll();
				if (visited.add(coord)) {
					x = coord / N;
					y = coord % N;
					if (x == i && y == j)
						return dist;
					if (x + 1 < M && gym[x + 1][y] != 'O')
						q.add((x + 1) * N + y);
					if (x - 1 >= 0 && gym[x - 1][y] != 'O')
						q.add((x - 1) * N + y);
					if (y + 1 < N && gym[x][y + 1] != 'O')
						q.add(x * N + y + 1);
					if (y - 1 >= 0 && gym[x][y - 1] != 'O')
						q.add(x * N + y - 1);
				}

			}
			dist++;
		}
		return -1; // unreachable
	}

	// @Test
	public void test1() {
		char[][] gym = {

		{ 'E', 'O', ' ' },

		{ ' ', 'E', ' ' },

		{ ' ', ' ', ' ' } };

		List<Integer> ans = new ArrayList<>();
		ans.add(1);
		ans.add(0);
		List<Integer> res = putChair(gym);
		assertTrue("Wrong: " + res.toString(), res.equals(ans));
	}

	@Test
	public void test2() {
		char[][] gym = {

		{ 'E', ' ', 'E', 'O', ' ' },

		{ 'E', 'O', ' ', ' ', 'E' },

		{ 'O', 'O', 'E', ' ', ' ' },

		{ ' ', 'O', ' ', 'E', 'E' },

		{ 'E', ' ', ' ', ' ', ' ' } };

//		int dist = distance(gym, 0, 0, 2, 4);
//		System.out.println(dist);
		 List<Integer> ans = new ArrayList<>();
		 ans.add(1);
		 ans.add(2);
		 List<Integer> res = putChair(gym);
		 assertTrue("Wrong: " + res.toString(), res.equals(ans));
	}
}
