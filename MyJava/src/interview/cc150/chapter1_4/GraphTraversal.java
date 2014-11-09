package interview.cc150.chapter1_4;

import general.datastructure.AdjacencyMatrix;
import general.datastructure.Graph;
import general.datastructure.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


/**
 * 4.2 Given a directed graph, design an algorithm to 
 * find out whether there is a route between two nodes.
 * @author yazhoucao
 *
 */
public class GraphTraversal{
	
	private boolean[] visited;
	private Graph g;
	
	public GraphTraversal(Graph graph){
		g = graph;
		visited = new boolean[g.sizeV()];
	}
	
	public void dfs_Iterative(Graph g, int s){
		resetVisited();

		Stack<Integer> stack = new Stack<Integer>();
		stack.push(s);
	
		while(!stack.isEmpty()){
			int v = stack.pop();
			if(!visited[v]){
				visit(v, visited);
				for(int neighbor : g.adj(v)){
					if(!visited[neighbor]){
						stack.push(neighbor);
					}
				}
			}
		}
		
		resetVisited();
	}
	
	public void dfs(Graph g, int s){
		if(s>=visited.length) 
			throw new IllegalArgumentException("Index out of bound, "+s+" "+visited.length);
		
		if(visited[s])
			return;
		
		visit(s, visited);
		
		for(int neighbor : g.adj(s))
			if(!visited[neighbor])
				dfs(g, neighbor);
	}
	
	public void bfs(Graph g, int s){
		resetVisited();
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(s);
		
		while(!queue.isEmpty()){
			int v = queue.poll(); 
			if(!visited[v]){
				visit(v, visited);
				for(int w : g.adj(v))
					if(!visited[w])
						queue.add(w);
			}
		}
				
		resetVisited();
	}
	
	private void visit(int v, boolean[] visited){
		visited[v] = true;
		System.out.print(" "+v+" ");
	}
	
	public void resetVisited(){
		visited = new boolean[g.sizeV()];
	}
	
	public static void main(String[] args){
		List<Node<String>> nodes = new ArrayList<Node<String>>();
		nodes.add(new Node<String>("A"));
		nodes.add(new Node<String>("B"));
		nodes.add(new Node<String>("C"));
		nodes.add(new Node<String>("D"));
		nodes.add(new Node<String>("E"));
		nodes.add(new Node<String>("F"));
		
		Graph g = new AdjacencyMatrix<String>(nodes, false);
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(0, 3);
		g.addEdge(1, 4);
		g.addEdge(2, 4);
		g.addEdge(2, 5);
		g.addEdge(3, 5);
		
		System.out.println(g.toString());
		GraphTraversal gt = new GraphTraversal(g);
		System.out.println("DFS Recursive:");
		gt.dfs(g, 0);
		System.out.println("\nDFS Iterative:");
		gt.dfs_Iterative(g, 0);
		
		System.out.println("\nBFS:");
		gt.bfs(g, 0);
		
	}
}