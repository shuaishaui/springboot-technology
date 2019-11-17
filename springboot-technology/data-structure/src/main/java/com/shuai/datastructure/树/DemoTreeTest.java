package com.shuai.datastructure.树;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DemoTreeTest {

  private static DemoTree tree = new DemoTree();

  // 给二叉树赋值
  private static void create12(int[] obj) {
    List<DemoTree> list = new ArrayList<>();
    for (int i : obj) {
      list.add(new DemoTree(i));
    }
    tree = list.get(0);
    for (int i = 0; i < obj.length / 2; i++) {
      list.get(i).setLeft(list.get(i * 2 + 1));
      if (i * 2 + 2 < obj.length) {
        list.get(i).setRight(list.get(i * 2 + 2));
      }
    }
  }

  // 非递归中序遍历
  private static void fact(DemoTree root) {
    // 使用栈来保存左孩子
    Stack<DemoTree> stack = new Stack<>();

    if (root == null) {
      return;
    }

    while (true) {
      while (root != null) {
        stack.push(root);
        root = root.getLeft();
      }
      if (stack.isEmpty()) {
        break;
      } else {
        root = stack.pop();
        System.out.print(root.getData() + " ");
        root = root.getRight();
      }
    }
  }

  public static void main(String[] args) {
    int[] objs = {1, 2, 3, 4, 5, 6, 7};
    create12(objs);
    fact(tree);
    System.out.println("");
    System.out.println("=========");
    postOrder(tree);
    System.out.println("");
    System.out.println("=========");
    levels(tree);
    System.out.println("");
    System.out.println("=========");
    System.out.println(findMax(tree));
    System.out.println("=========");
    System.out.println(size(tree));
  }

  // 非递归后序遍历
  // 这里有一点不同，前和中，当元素出栈后就不需要再次访问该节点了，而后序需要访问两次
  // 利用栈回退到时，并不知道是从左子树回退到根节点，还是从右子树回退到根节点，如果从左子树回退到根节点，
  // 此时就应该去访问右子树，而如果从右子树回退到根节点，此时就应该访问根节点
  private static void postOrder(DemoTree root) {
    int left = 1;//在辅助栈里表示左节点
    int right = 2;//在辅助栈里表示右节点
    Stack<DemoTree> stack = new Stack<DemoTree>();
    Stack<Integer> stack2 = new Stack<Integer>();//辅助栈，用来判断子节点返回父节点时处于左节点还是右节点。

    while (root != null|| !stack.empty()) {
      while (root != null) {//将节点压入栈1，并在栈2将节点标记为左节点
        stack.push(root);
        stack2.push(left);
        root = root.getLeft();
      }

      while (!stack.empty() && stack2.peek() == right) {//如果是从右子节点返回父节点，则任务完成，将两个栈的栈顶弹出
        stack2.pop();
        System.out.print(stack.pop().getData() + " ");
      }

      if (!stack.empty() && stack2.peek() == left) {//如果是从左子节点返回父节点，则将标记改为右子节点
        stack2.pop();
        stack2.push(right);
        root = stack.peek().getRight(); // 这里如果 root 不为null，就会跳到上面的while循环那里，入栈
      }
    }
  }

  // 层次遍历
  // 使用queue来保存下一层的节点信息
  private static void levels(DemoTree root){
    Queue<DemoTree> queue = new ConcurrentLinkedQueue<>();
    DemoTree temp; // 临时节点

    if (root == null){
      return;
    }
    queue.add(root);
    while (!queue.isEmpty()){
      temp = queue.poll();
      System.out.print(temp.getData() + " ");
      if (temp.getLeft() != null){
        queue.add(temp.getLeft());
      }
      if (temp.getRight() != null){
        queue.add(temp.getRight());
      }
    }
  }

  // 递归找最大元素
  private  static int findMax(DemoTree root){
    int rootVal,leftVal,rightVal,max = 0;
    if (root != null){
      rootVal = root.getData();
      leftVal = findMax(root.getLeft());
      rightVal = findMax(root.getRight());
      max = leftVal > rightVal ? leftVal : rightVal;
      max = max > rootVal ? max : rootVal;
    }
    return max;
  }

  // 获取节点数
  static int size(DemoTree root){
    if (root == null){
      return 0;
    }
    return (size(root.getLeft()) + 1 + size(root.getRight()));
  }
}
