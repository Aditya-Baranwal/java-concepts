package com.leetcode;

public class TreeNode implements Comparable {
  int val;
  TreeNode left;
  TreeNode right;
  TreeNode next;

  TreeNode() {}

  TreeNode(int val) { this.val = val; }

  TreeNode(int val, TreeNode left, TreeNode right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }

  @Override
  public int compareTo(Object o) {
      TreeNode currentNode = (TreeNode) o;
    return Integer.compare(val, currentNode.val);
  }
}
