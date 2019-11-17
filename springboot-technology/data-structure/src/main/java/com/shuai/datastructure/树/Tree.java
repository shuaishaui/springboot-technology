package com.shuai.datastructure.树;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


  public class Tree {

    private Tree left;   // 左孩子
    private Tree right;  // 右孩子
    private Tree root;   // 根节点
    private Object data;

    private Tree(Tree left, Tree right, Object data) {
      this.left = left;
      this.right = right;
      this.data = data;
    }

    private Tree(Object data) {
      new Tree(null, null, data);
    }

    public Tree() {
    }

    public Tree getLeft() {
      return left;
    }

    public void setLeft(Tree left) {
      this.left = left;
    }

    public Tree getRight() {
      return right;
    }

    public void setRight(Tree right) {
      this.right = right;
    }

    public Tree getRoot() {
      return root;
    }

    public void setRoot(Tree root) {
      this.root = root;
    }

    public Object getData() {
      return data;
    }

    public void setData(Object data) {
      this.data = data;
    }

    // 给二叉树赋值,接收一个数组作为参数
    public   void create(Object[] obj) {
      // 定义一个list来保存每一个节点
      List<Tree> list = new ArrayList<>();
      // 通过这个for循环，就把想赋的值变成了一个个节点
      for (Object o : obj) {
        list.add(new Tree(o));
      }

      // 将上面得到的节点拼接起来
      root = list.get(0);   // 将第一个赋为根节点
      for (int i = 0; i < obj.length / 2; i++) {
        list.get(i).setLeft(list.get(i * 2 + 1));
        if (i * 2 + 2 < obj.length) {
          list.get(i).setRight(list.get(i * 2 + 2));
        }
      }
    }

  // 通过非递归的方式中序遍历二叉树
  private static void fact(Tree root) {
    // 使用栈来保存左孩子
    Stack<Tree> stack = new Stack<>();
    if (root == null){
      return;
    }
    while (true){
      while (root != null){
        stack.push(root);
        root = root.getLeft();
      }
      if (stack.isEmpty()){
        break;
      }else {
        root = stack.pop();
        System.out.println(root.getData() + " ");
        root = root.getRight();
      }
    }
  }

    private static void fact1(Tree root) {
      if (root == null){
        return;
      }
      Stack<Tree> stack = new Stack<>();
      // 这个while(true)真的牛批
      while (true) {
        while (root != null) {
          System.out.print(root.getData() + " ");
          stack.push(root);
          root = root.getLeft();
        }
        if (stack.isEmpty()) {
          break;
        }else {
          root = stack.pop();
          root = root.getRight();
        }
      }

    }

}
