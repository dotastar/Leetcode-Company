package general.algorithms;

import static org.junit.Assert.*;

import java.util.Arrays;

import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Sieve of Eratosthenes
 * 
 * http://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
 * 
 * @author yazhoucao
 *
 */
public class PrimeNumbers {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(PrimeNumbers.class);
	}

	/**
	 * Sieve of Eratosthenes
	 * 1.Use an boolean array[n], where A[i] represents if i is prime
	 * 2.Firstly, assume all numbers are primes
	 * 3.Secondly, iterate through the array start from 2,
	 * if i is prime, mark all the multiple of i as non-prime
	 * 4.the same as 3, 4, 5... till n
	 * 
	 * Time: O(nloglogn)
	 * Space: O(n)
	 * 
	 */
	public int countPrimes_Improved(int n) {
		boolean[] primes = new boolean[n];
		Arrays.fill(primes, true);
		for (int i = 2; i * i < n; i++) { // only calculate to sqrt(n)
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
		// TODO Write input, result, answer
		assertTrue(true);
	}
}
