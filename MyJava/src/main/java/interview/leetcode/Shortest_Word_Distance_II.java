package interview.leetcode;

import java.util.*;

/**
 * This is a follow up of Shortest Word Distance. The only difference is now you are given the list of words and your method will be called repeatedly many times with different parameters. How would you optimize it?
 * <p>
 * Design a class which receives a list of words in the constructor, and implements a method that takes two words word1 and word2 and return the shortest distance between these two words in the list.
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
 * Hide Company Tags LinkedIn
 * Hide Tags Hash Table Design
 * Show Similar Problems
 *
 * @author asia created on 1/13/16.
 */
public class Shortest_Word_Distance_II {

  public static void main(String[] args) {
    System.out.println(new WordDistance(new String[0]));
  }

  public static class WordDistance {

    private Map<String, List<Integer>> word2Idx;

    // O(n), n = words.length
    public WordDistance(String[] words) {
      word2Idx = new HashMap<>();
      for (int i = 0; i < words.length; i++) {
        List<Integer> idxList = word2Idx.get(words[i]);
        if (idxList == null) {
          idxList = new ArrayList<>();
          word2Idx.put(words[i], idxList);
        }
        idxList.add(i);
      }
    }

    public int shortest(String word1, String word2) {
      return minDiff(word2Idx.get(word1), word2Idx.get(word2));
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

}
