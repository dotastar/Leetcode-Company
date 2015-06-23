package interview.leetcode;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 * 
 * Some courses may have prerequisites, for example to take course 0 you have to
 * first take course 1, which is expressed as a pair: [0,1]
 * 
 * Given the total number of courses and a list of prerequisite pairs, return
 * the ordering of courses you should take to finish all courses.
 * 
 * There may be multiple correct orders, you just need to return one of them. If
 * it is impossible to finish all courses, return an empty array.
 * 
 * For example:
 * 
 * 2, [[1,0]]
 * 
 * There are a total of 2 courses to take. To take course 1 you should have
 * finished course 0. So the correct course order is [0,1]
 * 
 * 4, [[1,0],[2,0],[3,1],[3,2]]
 * 
 * There are a total of 4 courses to take. To take course 3 you should have
 * finished both courses 1 and 2. Both courses 1 and 2 should be taken after you
 * finished course 0. So one correct course order is [0,1,2,3]. Another correct
 * ordering is[0,2,1,3].
 * 
 * Note:
 * The input prerequisites is a graph represented by a list of edges, not
 * adjacency matrices. Read more about how a graph is represented.
 * 
 * @author yazhoucao
 *
 */
public class Course_Schedule_II {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Course_Schedule_II.class);
	}

	public int[] findOrder(int n, int[][] prerequisites) {
		// construct the graph
		Map<Integer, List<Integer>> graph = new HashMap<>();
		for (int i = 0; i < prerequisites.length; i++) {
			int[] edge = prerequisites[i];
			List<Integer> neighbors = graph.get(edge[0]);
			if (neighbors == null) {
				neighbors = new ArrayList<Integer>();
				graph.put(edge[0], neighbors);
			}
			neighbors.add(edge[1]);
		}
		// topological sort
		List<Integer> res = new ArrayList<>();
		boolean[] visited = new boolean[n], ances = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (!visited[i] && !topoSort(i, visited, ances, res, graph)) {
				return new int[0];
			}
		}
		// List to Array
		int[] resArr = new int[n];
		for (int i = 0; i < n; i++)
			resArr[i] = res.get(i);
		return resArr;
	}

	/**
	 * DFS topological sort, Time: O(|V|+|E|)
	 * 
	 * @param node
	 *            : current visiting node
	 * @param visited
	 *            : avoid repeated traversal
	 * @param ances
	 *            : to detect back edge, for cycle detection
	 * @param res
	 *            : topological sorted data
	 * @param g
	 * @return
	 */
	public boolean topoSort(int node, boolean[] visited, boolean[] ances,
			List<Integer> res, Map<Integer, List<Integer>> g) {
		if (visited[node])
			return true;
		visited[node] = true;
		ances[node] = true;
		List<Integer> neighbors = g.get(node);
		if (neighbors != null) {
			for (Integer neighbor : neighbors) {
				if (ances[neighbor])
					return false;
				if (!topoSort(neighbor, visited, ances, res, g))
					return false;
			}
		}
		ances[node] = false;
		res.add(node);
		return true;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
