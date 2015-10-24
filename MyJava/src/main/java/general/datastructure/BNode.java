package general.datastructure;

public class BNode<T> {
	private T data;
	private BNode<T> left;
	private BNode<T> right;
	
	public BNode(T dataIn){
		data = dataIn;
	}
	
	public BNode(T dataIn, BNode<T> parentIn, BNode<T> leftIn, BNode<T> rightIn){
		data = dataIn;
		left = leftIn;
		right = rightIn;
	}
	
	public BNode<T> getLeft(){ return left; }
	public void setLeft(BNode<T> leftIn){ left = leftIn; }
	
	public BNode<T> getRight(){ return right; }
	public void setRight(BNode<T> rightIn){ right = rightIn; }
	
	public T getData(){ return data; }
	public void setData(T dataIn){ data = dataIn; }
	
	public String toString(){ return data.toString(); }
}
