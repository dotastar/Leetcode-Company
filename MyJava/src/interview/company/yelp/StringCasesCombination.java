package interview.company.yelp;

import interview.cc150.Tools;

/**
 * Write code to generate all possible case 
 * combinations of a given lower-cased string. 
 * (e.g."0ab" -> ["0ab", "0aB", "0Ab", "0AB"])
 * 
 * @author yazhoucao
 *
 */
public class StringCasesCombination {
	
	public static void main(String[] args){
		String s = "0ab3c5";
		char[] c = s.toCharArray();	
		recursion(c, 0);
		Tools.printSeparator(0);
		c = s.toCharArray();
	}
	
	
	
	
	
	/**
	 * Recursively traverse each letter, the path like a binary tree,
	 * one is upper case, the other is lower case.
	 * When reach leaf, then print it.
	 * 
	 * k = the number of letters
	 * there are totally 2^k combinations (or leaves)
	 * Time: O(2^k)
	 * Space: O(k)
	 * 
	 * Time is optimal, because you have to find all the combinations
	 * @param str
	 * @param index
	 */
	public static void recursion(char[] chs, int index){ 
		if(index>chs.length-1){	//only print leaf node
			System.out.println(new String(chs));
			return;
		}
			
		char ch = chs[index];
		recursion(chs, index+1);
		if(Character.isAlphabetic(ch)){	//only letter
			chs[index] = Character.toUpperCase(ch);
			recursion(chs, index+1);	//second recursion
			chs[index] = Character.toLowerCase(ch);
		}
	}

}







