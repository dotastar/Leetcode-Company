package interview.leetcode;

/**
 * Reverse digits of an integer.
 * 
 * Example1: x = 123, return 321 Example2: x = -123, return -321
 * 
 * 
 * click to show spoilers.
 * 
 * Have you thought about this? Here are some good questions to ask before
 * coding. Bonus points for you if you have already thought through this!
 * 
 * If the integer's last digit is 0, what should the output be? ie, cases such
 * as 10, 100.
 * 
 * Did you notice that the reversed integer might overflow? Assume the input is
 * a 32-bit integer, then the reverse of 1000000003 overflows. How should you
 * handle such cases?
 * 
 * Throw an exception? Good, but what if throwing an exception is not an option?
 * You would then have to re-design the function (ie, add an extra parameter).
 * 
 * @author yazhoucao
 * 
 */
public class Reverse_Integer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

    public int reverse(int x) {
        boolean neg = false;
    	if(x<0){
    		neg = true;
    		x = 0 - x;
    	}
    	
    	int res = 0;
    	while(x>0){
    		int mod = x%10;
    		x = x/10;
    		res = res*10 + mod;
    	}
    	
    	if(neg)
    		res = 0 - res;
    	
    	return res;
    }
}
