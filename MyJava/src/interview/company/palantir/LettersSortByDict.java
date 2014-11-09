package interview.company.palantir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Sort the letters in one word by the order they occur in another in linear time.
 * Sort the input character array based on the dictionary given. 
 * For eg:, If input word is ��SHEEP��, sorting will make it as ��EEHPS��.
 * But in the dictionary, E may not be at first. As per the dictionary, if P is first, S followed and E later and finally H. 
 * Then sorted array is ��PSEEH��.
 * 
 * @author Asia
 *
 */
public class LettersSortByDict {

	public static void main(String[] args){

		char[] arr = "tabcdefg".toCharArray();
		
		System.out.println(sortLettersByDict("bbatdfsegcst", arr));
		
		List<Character> dict = new ArrayList<Character>();
		for(char ch : arr)
			dict.add(new Character(ch));
		
		System.out.println(sortLettersByDict("bbatdfsegcst", dict));
	}
	
	/*
	 * Version 1, use LinkedHashMap to maintain order of dict,
	 * and store the count of each char letter of input String as value,
	 * output the result by the LinkedHashMap order
	 */
	public static String sortLettersByDict(String letters, char[] dict){
		Map<Character, Integer> letterMap = new LinkedHashMap<Character, Integer>(dict.length);
		
		for(int i=0; i<dict.length; i++)
			letterMap.put(new Character(dict[i]), 0);
		
		
		for(char letter : letters.toCharArray()){
			Integer count = letterMap.get(letter);
			if(count!=null)
				letterMap.put(letter, ++count);
		}
		
		
		StringBuilder sb = new StringBuilder();
		for(Entry<Character, Integer> entry : letterMap.entrySet()){
			Integer count = entry.getValue();
			if(count!=0)
				for(int i=0; i<count; i++)
					sb.append(entry.getKey());
		}
		
		return sb.toString();
	}
	
	
	/*
	 * Version 2, use original char[] dict to maintain order,
	 * and store the Character as value, for character doesn't appear
	 * in input String s, it will use "" as a value to store in letterMap,
	 * output the result by looking up each character in letterMap 
	 * by the order of dict
	 */
	public static String sortLettersByDict(String s, List<Character> dict){
		String output = "";
		HashMap<Character, String> letterMap = new HashMap<Character, String>();
		for (Character c : dict)
			letterMap.put(c,"");
		for (int i = 0; i < s.length(); ++i){
			letterMap.put(s.charAt(i),letterMap.get(s.charAt(i))+s.charAt(i));
			System.out.println(letterMap.get(s.charAt(i))+s.charAt(i));
		}
			
		for (Character c : dict)
			output += letterMap.get(c);
		return output;
	}
}
