package interview.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a roman numeral, convert it to an integer.
 * 
 * Input is guaranteed to be within the range from 1 to 3999.
 * 
 */
public class Roman_to_Integer {

	public static void main(String[] args) {
		System.out.println(-5/2);
		String num7 = "DCXXI";
		System.out.println(romanToInt4(num7));
		
		String num1 = "VIII"; // 8
		String num2 = "XCIX"; // 99
		String num3 = "XIV"; // 14
		String num4 = "MCDXXXVII"; // 1437
		String num5 = "MDCCCLXXX"; // 1880
		String num6 = "MMMCCCXXXIII"; // 3333

		System.out.println(romanToInt(num1));
		System.out.println(romanToInt(num2));
		System.out.println(romanToInt(num3));
		System.out.println(romanToInt(num4));
		System.out.println(romanToInt(num5));
		System.out.println(romanToInt(num6));
		
	}
	
	
	/**
	 * Solution 4
	 */
	private final static int[] Values = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
	private final static String[] Romans = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };
    
    public static int romanToInt4(String s) {
        int res = 0;
        for(int i=0, j=Romans.length-1; i<s.length() && j>=0; i++){
            char c = s.charAt(i);
            while(j>0){
                String roman = Romans[j];
                if(roman.charAt(0)==c){
                    if(roman.length()==1)
                        break;
                    if(i!=s.length()-1 && roman.length()==2 && roman.charAt(1)==s.charAt(i+1)){
                        i++;
                        break;       
                    }
                }
                j--;
            }
            res += Values[j];
        }
        return res;
    }
    
    /**
     * The same of solution 4
     */
    public static int romanToInt5(String s) {
    	int res = 0;
        for(int i=0, j=Values.length-1; i<s.length() && j>=0; ){
            char c = s.charAt(i);
            if(c==Romans[j].charAt(0)){
                if(Romans[j].length()==1 || (i!=s.length()-1 && Romans[j].charAt(1) == s.charAt(i+1))){
                    res += Values[j];
                    i = Romans[j].length()==1 ? i+1 : i+2;
                }else
                    j--;
            }else
                j--;
        }
        return res;
    }

	/**
	 * A more compact way to write it
	 * 
	 */
	public static int romanToInt2(String s) {
		// X IX X IV III
		char[] romans = { 'I', 'V', 'X', 'L', 'C', 'D', 'M' };
		int[] numerals = { 1, 5, 10, 50, 100, 500, 1000 };
		int len = s.length();
		int j = romans.length - 1;
		int res = 0;
		for (int i = 0; i < len; i++) {
			while (j >= 0 && s.charAt(i) != romans[j])
				j--;
			if (i == len - 1)	//the last char
				res += numerals[j];
			else {
				if (j < romans.length - 1 && s.charAt(i + 1) == romans[j + 1]) {
					res += numerals[j + 1] - numerals[j]; // could be IV, XL, CD
					i++;
				} else if (j < romans.length - 2
						&& s.charAt(i + 1) == romans[j + 2]) {
					res += numerals[j + 2] - numerals[j]; // could be IX, XC, CM
					i++;
				} else
					res += numerals[j];
			}
		}
		return res;
	}

	/**
	 * Third time
	 */
    private static final Map<String, Integer> map = getMap();
    
    public int romanToInt3(String s) {
        int res = 0;
        for(int i=0; i<s.length(); i++){
            String curr = Character.toString(s.charAt(i));
            int val = map.get(curr);
            if(i!=s.length()-1){
                String nextChar = Character.toString(s.charAt(i+1));
                int next = map.get(nextChar);
                if(val<next){
                    val = map.get(curr+nextChar);
                    i++;
                }
            }
            res += val;
        }
        return res;
    }
    
    private static Map<String, Integer> getMap(){
        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);
        return map;
    }
    
    /**
     * First solution
     */
	public static int romanToInt(String s) {
		char[] chs = s.toCharArray();
		int res = 0;

		for (int i = 0; i < chs.length; i++) {
			char c = chs[i];
			char cnext = ' ';
			if (i + 1 < chs.length)
				cnext = chs[i + 1];

			int add = 0;
			switch (c) {
			case 'I': // 1, minus
				if (cnext == 'V') {
					add = 4;
					i++;
				} else if (cnext == 'X') {
					add = 9;
					i++;
				} else
					add = 1;
				break;
			case 'V': // 5
				add = 5;
				break;
			case 'X': // 10, minus
				if (cnext == 'L') {
					add = 40;
					i++;
				} else if (cnext == 'C') {
					add = 90;
					i++;
				} else
					add = 10;
				break;
			case 'L': // 50
				add = 50;
				break;
			case 'C': // 100, minus
				if (cnext == 'D') {
					add = 400;
					i++;
				} else if (cnext == 'M') {
					add = 900;
					i++;
				} else
					add = 100;
				break;
			case 'D': // 500
				add = 500;
				break;
			case 'M': // 1000
				add = 1000;
				break;
			}
			res += add;
		}
		return res;
	}
}
