package interview.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yazhoucao on 1/2/16.
 * <p>
 * Pretty Printer
 */
public class PrettyPrinter {
  private static final char SPACE = ' ';
  private static final char UNDER_SCORE = '_';

  public static void main(String[] args) {
    TreeNode root = new TreeNode(0);
    root.left = new TreeNode(1);
    root.right = new TreeNode(2);
    root.left.left = new TreeNode(3);
    root.left.right = new TreeNode(4);
    root.left.right.right = new TreeNode(9);
    root.right.left = new TreeNode(5);
    root.right.right = new TreeNode(6);
    root.right.left.left = new TreeNode(7);
    root.right.right.right = new TreeNode(8);
    PrettyPrinter.print(root);
  }

  // TODO: Implement top-down approach, should be more space efficient

  /**
   * Bottom-up approach, level-order traversal, generate lines
   *
   * Idea: think every tree as a complete tree, print null node as a space
   */
  public static void print(AbstractTreeNode root) {
    if (root == null) {
      return;
    }
    int h = height(root);
    // Separate nodes by level
    List<List<AbstractTreeNode>> levels = new ArrayList<>(h);
    levels.add(new ArrayList<>());
    levels.get(0).add(root);
    for (int i = 1; i < h; i++) {
      List<AbstractTreeNode> previous = levels.get(i - 1);
      List<AbstractTreeNode> current = new ArrayList<>(previous.size() * 2);
      for (AbstractTreeNode node : previous) {
        current.add(node != null ? node.getLeft() : null);
        current.add(node != null ? node.getRight() : null);
      }
      levels.add(current);
    }

    // Generate lines
    List<String> lines = new ArrayList<>(2 * h);
    lines.add(generateBottomLine(levels)); // Bottom line is special, print all leaf nodes first

    // Bottom-up generate rest lines
    StringBuilder nodeLine = new StringBuilder();
    StringBuilder diagonalLine = new StringBuilder();
    int branchLen = 1; // Consider "/--" as a branch whose length is 3
    int wholeBranchLen = 2; // wholeBranch includes all nodes of its subtree
    for (int i = levels.size() - 2; i >= 0; i--) {
      nodeLine.setLength(0);
      diagonalLine.setLength(0);
      for (AbstractTreeNode node : levels.get(i)) {
        addNode(node, nodeLine, wholeBranchLen, branchLen);              // (eg,  __3__)
        addDiagonalLine(node, diagonalLine, wholeBranchLen, branchLen);  // (eg, /     \)
        // For space of father node
        nodeLine.append(SPACE);
        diagonalLine.append(SPACE);
      }

      lines.add(diagonalLine.toString());
      lines.add(nodeLine.toString());

      branchLen = wholeBranchLen;
      wholeBranchLen = wholeBranchLen * 2 + 1;
    }

    StringBuilder sb = new StringBuilder();
    for (int i = lines.size() - 1; i >= 0; i--) {
      sb.append(lines.get(i));
      sb.append(System.lineSeparator());
    }
    System.out.println(sb);
  }

  private static String generateBottomLine(List<List<AbstractTreeNode>> levels) {
    StringBuilder lineBuilder = new StringBuilder();
    List<AbstractTreeNode> bottomLevel = levels.get(levels.size() - 1);
    for (int i = 0; i < bottomLevel.size(); i += 2) {
      AbstractTreeNode left = bottomLevel.get(i);
      AbstractTreeNode right = i + 1 < bottomLevel.size() ? bottomLevel.get(i + 1) : null;

      lineBuilder.append(left != null ? left : SPACE);
      appendChars(lineBuilder, SPACE, 3);
      lineBuilder.append(right != null ? right : SPACE);
      appendChars(lineBuilder, SPACE, 1);
    }
    return lineBuilder.toString();
  }

  private static void addDiagonalLine(AbstractTreeNode node, StringBuilder lineBuilder, int wholeBranchLen, int branchLen) {
    appendChars(lineBuilder, SPACE, wholeBranchLen - branchLen);
    lineBuilder.append(node != null && node.getLeft() != null ? '/' : SPACE);
    appendChars(lineBuilder, SPACE, 2 * (branchLen - 1) + 1);
    lineBuilder.append(node != null && node.getRight() != null ? '\\' : SPACE);
    appendChars(lineBuilder, SPACE, wholeBranchLen - branchLen);
  }

  private static void addNode(AbstractTreeNode node, StringBuilder lineBuilder, int wholeBranchLen, int branchLen) {
    if (node == null) {
      appendChars(lineBuilder, SPACE, branchLen + 1);
      return;
    }

    appendChars(lineBuilder, SPACE, wholeBranchLen - branchLen + 1);
    appendChars(lineBuilder, node.getLeft() != null ? UNDER_SCORE : SPACE, branchLen - 1);
    lineBuilder.append(node);
    appendChars(lineBuilder, node.getRight() != null ? UNDER_SCORE : SPACE, branchLen - 1);
    appendChars(lineBuilder, SPACE, wholeBranchLen - branchLen + 1);
  }

  private static int height(AbstractTreeNode root) {
    return height(root, 0);
  }

  private static int height(AbstractTreeNode root, int height) {
    if (root == null) {
      return height;
    }
    return Math.max(height(root.getLeft(), height + 1), height(root.getRight(), height + 1));
  }

  private static StringBuilder appendChars(StringBuilder sb, char c, int num) {
    while (num-- > 0) {
      sb.append(c);
    }
    return sb;
  }
}
