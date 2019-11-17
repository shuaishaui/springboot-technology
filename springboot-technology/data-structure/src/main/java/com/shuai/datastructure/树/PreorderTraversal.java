package com.shuai.datastructure.树;

import java.util.Stack;

public class PreorderTraversal {

  public static void main(String[] args) {
    BinTree binTree = new BinTree();
    Object[] objs = {1, 2, 3, 4, 5, 6, 7};
    binTree.createTree(objs);
//    binTree.preorder(binTree.getRoot()); //先序遍历
//		binTree.inorder(binTree.getRoot()); 中序遍历
    //   binTree.afterorder(binTree.getRoot()); //后序遍历

    fact(binTree.getRoot());

  }

  // 非递归实现前序遍历 ，使用栈来保存右孩子信息
  private static void fact(BinTree root) {
    if (root == null){
      return;
    }
    Stack<BinTree> stack = new Stack<>();
    // 这个while(true)真的牛批
    while (true) {
      while (root != null) {
        System.out.print(root.getData() + " ");
        stack.push(root);
        root = root.getlChild();
      }
      if (stack.isEmpty()) {
        break;
      }else {
        root = stack.pop();
        root = root.getrChild();
      }
    }

  }

}
