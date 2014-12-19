package interview.epi.chaper6_array;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Problem 6.2
 * Write a function which takes as input an array A of digits encoding a decimal
 * number D and updates A to represent the number D+1. For example, if A=<1,2,9>
 * then you should update A to <1,3,0>.
 * 
 * @author yazhoucao
 * 
 */
public class Q2_Increment_A_BigInteger {
	static String methodName = "plusOne";
	static Class<?> c = Q2_Increment_A_BigInteger.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	
	/**
	 * Time: O(n).
	 */
	public static int[] plusOne_Concise(int[] A) {
		A[A.length - 1]++;
		for (int i = A.length - 1; i > 0 && A[i] == 10; i--) {
			A[i] = 0;
			A[i - 1]++;
		}

		if (A[0] == 10) {
			int[] copy = new int[A.length + 1];
			System.arraycopy(A, 0, copy, 1, A.length);
			copy[1] = 0;	//don't forget here
			copy[0] = 1;
			return copy;
		} else
			return A;
	}

	public static int[] plusOne(int[] A) {
		boolean carry = true;
		for (int i = A.length - 1; i >= 0; i--) {
			if (carry && A[i] == 9) {
				A[i] = 0;
				carry = true;
			} else if (carry) {
				A[i]++;
				carry = false;
			} else
				break;
		}

		if (carry) {
			int[] copy = new int[A.length + 1];
			System.arraycopy(A, 0, copy, 1, A.length);
			copy[0] = 1;
			return copy;
		} else
			return A;
	}

	/****************** Unit Test  ******************/

	public int[] invokeMethod(Method m, int[] A) {
		try {
			Object res = m.invoke(null, A);
			return (int[]) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return new int[0];
		}
	}

	/**
	 * Array equal
	 */
	private boolean arrayEquals(int[] A, int[] B) {
		if (A.length != B.length)
			return false;
		for (int i = 0; i < A.length; i++) {
			if (A[i] != B[i])
				return false;
		}
		return true;
	}

	@Test
	public void test1() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = { 1, 2, 9 };
			int[] res = invokeMethod(m, A);
			assertTrue(m.getName(), arrayEquals(res, new int[] { 1, 3, 0 }));
		}
	}

	@Test
	public void test2() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = { 9 };
			int[] res = invokeMethod(m, A);
			assertTrue(m.getName(), arrayEquals(res, new int[] { 1, 0 }));
		}
	}

	@Test
	public void test3() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = { 9, 9, 9 };
			int[] res = invokeMethod(m, A);
			assertTrue(m.getName(), arrayEquals(res, new int[] { 1, 0, 0, 0 }));
		}
	}

	@Test
	public void test4() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = { 1, 1 };
			int[] res = invokeMethod(m, A);
			assertTrue(m.getName(), arrayEquals(res, new int[] { 1, 2 }));
		}
	}

}
