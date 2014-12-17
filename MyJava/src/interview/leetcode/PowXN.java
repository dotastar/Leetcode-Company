package interview.leetcode;

/**
 * Implement pow(x, n).
 * 
 * @author yazhoucao
 * 
 */
public class PowXN {

	public static void main(String[] args) {
		double x1 = 8.88023d;
		int n1 = 3;
		double res1 = 700.28148d;
		double calcRes1 = Math.floor(pow(x1, n1) * 100000) / 100000;
		assert calcRes1 == res1 : calcRes1;

		double x2 = 34.00515;
		int n2 = -3;
		double res2 = Math.pow(x2, n2);
		double calcRes2 = pow(x2, n2);
		assert calcRes2 == res2 : calcRes2;

		double x3 = 4.70975;
		int n3 = -6;
		double res3 = 0.00009;
		double calcRes3 = Math.floor(pow(x3, n3) * 100000) / 100000;
		assert calcRes3 == res3 : calcRes3;

	}

	/**
	 * Naive solution
	 */
	public static double pow_naive(double x, int n) {
		double res = x;
		for (int i = 0; i < n; i++)
			res *= x;
		return res;
	}

	/**
	 * Several problems to notice:
	 * 1.How to deal with the case when n is negative, the base case of
	 * recursion n==0 will avoid such problem, because 0 don't have any sign, if
	 * the base case is (terminated at) n==1 then it has the problem of deciding
	 * whether it is positive or negative. At the same time, the condition
	 * n%2==0 is also important to this problem, if change the condition to
	 * n%2==1, then it also has the same problem of missing the negative
	 * condition.
	 * 
	 * 2.The value could be overflow, e.g. n = -2147483648, and Math.abs(n)
	 * won't work. The above solution can avoid it.
	 * 
	 * 3.Where to write the logic to deal with odd number n, notice n can be odd
	 * number like 3, or it can be an even number like 6 and 6/2 is an odd
	 * number 3. It has to be written in the recursive helper function
	 * 'power()',
	 * the logic can not be put in the 'pow()' function because it will miss the
	 * second case like n=6 when written in pow(), and it is could have more
	 * than one time of calculation of 'half*half*x', e.g. n=27.
	 * 
	 * Divide and conquer
	 */
	public static double pow(double x, int n) {
		if (n < 0)
			return 1 / power(x, n);
		else
			return power(x, n);
	}

	public static double power(double x, int n) {
		if (n == 0) // important base case
			return 1;
		double half = power(x, n / 2);

		if (n % 2 == 0) // important, it has to be 0
			return half * half;
		else
			return half * half * x;
	}

}
