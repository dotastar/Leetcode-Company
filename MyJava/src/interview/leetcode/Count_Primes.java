package interview.leetcode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;

import org.junit.Test;

/**
 * Description:
 * 
 * Count the number of prime numbers less than a non-negative number, n
 * 
 * References:
 * 
 * How Many Primes Are There?
 * 
 * Sieve of Eratosthenes
 * 
 * @author yazhoucao
 *
 */
public class Count_Primes {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Count_Primes.class);
	}

	/**
	 * Sieve of Eratosthenes
	 * 1.Use an boolean array[n], where A[i] represents if i is prime
	 * 2.Firstly, assume all numbers are primes
	 * 3.Secondly, iterate through the array start from 2,
	 * if i is prime, mark all the multiple of i as non-prime
	 * 4.the same as 3, 4, 5... till n
	 */
	public int countPrimes(int n) {
		boolean[] primes = new boolean[n];
		Arrays.fill(primes, true);
		int cnt = 0;
		for (int i = 2; i < n; i++) {
			if (primes[i]) {
				cnt++;
				// suffices to consider multiples i, i+1, ..., N/i
				for (int j = 2; j * i < n; j++) {
					primes[i * j] = false;
				}
			}
		}
		return cnt;
	}

	/**
	 * Time: O(sqrt(n) * )
	 * Space: O(n)
	 * Improvement: only calculate to sqrt(n)
	 */
	public int countPrimes_Improved(int n) {
		boolean[] primes = new boolean[n];
		Arrays.fill(primes, true);
		for (int i = 2; i * i < n; i++) {
			if (primes[i]) {
				// suffices to consider multiples i, i+1, ..., N/i
				for (int j = 2; j * i < n; j++) {
					primes[i * j] = false;
				}
			}
		}
		int cnt = 0;
		for (int i = 2; i < n; i++)
			cnt = primes[i] ? cnt + 1 : cnt;
		return cnt;
	}

	@Test
	public void test1() {
		int n = 2;
		int res = countPrimes_Improved(n);
		int ans = 0;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		int n = 3;
		int res = countPrimes_Improved(n);
		int ans = 1;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test3() {
		int n = 5;
		int res = countPrimes_Improved(n); // 2 3
		int ans = 2;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test4() {
		int n = 9;
		int res = countPrimes_Improved(n); // 2 3 5 7
		int ans = 4;
		assertTrue("Wrong: " + res, res == ans);
	}
}
