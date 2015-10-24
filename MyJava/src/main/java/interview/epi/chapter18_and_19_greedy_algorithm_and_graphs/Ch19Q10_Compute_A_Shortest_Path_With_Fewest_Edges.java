package interview.epi.chapter18_and_19_greedy_algorithm_and_graphs;

import interview.epi.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Design an algorithm which takes as input a graph G = (V, E), directed or
 * undirected, a nonnegative cost function on E, and vertices s and t; your
 * algorithm should output a path with the fewest edges amongst all shortest
 * paths from s to t.
 * 
 * @author yazhoucao
 * 
 */
public class Ch19Q10_Compute_A_Shortest_Path_With_Fewest_Edges {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void dijkstraShortestPath(GraphVertex s, GraphVertex t) {
		// Initialization the distance of starting point.
		s.distance = new Pair<>(0, 0);
		PriorityQueue<GraphVertex> nodeSet = new PriorityQueue<>();
		nodeSet.add(s);
		while (!nodeSet.isEmpty()) {
			// Extracts the minimum distance vertex from heap.
			GraphVertex u = nodeSet.poll();
			if (u.equals(t))
				break;
			// Relax neighboring vertices of u.
			for (Pair<GraphVertex, Integer> v : u.edges) {
				int vDistance = u.distance.getFirst() + v.getSecond();
				int vNumEdges = u.distance.getSecond() + 1;
				if (v.getFirst().distance.getFirst() > vDistance
						|| (v.getFirst().distance.getFirst() == vDistance && v.getFirst().distance.getSecond() > vNumEdges)) {
					nodeSet.remove(v.getFirst());
					v.getFirst().pred = u;
					v.getFirst().distance = new Pair<>(vDistance, vNumEdges);
					nodeSet.add(v.getFirst());
				}
			}
		}
		// Outputs the shortest path with fewest edges.
		outputShortestPath(t);
	}

	private static void outputShortestPath(GraphVertex v) {
		if (v != null) {
			outputShortestPath(v.pred);
			System.out.print(v.id + " ");
		}
	}

	public static class GraphVertex implements Comparable<GraphVertex> {
		public Pair<Integer, Integer> distance; // Stores (dis, #edges) pair.
		public List<Pair<GraphVertex, Integer>> edges;
		public int id; // The id of this vertex.
		public GraphVertex pred; // The predecessor in the shortest path.

		public GraphVertex() {
			distance = new Pair<>(Integer.MAX_VALUE, 0);
			edges = new ArrayList<>();
			id = 0;
			pred = null;
		}

		@Override
		public int compareTo(GraphVertex o) {
			int res = distance.getFirst().compareTo(o.distance.getFirst());
			if (res == 0) {
				res = distance.getSecond().compareTo(o.distance.getSecond());
			}
			return res;
		}
	}

}
