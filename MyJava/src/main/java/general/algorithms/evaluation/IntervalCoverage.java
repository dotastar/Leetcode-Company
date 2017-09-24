package general.algorithms.evaluation;

import interview.AutoTestUtils;
import lombok.AllArgsConstructor;
import org.junit.Test;

import java.util.Comparator;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;


/**
 * Given a number of intervals, a total coverage length should be calculated.
 *
 * Expected time complexity is O(logN) for addInterval and O(N) for getTotalCoveredLength
 *
 * Expected memory complexity is O(N)
 *
 *
 * Scoring
 *
 * A Great (3.5+) candidate will:
 * Discuss the tradeoffs of merge-in-add vs. calculate-in-get, and either explicitly choose one or ask the interviewer for guidance
 * Describe their approach clearly, and cleanly implement it
 * Be able to cleanly modify their solution to handle followup questions. Note that calcualte-in-get will require significant modification to handle removeRange, while merge-in-add will require significant modification to handle removeInterval
 * Implement their solution and followups with few to no significant bugs. The candidate will check their code and catch their own mistakes without prompting.
 * Complete the question within 45 minutes
 *
 * A Good (3.0) candidate will:
 * Explicitly choose an approach, describe it clearly, and implement it cleanly
 * Be able to modify their approach to handle the followups, although the code may not be totally clean
 * When presented with the variant of remove() that their chosen approach does not handle well, they should mention the other approach
 * Implement their solution with only a few bugs, and they should be able to fix any bugs with only minor hints, such as failing inputs
 * Complete the base question within 45 minutes
 *
 * A Bad (2.8) candidate may
 * Fall into a particular approach rather than choosing it
 * Have difficulty with followups, if they reach them. In particular, they may be unable to see the other approach when confronted with the difficult remove()
 * Have difficulty explaining their approach or why they chose it
 * Have difficulty implementing their solution cleanly - there may be many special cases, unneeded checks, or awkward constructs
 * Have significant bugs in their implementation, and have difficulty correcting those bugs when they are pointed out
 * Take more than 45 minutes to complete the base question
 *
 * A Terrible (2.0-) candidate may
 * Be unable to decide on an approach, or may repeatedly flip between approahces
 * Be unable to describe why they are using the approach that they are, or explain why it will solve the problem
 * Have difficulty constructing a workable solution. They may have significant logical errors, ignore the spec, or otherwise produce mistakes beyond mere bugs
 * Have significant bugs in the implementation, and be unable to correct those bugs even with interviewer help
 * Be unable to complete the base question within the available time
 *
 */
public class IntervalCoverage {

  public static void main(String[] args) {
    AutoTestUtils.runTestClassAndPrint(IntervalCoverage.class);
  }

  public interface Intervals {

    /**
     * Adds an interval [from, to) into an internal structure.
     */
    void addInterval(int from, int to);

    /**
     * Returns a total length covered by the added intervals.
     * If several intervals intersect, the intersection should be counted only once.
     * Example:
     *
     * addInterval(3, 6)
     * addInterval(8, 9)
     * addInterval(1, 5)
     *
     * getTotalCoveredLength() -> 6
     *
     * i.e. [1,5) and [3,6) intersect and give a total covered interval [1,6) with a length of 5.
     *      [1,6) and [8,9) don't intersect, so the total covered length is a sum of both intervals, that is 5+1=6.
     *
     *          |__|__|__|                  (3,6)
     *                         |__|         (8,9)
     *    |__|__|__|__|                     (1,5)
     *
     * 0  1  2  3  4  5  6  7  8  9  10
     *
     */
    int getTotalCoveredLength();
  }

  @AllArgsConstructor
  static class Interval {
    int left;
    int right;

    public int getLength() {
      return right - left;
    }
  }

  public static class TreeIntervalsSolution implements Intervals {

    //    private List<Interval> intervals = new ArrayList<>();
    private TreeSet<Interval> intervals = new TreeSet<>(Comparator.comparingInt(o -> o.left));

    @Override
    public void addInterval(int from, int to) {
      intervals.add(new Interval(from, to));
    }

    @Override
    public int getTotalCoveredLength() {

      return simplified_getTotalCoveredLength();
    }

    private int simplified_getTotalCoveredLength() {
      int totalLength = 0;

      Interval prior = null;
      for (Interval current : intervals) {
        if (prior == null) {
          prior = current;
          continue;
        }
        // overlap and prior covers current
        if (prior.right > current.left && prior.right >= current.right) {
          continue;
        }

        totalLength += Math.min(prior.right, current.left) - prior.left;
        prior = current;

      }

      if (prior != null) {
        totalLength += prior.getLength();
      }
      return totalLength;
    }

    private int naive_getTotalCoveredLength() {
      int totalLength = 0;

      Interval prior = null;
      for (Interval current : intervals) {
        if (prior == null) {
          prior = current;
          continue;
        }

        if (prior.right <= current.left) { // no overlap
          totalLength += prior.right - prior.left;
          prior = current;
          continue;
        }

        // overlap case 1
        if (prior.right >= current.right) {
          continue;
        }

        totalLength += current.left - prior.left;
        prior = current;
      }

      if (prior != null) {
        totalLength += prior.getLength();
      }

      return totalLength;
    }
  }

  @Test
  public void testcase1() {
    int expectedLength = 0;
    Intervals i = new TreeIntervalsSolution();
    assertEquals(expectedLength, i.getTotalCoveredLength());
  }

  @Test
  public void testcase2() {
    int expectedLength = 0;
    Intervals i = new TreeIntervalsSolution();
    i.addInterval(1, 1);
    assertEquals(expectedLength, i.getTotalCoveredLength());
  }

  @Test
  public void testcase3() {
    int expectedLength = 4;
    Intervals i = new TreeIntervalsSolution();
    i.addInterval(1, 5);
    assertEquals(expectedLength, i.getTotalCoveredLength());
  }

  @Test
  public void testcase4() {
    int expectedLength = 5;
    Intervals i = new TreeIntervalsSolution();
    i.addInterval(8, 9);
    i.addInterval(1, 5);
    assertEquals(expectedLength, i.getTotalCoveredLength());
  }


  @Test
  public void testcase5() {
    int expectedLength = 8;
    Intervals i = new TreeIntervalsSolution();
    i.addInterval(1, 5);
    i.addInterval(4, 9);
    assertEquals(expectedLength, i.getTotalCoveredLength());
  }


  @Test
  public void testcase6() {
    int expectedLength = 6;

    Intervals i = new TreeIntervalsSolution();
    i.addInterval(3, 6);
    i.addInterval(8, 9);
    i.addInterval(1, 5);
    i.addInterval(1, 1);

    assertEquals(expectedLength, i.getTotalCoveredLength());
  }

  /**
   * Follow-up Questions and Variants
   * Add a color attribute to the interval and provide a separate result for each color.
   * Provide a removeRange(int start, int end) method which will remove all coverage between start and end
   * Provide a removeInterval(int start, int end) method which will remove an interval running from start to end if one has been defined
   */
  public interface ColorIntervals {

    void addInterval(int from, int to, int color);

    int getTotalCoveredLength();
  }

  @AllArgsConstructor
  static class ColorInterval {
    int left;
    int right;
    int color;

    public int getLength() {
      return right - left;
    }
  }

  public static class TreeColorIntervalsSolution implements ColorIntervals {

    @Override
    public void addInterval(int from, int to, int color) {

    }

    @Override
    public int getTotalCoveredLength() {
      return 0;
    }
  }
}
