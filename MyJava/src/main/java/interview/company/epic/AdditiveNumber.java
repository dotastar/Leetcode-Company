package interview.company.epic;

/**
 * Additive numbers are defined to be a positive integer whose digits form an
 * additive sequence. E.g. 11235 (1+1=2, 1+2=3, 2+3=5). What makes it difficult
 * is that 12,122,436 is also one (12+12=24, 12+24=36).
 * 
 * Given a range of integers, find all the additive numbers in that range..
 * 
 * @author yazhoucao
 * 
 */
public class AdditiveNumber {

	public static void main(String[] args) {
		AdditiveNumber o = new AdditiveNumber();
		o.additive1(0, 11235813);
	}

	/**
	 * Second time
	 */
	public void additive1(int start, int end) {
		int[] digits = new int[21];
		int maxi = end / (int) Math.pow(10, 2 * numOfDigits(end) / 3);
		for (int i = 1; i <= maxi; i++) {
			digits[0] = i;
			digits[1] = i;
			int idx = 2;
			int num = i * (int) Math.pow(10, numOfDigits(i)) + i;
			do {
				digits[idx] = digits[idx - 1] + digits[idx - 2];
				num = num * (int) Math.pow(10, numOfDigits(digits[idx])) + digits[idx];
				idx++;
				if (num >= start && num <= end)
					System.out.println(num);
			} while (num <= end);
		}
	}

	/**
	 * Idea:
	 * Any sequence {T(1),T(2),...T(n)} which satisfies T(n)=T(n-1)+T(n-2) is
	 * uniquely determined by n, T(1) and T(2). Thus we can generate any
	 * sequence in the range starting with the combination of any pair of T(1)
	 * and T(2). Here are two tricks. 1) the digit numbers of T(1) and T(2)
	 * cannot be larger than half of that of max range. 2) the digit numbers of
	 * sequence {T(1), T(2),...T(n)} must be in between those of min and max
	 * ranges.
	 */
	public void additive(int start, int end) {
		int[] digits = new int[20];
		int base = (int) Math.pow(10, 2 * numOfDigits(end) / 3);
		int maxi = end / base;
		for (int i = 1; i <= maxi; i++) {
			digits[0] = i;
			digits[1] = i;
			int num = digits[0] * (int) Math.pow(10, numOfDigits(digits[1]))
					+ digits[1];
			int idx = 2;
			while (num <= end) {
				digits[idx] = digits[idx - 1] + digits[idx - 2];
				num = num * (int) Math.pow(10, numOfDigits(digits[idx]))
						+ digits[idx++];
				if (num >= start && num <= end)
					System.out.println(num);
			}
		}
	}

	private int numOfDigits(int n) {
		int cnt = 0;
		while (n != 0) {
			n /= 10;
			cnt++;
		}
		return cnt;
	}
}
