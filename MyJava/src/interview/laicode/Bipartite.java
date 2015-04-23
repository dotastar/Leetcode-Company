package interview.laicode;

import interview.laicode.utils.GraphNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Bipartite
 * Hard
 * Graph
 * 
 * Determine if an undirected graph is bipartite. A bipartite graph is one in
 * which the nodes can be divided into two groups such that no nodes have direct
 * edges to other nodes in the same group.
 * 
 * Examples
 * 
 * 1 -- 2
 * 
 * /
 * 
 * 3 -- 4
 * 
 * is bipartite (1, 3 in group 1 and 2, 4 in group 2).
 * 
 * 1 -- 2
 * 
 * / |
 * 
 * 3 -- 4
 * 
 * is not bipartite.
 * 
 * Assumptions
 * 
 * The graph is represented by a list of nodes, and the list of nodes is not
 * null.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Bipartite {

	public static void main(String[] args) {
		
	}

	/**
	 * Time: O(|V|+|E|)
	 * use 0 and 1 to denote two different groups.
	 * the map maintains for each node which group it belongs to.
	 */
	public boolean isBipartite(List<GraphNode> graph) {
		if (graph == null)
			return true;
		Map<GraphNode, Integer> dict = new HashMap<>();
		for (GraphNode n : graph) {
			if (!colorNode(n, dict))
				return false;
		}
		return true;
	}

	/**
	 * BFS, color nodes
	 */
	private boolean colorNode(GraphNode start, Map<GraphNode, Integer> dict) {
		// if this node has been traversed, no need to do BFS again.
		if (dict.containsKey(start))
			return true;
		Queue<GraphNode> q = new LinkedList<>();
		q.add(start);
		dict.put(start, 0); // we can assign it to any color
		while (!q.isEmpty()) {
			GraphNode node = q.poll();
			int color = dict.get(node);
			int oppositeColor = (color + 1) % 2;
			// if the neighbor has not been traversed, just put it in the queue
			// and choose the opposite color.
			for (GraphNode neighbor : node.neighbors) {
				if (!dict.containsKey(neighbor)) {
					dict.put(neighbor, oppositeColor);
					q.offer(neighbor);
				} else if (dict.get(neighbor) == color)
					return false; // has visited, is in same color, conflicted
			}
		}
		return true;
	}
}
