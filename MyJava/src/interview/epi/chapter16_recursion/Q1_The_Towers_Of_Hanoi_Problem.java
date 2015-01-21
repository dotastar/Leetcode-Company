package interview.epi.chapter16_recursion;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Exactly n rings on P1 need to be transferred to P2, possibly using P3 as an
 * intermediate, subject to the stacking constraint. Write a function that
 * prints a sequence of operations that transfers all the rings from P1 to P2.
 * 
 * You have the following constraints:
 * (1) Only one disk can be moved at a time.
 * (2) A disk is slid off the top of one tower onto the next rod.
 * (3) A disk can only be placed on top of a larger disk.
 * 
 * @author yazhoucao
 * 
 */
public class Q1_The_Towers_Of_Hanoi_Problem {

	// static Class<?> c = Q1_The_Towers_Of_Hanoi_Problem.class;

	public static void main(String[] args) {
		// AutoTestUtils.runTestClassAndPrint(c);
		Q1_The_Towers_Of_Hanoi_Problem o = new Q1_The_Towers_Of_Hanoi_Problem();
		o.moveTowerHanoi(5);
	}

	public void moveTowerHanoi(int n) {
		List<Stack<Integer>> pegs = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			pegs.add(new Stack<Integer>());
		}
		// Initialize pegs.
		for (int i = n; i >= 1; --i) {
			pegs.get(0).push(i);
		}
		move(n, pegs, 0, 1, 2);
	}

	/**
	 * First move from to buffer, then move buffer to 'to'.
	 */
	private <T> void move(int n, List<Stack<T>> pegs, int from, int to, int buf) {
		if (n > 0) {
			move(n - 1, pegs, from, buf, to);
			pegs.get(to).push(pegs.get(from).pop());
			System.out.println("Move from peg " + from + " to peg " + to);
			move(n - 1, pegs, buf, to, from);
		}
	}
}
