package interview.company.zenefits;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Zenefits
 * 
 * Phone
 * 
 * Preorder judge
 * 
 * Given a list of numbers, determine whether it can represent the pre-order
 * traversal list of a binary search tree (BST).
 * 
 * Input
 * The first line contains the number of test cases, T. T lines follow,
 * consisting of two lines each.
 * The first line of each test case contains the number of nodes in the tree, N.
 * In next line there will a list of N unique numbers, where each number is from
 * the range [1, N].
 * 
 * Output
 * For each test case, print the string “YES” if there exists a BST whose
 * pre-order traversal is equal to the list, otherwise print the string “NO”
 * (without quotes, preserving capitalization).
 * 
 * Constraints
 * 1 ≤ T ≤ 10
 * 1 ≤ N ≤ 100
 * 
 * Sample Input
 * 5
 * 3
 * 1 2 3
 * 3
 * 2 1 3
 * 6
 * 3 2 1 5 4 6
 * 4
 * 1 3 4 2
 * 5
 * 3 4 5 1 2
 * 
 * Sample Output
 * YES
 * YES
 * YES
 * NO
 * NO
 * 
 * Explanation
 * 
 * The first three cases are from the above examples.
 * In case 4, after encountering the 3, the 4tells us we are on the right
 * sub-tree, which means no values smaller than 3 are allowed any longer. So
 * when we see the 2 we know the list is invalid.
 * Similarly, in case 5, after encountering the 3, the 4 and 5 tell us we are on
 * the right sub-tree, so the subsequent encounter of values 2 and 1, which
 * belong in the left sub-tree, tells us that the list is not valid as a
 * pre-order traversal of a BST
 * 
 * @author yazhoucao
 *
 */
public class PreorderJudge {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(PreorderJudge.class);
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertTrue(true);
	}
}
