package general.algorithms;

import interview.AutoTestUtils;
import interview.utils.GraphNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;

/**
 * Topological Sorting is mainly used for scheduling jobs from the given
 * dependencies among jobs. In computer science, applications of this type arise
 * in instruction scheduling, ordering of formula cell evaluation when
 * recomputing formula values in spreadsheets, logic synthesis, determining the
 * order of compilation tasks to perform in makefiles, data serialization, and
 * resolving symbol dependencies in linkers
 * 
 * @author yazhoucao
 *
 */
public class TopologicalSort {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(TopologicalSort.class);
	}

	/**
	 * Time: O(|V| + |E|)
	 * Space: O(|E|)
	 */
	public List<Integer> topologicalSort(List<GraphNode> graph) {
		Set<GraphNode> startNodes = getStartNodes(graph);
		Stack<Integer> stack = new Stack<Integer>();
		Set<GraphNode> visited = new HashSet<>();
		for (GraphNode start : startNodes) {
			topoSort(start, stack, visited);
		}
		return new ArrayList<Integer>(stack);
	}

	/**
	 * DFS -traverse all the neighbors and then push its value to stack
	 * Finally the stack will have the topologically ordered values
	 * 
	 * Assume there is no cycle
	 */
	private void topoSort(GraphNode node, Stack<Integer> res, Set<GraphNode> visited) {
		if (node == null || !visited.add(node))
			return;
		for (GraphNode neighbor : node.neighbors) {
			if (!visited.contains(neighbor))
				topoSort(neighbor, res, visited);
		}
		res.push(node.key);
	}

	private Set<GraphNode> getStartNodes(List<GraphNode> nodes) {
		Set<GraphNode> starts = new HashSet<>(nodes);
		for (GraphNode curr : nodes) {
			if (!starts.contains(curr))
				continue;
			for (GraphNode neighbor : curr.neighbors)
				starts.remove(neighbor);
		}
		return starts;
	}

	@Test
	public void test1() {
		GraphNode node0 = new GraphNode(0);
		GraphNode node1 = new GraphNode(1);
		GraphNode node2 = new GraphNode(2);
		GraphNode node3 = new GraphNode(3);
		GraphNode node4 = new GraphNode(4);
		GraphNode node5 = new GraphNode(5);
		node5.neighbors.add(node2);
		node5.neighbors.add(node0);
		node4.neighbors.add(node1);
		node4.neighbors.add(node0);
		node2.neighbors.add(node3);
		node3.neighbors.add(node1);
		List<GraphNode> graph = new ArrayList<>();
		graph.add(node0);
		graph.add(node1);
		graph.add(node2);
		graph.add(node3);
		graph.add(node4);
		graph.add(node5);

		List<Integer> res = topologicalSort(graph);
		System.out.println(res);
	}

	@Test
	public void test2() {
		GraphNode node0 = new GraphNode(0);
		GraphNode node1 = new GraphNode(1);
		GraphNode node2 = new GraphNode(2);
		GraphNode node3 = new GraphNode(3);
		GraphNode node4 = new GraphNode(4);
		GraphNode node5 = new GraphNode(5);
		GraphNode node6 = new GraphNode(6);
		node5.neighbors.add(node2);
		node5.neighbors.add(node0);
		node5.neighbors.add(node6);
		node4.neighbors.add(node1);
		node4.neighbors.add(node0);
		node6.neighbors.add(node2);
		node2.neighbors.add(node3);
		node3.neighbors.add(node1);

		List<GraphNode> graph = new ArrayList<>();
		graph.add(node0);
		graph.add(node1);
		graph.add(node2);
		graph.add(node3);
		graph.add(node4);
		graph.add(node5);

		List<Integer> res = topologicalSort(graph);
		System.out.println(res);
	}

}
