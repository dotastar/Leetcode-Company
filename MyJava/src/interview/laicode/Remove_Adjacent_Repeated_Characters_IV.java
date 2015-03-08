package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Stack;

import org.junit.Test;

/**
 * 
 * Remove Adjacent Repeated Characters IV
 * Hard
 * String
 * 
 * Repeatedly remove all adjacent, repeated characters in a given string from
 * left to right.
 * 
 * No adjacent characters should be identified in the final string.
 * 
 * Examples
 * 
 * "abbbaaccz" → "aaaccz" → "ccz" → "z"
 * "aabccdc" → "bccdc" → "bdc"
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Remove_Adjacent_Repeated_Characters_IV {
	private static Class<?> c = Remove_Adjacent_Repeated_Characters_IV.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Better solution
	 * Do it in place, use array to simulate stack
	 * Time: O(n), Space: O(1)
	 */
	public String deDup2(String input) {
		char[] res = input.toCharArray();
		int top = -1; // pointer of the top of Stack
		boolean isRepeat = false;
		for (int i = 0; i < res.length; i++) {
			if (top == -1 || res[i] != res[top]) {
				if (isRepeat) {
					top--;
					i--;
					isRepeat = false;
				} else
					res[++top] = res[i];
			} else
				isRepeat = true;
		}
		if (isRepeat)
			top--;
		return new String(res, 0, top + 1);
	}

	/**
	 * Naive solution, use Stack + flag (isRepeated)
	 * Time: O(n), Space: O(n)
	 * 
	 * Compare current element with the top of the stack,
	 * 
	 * If it's empty stack, we push the current element,
	 * 
	 * Else if they are not equals, check if the top element isRepeated, if yes,
	 * then pop the element, re-set the flag, and re-read the current again,
	 * otherwise is not repeated, push the current into stack.
	 * 
	 * Otherwise they are equals, mark flag and ignore the current,
	 */
	public String deDup(String input) {
		Stack<Character> stk = new Stack<>();
		boolean isRepeated = false;
		for (int i = 0; i < input.length(); i++) {
			char ci = input.charAt(i);
			if (stk.isEmpty() || stk.peek() != ci) {
				if (isRepeated) {
					isRepeated = false;
					stk.pop();
					i--;
				} else
					stk.push(ci);
			} else
				isRepeated = true; // stk.peek() == ci
		}
		if (isRepeated)
			stk.pop();
		char[] res = new char[stk.size()];
		for (int i = res.length - 1; i >= 0; i--) {
			res[i] = stk.pop();
		}
		return new String(res);
	}

	@Test
	public void test1() {
		String input = "abbbaaccz";
		String res = deDup(input);
		String ans = "z";
		assertTrue("Wrong: " + res, res.equals(ans));
	}

	@Test
	public void test2() {
		String input = "aabccdc";
		String res = deDup(input);
		String ans = "bdc";
		assertTrue("Wrong: " + res, res.equals(ans));
	}

	@Test
	public void test3() {
		String input = "abcdddcbba";
		String res = deDup(input);
		String ans = "";
		assertTrue("Wrong: " + res, res.equals(ans));
	}

}
