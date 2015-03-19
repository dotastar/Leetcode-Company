package interview.laicode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 
 * Array Hopper IV
 * Hard
 * Graph
 * 
 * Given an array A of non-negative integers, you are initially positioned at an
 * arbitrary index of the array. A[i] means the maximum jump distance from that
 * position (you can either jump left or jump right). Determine the minimum
 * jumps you need to reach the end of the array. Return -1 if you can not reach
 * the end of the array.
 * 
 * Assumptions
 * 
 * The given array is not null and has length of at least 1.
 * 
 * Examples
 * 
 * {1, 3, 1, 2, 2}, if the initial position is 2, the minimum jumps needed is 2
 * (jump to index 1 then to the end of array)
 * 
 * {3, 3, 1, 0, 0}, if the initial position is 2, the minimum jumps needed is 2
 * (jump to index 1 then to the end of array)
 * 
 * {4, 0, 1, 0, 0}, if the initial position is 2, you are not able to reach the
 * end of array, return -1 in this case.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Array_Hopper_IV {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * BFS, Time: O(V+E)
	 */
	public int minJump(int[] A, int index) {
		Map<Integer, List<Integer>> graph = new HashMap<>();
		// construct a graph, initialization
		for (int i = 0; i < A.length; i++) {
			List<Integer> neighbors = new ArrayList<Integer>();
			int j = i - A[i] >= 0 ? i - A[i] : 0;
			for (; j <= i + A[i]; j++)
				neighbors.add(j);
			graph.put(i, neighbors);
		}
		// do a BFS, search the end node from index
		Queue<Integer> q = new LinkedList<>();
		Set<Integer> visited = new HashSet<>();
		q.add(index);
		int length = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Integer node = q.poll();
				if (node >= A.length - 1)
					return length;
				visited.add(node);
				List<Integer> neighbors = graph.get(node);
				for (Integer neighbor : neighbors) {
					if (!visited.contains(neighbor))
						q.add(neighbor);
				}
			}
			length++;
		}
		return -1;
	}
}
