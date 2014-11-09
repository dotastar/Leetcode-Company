package interview.leetcode;

/**
 * Given two numbers represented as strings, return multiplication of the
 * numbers as a string.
 * 
 * Note: The numbers can be arbitrarily large and are non-negative.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Multiply_Strings {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(multiply("1", "1"));
		System.out.println(multiply("999", "2")); // 1998
		System.out.println(multiply("123", "456")); // 56088
	}

	/**
	 * Time: O(len1*len2)
	 * 
	 * Space: O(n) = n, one n for int array, n = len1 + len2
	 */
	public static String multiply(String num1, String num2) {
		String str1 = new StringBuilder(num1).reverse().toString();
		String str2 = new StringBuilder(num2).reverse().toString();
		int[] res = new int[str1.length() + str2.length()];
		for (int i = 0; i < str1.length(); i++) {
			int digit1 = str1.charAt(i) - '0';
			for (int j = 0; j < str2.length(); j++) {
				int digit2 = str2.charAt(j) - '0';
				res[i + j] += digit1 * digit2; // i+j is the key
				res[i + j + 1] += res[i + j] / 10; // carry
				res[i + j] %= 10;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = res.length - 1; i >= 0; i--)
			sb.append(res[i]); // to String
		while (sb.charAt(0) == '0' && sb.length() != 1)
			sb.deleteCharAt(0); // delete '0's at high-order

		return sb.toString();
	}
	
	
	/**
	 * Same solution, just do not reverse input at the beginning
	 * 
	 */
    public String multiply2(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        
        int[] bigNum = new int[len1+len2];
        for(int i=len1-1; i>=0; i--){
            for(int j=len2-1; j>=0; j--){
                int pos = len1-1-i+len2-1-j;
                bigNum[pos] += (num1.charAt(i)-'0')*(num2.charAt(j)-'0');
                bigNum[pos+1] += bigNum[pos]/10;    //carry for next
                bigNum[pos] %= 10;
            }
        }
        
        boolean isRealNum = false;
        StringBuilder sb = new StringBuilder();
        for(int i=len1+len2-1; i>=0; i--){
            if(i!=0 && !isRealNum && bigNum[i]==0)
                continue;
            else
                isRealNum = true;
            sb.append(bigNum[i]);
        }
        return sb.toString();
    }
}
