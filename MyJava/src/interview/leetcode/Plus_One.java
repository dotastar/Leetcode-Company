package interview.leetcode;

/**
 * Given a non-negative number represented as an array of digits, plus one to
 * the number.
 * 
 * The digits are stored such that the most significant digit is at the head of
 * the list.
 * 
 * @author yazhoucao
 * 
 */
public class Plus_One {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int[] plusOne(int[] digits) {
		for(int i=digits.length-1; i>=0; i--){
			if(digits[i]+1>9){	//if loop continue, there must be a carry = 1
				digits[i] = 0;
			}else{	//otherwise, add 1 and return 
				digits[i]++;
				return digits;
			}
		}//for end, it must be overflowed
		
		int[] num_new = new int[digits.length+1];
		num_new[0] = 1;
		return num_new;
	}
	
}
