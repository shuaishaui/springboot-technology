package com.shuai.datastructure.链式存储结构;

import java.io.Serializable;
import java.util.TreeMap;

public class Node implements Serializable {

  public static void main(String[] args) {
    Node node = new Node();
    System.out.println(node instanceof Serializable);
  }

  class ListNode{
    private int data;
    private ListNode next;

    public ListNode(int data) {
      this.data = data;
    }

    public int getData() {
      return data;
    }

    public void setData(int data) {
      this.data = data;
    }

    public ListNode getNext() {
      return next;
    }

    public void setNext(ListNode next) {
      this.next = next;
    }
  }

  class DLLNode{
    private int data;
    private DLLNode next;
    private DLLNode previous;

    public DLLNode(int data) {
      this.data = data;
    }

    public int getData() {
      return data;
    }

    public void setData(int data) {
      this.data = data;
    }

    public DLLNode getNext() {
      return next;
    }

    public void setNext(DLLNode next) {
      this.next = next;
    }

    public DLLNode getPrevious() {
      return previous;
    }

    public void setPrevious(DLLNode previous) {
      this.previous = previous;
    }
  }

  class BinaryTreeNode{
    private int data;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    public BinaryTreeNode(int data) {
      this.data = data;
    }

    public BinaryTreeNode() {
    }

    public int getData() {
      return data;
    }

    public void setData(int data) {
      this.data = data;
    }

    public BinaryTreeNode getLeft() {
      return left;
    }

    public void setLeft(BinaryTreeNode left) {
      this.left = left;
    }

    public BinaryTreeNode getRight() {
      return right;
    }

    public void setRight(BinaryTreeNode right) {
      this.right = right;
    }
  }
}
