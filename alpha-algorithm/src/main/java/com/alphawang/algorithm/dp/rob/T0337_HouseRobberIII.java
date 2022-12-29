package com.alphawang.algorithm.dp.rob;

import com.alphawang.algorithm.TreeNode;
import com.alphawang.algorithm.TreeNodeCreator;
import com.alphawang.algorithm.TreeNodePrinter;
import java.util.HashMap;
import java.util.Map;

/**
 * nums[i] 表示第i间房子中的现金数额，相邻房子里的钱不能同时取出；问最多取出多少钱？
 *
 * 限制：房子处于二叉树上，相邻节点不能同时取出。
 */
public class T0337_HouseRobberIII {

  Map<TreeNode, Integer> memo;

  public int rob(TreeNode root) {
    memo = new HashMap<>();
    return dp(root);
  }

  private int dp(TreeNode node) {
    if (node == null) {
      return 0;
    }
    if (memo.containsKey(node)) {
      return memo.get(node);
    }

    //不取node
    // int sum_not = Math.max(dp(node.left), dp(node.right)); //不取当前节点，说明下一级节点要取，是求sum而非求max
    int sum_not = dp(node.left) + dp(node.right);
    //取node
    int sum_do = node.val;
    if (node.left != null) {
      sum_do += dp(node.left.left) + dp(node.left.right);
    }
    if (node.right != null) {
      sum_do += dp(node.right.left) + dp(node.right.right);
    }

    int sum = Math.max(sum_not, sum_do);
    memo.put(node, sum);
    return sum;
  }


  public static void main(String[] args) {
    T0337_HouseRobberIII sut = new T0337_HouseRobberIII();
    sut.test(TreeNodeCreator.createTree(3,2,3,null,3,null,1)); //7
    sut.test(TreeNodeCreator.createTree(3,4,5,1,3,null,1)); //9
  }

  private void test(TreeNode root) {
    TreeNodePrinter.print(root);
    System.out.println("--> " + rob(root));
  }


}
