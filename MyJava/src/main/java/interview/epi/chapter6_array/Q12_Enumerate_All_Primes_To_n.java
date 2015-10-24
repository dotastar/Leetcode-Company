package interview.epi.chapter6_array;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Q12_Enumerate_All_Primes_To_n {

	static String methodName = "generatePrimesFrom1toN";
	static Class<?> c = Q12_Enumerate_All_Primes_To_n.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: greater than O(n), but close to O(n)
	 * Two optimizations:
	 * 1.just need to try calculated primes (instead of all odd numbers)
	 * 2.just need to try the number less than or equal to sqrt(n)
	 */
	public static List<Integer> generatePrimesFrom1toN(int n) {
		List<Integer> primes = new ArrayList<Integer>();
		if (n > 1)
			primes.add(2);
		int maxTry = (int) (Math.sqrt(n) + 1);
		for (int i = 3; i < n; i += 2) {
			boolean canDivid = false;
			for (int j = 0; j < primes.size() && primes.get(j) < maxTry; j++) {
				if (i % primes.get(j) == 0) {
					canDivid = true;
					break;
				}
			}
			if (!canDivid)
				primes.add(i);
		}
		return primes;
	}

	/**
	 * Solution from EPI
	 * Time: O(n/2 + n/3 + n/5 + n/7 + ...) = O(n * loglogn)
	 */
	public static List<Integer> generatePrimesFrom1toN_EPI(int n) {
		int size = (int) Math.floor(0.5 * (n - 3)) + 1;
		// Stores the primes from 1 to n.
		List<Integer> primes = new ArrayList<>();
		primes.add(2);
		// isPrime[i] represents (2i + 3) is prime or not. Initially assuming
		// everyone is prime (by setting as true).
		boolean[] isPrime = new boolean[size];
		Arrays.fill(isPrime, true);
		for (int i = 0; i < size; ++i) {
			if (isPrime[(int) i]) {
				int p = (int) ((i * 2) + 3);
				primes.add(p);
				// Sieving from p^2, whose index is 4i^2 + 12i + 9 whose index
				// in isPrime is 2i^2 + 6i + 3 because isPrime[i] represents 2i
				// + 3.
				for (long j = ((i * i) * 2) + 6 * i + 3; j < size; j += p) {
					isPrime[(int) j] = false;
				}
			}
		}
		return primes;
	}

	/****************** Unit Test ******************/

	@SuppressWarnings("unchecked")
	public List<Integer> invokeMethod(Method m, int n) {
		try {
			Object res = m.invoke(null, n);
			return (List<Integer>) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return new ArrayList<Integer>();
		}
	}

	@Test
	public void test1() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int n = 100;
			List<Integer> res = invokeMethod(m, n);
			int[] correct = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,
					43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 };
			assertTrue(res.size() == correct.length);
			for (int i = 0; i < correct.length; i++) {
				assertTrue(res.get(i) == correct[i]);
			}
		}
	}
}
