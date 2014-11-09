package general.datastructure;

public class Node<T> {
	private T data;
	
	public Node(T dataIn){ data = dataIn; }
	public T getData(){return data;}
	public void setData(T dataIn){ data = dataIn; }
	public String toString(){ return data.toString(); }
}
