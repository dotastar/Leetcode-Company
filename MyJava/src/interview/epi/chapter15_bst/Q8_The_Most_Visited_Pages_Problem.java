package interview.epi.chapter15_bst;

/**
 * You are given a log file containing billions of entries. Each entry contains
 * an integer timestamp and page which is of type string. The entries in a log
 * file appear in increasing order of timestamp. You are to implement methods to
 * analyze log file data to find the most visited pages. Specifically, implement
 * the following methods:
 * void add(Entry p) - add p.page to the set of visited pages.
 * List<String> common(int k) - return a list of the k most common pages.
 * 
 * @author yazhoucao
 * 
 */
public class Q8_The_Most_Visited_Pages_Problem {

	public static void main(String[] args) {
		/** ignore implementation **/
	}

	/**
	 * If just call once common(int k), then it can be done by building a
	 * HashMap, use an integer count for counting duplicate page.
	 */

	/**
	 * If need to call common(int k) a lot of times, then it can be done by a
	 * BST, and count the duplicate as well.
	 */
}
