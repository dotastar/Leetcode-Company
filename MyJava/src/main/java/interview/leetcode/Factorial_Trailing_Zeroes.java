package interview.leetcode;

/**
 * Given an integer n, return the number of trailing zeroes in n!.
 * 
 * Note: Your solution should be in logarithmic time complexity.
 * 
 * @author yazhoucao
 * 
 */
public class Factorial_Trailing_Zeroes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * The idea is to consider prime factors of a factorial n. A trailing zero
	 * is always produced by prime factors 2 and 5.
	 * If we can count the number of 5s and 2s, our task is done.
	 * And the number of 2s is always greater or equals to the number of 5s, we
	 * just need to count 5s.
	 * We need to count not only 5, but also 25, 125...
	 * 
	 * So the problem become calculating :
	 * count = floor(n/5) + floor(n/25) + floor(n/125) + ....
	 * 
	 * Overflow problem is a little tricky, solution is to use division instead
	 * of multiplication!
	 **/
	public int trailingZeroes(int n) {
		int res = 0;
		for (int i = 5; i <= n; n /= 5) {
			res += n / i;
		}
		return res;
	}

	/**
	 * Same solution, the idea is more clear, but dosen't solve overflow
	 * Function to return trailing 0s in factorial of n
	 */
	public int trailingZeroes2(int n) {
		int count = 0;
		// Keep dividing n by powers of 5 and update count
		for (int i = 5; n / i >= 1; i *= 5) { // i*=5 could overflow!
			count += n / i;
		}
		return count;
	}

}
