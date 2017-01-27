package interview.epi.chapter5_primitive_type;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.junit.Test;

/**
 * The problem is motivated by the following. Five friends have to select a
 * designated driver using a single unbiased coin. The process should be fair to
 * everyone.
 * 
 * Problem 5.12
 * How would you implement a random number generator that generates a random
 * integer i between a and b, inclusive, given a random number generator that
 * produces either zero or one with equal probability? All generated values
 * should be equally likely.
 * 
 * @author yazhoucao
 * 
 */
public class Q12_Generate_A_Uniform_Random_Numbers {

	static String methodName = "uniformRandom";
	static Class<?> c = Q12_Generate_A_Uniform_Random_Numbers.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Idea: concatenating bits produced by the random 0/1 generator, the
	 * challenge is know when to stop.
	 * First, we convert the problem to generate a number from 0 to t = b-a.
	 * Then, we keep concatenating until 2^i > t (just greater than).
	 * So we know 2^i is the tightest range that include 0~t. But it could be
	 * greater than t, we need to check it is greater than t, try again.
	 * 
	 * Time: O(1).
	 * 
	 * The running time is related to the probability of retrying, which
	 * is (((2^i)-t) / (2^i))^k, k is the number of trying (the while loop).
	 * 
	 * Since 2^i is the smallest power of 2 greater than or equals to t,
	 * ((2^i)-t) / (2^i))^k < (1/2)^k.
	 * 
	 * Hence the average number of calls is bounded by 1(1/2) + 2(1/2)^2 +
	 * 3(1/2)^3 + .... Terms in this series reduce by a factor of 2 at each
	 * step, in the limit, this series converges, therefore the time is O(1).
	 */
	public static int uniformRandom(int a, int b) {
		assert b > a;
		Random ran = new Random();
		int range = b - a;
		int res = 0;
		do {
			res = 0;
			for (int i = 0; (1 << i) <= range; i++)
				res = (res << 1) | ran.nextInt(2);
		} while (res >= range);

		return a + res;
	}

	/****************** Unit Test ******************/

	public int invokeMethod(Method m, int a, int b) {
		try {
			Object[] args = new Object[] { a, b };
			Object res = m.invoke(null, args);
			return (int) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Test
	public void test0() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			Random gen = new Random();
			int range = 100;
			int a = gen.nextInt(range), b = gen.nextInt(range) + a + 1;
			System.out.println(String.format("a = %d, b = %d", a, b));
			for (int times = 0; times < 1000; ++times) {
				int x = invokeMethod(m, a, b);
				System.out.print(x+", ");
				assertTrue(x >= a && x <= b);
			}
			System.out.println();
		}
	}
}
