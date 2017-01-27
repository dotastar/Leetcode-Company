package interview.utils;

import lombok.Data;

@Data
public class TreeNode implements AbstractTreeNode {
  public int key;
  public TreeNode left;
  public TreeNode right;

  public TreeNode(int key) {
    this.key = key;
  }

  public String toString() {
    return String.valueOf(key);
  }

}
