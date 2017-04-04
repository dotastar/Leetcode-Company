package interview.company.others;
/**
 * Find the most frequent char in String.
 * Given a string, design an algorithm in O(n) running time to find the
 * character that appears more than half of the time in the string. If the
 * character does not exist, output null.
 * 
 * @author yazhoucao
 * 
 */
public class Find_The_Most_Frequent_Char_In_String {
	
	public static void main(String[] args) {
		if(args.length>0)
			System.out.println(solution(args[0]));
		
//		System.out.println(solution("abadacababaaaa"));
//		System.out.println(solution("abcdeabbad"));
//		
//		System.out.println(solution("d"));
//		System.out.println(solution("!@#$*)(&^^%%%%%%%%%%%%%"));
//		System.out.println(solution("cbcbcb"));
	}

	/**
	 * Time: O(n), Space: O(1)
	 * Available for ASCII, not fit for Unicode.
	 */
	public static Character solution(String s) {
		int[] count = new int[128];
		int len = s.length();
		int max = 0;
		for(int i=0; i<len; i++){
			char c = s.charAt(i);
			count[c]++;
			max = count[c]>max ? count[c]:max;
			if(max>len/2)
				return c;
		}
		return null;
	}
}
