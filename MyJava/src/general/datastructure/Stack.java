package general.datastructure;

public class Stack<T extends Comparable<T>>{
	private int size;
	private Node<T> top;

	public Stack(){
		size = 0;
		top = null;
	}

	public int size(){ return size;}

	public T peek(){
		if(top==null) return null;
		else return top.getData();
	}

	public T pop(){
		if(top==null) return null;

		T data = top.getData();
		top = top.next();
		size--;
		return data;
	}

	public void push(T data){
		Node<T> newtop = new Node<T>(data);
		newtop.setNext(top);
		top = newtop;
		size++;
	}


	public static void main(String[] args){
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(5);
		stack.push(4);
		stack.push(3);
		stack.push(2);
		stack.push(1);
		
		System.out.println("size: "+stack.size());
		while(stack.peek()!=null)
			System.out.println(stack.pop());
		System.out.println("size: "+stack.size());
		System.out.println("end");
	}
}