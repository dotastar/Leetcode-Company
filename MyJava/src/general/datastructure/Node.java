package general.datastructure;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
	
	private T data;
	private Node<T> next;
	
	public Node(T dataIn){
		data = dataIn;
	}
	
	public T getData(){ return data; }
	public void setData(T dataIn){ data = dataIn; }
	
	public Node<T> next(){ return next; }
	public void setNext(Node<T> nextIn){ next = nextIn; }

	@Override
	public int compareTo(Node<T> o) {
		T cmp = o.getData();
		return data.compareTo(cmp);
	}
	
	@Override
	public String toString(){ return data.toString(); }
}
