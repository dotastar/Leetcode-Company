package interview.leetcode;

import java.util.Arrays;
import java.util.Random;

/**
 * Suppose you are at a party with n people (labeled from 0 to n - 1) and among them, there may exist one celebrity. The definition of a celebrity is that all the other n - 1 people know him/her but he/she does not know any of them.
 * <p>
 * Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed to do is to ask questions like: "Hi, A. Do you know B?" to get information of whether A knows B. You need to find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).
 * <p>
 * You are given a helper function bool knows(a, b) which tells you whether A knows B. Implement a function int findCelebrity(n), your function should minimize the number of calls to knows.
 * <p>
 * Note: There will be exactly one celebrity if he/she is in the party. Return the celebrity's label if there is a celebrity in the party. If there is no celebrity, return -1.
 * <p>
 * Hide Company Tags LinkedIn
 * Hide Tags Array
 *
 * @author asia created on 1/13/16.
 */
public class Find_the_Celebrity {
  /* The knows API is defined in the parent class Relation. */
  public static boolean knows(int a, int b) {
    Arrays.asList(new Integer[5]);
    return new Random().nextBoolean();
  }

  // O(n), O(1), eliminate either l or r at a time
  public int findCelebrity2(int n) {
    int l = 0, r = n - 1;
    while (l < r) {
      if (knows(l, r))
        l++;
      else
        r--;
    }
    // l == r, validate l
    for (int i = 0; i < n; i++) {
      if (i != l && (knows(l, i) || !knows(i, l)))
        return -1;
    }
    return l;
  }

  // Time: O(n^2), Space: O(n)
  public int findCelebrity(int n) {
    boolean[] notCelebrity = new boolean[n];
    int[] popularity = new int[n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i != j) {
          if (knows(i, j)) {
            notCelebrity[i] = true;
            popularity[j]++;
          }
        }
      }
    }
    for (int i = 0; i < n; i++) {
      if (!notCelebrity[i] && popularity[i] == n - 1)
        return i;
    }
    return -1;
  }
}
