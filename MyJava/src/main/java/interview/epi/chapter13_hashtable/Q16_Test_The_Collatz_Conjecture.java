package interview.epi.chapter13_hashtable;

import java.util.HashSet;
import java.util.Set;

/**
 * Collatz Conjecture is for any given number n, if n is odd number n = 3n+1,
 * else n = n/2 for even number. For any given number, apply the above rule, it
 * will converge to 1.
 * E.g. n=11: 11, 34, 17, 52, 26, 13, 40, 20, 10, 5, 16, 8, 4, 2, 1.
 * How would you test the Collatz Conjecture for the first n positive integers?
 * 
 * @author yazhoucao
 * 
 */
public class Q16_Test_The_Collatz_Conjecture {

	public static void main(String[] args) {
		Q16_Test_The_Collatz_Conjecture o = new Q16_Test_The_Collatz_Conjecture();
		System.out.println(o.testCollatzConjecture(100));
	}

	/**
	 * Optimizations and Notices:
	 * 1.Store previously calculated numbers and numbers during the process
	 * 2.For space efficiency, only need to store odd numbers, even number will
	 * converge to odd number eventually.
	 * 3.If you have tested every number below k, you don't have to store them,
	 * if the current testing number is below k then it is valid.
	 * 4.Use bit shift rather than multiply and division.
	 * 5.Notice overflow, it needs to be taken care of.
	 */
	public boolean testCollatzConjecture(int n) {
		// Stores the odd number that converges to 1.
		Set<Long> tested = new HashSet<>();
		// Starts from 3 since we don't need to test 1, and 2 is obvious.
		for (int i = 3; i <= n; ++i) {
			Set<Long> currVisited = new HashSet<>();
			long testNum = i;
			while (testNum >= i) {
				if (!currVisited.add(testNum))
					return false;	// We met some number encountered before.
				if ((testNum & 1) == 1) { // odd number
					if (!tested.add(testNum))
						break; // have already be proven to converge to 1.

					long old = testNum;
					testNum = (testNum << 1) + testNum + 1; // 3n+1
					if (old > testNum)
						throw new RuntimeException("test process overflow!");
				} else
					testNum >>= 1; // n/2
			}
			System.out.println(currVisited.toString());
			tested.remove((long) i);
		}
		return true;
	}
}
