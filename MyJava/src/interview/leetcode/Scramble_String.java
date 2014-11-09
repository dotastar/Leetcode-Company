package interview.leetcode;

/**
 * Given a string s1, we may represent it as a binary tree by partitioning it to
 * two non-empty substrings recursively.
 * 
 * Below is one possible representation of s1 = "great":
 * 
    great
   /    \
  gr    eat
 / \    /  \
g   r  e   at
           / \
          a   t
 * 
 * To scramble the string, we may choose any non-leaf node and swap its two children.
 * 
 * For example, if we choose the node "gr" and swap its two children, it produces a scrambled string "rgeat".
 * 
    rgeat
   /    \
  rg    eat
 / \    /  \
r   g  e   at
           / \
          a   t
 * We say that "rgeat" is a scrambled string of "great".
 * 
 * Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled string "rgtae".
 * 
    rgtae
   /    \
  rg    tae
 / \    /  \
r   g  ta  e
       / \
      t   a
 * We say that "rgtae" is a scrambled string of "great".
 * 
 * Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.
 * 
 * @author yazhoucao
 * 
 */
public class Scramble_String {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	/**
	 * Recursion
	 * If string s1 and s2 are scramble strings, there must be a point that breaks s1 to 
	 * two parts s11, s12, and a point that breaks s2 to two parts, s21, s22, and 
	 * isScramble(s11, s21) && isScramble(s12, s22) is true, or 
	 * isScramble(s11, s22) && isScramble(s12, s21) is true.
	 * 
	 * It's like: s1[..][....] & s2[..][....] or s1[..][....] & s2[....][..] 
	 * 
	 * So we can make it recursively. We just break s1 at different position to check if 
	 * there exists one position satisfies the requirement.
	 * 
	 * Some checks(pruning) are needed otherwise it will time out. 
	 * For example, if the lengths of two strings are different, they can’t be scramble. 
	 * And if the characters in two strings are different, they can’t be scramble either.
	 * 
	 * Time: O()
	 */
    public boolean isScramble(String s1, String s2) {
    	if(s1.length()!=s2.length())
    		return false;
    	if(s1.equals(s2))
    		return true;
    	
    	int L = s1.length();
    	int[] charCount = new int[26];
    	for(int i=0; i<L; i++){
    		charCount[s1.charAt(i)-'a']++;
    		charCount[s2.charAt(i)-'a']--;
    	}
    	for(int cnt : charCount)
    		if(cnt!=0)
    			return false;
    	
    	for(int i=1; i<L; i++){
    		String s11 = s1.substring(0, i);
    		String s12 = s1.substring(i, L);
    		String s21 = s2.substring(0, i);
    		String s22 = s2.substring(i, L);
    		
    		if(isScramble(s11, s21) && isScramble(s12, s22))
    			return true;
    		s21 = s2.substring(0, L-i);
    		s22 = s2.substring(L-i, L);
    		if(isScramble(s11, s22) && isScramble(s12, s21))
    			return true;
    	}
    	
        return false;
    }
    
    
    /**
     * DP, Time: O(n^4), Space: O(n^3)
     * Another way is to use DP. I use a three dimension array dp[i][j][n] : 
     * s1 start from i, s2 start from j, length is n, to save the states. 
     * We are trying to find scramble[0][0][L]. 
     * 
     * For every length k, we try to divide the string to two parts differently, 
     * checking if there is a way that can make it true.
     * 
     * dp[i][j][n] : s1 start from i, s2 start from j, length is n
     * 
     * dp[i][j][len] |= (dp[i][j][k] && dp[i+k][j+k][len-k]) 
     * 				|| (dp[i][j+len-k][k] && dp[i+k][j][len-k]);
     * 
     * It's like: s1[..][....] & s2[..][....] or s1[..][....] & s2[....][..] 
     */
    public boolean isScramble_DP(String s1, String s2){
    	if(s1.length()!=s2.length())
    		return false;
    	int n = s1.length();
    	boolean[][][] dp = new boolean[n][n][n+1];
    	for(int i=0; i<n; i++){
    		for(int j=0; j<n; j++){
    			//dp[i][j][0] = true; //no need to do it
    			dp[i][j][1] = s1.charAt(i)==s2.charAt(j);
    		}
    	}
    	
    	for(int len=2; len<=n; len++){
    		for(int i=0; i<n-len+1; i++){
        		for(int j=0; j<n-len+1; j++){
        			for(int k=1; k<len; k++){ //k: split point(index)
        				dp[i][j][len] |= (dp[i][j][k] && dp[i+k][j+k][len-k]) 
        						|| (dp[i][j+len-k][k] && dp[i+k][j][len-k]);	
        			}
        		}
        	}	
    	}

    	return dp[0][0][n];
    }
}
