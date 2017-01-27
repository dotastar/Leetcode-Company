package general.datastructure;

public interface Graph {
	
	int sizeV();	//size of vertices
	int sizeE();	//size of edges
	
	/**
	 * add edge v-w to this graph
	 */
	void addEdge(int v, int w);

	/**
	 * vertices adjacent to v
	 * @return
	 */
	Iterable<Integer> adj(int v);
}
