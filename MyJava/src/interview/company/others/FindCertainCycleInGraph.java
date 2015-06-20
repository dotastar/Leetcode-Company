package interview.company.others;

import interview.AutoTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a Graph g, determine whether or not the graph contains a cycle of size
 * exactly 5. Given that there are no cycles of size less than 5. (No 4-cycles,
 * no 3-cycles).
 * 
 * Connectifier
 * 
 * @author yazhoucao
 *
 */
public class FindCertainCycleInGraph {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(FindCertainCycleInGraph.class);
	}

	static class GraphNode {
		int val;
		List<GraphNode> neighbors;
	}

	public boolean hasCycle(List<GraphNode> nodes) {
		Map<GraphNode, Integer> depthMap = new HashMap<>();
		for (GraphNode node : nodes) {
			if (!depthMap.containsKey(node) && hasCycleHelper(node, depthMap, 0))
				return true;
		}
		return false;
	}

	// dfs traverse the node
	// to see if there is cycle, if cycle size == 5
	// is cycle ⇒ depthMap.containsKey(curr)
	// otherwise put( curr, currDepth) in map
	public boolean hasCycleHelper(GraphNode curr, Map<GraphNode, Integer> depthMap,
			int currDepth) {
		if (depthMap.containsKey(curr)) {
			int lastDepth = depthMap.get(curr);
			return currDepth - lastDepth == 5 ? true : false;
		} else
			depthMap.put(curr, currDepth);

		for (GraphNode neighbor : curr.neighbors) {
			if (hasCycleHelper(neighbor, depthMap, currDepth + 1))
				return true;
		}
		return false;
	}

}
