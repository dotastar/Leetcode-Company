package general.algorithms.evaluation;

import interview.AutoTestUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;


/**
 * CA2
 * Given the grid of integers, each number representing one type of enemy.
 * If you attack one enemy, such as enemy type 1 at (0,0) you can kill all the connected enemies reachable from (0.0),
 * so in the following example five enemies from row 0 and column 0 will die.
 *
 * You want to wipe out all enemies with the minimal number of attacks, and attacking the most possible enemies at once each time.
 * Return the best attack order.
 *
 * grid =
 * [[1, 0, 1],
 * [0, 0, 1],
 * [1, 1, 1]]
 *
 * Note: best attack order means you want to kill the largest group of enemies first, then the next largest, and so on.
 * So if you have groups of 1 enemy, 2 enemies, and 4 enemies, you kill the 4 first, then 2, then 1.
 */
public class BestAttackStrategy {

  /**
   * This is essentially finding the size of connected sub-components, then return the ordered list of them with starting position.
   */

  public static void main(String[] args) {
    AutoTestUtils.runTestClassAndPrint(BestAttackStrategy.class);
  }

  @Data @AllArgsConstructor
  static class Component {
    int x;
    int y;
    int cnt;
  }

  public static class Solution {
    public List<Component> bestAttack(int[][] grid) {
      List<Component> subComponents = new ArrayList<>();
      // determine number of sub-connected-component
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[i].length; j++) {
          if (grid[i][j] != 1) {
            continue;
          }
          Component subComponent = new Component(i, j, 0);
          markSubComponent(grid, i, j, subComponent);
          subComponents.add(subComponent);
        }
      }
      restore(grid); // unmark/restore the grid ... ignored

      subComponents.sort(Comparator.comparing(Component::getCnt).reversed());
      return subComponents;
    }

    private void restore(int[][] grid) {
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[i].length; j++) {
          if (grid[i][j] == -1) {
            grid[i][j] = 1;
          }
        }
      }
    }

    private void markSubComponent(int[][] grid, int i, int j, Component component) {
      if (i < 0 || j < 0 || i >= grid.length || j >= grid[i].length || grid[i][j] != 1) {
        return;
      }
      grid[i][j] = -1;
      component.setCnt(component.getCnt() + 1);
      markSubComponent(grid, i + 1, j, component);
      markSubComponent(grid, i - 1, j, component);
      markSubComponent(grid, i, j + 1, component);
      markSubComponent(grid, i, j - 1, component);
    }
  }

  @Test
  public void test1() {
    int[][] grid = {
        { 0, 0, 1, 0 },
        { 1, 0, 0, 1 },
        { 1, 0, 1, 1 }
    };

    Solution solution = new Solution();
    List<Component> components = solution.bestAttack(grid);
    // Expected: [BestAttackStrategy.Component(x=1, y=3, cnt=3), BestAttackStrategy.Component(x=1, y=0, cnt=2), BestAttackStrategy.Component(x=0, y=2, cnt=1)]
    System.out.println(components);
  }

  @Test
  public void test_not_rectangle() {
    int[][] grid = {
        { 0, 0, 1, 0 },
        { 1, 1, 0 },
        { 1, 0, 1, 1, 1, 1 },
        { 0, 1 },
        { 1, 0, 1, 1 },
        { 1, 0, 1, 1 },
        { 0, 1, 1 }
    };

    Solution solution = new Solution();
    List<Component> components = solution.bestAttack(grid);
    System.out.println(components);

    /**
     * Expected
     *
     * [BestAttackStrategy.Component(x=4, y=2, cnt=6),
     * BestAttackStrategy.Component(x=2, y=2, cnt=4),
     * BestAttackStrategy.Component(x=1, y=0, cnt=3),
     * BestAttackStrategy.Component(x=4, y=0, cnt=2),
     * BestAttackStrategy.Component(x=0, y=2, cnt=1),
     * BestAttackStrategy.Component(x=3, y=1, cnt=1)]
     */
  }

  /**
   * Evaluation Criteria:
   *
   * A great candidate will
   * Understand the essence of the problem is to find out the size of connected sub-components
   * Writes the code cleanly
   * Take no more than 30 minutes
   *
   * A good candidate will
   * Understands the problem after one or two hints
   * writes the code which works, but can miss couple edge cases
   * Take no more than 40 minutes.
   *
   * A bad candidate will
   * Take more than 50 minutes
   * Can not solve this problem
   */

  /**
   * Follow-up:
   * 1.How to validate other attack orders
   * 2.What if it's a 3D grid
   */
}
