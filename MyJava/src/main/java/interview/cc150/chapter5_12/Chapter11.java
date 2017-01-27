package interview.cc150.chapter5_12;

import interview.cc150.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Sorting and Searching
 * 
 * @author yazhoucao
 * 
 */
public class Chapter11 {

	public static void main(String[] args) {
		Tools.runAllQuestions(Chapter11.class, 3);
	}

	/********************************** 11.1 **********************************/
	/**
	 * You are given two sorted arrays, A and B, where A has a large enough
	 * buffer at the end to hold B. Write a method to merge B into A in sorted
	 * order.
	 */
	public static void q1() {
		int[] a = { 1, 3, 5, 7, 9, 0, 0, 0, 0, 0, 0, 0 };
		int[] b = { 2, 4, 6, 8, 10, 11, 15 };
		int lenA = 5;
		merge(a, b, lenA);
	}

	/**
	 * Suppose a's buffer size is exact b's size. Time: len(a)+len(b)
	 */
	public static void merge(int[] a, int[] b, int lenA) {
		// move buffer to the front
		int end = a.length - 1;
		for (int i = lenA - 1; i >= 0; i--)
			a[end--] = a[i];
		// write 0
		int bufLen = a.length - lenA;
		for (int i = 0; i < bufLen; i++)
			a[i] = 0;
		// end moving

		System.out.println(Arrays.toString(a));
		int idxA = bufLen;
		int idxB = 0;
		for (int i = 0; i < a.length; i++) {
			// either one of the array finished
			if (idxB >= b.length) // the rest is sorted a
				break;
			else if (idxA >= a.length) { // a finished
				// append the rest of b to a
				for (int j = i; j < a.length; j++)
					a[j] = b[idxB++];
				break;
			}

			// merging
			if (a[idxA] < b[idxB])
				a[i] = a[idxA++];
			else
				a[i] = b[idxB++];
		}
		System.out.println(Arrays.toString(a));
	}


	/********************************** 11.2 **********************************/
	/**
	 * Write a method to sort an array of strings so that all the anagrams are
	 * next to each other.
	 */
	public static void q2() {
		String[] strs = {"cat","god","tip","dog","act","odg","good","apple","doog"};
		List<Collection<String>> groups = groupAnagrams(strs);
		for(Collection<String> grp : groups)
			System.out.println(grp.toString());
	}
	
	/**
	 * Several ways to represent an anagram:
	 * 1.if only letters and is not case sensitive:  
	 * use prime numbers multiplication to stand for a word, the same anagrams have
	 * the same value.
	 * 
	 * 2.general case: 
	 * 	1).sort each word to represent it, easy to implement
	 * 	2).if all words are averagely very long, like a sentence, we can use 'd1g1o2', 
	 * an integer array(256, the size of extended ASK II) to represent an anagram. 
	 * It is faster when String is very long.
	 * 
	 * once find the way to represent an anagram, then group them use same method.
	 * 
	 * 
	 * Below method implement the first one of general case.
	 * Time: nk (k is the average length of a word)
	 */
	public static List<Collection<String>> groupAnagrams(String[] strs){
		if(strs==null) return null;
		
		List<Collection<String>> anagroups = new ArrayList<Collection<String>>();
		Map<String, List<String>> anaMap = new HashMap<String, List<String>>();
		for(String word : strs){
			char[] chs = word.toCharArray();
			Arrays.sort(chs);
			String anagram = new String(chs);
			if(anaMap.containsKey(anagram)){
				anaMap.get(anagram).add(word);
			}else{
				List<String> newGroup = new ArrayList<String>();
				newGroup.add(word);
				anaMap.put(anagram, newGroup);
			}
		}
		
		for(Entry<String, List<String>> entry : anaMap.entrySet()){
			anagroups.add(entry.getValue());
		}
		return anagroups;
	}

	
	
	/********************************** 11.3 **********************************/
	/**
	 * 11.3 
	 * Given a sorted array of n integers that has been rotated an unknown number 
	 * of times, write code to find an element in the array. You may assume that 
	 * the array was originally sorted in increasing order.
	 */
	public static void q3() {
		int[] a = {4,5,6,7,9,-8,-2,1,3};
		System.out.println("Search: "+search(a, 4));
	}
	
	
	/**
	 * Find the peak point, and then do a normal binary search in the right half.
	 * Time: 2*log(n)
	 */
	public static int search(int[] a, int x){
		int n = a.length;
		int left = a[0];
		int right = a[n-1];
		if(right>left){ //normal case, just binary search
			return Arrays.binarySearch(a, x);
		}else{
			int peakIdx = searchPeak(a, 0, n);
			if(x>=left){
				return Arrays.binarySearch(a, 0, peakIdx+1, x);
			}else{
				return Arrays.binarySearch(a, peakIdx+1, n, x);
			}
		}
	}
	
	public static int searchPeak(int[] a, int l, int r){
		if(l>r)
			return -1;
		
		int mid = (l+r)/2;
		if(a[mid]>a[mid-1]&&a[mid]>a[mid+1])
			return mid;	//peak index
		
		if(a[l]<=a[mid]){ //search right
			return searchPeak(a, mid+1, r);
		}else //search lest
			return searchPeak(a, l, mid-1);
	}

	/********************************** 11.4 **********************************/
	public static void q4() {

	}

	/********************************** 11.5 **********************************/
	public static void q5() {

	}

	/********************************** 11.6 **********************************/
	public static void q6() {

	}

	/********************************** 11.7 **********************************/
	public static void q7() {

	}

	/********************************** 11.8 **********************************/
	public static void q8() {

	}
}
