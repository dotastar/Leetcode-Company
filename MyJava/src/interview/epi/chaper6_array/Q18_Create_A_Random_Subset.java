package interview.epi.chaper6_array;

import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Problem 6.18
 * Design an algorithm that computes an array of size k consisting of distinct
 * integers in the set { 0, 1, ..., n-1 }. All subsets should be equally likely.
 * Time should be O(k), Space can be O(k).
 * 
 * @author yazhoucao
 * 
 */
public class Q18_Create_A_Random_Subset {

	static String methodName = "onlineSampling";
	static Class<?> c = Q18_Create_A_Random_Subset.class;

	public static void main(String[] args) {
		int n = 20;
		int k = 15;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			for (int i = 0; i < 20; i++)
				System.out.println(Arrays.toString(invokeMethod(m, n, k)));
			System.out.println("=================================");
		}
	}

	/**
	 * Time: O(k), Space: O(k).
	 */
	public static int[] onlineSampling_Improved(int n, int k) {
		Map<Integer, Integer> subset = new HashMap<>();
		Random ran = new Random();
		for (int i = 0; i < k; i++) {
			int r = ran.nextInt(n - i);
			Integer ptr1 = subset.get(r), ptr2 = subset.get(n - 1 - i);
			if (ptr1 == null && ptr2 == null) {
				subset.put(r, n - 1 - i);
				subset.put(n - 1 - i, r);
			} else if (ptr1 == null && ptr2 != null) {
				subset.put(r, ptr2);
				subset.put(n - 1 - i, r);
			} else if (ptr1 != null && ptr2 == null) {
				subset.put(n - 1 - i, ptr1);
				subset.put(r, n - 1 - i);
			} else {
				subset.put(n - 1 - i, ptr1);
				subset.put(r, ptr2);
			}
			// System.out.println("keys:"+subset.keySet().toString());
			// System.out.println("values:"+subset.values().toString());
		}

		int[] res = new int[k];
		for (int i = 0; i < k; i++)
			res[i] = subset.get(n - 1 - i);
		return res;
	}

	/**
	 * Time: O(n), Space: O(n) solution
	 */
	public static int[] onlineSampling(int n, int k) {
		assert k <= n;
		Random ran = new Random();
		int size = ran.nextInt(n);
		int[] subset = new int[size];
		int[] total = new int[n];
		for (int i = 0; i < n; i++)
			total[i] = i;

		for (int i = 0; i < size; i++) {
			int choosen = i + ran.nextInt(n - i);
			subset[i] = total[choosen];
			swap(total, i, choosen);
		}
		return subset;
	}

	private static void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}

	/****************** Unit Test ******************/

	public static int[] invokeMethod(Method m, int n, int k) {
		try {
			Object[] args = new Object[] { n, k };
			Object res = m.invoke(null, args);
			return (int[]) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return new int[0];
		}
	}

}
