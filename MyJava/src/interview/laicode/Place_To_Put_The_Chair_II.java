package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Place To Put The Chair II
 * Fair
 * Data Structure
 * 
 * Given a gym with k pieces of equipment without any obstacles. Let’s say we
 * bought a chair and wanted to put this chair into the gym such that the sum of
 * the shortest path cost from the chair to the k pieces of equipment is
 * minimal. The gym is represented by a char matrix, ‘E’ denotes a cell with
 * equipment, ' ' denotes a cell without equipment. The cost of moving from one
 * cell to its neighbor(left, right, up, down) is 1. You can put chair on any
 * cell in the gym.
 * 
 * Assumptions
 * 
 * There is at least one equipment in the gym
 * The given gym is represented by a char matrix of size M * N, where M >= 1 and
 * N >= 1, it is guaranteed to be not null
 * 
 * ​Examples
 * 
 * { { 'E', ' ', ' ' },
 * 
 * { ' ', 'E', ' ' },
 * 
 * { ' ', ' ', 'E' } }
 * 
 * we should put the chair at (1, 1), so that the sum of cost from the chair to
 * the two equipments is 2 + 0 + 2 = 4, which is minimal.
 * 
 * @author yazhoucao
 * 
 */
public class Place_To_Put_The_Chair_II {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Place_To_Put_The_Chair_II.class);
	}

	/**
	 * Improved, Time: O(mn)
	 * 
	 * 1.Traverse the matrix, put k equipments in a list, and calculate the
	 * total sum of x and y.
	 * 
	 * 2.Based on the total sum of x, y, we can get the center point within
	 * those k points by calculate the arithmetic average value of total x, y.
	 * 
	 * 3.Because its precision may loss due to the integer divide by integer,
	 * we calculate the exact center point by try its up/down/left/right
	 * neighbor point, and compare their cost.
	 */
	public List<Integer> putChair_Improved(char[][] gym) {
		int x = 0, y = 0, cnt = 0;
		List<Pair> equips = new ArrayList<>();
		int M = gym.length, N = gym[0].length;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (gym[i][j] == 'E') {
					x += i;
					y += j;
					cnt++;
					equips.add(new Pair(i, j));
				}
			}
		}
		// calculate arithmetic average value (center point)
		x = Math.round(x / (float) cnt);
		y = Math.round(y / (float) cnt);
		// compare with its neighbors
		int bestX = x, bestY = y, minCost = Integer.MAX_VALUE;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 || j == 0) {
					int cost = calculateCost(equips, x + i, y + j);
					if (cost < minCost) {
						bestX = x + i;
						bestY = y + j;
						minCost = cost;
					}
				}
			}
		}
		List<Integer> res = new ArrayList<>();
		res.add(bestX);
		res.add(bestY);
		return res;
	}

	/**
	 * Naive solution, Time: O(mnk)
	 * 1.Traverse the matrix, put k equipments in a list. Time: O(mn)
	 * 2.Brute force, try put the chair at every position in the matrix, and
	 * calculate its cost, update the minCost. Time: O(mnk).
	 */
	public List<Integer> putChair(char[][] gym) {
		List<Pair> equips = new ArrayList<>();
		int M = gym.length, N = gym[0].length;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (gym[i][j] == 'E')
					equips.add(new Pair(i, j));
			}
		}
		int minCost = Integer.MAX_VALUE;
		List<Integer> res = new ArrayList<>();
		res.add(0);
		res.add(0);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int cost = calculateCost(equips, i, j);
				if (cost < minCost) {
					minCost = cost;
					res.set(0, i);
					res.set(1, j);
				}
			}
		}

		return res;
	}

	private int calculateCost(List<Pair> equips, int x, int y) {
		int cost = 0;
		for (Pair p : equips) {
			cost += (int) (Math.abs(x - p.x) + Math.abs(y - p.y));
		}
		return cost;
	}

	@Test
	public void test1() {
		char[][] gym = {

		{ 'E', 'E', ' ', ' ', ' ' },

		{ ' ', 'E', ' ', ' ', 'E' },

		{ ' ', ' ', 'E', ' ', ' ' },

		{ ' ', ' ', ' ', ' ', ' ' } };

		List<Integer> ans = new ArrayList<>();
		ans.add(1);
		ans.add(1);
		List<Integer> res = putChair_Improved(gym);
		assertTrue("Wrong: " + res.toString(), res.equals(ans));
	}

	public static class Pair {
		int x;
		int y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int hashCode() {
			return 31 * x + y;
		}

		public boolean equals(Object obj) {
			if (obj instanceof Pair) {
				Pair other = (Pair) obj;
				return this.x == other.x && this.y == other.y;
			}
			return false;
		}
	}
}
