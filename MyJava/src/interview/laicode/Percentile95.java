package interview.laicode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Test;

/**
 * 
 * 95 Percentile
 * Fair
 * None
 * 
 * Given a list of integers representing the lengths of urls, find the 95
 * percentile of all lengths (95% of the urls have lengths <= returned length).
 * 
 * Assumptions
 * 
 * The maximum length of valid url is 4096
 * 
 * The list is not null and is not empty and does not contain null
 * 
 * Examples
 * 
 * [1, 2, 3, ..., 95, 96, 97, 98, 99, 100], 95 percentile of all lengths is 95.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Percentile95 {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Percentile95.class);
	}

	/**
	 * Time: O(n)
	 * Because URL has a maximum length which is 4096 characters, we can use an
	 * array as HashMap to count the length.
	 */
	public int percentile95_Improved(List<Integer> lengths) {
		int[] urlLengths = new int[4097];
		for (Integer length : lengths)
			urlLengths[length]++;

		double threshold = lengths.size() * 0.05d;
		double total = 0;
		int i = urlLengths.length;
		while (total <= threshold) {
			total += urlLengths[--i];
		}
		return i;
	}

	/**
	 * Time: O(nlogk), k is the unique number of url length.
	 * Use a TreeMap<Integer, Pair> to aggregate the count
	 * Keep popping until the 5% is out
	 */
	public int percentile95(List<Integer> lengths) {
		TreeMap<Integer, Integer> lengthCnt = new TreeMap<>();
		for (Integer len : lengths) {
			if (lengthCnt.containsKey(len)) {
				lengthCnt.put(len, lengthCnt.get(len) + 1);
			} else
				lengthCnt.put(len, 1);
		}

		double threshold = 0.05d * lengths.size();
		double currSize = 0;
		Entry<Integer, Integer> currLength = null;
		while (currSize <= threshold) {
			currLength = lengthCnt.pollLastEntry();
			currSize += currLength.getValue();
		}
		return currLength == null ? 0 : currLength.getKey();
	}

	@Test
	public void test1() {
		Integer[] rawInput = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
				15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
				31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,
				47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62,
				63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
				79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94,
				95, 96, 97, 98, 99, 100 };
		List<Integer> lengths = Arrays.asList(rawInput);
		int res = percentile95_Improved(lengths);
		int ans = 95;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		Integer[] rawInput = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
				15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
				31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,
				47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62,
				63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
				79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94,
				95, 96, 97, 98, 99, 100, 101 };
		List<Integer> lengths = Arrays.asList(rawInput);
		int res = percentile95_Improved(lengths);
		int ans = 96;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test3() {
		Integer[] rawInput = { 1, 2, 3 };
		List<Integer> lengths = Arrays.asList(rawInput);
		int res = percentile95_Improved(lengths);
		int ans = 3;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test4() {
		Integer[] rawInput = { 1, 2 };
		List<Integer> lengths = Arrays.asList(rawInput);
		int res = percentile95_Improved(lengths);
		int ans = 2;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test5() {
		Integer[] rawInput = { 1 };
		List<Integer> lengths = Arrays.asList(rawInput);
		int res = percentile95_Improved(lengths);
		int ans = 1;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test6() {
		Integer[] rawInput = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3 };
		List<Integer> lengths = Arrays.asList(rawInput);
		int res = percentile95_Improved(lengths);
		int ans = 2;
		assertTrue("Wrong: " + res, res == ans);
	}

}
