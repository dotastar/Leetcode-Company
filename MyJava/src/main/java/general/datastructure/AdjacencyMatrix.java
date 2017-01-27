package general.datastructure;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrix<T> implements Graph{
	
	private List<T> vertices;
	int vertexNum;
	private int[][] edges;
	private int edgeNum;
	private final boolean isDirected;
	
	public AdjacencyMatrix(List<T> verticesIn, boolean isDirectedIn){
		vertices = verticesIn;
		int n = vertices.size();
		edges = new int[n][n];
		vertexNum = n;
		isDirected = isDirectedIn;
	}
	
	public void addEdge(int v, int w, int weight){
		if(v>=vertexNum || w >=vertexNum)
			return;
		
		edges[v][w] = weight;
		edgeNum++;
		
		if(!isDirected){
			edges[w][v] = weight;
		}
	}
	
	public void addVertex(T vertex){
		vertices.add(vertex);
		vertexNum++;
	}

	@Override
	public int sizeV() {
		return vertexNum;
	}

	@Override
	public int sizeE() {
		return edgeNum;
	}

	@Override
	public void addEdge(int v, int w) {
		addEdge(v, w, 1);
	}

	@Override
	public Iterable<Integer> adj(int v) {
		List<Integer> neighbors = new ArrayList<Integer>();
		int i = 0;
		for(int w : edges[v]){
			if(w>0)
				neighbors.add(i);
			i++;
		}
			
		return neighbors;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(T node : vertices)
			sb.append("\t"+node.toString());
		sb.append("\n");
		
		for(int i=0; i<vertexNum; i++){
			int[] neighbors = edges[i];
			sb.append(vertices.get(i).toString());
			for(int v : neighbors){
				sb.append("\t"+v);
			}
			sb.append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	
	public static void main(String[] args){
		List<String> nodes = new ArrayList<String>();
		nodes.add(new String("A"));
		nodes.add(new String("B"));
		nodes.add(new String("C"));
		nodes.add(new String("D"));
		nodes.add(new String("E"));
		nodes.add(new String("F"));
		
		AdjacencyMatrix<String> matrix = new AdjacencyMatrix<String>(nodes, false);
		matrix.addEdge(0, 1);
		matrix.addEdge(0, 2);
		matrix.addEdge(0, 3);
		matrix.addEdge(1, 4);
		matrix.addEdge(2, 4);
		matrix.addEdge(2, 5);
		matrix.addEdge(3, 5);

		System.out.println(matrix.toString());
	}
}
