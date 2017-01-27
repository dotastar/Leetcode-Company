package general.datastructure;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
	
	public T data;
	public Node<T> next;
	
	public Node(T dataIn){
		data = dataIn;
	}
	
	@Override
	public int compareTo(Node<T> o) {
		T cmp = o.data;
		return data.compareTo(cmp);
	}
	
	@Override
	public String toString(){ return data.toString(); }
}
