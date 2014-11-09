package interview.cc150.chapter1_4;
import java.util.Random;
import java.util.Stack;


public class SortStack {
	
	/**
	 * 3.6 Write a program to sort a stack in ascending 
	 * order (with biggest items on top). You may use at 
	 * most one additional stack to hold items, but you 
	 * may not copy the elements into any other data 
	 * structure (such as an array). The stack supports 
	 * the following operations: push, pop, peek, and isEmpty.
	 * Time: n^2, Space: n
	 * Sort by ascending order from top to bottom
	 */
	public static void sort(Stack<Integer> stack){
		Stack<Integer> tmpStack = new Stack<Integer>();
		while(stack.size()!=0){
			int stackData = stack.pop();
			while(tmpStack.size()!=0 && stackData < tmpStack.peek()){
				stack.push(tmpStack.pop());
			}
			tmpStack.push(stackData);
		}
		
		while(tmpStack.size()!=0)
			stack.push(tmpStack.pop());
	}
	
	public static void main(String[] args) {
		Random ran = new Random(System.currentTimeMillis());
		Stack<Integer> stack = new Stack<Integer>();
		for(int i=0; i<10; i++){
			stack.push(ran.nextInt(100));
		}
		System.out.println("Before sorting:"+stack.toString());
		sort(stack);
		System.out.println("After sorting:"+stack.toString());
	}

}
