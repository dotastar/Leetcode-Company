package interview.company.others;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.htmlparser.lexer.Page;
import org.junit.Test;

/**
 * Booking.com
 * 
 * Phone Interview
 * 
 * @author yazhoucao
 *
 */
public class BookingCOM {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(BookingCOM.class);
	}

	/**
	 * Problem 1:
	 * 
	 * Implement a function nondecreasing_subsequences() that, given a sequence
	 * of numbers such as:
	 * 
	 * [ 3,6,61,6,7,9,1,7,7,2,7,7,2,388,3,72,7 ]
	 * 
	 * ... will identify and return each contiguous sub-sequence of
	 * non-decreasing numbers. E.g. this example input should return this
	 * array-of-arrays (e.g. or list-of-lists)
	 * 
	 * [ [3,6,61],[6,7,9],[1,7,7],[2,7,7],[2,388],[3,72],[7] ]
	 * 
	 * (Each array includes a sequence of numbers that do not get smaller. The
	 * original order is unchanged.) For a visual example of a non-decreasing,
	 * see:
	 * http://en.wikipedia.org/wiki/File:Monotonicity_example1.png
	 * 
	 */
	public List<List<Integer>> nondecreasing_subsequences(int[] A) {
		List<List<Integer>> res = new ArrayList<>();
		if (A == null || A.length == 0)
			return res;
		List<Integer> seq = new ArrayList<>();
		for (int i = 0; i < A.length - 1; i++) {
			if (A[i] <= A[i + 1])
				seq.add(A[i]);
			else {
				seq.add(A[i]);
				res.add(seq);
				seq = new ArrayList<>();
			}
		}
		seq.add(A[A.length - 1]);
		res.add(seq);
		return res;
	}

	/**
	 * Problem 2:
	 * 
	 * Implement a function all_anagram_groups() that, given many input strings,
	 * will identify and group words that are anagrams of each other. An anagram
	 * is word that is just a re-arrangement of the characters of another word,
	 * like "reap" and "pear" and "a per" (whitespace is ignored). But "pear"
	 * and "rep" are not, since "rep" does not have an "a". Also, "the" and
	 * "thee" are not anagrams, because "the" only has one "e".
	 * 
	 * Given this example input:
	 * 
	 * [ "pear","dirty room","amleth","reap","tinsel","tesla","hamlet",
	 * "dormitory","listen","silent" ]
	 * 
	 * The output should be an array-of-arrays (or list-of-lists)
	 * 
	 * [
	 * ["pear","reap"],
	 * ["dirty room","dormitory"],
	 * ["amleth","hamlet"],
	 * ["tinsel","listen","silent"],
	 * ["tesla"]
	 * ]
	 * 
	 * @param words
	 * @return
	 */
	public List<List<String>> all_anagram_groups(String[] words) {
		List<List<String>> res = new ArrayList<>();
		Map<String, List<String>> groups = new HashMap<>();
		for (String word : words) {
			char[] chs = word.toCharArray();
			Arrays.sort(chs);
			String key = String.valueOf(chs);
			List<String> grp = groups.get(key);
			if (grp == null) {
				grp = new ArrayList<>();
				groups.put(key, grp);
			}
			grp.add(word);
		}
		for (List<String> group : groups.values())
			res.add(group);
		return res;
	}

	/**
	 * Problem 3:
	 * 
	 * Write a function get_hops_from(page1, page2) that will determine the
	 * number of hyperlinks that you would need to click on to get from some
	 * page1 on the web to some other page2 on the web.
	 * 
	 * For example, if each page below links to the pages that are indented
	 * below it, e.g. page 1 links to pages 2 and 5, and page 2 links to pages 3
	 * and 4, and page 5 links to pages 3 and 7, then the get_hops_from(page1,
	 * page7) should return 2 (2 hops), since you have to hop once from page 1
	 * to 5 and once more from page 5 to page 7.
	 * 
	 * 
	 * page1 : distance == 0
	 * page2 : distance == 1
	 * page3 : distance == 2
	 * page4 : distance == 2
	 * page5 : distance == 2
	 * page3 : distance == 2
	 * page7 : distance == 2
	 * 
	 * Assume that an API is available to:
	 * get_links(a_page) will return an array/list of all pages that a_page
	 * links to
	 * 
	 */
	public int get_hops_from(Page p1, Page p2) {
		if (p1 == null || p2 == null)
			return 0;
		Queue<Page> q = new LinkedList<>();
		q.add(p1);
		int dis = 0;
		while (!q.isEmpty()) {
			int length = q.size();
			while (length-- > 0) {
				Page curr = q.poll();
				if (curr == p2)
					break;
				List<Page> neighbors = get_links(curr);
				for (Page next : neighbors) {
					if (next != null)
						q.add(next);
				}
			}
			dis++;
		}
		return dis;
	}

	private List<Page> get_links(Page p) {
		return new ArrayList<Page>();
	}

	/**
	 * Problem 4:
	 * 
	 * Implement a function sort_numerically() that will receive a list of
	 * numbers represented in English words and return the listed sorted by
	 * their numeric value, starting with the largest.
	 * 
	 * Input:
	 * 
	 * [
	 * "seventy five",
	 * "two hundred forty one",
	 * "three thousand",
	 * "one million thirty five thousand twelve",
	 * "twenty",
	 * "five hundred thousand",
	 * "two hundred",
	 * ]
	 * 
	 * Output:
	 * 
	 * [
	 * "one million thirty five thousand twelve",
	 * "five hundred thousand"
	 * "three thousand",
	 * "two hundred forty one",
	 * "two hundred",
	 * "seventy five",
	 * "twenty",
	 * ]
	 */
	public String[] sort_numerically(String[] S) {
		final Map<String, Long> wordValMap = new HashMap<>();
		for (String word : S) {
			if (!wordValMap.containsKey(word))
				wordValMap.put(word, convertToValue(word));
		}
		Arrays.sort(S, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return wordValMap.get(s2).intValue() - wordValMap.get(s1).intValue();
			}
		});
		return S;
	}

	private static Map<String, Long> valueMap = new HashMap<>();
	static { // initialize valueMap
		valueMap.put("one", 1L);
		valueMap.put("two", 2L);
		// skip the enumeration....
		valueMap.put("eleven", 11L);
		valueMap.put("tweleve", 12L);
		// skip the enumeration....
		valueMap.put("twenty", 20L);
		valueMap.put("thirty", 30L);
		// skip the enumeration....
		valueMap.put("hundred", 100L);
		valueMap.put("thousand", 1000L);
		valueMap.put("million", 1_000_000L);
		valueMap.put("billion", 1_000_000_000L);
	}

	private long convertToValue(String word) {
		// assuming there is at least one number
		String[] numbers = word.split(" ");
		if (numbers.length == 0)
			return 0;
		// currentVal > nextVal --> current = current + next
		// currentVal <= nextVal --> current = current * next
		long current = valueMap.get(numbers[0]);
		for (int i = 1; i < numbers.length; i++) {
			long next = valueMap.get(numbers[i]);
			if (current < next) {
				if (current > Long.MAX_VALUE - next)
					return Long.MAX_VALUE;
				current += next;
			} else {
				if (current > Long.MAX_VALUE / next)
					return Long.MAX_VALUE;
				current *= next;
			}
		}
		return current;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
