package interview.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * You are given a string, S, and a list of words, L, that are all of the same
 * length. Find all starting indices of substring(s) in S that is a
 * concatenation of each word in L exactly once and without any intervening
 * characters.
 * 
 * For example, given: S: "barfoothefoobarman" L: ["foo", "bar"]
 * 
 * You should return the indices: [0,9]. (order does not matter).
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Substring_with_Concatenation_of_All_Words {

	public static void main(String[] args) {

		String S6 = "abababab";
		String[] L6 = new String[] { "a", "b", "a" };
		System.out.println(findSubstring2(S6, L6).toString());// [0, 2, 4]	
		
		String S4 = "aaa";
		String[] L4 = new String[] { "a", "a" };
		System.out.println(findSubstring2(S4, L4).toString());// [0, 1]
		
		String S5 = "aaa";
		String[] L5 = new String[] { "a", "b" };
		System.out.println(findSubstring2(S5, L5).toString());// []
		
		String S = "barfoothefoobarman";
		String[] L = new String[] { "foo", "bar" };
		System.out.println(findSubstring2(S, L).toString()); // [0,9]

		String S0 = "a";
		String[] L0 = new String[] { "a" };
		System.out.println(findSubstring2(S0, L0).toString());// [0]

		String S1 = "aaa";
		String[] L1 = { "aa", "aa" };
		System.out.println(findSubstring2(S1, L1).toString()); // []

		String S2 = "lingmindraboofooowingdingbarrwingmonkeypoundcake";
		String[] L2 = { "fooo", "barr", "wing", "ding", "wing" };
		System.out.println(findSubstring2(S2, L2).toString()); // [13], fooo


		String S3 = "abaababbaba";
		String[] L3 = { "ab", "ba", "ab", "ba" };
		System.out.println(findSubstring2(S3, L3).toString()); // [1,3]

	}
	
    public static List<Integer> findSubstring2(String S, String[] L) {
        List<Integer> res = new ArrayList<Integer>();
        if(L.length==0)
            return res;
        Map<String, Integer> dict = new HashMap<String, Integer>();
        for(String s : L){
        	if(dict.containsKey(s))
        		dict.put(s, dict.get(s)+1);
        	else
        		dict.put(s,1);
        }

        int unitLen = L[0].length();
        int endL = S.length()-L.length*unitLen;
        int endR = S.length()-unitLen;
        Map<String, Integer> currDict = new HashMap<String, Integer>();
        for(int k=0; k<unitLen; k++){	//this is important
        	int total = L.length;
        	currDict.clear();
        	for(int l=k, r=k; r<=endR; r+=unitLen){
            	String curr = S.substring(r,r+unitLen);
            	if(dict.containsKey(curr)){
            		if(currDict.containsKey(curr))
            			currDict.put(curr, currDict.get(curr)+1);
            		else
            			currDict.put(curr, 1);
            		
            		if(currDict.get(curr)>dict.get(curr)){
            			while(l<=endL){
            				String leftmost = S.substring(l, l+unitLen);
            				currDict.put(leftmost, currDict.get(leftmost)-1);
            				l += unitLen;
            				if(leftmost.equals(curr))
            					break;
            				total++;
            			}
            		}else
            			total--;
            		
            		if(total==0)
            			res.add(l);
            	}else{
            		total = L.length;
            		currDict.clear();
            		l = r+unitLen;
            	}
            }
        }
        return res;
    }


    /**
     * Sliding window, move left/right one wordLength at a time.
     * Time: O(n*m), n = S.length(), m = L[0].length() (wordLength).
     */
	public static List<Integer> findSubstring(String S, String[] L) {
		int slen = S.length();
		int llen = L.length;
		List<Integer> res = new ArrayList<Integer>();
		if (slen == 0 || llen == 0)
			return res;

		Map<String, Integer> wordCnts = new HashMap<String, Integer>();
		for (String l : L) {
			if (wordCnts.containsKey(l))
				wordCnts.put(l, wordCnts.get(l) + 1);
			else
				wordCnts.put(l, 1);
		}

		int wordLen = L[0].length();

		Map<String, Integer> currWordCnts = new HashMap<String, Integer>();
		// try different window combination (start with different index)
		for (int i = 0; i < wordLen; i++) {
			int left = i;
			int cnt = 0;
			for (int right = i; right <= slen - wordLen; right += wordLen) {
				String word = S.substring(right, right + wordLen);
				if (wordCnts.containsKey(word)) { // in the L
					// update current word count map
					if(currWordCnts.containsKey(word))
						currWordCnts.put(word, currWordCnts.get(word)+1);
					else
						currWordCnts.put(word, 1);

					// check if the count of current word exceeded in L
					if (currWordCnts.get(word) > wordCnts.get(word)) {
						// if exceeded, then l advance right until it is equal
						while (left <= slen - wordLen) {
							String leftmost = S.substring(left, left + wordLen);
							currWordCnts.put(leftmost,
									currWordCnts.get(leftmost) - 1);
							left += wordLen;
							if (leftmost.equals(word))
								break;
							cnt--;
						}
					} else
						cnt++; // not exceeded, r keep right one wordLen

					if (cnt == llen) // satisfied the requirement
						res.add(left);

				} else { // the word is not in the L, advance both l and r
					left = right + wordLen;
					cnt = 0;
					currWordCnts.clear();
				}
			}
			currWordCnts.clear();
		}
		return res;
	}

}
