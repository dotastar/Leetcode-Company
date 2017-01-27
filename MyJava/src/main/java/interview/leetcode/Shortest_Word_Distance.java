package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
 * <p>
 * For example,
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 * <p>
 * Given word1 = “coding”, word2 = “practice”, return 3.
 * Given word1 = "makes", word2 = "coding", return 1.
 * <p>
 * Note:
 * You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 * <p>
 * <p>
 * Hide Company Tags LinkedIn
 * Hide Tags Array
 * Hide Similar Problems
 *
 * @author asia created on 1/13/16.
 */
public class Shortest_Word_Distance {
  public int shortestDistance(String[] words, String word1, String word2) {
    List<Integer> list1 = new ArrayList<>(), list2 = new ArrayList<>();
    for (int i = 0; i < words.length; i++) {
      String w = words[i];
      if (w.equals(word1))
        list1.add(i);
      else if (w.equals(word2))
        list2.add(i);
    }
    // Find the min diff in (list1, list2), list1 and list2 are already ordered
    int min = words.length;
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
