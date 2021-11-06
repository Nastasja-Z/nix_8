package ua.com.alevel.treenode;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class ExecutableTreeNode {

    private static void insertTreeNode(TreeNode treeNode, int value) {
        if (value < treeNode.val) {
            if (treeNode.left != null) {
                insertTreeNode(treeNode.left, value);
            } else {
                treeNode.left = new TreeNode(value);
            }
        } else if (value > treeNode.val) {
            if (treeNode.right != null) {
                insertTreeNode(treeNode.right, value);
            } else {
                treeNode.right = new TreeNode(value);
            }
        }
    }

    private int findMaximumDepthOfTreeNode(TreeNode treeNode) {
        if (treeNode == null)
            return 0;
        else {
            int leftDepth = findMaximumDepthOfTreeNode(treeNode.left);
            int rightDepth = findMaximumDepthOfTreeNode(treeNode.right);
            return leftDepth > rightDepth ? leftDepth + 1 : rightDepth + 1;
        }
    }

    private void printTreeNodeByLevels(TreeNode treeNode) {
        Queue<TreeNode> level = new LinkedList<>();
        level.add(treeNode);
        while (!level.isEmpty()) {
            TreeNode node = level.poll();
            System.out.print(node.val + " ");
            if (node.left != null)
                level.add(node.left);
            if (node.right != null)
                level.add(node.right);
        }
    }

    public void createAndCheckTreeNode(BufferedReader reader) {
        try {
            System.out.println("Enter string of integers to fill the tree node: ");
            String[] stringOfNumbersForTreeNode= reader.readLine().split(" ");
            int x= Integer.parseInt(stringOfNumbersForTreeNode[0]);
            TreeNode currentNode = new TreeNode(x);
            for (String s : stringOfNumbersForTreeNode) {
                insertTreeNode(currentNode,Integer.parseInt(s));
            }
            System.out.println("max depth = " + findMaximumDepthOfTreeNode(currentNode));
            System.out.println("Your tree node:");
            printTreeNodeByLevels(currentNode);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }
}
