package interview.leetcode;

/**
 * Implement wildcard pattern matching with support for '?' and '*'.
 * 
 * '?' Matches any single character. '*' Matches any sequence of characters
 * (including the empty sequence).
 * 
 * The matching should cover the entire input string (not partial).
 * 
 * The function prototype should be: bool isMatch(const char *s, const char *p)
 * 
 * Some examples:
 * 
 * isMatch("aa","a") → false
 * 
 * isMatch("aa","aa") → true
 * 
 * isMatch("aaa","aa") → false
 * 
 * isMatch("aa", "*") → true
 * 
 * isMatch("aa", "a*") → true
 * 
 * isMatch("ab", "?*") → true
 * 
 * isMatch("aab", "c*a*b") → false
 * 
 */
public class Wildcard_Matching {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		assert isMatch("aa", "a")==false;
		assert isMatch("aa", "*b")==false;
		assert isMatch("aa", "aaa")==false;
		assert isMatch("aa", "aa")==true;
		assert isMatch("aa", "*")==true;
		assert isMatch("aa", "*?")==true;
		assert isMatch("cab", "*ab")==true;

		assert isMatch_Improved("aa", "a")==false;
		assert isMatch_Improved("aa", "*b")==false;
		assert isMatch_Improved("aa", "aaa")==false;
		assert isMatch_Improved("aa", "aa")==true;
		assert isMatch_Improved("aa", "*")==true;
		assert isMatch_Improved("aa", "*?")==true;
		assert isMatch_Improved("cab", "*ab")==true;
		
		// big data set
		String s = "babbbbaabababaabbababaababaabbaabababbaaababbababaaaaaabbabaaaabababbabbababbbaaaababbbabbbbbbbbbbaabbb";
		String p = "b**bb**a**bba*b**a*bbb**aba***babbb*aa****aabb*bbb***a";
		long begin = System.currentTimeMillis();
		assert isMatch(s, p)==false;
		System.out.println("isMatch Running Time: "+ (System.currentTimeMillis() - begin));

		begin = System.currentTimeMillis();
		assert isMatch_Improved(s, p)==false;
		System.out.println("isMatch_Improved Running Time: "+ (System.currentTimeMillis() - begin));
			
	}	
	
	/**
	 * Backtracking (Recursion + Pruning), Time: Exponential
	 * 
	 * Second practice, avoid using String substring() method
	 */
    public static boolean isMatch(String s, String p) {
        return isMatch(s, p, 0, 0);    
    }
    
	public static boolean isMatch(String s, String p, int i, int j){
		int slen = s.length();
		int plen = p.length();

		while(i<slen){
			if(j==plen)
				return false;
			char si = s.charAt(i);
			char pj = p.charAt(j);
			if(si==pj||pj=='?')
				j++;
			else if(pj=='*'){
				//pruning, skip all continuous '*', here is a huge improve
				while(j+1<plen && p.charAt(j+1)=='*')
					j++; 
				if(isMatch(s, p, i, j+1))
					return true;
			}else
				return false;

			i++;
		}
		while(j<plen && p.charAt(j)=='*')
			j++;
		return j==plen;
	}
	
	
	
	/**
	 * Greedy, keep matching until it can't, then back to the previous '*', use
	 * it once and then start from there again
	 * 
	 * 贪心的策略，能匹配就一直往后遍历，匹配不上了就看看前面有没有'*'来救救场，再从'*'后面接着试。
	 */
	public static boolean isMatch_Improved(String s, String p) {
		int i = 0; // s pointer
		int j = 0; // p pointer
		int slen = s.length();
		int plen = p.length();
		int pre_s = 0, pre_p = 0;
		boolean hasStar = false;
		while (i < slen) {
			char si = s.charAt(i);
			char pj = j < plen ? p.charAt(j) : '\0';
			if (j < plen && (si == pj || pj == '?')) {
				i++;
				j++;
			} else if (j < plen && pj == '*') {
				hasStar = true;
				pre_s = i;
				pre_p = ++j;
			} else if (hasStar) { // if previous string include '*'
				pre_s++;
				i = pre_s; // back to pre_s+1, cause pre_s already tried,
							// won't work
				j = pre_p; // back to previous j which is the next of '*'
			} else
				return false;
		}

		while (j < p.length() && p.charAt(j) == '*') {
			j++;
		}
		return j == p.length();
	}
	
	/**
	 * Second time practice
	 */
	public static boolean isMatch_Greedy(String s, String p){
		int i=0;
		int j=0;
		int previ = 0;
		int prevj = 0;
		boolean hasWC = false; //has Wild Card
		int slen = s.length();
		int plen = p.length();
		while(i<slen){
			if(j==plen){
				if(hasWC){
					i = ++previ;
					j = prevj;
					continue;
				}else
					return false;
			}
			
			char si = s.charAt(i);
			char pj = p.charAt(j);
			if(pj=='*'){
				previ = i;
				prevj = j;
				j++;
				hasWC = true;
			}else if(si==pj || pj=='?'){
				i++;
				j++;
			}else if(hasWC){
				i = ++previ;
				j = prevj;
				j++;
			}else
				return false;
		}
		while(j<plen && p.charAt(j)=='*')
			j++;
		return j==plen;
	}
}
