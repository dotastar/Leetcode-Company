package interview.leetcode;

import java.io.UnsupportedEncodingException;


/**
 * Given two binary strings, return their sum (also a binary string).
 * 
 * For example, a = "11" b = "1" Return "100".
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Add_Binary {
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

	}

	
	/**
	 * This new solution is more readable
	 * 
	 */
    public String addBinary2(String a, String b) {
        char[] chsA = a.toCharArray();
        char[] chsB = b.toCharArray();
        int ia = chsA.length-1;
        int ib = chsB.length-1;
        
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        while(ia>=0 && ib>=0){  //add a+b
            int sum = (chsA[ia] - '0') + (chsB[ib] - '0') + carry;
            sb.append(sum%2);
            carry = sum/2;
            ia--;
            ib--;
        }
        
        while(ia>=0){	//add rest a
            int sum = (chsA[ia]-'0') + carry;
            sb.append(sum%2);
            carry = sum/2;
            ia--;
        }
        while(ib>=0){	//add rest b
            int sum = (chsB[ib]-'0') + carry;
            sb.append(sum%2);
            carry = sum/2;
            ib--;
        }
        if(carry>0)
            sb.append(carry);
            
        return sb.reverse().toString();
    }
    

	public static String addBinary(String a, String b) {
		int alen = a.length();
		int blen = b.length();
		int minLen = alen;
		int maxLen = blen;
		if(alen>blen){
			maxLen = alen;
			minLen = blen;
		}
		StringBuilder sb = new StringBuilder();
		int carry = 0;
		for (int i = 0; i < minLen; i++) {	//add the common part of a, b
			int aint = a.charAt(alen - 1 - i) - '0';
			int bint = b.charAt(blen - 1 - i) - '0';
			int res = aint + bint + carry;
			if (res >= 2) {
				res %= 2;
				carry = 1;
			} else
				carry = 0;
			sb.append(Integer.toString(res));
		}
		
		String rest = alen>blen?a:b;
		for(int i=minLen; i<maxLen; i++){	//add the rest string
			int restInt = rest.charAt(maxLen-1-i) - '0';
			int res = restInt + carry;
			if(res>=2){
				res %= 2;
				carry = 1;
			}else
				carry = 0;
			sb.append(Integer.toString(res));
		}
		if(carry==1)
			sb.append("1");
		return sb.reverse().toString();
	}
}
