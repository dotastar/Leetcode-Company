package general.datastructure;

public class SingLinkList<T extends Comparable<T>> {
	
	private Node<T> head;
	private Node<T> tail;
	
	private int size;
	
	public SingLinkList(){
		head = null;
		tail = null;		
		size = 0;
	}
	
	public int size(){ return size; }
	public void setHead(Node<T> newHead){ head = newHead; }
	public Node<T> getHead(){ return head; }
	public Node<T> getTail(){ return tail; }
	
	public void add(Node<T> node){ 
		if(node==null)	return;
		size++;
		
		if(head==null){
			head = node;
			tail = node;
		}
		else{
			tail.setNext(node);
			tail = node;
		}
	}
	
	public Node<T> delete(int index){
		if(index>size-1) return null;
		
		if(index==0){
			Node<T> res = head;
			head = head.next();
			return res;
		}
		
		Node<T> current = head.next();
		Node<T> prev = head;
		for(int i=1; i<index; i++){
			prev = current;
			current = current.next();
		}
		
		//delete
		prev.setNext(current.next());
		return current;
	}
	
	public Node<T> get(int index){
		if(index>size-1) return null;
		Node<T> current = head;
		for(int i=0; i<index; i++)
			current = current.next();
		
		return current;
	}
	
	public void swap(int i, int j){
		if(i>size-1||j>size-1) return;
		
		Node<T> nodei = null;
		Node<T> nodej = null;		
		Node<T> current = head;
		
		for(int k=0; k<size; k++){
			if(i==k) nodei = current;
			if(j==k) nodej = current;
			
			if(nodei!=null&&nodej!=null) return;
			else current = current.next();
		}
	}
	
	@Override
	public String toString(){
		if(size==0) return "[]";
		StringBuffer sb = new StringBuffer("["+head.getData().toString());
		Node<T> current = head;
		while(true){
			current = current.next();
			if(current==null) break;
			sb.append(", "+current.getData().toString());
		}
		sb.append("]");
		return sb.toString();
	}
}
