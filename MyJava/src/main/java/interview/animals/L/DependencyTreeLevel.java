package interview.company.linkedin;

import interview.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Define a dependency tree like this
 * <p>
 * ******** A(3)
 * ******* / \
 * ***** B(2) C(0)
 * ******* \
 * ******** D(1)
 * ******* / \
 * ***** E(0) F(0)
 * <p>
 * B, C depend on A, D depends on B, E, F depend on D
 * <p>
 * Given a dependency tree like that
 * <p>
 * The output should be: [E, F, C], [D], [B], [A]
 * <p>
 * The order inside E, F, C doesn't matter
 *
 * @author asia created on 1/15/16.
 */
public class DependencyTreeLevel {

  public static void main(String[] args) {
    DependencyTreeLevel o = new DependencyTreeLevel();
    TreeNode root = new TreeNode(3);
    root.setLeft(new TreeNode(2));
    root.setRight(new TreeNode(0));
    root.getLeft().setRight(new TreeNode(1));
    root.getLeft().getRight().setLeft(new TreeNode(0));
    root.getLeft().getRight().setRight(new TreeNode(0));

    List<List<TreeNode>> res = o.findDependencies(root);
    res.forEach(System.out::println);
  }

  public List<List<TreeNode>> findDependencies(TreeNode root) {
    List<List<TreeNode>> res = new ArrayList<>();
    collectDependency(root, res);
    return res;
  }

  private int collectDependency(TreeNode root, List<List<TreeNode>> res) {
    if (root == null) {
      return -1;
    }
    int leftLevel = collectDependency(root.left, res);
    int rightLevel = collectDependency(root.right, res);
    int currLevel = Math.max(leftLevel, rightLevel) + 1;
    if (currLevel >= res.size()) {
      res.add(new ArrayList<>());
    }
    List<TreeNode> nodes = res.get(currLevel);
    nodes.add(root);
    return currLevel;
  }
}
