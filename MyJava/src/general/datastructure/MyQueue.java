package general.datastructure;
import java.util.Stack;

/**
 * 3.5 Implement a MyQueue class which implements a queue using two stacks.
 * @author yazhoucao
 *
 */
public class MyQueue<T> {
	
	private Stack<T> inStack;
	private Stack<T> outStack;
	private int size;
	
	public MyQueue(){
		size = 0;
		inStack = new Stack<T>();
		outStack = new Stack<T>();
	}
	
	public int size(){ return size; }
	
	public void enqueue(T item){
		inStack.push(item);
		size++;
	}
	
	public T dequeue(){
		if(size==0)	return null;
		
		size--;
		
		if(outStack.size()!=0) return outStack.pop();
		
		while(inStack.size()!=0){
			outStack.push(inStack.pop());
		}
		
		return outStack.pop();
	}
	
	public static void main(String[] args) {
		MyQueue<Integer> queue = new MyQueue<Integer>();
		for(int i=0; i<10;i++)
			queue.enqueue(i*5);
		
		for(int i=0; i<5; i++)
			System.out.print(queue.dequeue()+" ");
		
		for(int i=0; i<5; i++)
			queue.enqueue(i*3);
		
		while(queue.size()!=0)
			System.out.print(queue.dequeue()+" ");
	}

}
