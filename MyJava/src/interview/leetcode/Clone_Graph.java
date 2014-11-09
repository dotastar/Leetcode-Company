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
	 * Time O(n)
	 * 
	 * Space 2n = O(n)
	 * 
	 * @param node
	 * @return
	 */
	public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
		if (node == null)
			return null;
		Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
		Queue<UndirectedGraphNode> q = new LinkedList<UndirectedGraphNode>();
		q.add(node); // BFS
		while (!q.isEmpty()) { // set corresponding relation from old to new
								// copy
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
	 * Basicly is combine the above two while loop into one, do them together.
	 * 
	 * BFS traverse all nodes, add correspondence for every neighbor of the node
	 * it meets. If the neighbor is in the map, it means its correspondence
	 * has already been created, just add the correspondence to curr as
	 * neighbor, otherwise create the correspondence node and add it to curr as
	 * a neighbor correspondence node.
	 * 
	 * Time O(n) Space n = O(n)
	 * 
	 * @param node
	 * @return
	 */
	public UndirectedGraphNode cloneGraph_Improve(UndirectedGraphNode node) {
        if(node==null) return null;
        Queue<UndirectedGraphNode> q = new LinkedList<UndirectedGraphNode>();
        Map<UndirectedGraphNode, UndirectedGraphNode> visited = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
        visited.put(node, new UndirectedGraphNode(node.label)); // must create first copy
        q.add(node); // BFS, it makes sure all the nodes that took from q have a correspondence already created
        while(!q.isEmpty()){
            UndirectedGraphNode curr = q.poll();
            UndirectedGraphNode cp = visited.get(curr); //it won't be null
            for(UndirectedGraphNode neighbor : curr.neighbors){
                if(!visited.containsKey(neighbor)){
                    q.add(neighbor);    //this make sure that BFS ends
                    UndirectedGraphNode cpNeibor = new UndirectedGraphNode(neighbor.label);
                    visited.put(neighbor, cpNeibor);
                }
                cp.neighbors.add(visited.get(neighbor));
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
