package general.datastructure;

public class LinkedNode<T extends Comparable<T>> implements Comparable<LinkedNode<T>>{
	
	private T data;
	private LinkedNode<T> next;
	
	public LinkedNode(T dataIn){
		data = dataIn;
	}
	
	public T getData(){ return data; }
	public void setData(T dataIn){ data = dataIn; }
	
	public LinkedNode<T> next(){ return next; }
	public void setNext(LinkedNode<T> nextIn){ next = nextIn; }

	@Override
	public int compareTo(LinkedNode<T> o) {
		T cmp = o.getData();
		return data.compareTo(cmp);
	}
	
	@Override
	public String toString(){ return data.toString(); }
}
