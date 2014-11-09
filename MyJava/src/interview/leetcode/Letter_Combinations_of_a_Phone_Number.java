package interview.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a digit string, return all possible letter combinations that the number
 * could represent.
 * 
 * A mapping of digit to letters (just like on the telephone buttons) is given
 * below.
 * 
 * Input:Digit string "23"
 * 
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 * 
 * Note: Although the above answer is in lexicographical order, your answer
 * could be in any order you want.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Letter_Combinations_of_a_Phone_Number {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Combination of different char list 
	 */
	public List<String> letterCombinations(String digits) {
		String[] map = new String[] { "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
		List<String> res = new ArrayList<String>();
		combination(map, 0, new char[digits.length()], digits, res);
		return res;
	}
	
	public void combination(String[] map, int curr, char[] comb, String digits, List<String> res){
		if(curr==digits.length()){
			res.add(new String(comb));
			return;
		}

		int idx = digits.charAt(curr)-'1';
		if(idx<0 || idx>=map.length)
			return;
		String letters = map[idx];
		for(int i=0; i<letters.length(); i++){
			comb[curr] = letters.charAt(i);
			combination(map, curr+1, comb, digits, res);
		}
	}
	
	/**
	 * Same solution, second time practice
	 */
    public List<String> letterCombinations2(String digits) {
        Map<Character, String> map = new HashMap<Character, String>();
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");
        
        List<String> res = new ArrayList<String>();
        char[] comb = new char[digits.length()];
        combine(digits, 0, comb, res, map);
        return res;
    }
    
    public void combine(String digits, int start, char[] comb, List<String> res, Map<Character, String> map){
        if(start==digits.length()){
            res.add(new String(comb));
            return;
        }
        char digit = digits.charAt(start);
        String letters = map.get(digit);
        if(letters!=null){
            for(int i=0; i<letters.length(); i++){
                comb[start] = letters.charAt(i);
                combine(digits, start+1, comb, res, map);
            }   
        }
    }
}
