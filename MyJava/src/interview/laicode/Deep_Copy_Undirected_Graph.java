package interview.laicode;

import interview.laicode.utils.GraphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 
 * Deep Copy Undirected Graph
 * Fair
 * Graph
 * 
 * Make a deep copy of an undirected graph, there could be cycles in the
 * original graph.
 * 
 * Assumptions
 * 
 * The given graph is not null
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Deep_Copy_Undirected_Graph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Use HashMap to keep the relation between original and copy,
	 * while creating new copy node, copy the neighbor at the same time.
	 */
	public List<GraphNode> copy(List<GraphNode> graph) {
		List<GraphNode> res = new ArrayList<>();
		Map<GraphNode, GraphNode> visited = new HashMap<>();
		Queue<GraphNode> q = new LinkedList<>();
		for (GraphNode start : graph) {
			if (visited.containsKey(start)) {
				res.add(visited.get(start));
				continue;
			} else {
				GraphNode copyStart = new GraphNode(start.key);
				visited.put(start, copyStart);
				res.add(copyStart);
			}
			// BFS, copy the structure
			q.add(start);
			while (!q.isEmpty()) {
				GraphNode node = q.poll();
				GraphNode copy = visited.get(node);
				for (GraphNode neighb : node.neighbors) {
					if (!visited.containsKey(neighb)) {
						visited.put(neighb, new GraphNode(neighb.key));
						q.add(neighb);
					}
					copy.neighbors.add(visited.get(neighb));
				}
			}
		}
		return res;
	}
}
