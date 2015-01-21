package interview.epi.chapter16_recursion;

import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Given n nodes, enumerate all unique binary trees.
 * 
 * @author yazhoucao
 * 
 */
public class Q9_Enumerate_Binary_Trees {

	public static void main(String[] args) {

	}

	public List<BinaryTree<Integer>> generateAllBinaryTrees(int n) {
		return generate(1, n);
	}

	/**
	 * Time: Exponential
	 * Sum.i.(C(n-i)*C(i-1)), where i = 1...n.  
	 * = 4^n /( (n^(3/2))*sqrt(n) ), (Catalan number)
	 */
	private List<BinaryTree<Integer>> generate(int start, int end) {
		List<BinaryTree<Integer>> result = new ArrayList<>();
		if (start > end) {
			result.add(null);
			return result;
		}
		for (int i = start; i <= end; ++i) {
			// Try all possible combinations of left and right subtrees.
			List<BinaryTree<Integer>> lefts = generate(start, i - 1);
			List<BinaryTree<Integer>> rights = generate(i + 1, end);
			for (BinaryTree<Integer> left : lefts) {
				for (BinaryTree<Integer> right : rights) {
					result.add(new BinaryTree<>(i, left, right));
				}
			}
		}
		return result;
	}
}
