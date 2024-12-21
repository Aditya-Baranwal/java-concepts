package com.leetcode;

public class Main {

    public static void main(String[] args) {

        // root = [1,2,3,4,5,6,7]
        TreeNode one = new TreeNode(1);
        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(3);
        TreeNode four = new TreeNode(4);
        TreeNode five = new TreeNode(5);
        TreeNode six = new TreeNode(6);
        TreeNode seven = new TreeNode(7);

        one.left = two;
        one.right = three;
        two.left = four;
        two.right = five;
        three.left = six;
//        three.right = seven;

        /**
         *    1
         *  2  3
         * 4 5 6
         */

        boolean result = Problems.hasPathSum(one, 4);

        System.out.println(result);

    }

}

