package interview.leetcode;

import java.util.Stack;

/**
 * Design a stack that supports push, pop, top, and retrieving the minimum
 * element in constant time.
 * 
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 * 
 * @author yazhoucao
 * 
 */
public class Min_Stack {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MinStack minStk = new MinStack();
		minStk.push(512);
		minStk.push(-1024);
		minStk.push(-1024);
		minStk.push(512);
		
		minStk.pop();
		assert minStk.getMin()==-1024;
		minStk.pop();
		assert minStk.getMin()==-1024;
		minStk.pop();
		assert minStk.getMin()==512;
		System.out.println("Finished!");
	}

}

class MinStack {
    private Stack<Integer> stk;
    private Stack<Integer> mins;
    
    public MinStack(){
        stk = new Stack<Integer>();
        mins = new Stack<Integer>();
    }
    
    public void push(int x) {
        stk.push(x);
        if(mins.isEmpty() || x<=mins.peek())
            mins.push(x);
    }

    public void pop() {
        if(stk.pop().equals(mins.peek()))
            mins.pop();
    }

    public int top() {
        assert !stk.isEmpty();
        return stk.peek();
    }

    public int getMin() {
        assert !mins.isEmpty();
        return mins.peek();
    }
}

