package com.shuai.datastructure.递归与回溯;

public class Demo1 {

  private  static int fact(int n){
    if (n == 0 || n == 1){
      return 1;
    }
    else {
      return n *fact(n-1);
    }
  }

  public static void main(String[] args) {
    System.out.println(fact(5));
  }

}
