package interview.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Clone an undirected graph. Each node in the graph contains a label and a list
 * of its neighbors.
 * 
 * @author yazhoucao
 * 
 */
public class Clone_Graph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Naive solution
	 * Time: O(n) = 3n, 3 BFS traversals
	 * 1.create copy and insert it in the tail of current node
	 * 2.copy the neighbor pointers
	 * 3.break the new copied graph from the old graph.
	 */
	public UndirectedGraphNode cloneGraph2(UndirectedGraphNode start) {
		if (start == null)
			return null;
		Set<UndirectedGraphNode> visited = new HashSet<>();
		Queue<UndirectedGraphNode> q = new LinkedList<>();
		// copy node value
		q.add(start);
		while (!q.isEmpty()) {
			UndirectedGraphNode node = q.poll();
			if (!visited.add(node))
				continue;
			for (UndirectedGraphNode neighb : node.neighbors)
				q.add(neighb);
			UndirectedGraphNode copy = new UndirectedGraphNode(node.label);
			node.neighbors.add(copy);
		}

		// copy neighbors pointer
		visited.clear();
		q.add(start);
		while (!q.isEmpty()) {
			UndirectedGraphNode node = q.poll();
			if (!visited.add(node))
				continue;
			List<UndirectedGraphNode> neighbors = node.neighbors;
			UndirectedGraphNode copy = neighbors.get(neighbors.size() - 1);
			for (int i = 0; i < neighbors.size() - 1; i++) {
				UndirectedGraphNode neigh = neighbors.get(i);
				copy.neighbors
						.add(neigh.neighbors.get(neigh.neighbors.size() - 1));
				q.add(neigh);
			}
		}

		// break copied the graph
		UndirectedGraphNode res = start.neighbors
				.get(start.neighbors.size() - 1);
		visited.clear();
		q.add(start);
		while (!q.isEmpty()) {
			UndirectedGraphNode node = q.poll();
			if (!visited.add(node))
				continue;
			List<UndirectedGraphNode> neighbors = node.neighbors;
			neighbors.remove(neighbors.size() - 1);
			for (UndirectedGraphNode neighb : neighbors)
				q.add(neighb);
		}
		return res;
	}

	/**
	 * Time O(n)
	 * 
	 * Space 2n = O(n)
	 * 
	 */
	public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
		if (node == null)
			return null;
		Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
		Queue<UndirectedGraphNode> q = new LinkedList<UndirectedGraphNode>();
		// BFS, set corresponding relation from old to new copy
		q.add(node);
		while (!q.isEmpty()) {
			UndirectedGraphNode nd = q.poll();
			if (map.containsKey(nd))
				continue;
			UndirectedGraphNode cp = new UndirectedGraphNode(nd.label);
			map.put(nd, cp);
			q.addAll(nd.neighbors);
		}

		Set<UndirectedGraphNode> visited = new HashSet<UndirectedGraphNode>();
		q.add(node); // BFS
		while (!q.isEmpty()) { // create new neighbors for new nodes
			UndirectedGraphNode nd = q.poll();
			if (visited.contains(nd))
				continue;

			UndirectedGraphNode cp = map.get(nd);
			cp.neighbors = new ArrayList<UndirectedGraphNode>();
			for (UndirectedGraphNode neighbor : nd.neighbors)
				cp.neighbors.add(map.get(neighbor));
			q.addAll(nd.neighbors);
			visited.add(nd);
		}

		return map.get(node);
	}

	/**
	 * Space Improved.
	 * 
	 * Basically is combine the above two while loop into one, do them together.
	 * 
	 * BFS traverse all nodes, add correspondence for every neighbor of the node
	 * it meets. If the neighbor is in the map, it means its correspondence
	 * has already been created, just add the correspondence to curr as
	 * neighbor, otherwise create the correspondence node and add it to curr as
	 * a neighbor correspondence node.
	 * 
	 * Time O(n) Space n = O(n)
	 * 
	 */
	public UndirectedGraphNode cloneGraph_Improve(UndirectedGraphNode node) {
		if (node == null)
			return null;
		Queue<UndirectedGraphNode> q = new LinkedList<UndirectedGraphNode>();
		Map<UndirectedGraphNode, UndirectedGraphNode> visited = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
		// must create first copy
		visited.put(node, new UndirectedGraphNode(node.label));
		// BFS, it makes sure all the nodes that took from q have a
		// correspondence already created
		q.add(node);
		while (!q.isEmpty()) {
			UndirectedGraphNode curr = q.poll();
			UndirectedGraphNode copy = visited.get(curr); // it won't be null
			for (UndirectedGraphNode neighbor : curr.neighbors) {
				if (!visited.containsKey(neighbor)) {
					q.add(neighbor); // this make sure that BFS ends
					visited.put(neighbor, new UndirectedGraphNode(neighbor.label));
				}
				copy.neighbors.add(visited.get(neighbor));
			}

		}
		return visited.get(node);
	}

	public static class UndirectedGraphNode {
		int label;
		List<UndirectedGraphNode> neighbors;

		UndirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<UndirectedGraphNode>();
		}
	};
}
