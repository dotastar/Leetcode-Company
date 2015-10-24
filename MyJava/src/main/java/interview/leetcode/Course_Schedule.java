package interview.leetcode;

import java.util.ArrayList;
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
 * 
 * Hints:
 * 
 * This problem is equivalent to finding if a cycle exists in a directed graph.
 * If a cycle exists, no topological ordering exists and therefore it will be
 * impossible to take all courses.
 * Topological Sort via DFS - https://class.coursera.org/algo-003/lecture/52
 * Topological sort could also be done via BFS.
 * 
 * 
 * 
 * @author yazhoucao
 *
 */
public class Course_Schedule {

	/**
	 * Time: O(V+E), Sapce: O(V)
	 */
	public boolean canFinish(int n, int[][] prerequisites) {
		// Construct the graph g
		Map<Integer, Set<Integer>> g = new HashMap<>();
		for (int i = 0; i < prerequisites.length; i++) {
			int[] pair = prerequisites[i];
			Set<Integer> edges = g.get(pair[0]);
			if (edges == null) {
				edges = new HashSet<Integer>();
				g.put(pair[0], edges);
			}
			edges.add(pair[1]);
		}
		// DFS traverse every vertices
		boolean[] ances = new boolean[n], visited = new boolean[n];
		for (int v = 0; v < n; v++) {
			if (!canFinish(v, g, ances, visited))
				return false;
		}
		return true;
	}

	/**
	 * DFS traverse the graph
	 * ances is used as a Stack to store the ancestor nodes of the graph,
	 * if the ances node is visited again, it means there is a back edge from
	 * current node to that ancestor, which means there is a cycle.
	 */
	private boolean canFinish(Integer curr, Map<Integer, Set<Integer>> g,
			boolean[] ances, boolean[] visited) {
		if (visited[curr])
			return true;
		visited[curr] = true;
		ances[curr] = true;
		Set<Integer> edges = g.get(curr);
		if (edges != null) {
			for (Integer vert : edges) {
				if (ances[vert] || !canFinish(vert, g, ances, visited))
					return false;
			}
		}
		ances[curr] = false;
		return true;
	}

	/**
	 * Object-oriented model
	 * DFS
	 */

	public boolean canFinish2(int numCourses, int[][] prerequisites) {
		Course clist[] = new Course[numCourses];

		for (int i = 0; i < numCourses; i++) {
			clist[i] = new Course();
		}

		for (int[] couple : prerequisites) {
			Course c1 = clist[couple[0]];
			Course c2 = clist[couple[1]];
			c1.addPre(c2);
		}

		for (int i = 0; i < numCourses; i++) {
			if (clist[i].isCyclic()) {
				return false;
			}
		}

		return true;
	}

	private static class Course {
		private boolean vis;
		private boolean done;
		private ArrayList<Course> pre = new ArrayList<Course>();

		void addPre(Course preCourse) {
			pre.add(preCourse);
		}

		boolean isCyclic() {
			if (done) {
				return false;
			}
			if (vis) {
				return true;
			}
			vis = true;

			for (Course preCourse : pre) {
				if (preCourse.isCyclic()) {
					return true;
				}
			}

			vis = false;
			done = true;
			return false;
		}
	}

}