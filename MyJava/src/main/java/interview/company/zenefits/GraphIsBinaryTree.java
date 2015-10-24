package interview.company.zenefits;

import interview.AutoTestUtils;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Zenefits
 * Onsite
 * 
 * http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=131422&extra=page%3D3%26filter%3Dsortid%26sortid%3D311%26sortid%3D311
 * 
 * You are given a binary tree as a sequence of parent-child pairs. For example,
 * the tree represented by the node pairs below:
 * (A,B) (A,C) (B,G) (C,H) (E,F) (B,D) (C,E)
 * may be illustrated in many ways, with two possible representations below:
 *********** A
 ********* / _ \
 ******* B ____ C
 ****** / \ __ / \
 ***** G _ D _ H - E
 ****************** \
 ******************** F
 * 
 * Below is the recursive definition for the S-expression of a tree:
 * 
 * S-exp(node) = 
 * (node->val (S-exp(node->first_child))(S-exp(node->second_child))), 
 * if node != NULL = "", node == NULL
 * where, first_child->val < second_child->val (lexicographically smaller)
 * 
 * This tree can be represented in a S-expression in multiple ways. 
 * The lexicographically smallest way of expressing this is as follows:
 * (A(B(D)(G))(C(E(F))(H)))
 * We need to translate the node-pair representation into an S-expression
 * (lexicographically smallest one), and report any errors that do not conform
 * to the definition of a binary tree.
 * 
 * The list of errors with their codes is as follows:
 * 
 * Error Code Type of error
 * E1 More than 2 children
 * E2 Duplicate Edges
 * E3 Cycle present
 * E4 Multiple roots
 * E5 Any other error
 * 
 * Input Format:
 * Input must be read from standard input.
 * Input will consist of on line of parent-child pairs. Each pair consists of
 * two node names separated by a single comma and enclosed in parentheses. A
 * single space separates the pairs.
 * 
 * Output:
 * The function must write to standard output.
 * Output the given tree in the S-expression representation described above.
 * There should be no spaces in the output.
 * 
 * Constraints:
 * There is no specific sequence in which the input (parent,child) pairs are
 * represented.
 * The name space is composed of upper case single letter (A-Z) so the maximum
 * size is 26 nodes.
 * Error cases are to be tagged in the order they appear on the list. For
 * example, if one input pair raises both error cases 1 and 2, the output must
 * be E1.
 * 
 * 
 * 
 * Sample Input #00
 * (B,D) (D,E) (A,B) (C,F) (E,G) (A,C)
 * Sample Output #00
 * (A(B(D(E(G))))(C(F)))
 * 
 * Sample Input #01
 * (A,B) (A,C) (B,D) (D,C)
 * Sample Output #01
 * E3
 * 
 * Explanation
 * Node D is both a child of B and a parent of C, but C and B are both child
 * nodes of A. Since D tries to attach itself as parent to a node already above
 * it in the tree, this forms a cycle.
 * 
 * 
 * @author yazhoucao
 *
 */
public class GraphIsBinaryTree {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(GraphIsBinaryTree.class);
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertTrue(true);
	}
}
