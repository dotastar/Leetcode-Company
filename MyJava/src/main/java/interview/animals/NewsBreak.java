package interview.animals;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;


public class NewsBreak {

  @Test
  public void testCase1() {
    assertThat(editDistance("ab", "ef")).isEqualTo(0);
  }

  @Test
  public void testCase2() {
    assertThat(editDistance("ppp", "ssh")).isEqualTo(2);
  }

  @Test
  public void testCase3() {
    assertThat(editDistance("ab", "ef")).isEqualTo(0);
  }

  public static int editDistance(String source, String target) {
    int[] dp = new int[source.length() + 1];
    int min = Integer.MAX_VALUE;
    char[] shiftedSource = source.toCharArray();
    // shift source to a point the edit distance is smallest
    for (int i = 0; i < 24; i++) {
      String newSource = shiftBy(source, shiftedSource, i);
      int distance = vanilaEditDistance(dp, newSource, target);
      min = Math.min(min, distance);
    }
    return min;
  }

  private static String shiftBy(String source, char[] shiftedSource, int offset) {
    for (int j = 0; j < source.length(); j++) {
      shiftedSource[j] = shiftBy(source.charAt(j), offset);
    }
    return new String(shiftedSource);
  }

  private static char shiftBy(char c, int offset) {
    int newPos = (c + offset);
    if (newPos > 'z') {
      newPos = newPos - 24;
    }
    return (char) newPos;
  }

  // compute vanila eidt distance
  private static int vanilaEditDistance(int[] dp, String source, String target) {
    int n = dp.length - 1;
    for (int i = 0; i <= n; i++) {
      dp[i] = i;
    }
    int prev = 0;
    for (int i = 1; i <= n; i++) {
      prev = i - 1;
      dp[0] = i;
      for (int j = 1; j <= n; j++) {
        if (source.charAt(i - 1) == target.charAt(j - 1)) {
          int temp = dp[j];
          dp[j] = prev;
          prev = temp;
        } else {
          prev = dp[j];
          dp[j] = Math.min(dp[j] + 1, dp[j - 1] + 1);
        }
      }
    }
    return dp[n];
  }
}
