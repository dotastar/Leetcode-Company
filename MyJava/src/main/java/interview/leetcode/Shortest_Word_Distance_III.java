package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a follow up of Shortest Word Distance. The only difference is now word1 could be the same as word2.
 * <p>
 * Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
 * <p>
 * word1 and word2 may be the same and they represent two individual words in the list.
 * <p>
 * For example,
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 * <p>
 * Given word1 = “makes”, word2 = “coding”, return 1.
 * Given word1 = "makes", word2 = "makes", return 3.
 * <p>
 * Note:
 * You may assume word1 and word2 are both in the list.
 * <p>
 * Hide Company Tags LinkedIn
 * Hide Tags Array
 * Show Similar Problems
 *
 * @author asia created on 1/13/16.
 */
public class Shortest_Word_Distance_III {

  public int shortestWordDistance(String[] words, String word1, String word2) {
    boolean oneWordOnly = false;
    if (word1.equals(word2)) {
      oneWordOnly = true;
    }
    List<Integer> list1 = new ArrayList<>(), list2 = new ArrayList<>();
    for (int i = 0; i < words.length; i++) {
      String w = words[i];
      if (w.equals(word1))
        list1.add(i);
      else if (!oneWordOnly && w.equals(word2))
        list2.add(i);
    }
    return oneWordOnly ? minDiff(list1) : minDiff(list1, list2);
  }

  private int minDiff(List<Integer> list) {
    int min = Integer.MAX_VALUE;
    for (int i = 1; i < list.size(); i++) {
      min = Math.min(min, Math.abs(list.get(i) - list.get(i - 1)));
      if (min == 1) {
        return 1;
      }
    }
    return min;
  }

  // O(n)
  private int minDiff(List<Integer> list1, List<Integer> list2) {
    int min = Integer.MAX_VALUE;
    for (int i = 0, j = 0; i < list1.size() && j < list2.size(); ) {
      int idx1 = list1.get(i), idx2 = list2.get(j);
      min = Math.min(min, Math.abs(idx1 - idx2));
      if (min == 1)
        return 1;
      if (idx1 < idx2)
        i++;
      else
        j++;
    }
    return min;
  }
}
