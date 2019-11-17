package com.shuai.datastructure.递归与回溯;

public class TowersOfHanoi {

  private static void fact(int n, char from, char to, char middle) {
    if (n == 1) {
      System.out.println("将disk1从柱子" + from + "移到柱子" + to);
    }else {

      fact(n - 1, from, middle, to);
      System.out.println("将disk" + n + "从柱子" + from + "移到柱子" + to);
      fact(n - 1, middle, to, from);
    }
  }

  public static void main(String[] args) {
    char a = 'a';
    char b = 'b';
    char c = 'c';
    fact(4, a, b, c);
  }


}
