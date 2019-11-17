package com.shuai.datastructure.树;


public class DemoTree {

  private int data;
  private DemoTree left;
  private DemoTree right;

  public DemoTree(int data, DemoTree left, DemoTree right) {
    this.data = data;
    this.left = left;
    this.right = right;
  }

  // 踏马德，这个地方不可以直接调用上面的那个构造函数，不然赋不了值
  public DemoTree(int data) {
//   new DemoTree(data,null,null);
    this.data = data;
  }

  public DemoTree() {
  }

  public int getData() {
    return data;
  }

  public void setData(int data) {
    this.data = data;
  }

  public DemoTree getLeft() {
    return left;
  }

  public void setLeft(DemoTree left) {
    this.left = left;
  }

  public DemoTree getRight() {
    return right;
  }

  public void setRight(DemoTree right) {
    this.right = right;
  }
}
