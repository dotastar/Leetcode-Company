
package general.datastructure;

public class MinStack<T extends Comparable<T>>{
	private Node<T> top;
	private Node<T> min;
	private int size;

	public MinStack(){
		size = 0;
		top = null;
		min = null;
	}

	public int size(){ return size; }
	
	public T pop(){ 
		if(top==null) return null;

		T data = top.getData();
		top = top.next();
		if(min.getData().compareTo(data)==0)
			min = min.next();

		size--;
		
		return data;
	}

	public T peek(){
		if(top==null) return null;
		else return top.getData();
	}

	public T min(){ 
		if(top==null) return null;
		else return min.getData();
	}

	public void push(T data){
		size++;
		
		Node<T> newtop = new Node<T>(data);
		newtop.setNext(top);
		top = newtop;
		
		if(min==null)
			min = new Node<T>(data);
		else{		
			if(data.compareTo(min.getData())<=0){
				Node<T> newmin = new Node<T>(data);	
				newmin.setNext(min);
				min = newmin;
			}
		}
	}

	
	public String toString(){
		if(size==0) return "[]";
		else if(size==1) return "["+top.toString()+"]";
		
		StringBuffer sb = new StringBuffer("["+top.toString());
		Node<T> iter = top.next();
		while(iter!=null){
			sb.append(","+iter.toString());
			iter = iter.next();
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	public static void main(String[] args){
		MinStack<Integer> stack = new MinStack<Integer>();
		stack.push(21);
		stack.push(15);
		stack.push(32);
		stack.push(12);
		stack.push(11);
		stack.push(7);
		System.out.println(stack.toString());
		System.out.println("size: "+stack.size()+"\t\t\t min value: "+stack.min());
		while(stack.peek()!=null)
			System.out.println("pop out: "+stack.pop()+"\t\t min value: "+stack.min());
		System.out.println("size: "+stack.size());
		System.out.println("end");
	}
}