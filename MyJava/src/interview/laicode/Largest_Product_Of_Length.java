package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

/**
 * Largest Product Of Length
 * 
 * Hard
 * String
 * 
 * Given a dictionary containing many words, find the largest product of two
 * words’ lengths, such that the two words do not share any common characters.
 * 
 * Assumptions
 * 
 * The words only contains characters of 'a' to 'z'
 * The dictionary is not null and does not contains null string, and has at
 * least two strings
 * If there is no such pair of words, just return 0
 * 
 * Examples
 * 
 * dictionary = [“abcde”, “abcd”, “ade”, “xy”], the largest product is 5 * 2 =
 * 10 (by choosing “abcde” and “xy”)
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Largest_Product_Of_Length {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Largest_Product_Of_Length.class);
	}

	/**
	 * Time: O(n^2)
	 * 1.sort the input array by the length of string in descending order
	 * 2.from left to right, for each string compare it with its right others,
	 * the first two strings do share any characters is the largest product
	 * 
	 * Notice: if the String is too long, the number to represent the String
	 * could overflow.
	 */
	public int largestProduct(String[] dict) {
		Arrays.sort(dict, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s2.length() - s1.length();
			}
		});

		int[] primes = generatePrimes(26);
		for (int i = 0; i < dict.length - 1; i++) {
			long id = 1; // potential overflow
			String curr = dict[i];
			for (int j = 0; j < curr.length(); j++)
				id *= primes[curr.charAt(j) - 'a'];
			// compare with its right neighbors
			for (int j = i + 1; j < dict.length; j++) {
				String right = dict[j];
				boolean hasCommon = false;
				for (int k = 0; k < right.length(); k++) {
					if (id % primes[right.charAt(k) - 'a'] == 0) {
						hasCommon = true;
						break;
					}
				}
				if (!hasCommon) {
					return curr.length() * right.length();
				}
			}
		}
		return 0;
	}

	@Test
	public void test1() {
		String[] dict = { "abc", "def", "ghil", "lmno", "pafogxyyy" };
		int ans = 12;
		int res = largestProduct(dict);
		assertTrue("Wrong: " + res, ans == res);
	}

	private static int[] generatePrimes(int size) {
		int[] primes = new int[size];
		if (size < 1)
			return primes;
		primes[0] = 2;
		int num = 3;
		for (int i = 1; i < size; i++) {
			while (!isPrime(num, primes, i - 1))
				num += 2;
			primes[i] = num;
			num += 2;
		}
		return primes;
	}

	private static boolean isPrime(int num, int[] primes, int end) {
		for (int i = 0; i <= end; i++) {
			if (num % primes[i] == 0)
				return false;
		}
		return true;
	}
}
