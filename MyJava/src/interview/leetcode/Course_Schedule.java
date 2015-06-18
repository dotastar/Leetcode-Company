package interview.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 * 
 * Some courses may have prerequisites, for example to take course 0 you have to
 * first take course 1, which is expressed as a pair: [0,1]
 * 
 * Given the total number of courses and a list of prerequisite pairs, is it
 * possible for you to finish all courses?
 * 
 * For example:
 * 
 * 2, [[1,0]]
 * 
 * There are a total of 2 courses to take. To take course 1 you should have
 * finished course 0. So it is possible.
 * 
 * 2, [[1,0],[0,1]]
 * 
 * There are a total of 2 courses to take. To take course 1 you should have
 * finished course 0, and to take course 0 you should also have finished course
 * 1. So it is impossible.
 * 
 * Note:
 * The input prerequisites is a graph represented by a list of edges, not
 * adjacency matrices.
 * 
 * @author yazhoucao
 *
 */
public class Course_Schedule {
	public boolean canFinish(int n, int[][] prerequisites) {
		if (n > prerequisites.length)
			return false;
		Map<Integer, Set<Integer>> g = new HashMap<>();
		Set<Integer> starts = new HashSet<>();
		for (int i = 0; i < n; i++)
			starts.add(i);
		for (int i = 0; i < prerequisites.length; i++) {
			int[] pair = prerequisites[i];
			Set<Integer> edges = g.get(pair[0]);
			if (edges == null) {
				edges = new HashSet<Integer>();
				g.put(pair[0], edges);
			}
			edges.add(pair[1]);
			starts.remove(pair[1]);
		}
		if (starts.isEmpty())
			return false;
		boolean[] ances = new boolean[n], visited = new boolean[n];
		for (Integer start : starts) {
			Arrays.fill(visited, false);
			if (!canFinish(start, g, ances, visited))
				return false;
		}
		return true;
	}

	private boolean canFinish(Integer curr, Map<Integer, Set<Integer>> g,
			boolean[] ances, boolean[] visited) {
		if (visited[curr])
			return true;
		visited[curr] = true;
		ances[curr] = true;
		Set<Integer> edges = g.get(curr);
		for (Integer vert : edges) {
			if (ances[vert] || !canFinish(vert, g, ances, visited))
				return false;
		}
		ances[curr] = false;
		return true;
	}
}