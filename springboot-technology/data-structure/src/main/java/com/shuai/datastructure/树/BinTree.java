package com.shuai.datastructure.树;

import java.util.ArrayList;
import java.util.List;

public class BinTree {

  private BinTree lChild;//左孩子
  private BinTree rChild;//右孩子
  private BinTree root;//根节点
  private Object data; //数据域
  private List<BinTree> datas;//存储所有的节点

  public BinTree getlChild() {
    return lChild;
  }

  public void setlChild(BinTree lChild) {
    this.lChild = lChild;
  }

  public BinTree getrChild() {
    return rChild;
  }

  public void setrChild(BinTree rChild) {
    this.rChild = rChild;
  }

  public void setRoot(BinTree root) {
    this.root = root;
  }

  public BinTree(BinTree lChild, BinTree rChild, Object data) {
    this.lChild = lChild;
    this.rChild = rChild;
    this.data = data;
  }

  public BinTree(Object data) {
    this(null, null, data);
  }

  public BinTree() {
  }

  public void createTree(Object[] objs) {
    datas = new ArrayList<BinTree>();
    for (Object object : objs) {
      datas.add(new BinTree(object));
    }
    root = datas.get(0);//将第一个作为根节点
    for (int i = 0; i < objs.length / 2; i++) {
      datas.get(i).lChild = datas.get(i * 2 + 1);
      if (i * 2 + 2 < datas.size()) {//避免偶数的时候 下标越界
        datas.get(i).rChild = datas.get(i * 2 + 2);
      }
    }
  }

  //先序遍历
  public void preorder(BinTree root) {
    if (root != null) {
      visit(root.getData());
      preorder(root.lChild);
      preorder(root.rChild);
    }

  }

  //中序遍历
  public void inorder(BinTree root) {
    if (root != null) {
      inorder(root.lChild);
      visit(root.getData());
      inorder(root.rChild);
    }

  }

  //后序遍历
  public void afterorder(BinTree root) {
    if (root != null) {
      afterorder(root.lChild);
      afterorder(root.rChild);
      visit(root.getData());
    }

  }

  private void visit(Object obj) {
    System.out.print(obj + " ");
  }

  public Object getData() {
    return data;
  }

  public BinTree getRoot() {
    return root;
  }



}