package interview.company.facebook;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Given a set of n people, find the celebrity.
 * Celebrity is a person who:
 * 1. Knows only himself and no one else
 * 2. Every one else knows this person
 * 
 * You are given the following helper function:
 * bool knows(i, j);
 * Returns:
 * True: If i knows j
 * False: otherwise
 * 
 * 
 * In a party of N people, only one person is known to everyone. Such a person
 * may be present in the party, if yes, (s)he doesn’t know anyone in the party.
 * We can only ask questions like “does A know B? “. Find the stranger
 * (celebrity) in minimum number of questions.
 * 
 * @author yazhoucao
 * 
 */
public class FindCelebrity {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(FindCelebrity.class);
	}

	/**
	 * Graph:
	 * We can model the solution using graphs. Initialize indegree and outdegree
	 * of every vertex as 0. If A knows B, draw a directed edge from A to B,
	 * increase indegree of B and outdegree of A by 1. Construct all possible
	 * edges of the graph for every possible pair[i, j].
	 * If celebrity is present in the party, we will have one sink node in the
	 * graph with outdegree of zero, and indegree of N-1. We can find the sink
	 * node in (N) time, but the overall complexity is O(N^2) as we need to
	 * construct the graph first.
	 * 
	 */

	/**
	 * Recursion:
	 * We can decompose the problem into combination of smaller instances. Say,
	 * if we know celebrity of N-1 persons, can we extend the solution to N? We
	 * have two possibilities, Celebrity(N-1) may know N, or N already knew
	 * Celebrity(N-1). In the former case, N will be celebrity if N doesn’t know
	 * anyone else. In the later case we need to check that Celebrity(N-1)
	 * doesn’t know N.
	 * 
	 * Solve the problem of smaller instance during divide step. On the way
	 * back, we may find a celebrity from the smaller instance. During combine
	 * stage, check whether the returned celebrity is known to everyone and he
	 * doesn’t know anyone. The recurrence of the recursive decomposition is,
	 * 
	 * T(N) = T(N-1) + O(N)
	 * 
	 * T(N) = O(N^2).
	 * 
	 * You may try Writing pseudo code to check your recursion skills.
	 * 
	 */

	/**
	 * Elimination rule by the observation
	 * Time: O(n), Space: O(1)
	 * We have following observation based on elimination technique:
	 * 
	 * If A knows B, then A can’t be celebrity. Discard A, and B may be
	 * celebrity.
	 * If A doesn’t know B, then B can’t be celebrity. Discard B, and A may be
	 * celebrity.
	 * Repeat above two steps till we left with only one person.
	 * Ensure the remained person is celebrity. (Why do we need this step?)
	 * 
	 */
	public int findCelebrity(int[] A) {
		int i = 0, j = 1;
		while (i < A.length && j < A.length) {
			if (knows(A[j], A[i]))
				j = i > j ? i + 1 : j + 1; // j can't be celebrity
			else if (knows(A[i], A[j]) || !knows(A[j], A[i]))
				i = j > i ? j + 1 : i + 1; // i can't be celebrity
		}
		// cause the increments after the condition makes i/j always will be
		// greater than the other, when the while ends, it can't be both
		// i>=A.length && j>=A.length

		int celeb = i < A.length ? i : j; // final celebrity
		for (i = 0; i < A.length; i++) {
			if (!knows(A[i], A[celeb]) || (i != celeb && knows(A[celeb], A[i])))
				return -1;
		}
		return celeb;
	}

	private int[][] relationships = null;

	public boolean knows(int i, int j) {
		return relationships[i][j] == 1;
	};

	@Test
	public void test1() { // simple test
		relationships = new int[][] {
				// graph for relationships
				{ 1, 0, 1, 0 }, // first person
				{ 0, 1, 1, 0 }, // second person
				{ 0, 0, 1, 0 }, // third person
				{ 0, 1, 1, 1 } }; // fourth perseon
		int[] A = { 0, 1, 2, 3 };
		int res = findCelebrity(A);
		assertTrue("Wrong: " + res, res == 2);
	}

	@Test
	public void test2() { // false case, fifth person cancels third person
		relationships = new int[][] {
				// graph for relationships
				{ 1, 0, 1, 0, 0 }, // first person
				{ 0, 1, 1, 0, 0 }, // second person
				{ 0, 0, 1, 0, 0 }, // third person
				{ 0, 1, 1, 1, 0 }, // fourth person
				{ 0, 0, 0, 0, 1 } }; // fifth person
		int[] A = { 0, 1, 2, 3, 4 };
		int res = findCelebrity(A);
		assertTrue("Wrong: " + res, res == -1);
	}

	@Test
	public void test3() { // false case, nobody knows nobody
		relationships = new int[][] {
				// graph for relationships
				{ 1, 0, 0, 0, 0 }, // first person
				{ 0, 1, 0, 0, 0 }, // second person
				{ 0, 0, 1, 0, 0 }, // third person
				{ 0, 0, 0, 1, 0 }, // fourth person
				{ 0, 0, 0, 0, 1 } }; // fifth person
		int[] A = { 0, 1, 2, 3, 4 };
		int res = findCelebrity(A);
		assertTrue("Wrong: " + res, res == -1);
	}

	@Test
	public void test4() { // false case, everybody knows everybody
		relationships = new int[][] {
				// graph for relationships
				{ 1, 1, 1, 1, 1 }, // first person
				{ 1, 1, 1, 1, 1 }, // second person
				{ 1, 1, 1, 1, 1 }, // third person
				{ 1, 1, 1, 1, 1 }, // fourth person
				{ 1, 1, 1, 1, 1 } }; // fifth person
		int[] A = { 0, 1, 2, 3, 4 };
		int res = findCelebrity(A);
		assertTrue("Wrong: " + res, res == -1);
	}

}
