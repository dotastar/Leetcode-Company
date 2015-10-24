package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Given an unsorted array of integers, find the length of the longest
 * consecutive elements sequence.
 * 
 * For example, Given [100, 4, 200, 1, 3, 2], The longest consecutive elements
 * sequence is [1, 2, 3, 4]. Return its length: 4.
 * 
 * Your algorithm should run in O(n) complexity.
 * 
 * @author yazhoucao
 * 
 */
public class Longest_Consecutive_Sequence {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Longest_Consecutive_Sequence.class);
	}

	/**
	 * Time: O(n), Space: O(n)
	 * Save the length of current consecutive sequence, all we need is to
	 * maintain the updated length in the head and tail of a sequence.
	 * 
	 * When we insert a new number, we check if its neighbor exists, if yes we
	 * get their lengths, add up to the total length, then we can easily find
	 * the tail and head value of the sequence by the neighbor's length. Then we
	 * update their lengths.
	 */
	public int longestConsecutive2(int[] num) {
		int max = 0;
		Map<Integer, Integer> cntMap = new HashMap<>();
		for (int n : num) {
			if (!cntMap.containsKey(n)) {
				int cnt = 1;
				int upper = cntMap.containsKey(n + 1) ? cntMap.get(n + 1) : 0;
				int lower = cntMap.containsKey(n - 1) ? cntMap.get(n - 1) : 0;
				cnt += upper + lower;
				cntMap.put(n + upper, cnt); // update head
				cntMap.put(n - lower, cnt); // update tail
				// must store the current value so that it is visited
				cntMap.put(n, cnt);
				max = Math.max(cnt, max);
			}
		}
		return max;
	}

	/**
	 * Think it as a graph problem, two nodes are connected if they are
	 * consecutive(n--n-1, n--n+1).
	 * 
	 * The purpose is to traverse all the nodes, and find the longest path.
	 * 
	 * Because the property of two connected nodes(node.val+1, node.val-1), it
	 * is easy to check if there is a connected node by Hashset.
	 * 
	 * Time: O(n^2), Space: O(n)
	 */
	public int longestConsecutive(int[] num) {
		Set<Integer> unvisited = new HashSet<Integer>();
		for (int n : num)
			unvisited.add(n);

		int max = 1;
		for (int n : num) {
			int length = 1;
			int greater = n + 1;
			while (unvisited.contains(greater)) {
				unvisited.remove(greater);
				length++;
				greater++;
			}
			int less = n - 1;
			while (unvisited.contains(less)) {
				unvisited.remove(less);
				length++;
				less--;
			}

			max = length > max ? length : max;
		}
		return max;
	}

	@Test
	public void test1() {
		int[] num = { 4, 0, -4, -2, 2, 5, 2, 0, -8, -8, -8, -8, -1, 7, 4, 5, 5, -4, 6, 6,
				-3 };
		int ans = 5;
		int res = longestConsecutive2(num);
		assertTrue("Wrong: " + res, ans == res);
	}
}
