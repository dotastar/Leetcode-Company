package interview.leetcode;

/**
 * A message containing letters from A-Z is being encoded to numbers using the
 * following mapping:
 * 
 * 'A' -> 1 'B' -> 2 ... 'Z' -> 26
 * 
 * Given an encoded message containing digits, determine the total number of
 * ways to decode it.
 * 
 * For example, Given encoded message "12", it could be decoded as "AB" (1 2) or
 * "L" (12).
 * 
 * The number of ways decoding "12" is 2.
 * 
 * @author yazhoucao
 * 
 */
public class Decode_Ways {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(numDecodings("")); // 0
		System.out.println(numDecodings("8")); // 1
		System.out.println(numDecodings("12")); // 2
		System.out.println(numDecodings("112233")); // 8
		System.out.println(numDecodings("110")); // [1,2,1] -> 1
		System.out.println(numDecodings("101")); // [1,1,1] -> 1
		System.out.println(numDecodings("230")); // [1,2,0] -> 0
		System.out.println(numDecodings("1010")); // [1,1,1,1,1] -> 1
		System.out.println(numDecodings("10000")); // [1,1,1,0,0,0,0,0,0] -> 0
	}

	/**
	 * DP
	 * 
	 * dp[i] : number of decoding ways of string 0 to i-1 (length i)
	 * 
	 * there are several restrictions:
	 * 
	 * 1.if last and curr are both 0, then it is not a valid encode, return 0.
	 * 
	 * 2.if last is 0, and current is not 0, then dp[i] = dp[i-1] (just append
	 * str[i] to all old combinations)
	 * 
	 * 3.if curr==0 and last is not 0, it must be combined with last, then check
	 * the sum whether is valid(<=26) if yes then dp[i] = dp[i-2]
	 * 
	 */
	public static int numDecodings(String s) {
		if (s.length() == 0 || s.charAt(0) == '0')
			return 0;
		int dp[] = new int[s.length() + 1];
		dp[0] = 1;
		dp[1] = 1;
		int last = s.charAt(0) - '0'; // convert to int
		for (int i = 2; i <= s.length(); i++) {
			int curr = s.charAt(i - 1) - '0';
			if (last == 0 && curr == 0)
				return 0;
			else if (last == 0)
				dp[i] = dp[i - 1];
			else if (curr == 0) {
				if (last > 2)
					return 0;
				dp[i] = dp[i - 2];
			} else {
				int sum = last * 10 + curr;
				dp[i] = sum <= 26 ? dp[i - 1] + dp[i - 2] : dp[i - 1];
			}

			last = curr;
		}
		return dp[dp.length - 1];
	}

	/**
	 * DP, improved in space
	 * 
	 * Time: O(n), Space: O(1)
	 * 
	 */
	public int numDecodings_Improved(String s) {
		int len = s.length();
		if (len ==0 || s.charAt(0)=='0')
			return 0;
		int n1 = 1;
		int n2 = 1;
		int prev = s.charAt(0) - '0';
		for (int i = 2; i <= len; i++) {
			int tmp = 0;
			int curr = s.charAt(i - 1) - '0';
			if (curr == 0 && (prev == 0 || prev > 2)) // 00, 30, 40...90
				return 0;
			else if (curr == 0) // 10, 20
				tmp = n1;
			else if (prev == 1 || (prev == 2 && curr < 7)) // 11~19,21~26
				tmp = n1 + n2;
			else
				// 01~09, 27~99
				tmp = n2;
			n1 = n2;
			n2 = tmp;
			prev = curr;
		}
		return n2;
	}
}
