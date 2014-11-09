package interview.company.yelp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AnagramSort {
	
	public static void main(String[] args){
		String[] words = {"god","testx","testy","testz","dog","dogd","cat","act","gdo","odg","responsibilityresponsibility"};

		System.out.println(test(words));
		System.out.println(solution1(words));
		System.out.println(solution2(words));
	}
	
	
	public static List<String> test(String[] words){
		List<String> anagrams = new ArrayList<String>();
		Map<String, Integer> firstIndex = new HashMap<String, Integer>();
		for(int i=0; i<words.length; i++){
			String word = words[i];
			char[] chs = word.toCharArray();
			Arrays.sort(chs);;
			String sorted = new String(chs);
			Integer idx = firstIndex.get(sorted);
			if(idx==null){
				idx = i;
				firstIndex.put(sorted, idx);
			}else{
				if(idx>=0){	//haven't add first word yet
					String first = words[idx];
					anagrams.add(first);
					firstIndex.put(sorted, -1);
				}
				//add current word
				anagrams.add(word);
			}	
		}
		
		return anagrams;
	}
	
	
	/**
	 * Use a prime number to represent a letter, use the unique product
	 * of all letters of a word to represent the anagram of the word.
	 * There is a table consists of 26 prime numbers for 26 letters  
	 * Then group them together by the unique product, using a HashMap, 
	 * key as product, value as a word list. 
	 * 
	 * O(n) Time
	 * O(n)	Space
	 * n is the number of words
	 * 
	 * Disadvantages:
	 * 1.only feasible in all are lower case letters and no other characters like !@#$%
	 * 2.if it is a long word, the Id(long) which represent a word could be overflow.
	 * @param words
	 * @return
	 */
	public static List<String> solution1(String[] words){
		List<String> anagrams = new ArrayList<String>();
		int[] primes = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
		int len = words.length;

		//group anagrams
		Map<Long, List<String>> anagramGroups = new HashMap<Long, List<String>>();
		for(int i=0; i<len; i++){
			String word = words[i];
			long wordId = 1;
			char[] chs = word.toCharArray();
			for(char ch : chs){
				wordId *= primes[ch-'a'];
			}
			
			List<String> group = anagramGroups.get(wordId);
			if(group==null){
				group = new ArrayList<String>();
				anagramGroups.put(wordId, group);
			}
			
			group.add(word);
		}

		for(Entry<Long, List<String>> entry : anagramGroups.entrySet()){
			List<String> group = entry.getValue();
			if(group.size()>1){
				for(String word : group)
					anagrams.add(word);
			}
		}
		
		return anagrams;
	}
	
	
	/**
	 * Store the sorted version of each word in a HashMap,
	 * use the index of the word as value.
	 * 
	 * Traverse all words, sort each word when comparing,
	 * if contains in HashMap, then it is an anagram.
	 * 
	 *  O(n*klog(k)) Time
	 *  O(n) Space
	 *  n is the number of words, k is the average length of a word
	 * @param words
	 * @return
	 */
	public static List<String> solution2(String[] words){
		List<String> anagrams = new ArrayList<String>();
		if(words.length==0)
			return anagrams;
		
		//Word as key, The first word's Index as value
		Map<String, Integer> idxMap = new HashMap<String, Integer>();
		for(int i=0; i<words.length; i++){
			String word = words[i];
			char[] wch = word.toCharArray();
			Arrays.sort(wch);
			String sorted = new String(wch);
			if(idxMap.containsKey(sorted)){
				int index = idxMap.get(sorted);
				if(index!=-1){	//already added to result
					anagrams.add(words[index]);
					idxMap.put(sorted, -1);
				}
				anagrams.add(word);
			}else
				idxMap.put(sorted, i);
		}
		
		return anagrams;
	}
}
