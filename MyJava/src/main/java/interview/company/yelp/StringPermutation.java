package interview.company.yelp;

public class StringPermutation {

	/**
	 * Find all the permutation of a String
	 * @param args
	 */
	public static void main(String[] args) {
		String s1 = "abc";
		String s2 = "4321";
		permutation(s1);
		permutation(s2);
	}
	
	public static void permutation(String s){
		char[] ch = s.toCharArray();
		permutation(ch, 0);
		System.out.println("=====================================");
		practice1(ch, 0);
	}
	
	
	/**
	 * 7/10/2014
	 * @param s
	 * @param fixed
	 */
	public static void practice1(char[] s, int fixed){
		if(fixed>s.length-1){
			System.out.println(new String(s));
			return;
		}
			
		//do switch
		for(int i=fixed; i<s.length; i++){
			swap(s, fixed, i);
			permutation(s, fixed+1);
			swap(s, i, fixed);
		}
	}
	

	/**
	 * Recursion
	 * @param s
	 * @param fixed
	 */
	public static void permutation(char[] s, int fixed){
		if(fixed==s.length-1){
			System.out.println(new String(s));
			return;
		}
			
		for(int i=fixed; i<s.length; i++){
			swap(s, fixed, i);
			permutation(s, fixed+1);
			swap(s, i, fixed);
		}
	}
	
	private static void swap(char[] ch, int i, int j){
		char tmp = ch[i];
		ch[i] = ch[j];
		ch[j] = tmp;
	}
}
