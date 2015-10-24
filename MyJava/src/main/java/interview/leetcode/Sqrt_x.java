package interview.leetcode;

/**
 * Implement int sqrt(int x).
 * 
 * Compute and return the square root of x.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Sqrt_x {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.print(sqrt(1) + "\t");
		System.out.println(sqrt2(1));

		System.out.print(sqrt(2) + "\t");
		System.out.println(sqrt2(2));

		System.out.print(sqrt(100) + "\t");
		System.out.println(sqrt2(100));

		System.out.print(sqrt(Integer.MAX_VALUE) + "\t");
		System.out.println(sqrt2(Integer.MAX_VALUE));
	}

	/**
	 * Binary search, sqrt(x) must < (x/2) + 1
	 * 
	 * when x = Integer.MAX_VALUE, mid is very large, mid*mid could overflow
	 * 
	 * convert int to long to avoid overflow problem
	 * 
	 */
	public static int sqrt(int x) {
		long l = 0;
		long r = x / 2 + 1;
		while (l <= r) {
			long mid = (l + r) / 2;
			long sq = mid * mid;
			if (sq == x)
				return (int) mid;
			else if (sq < x)
				l = mid + 1;
			else
				r = mid - 1;
		}
		return (int) r; // for the case x = 1
	}

	/**
	 * Solution without convert int to long, still B-Search
	 * 
	 * use divisor = x / mid to avoid overflow
	 * 
	 */
	public static int sqrt2(int x) {
		if (x <= 1)
			return x;
		int l = 1;
		int r = x / 2 + 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			int divisor = x/mid;	
			if (divisor==mid)
				return mid;
			else if (divisor > mid)
				l = mid + 1;
			else
				r = mid - 1;
		}
		return r;
	}
}
