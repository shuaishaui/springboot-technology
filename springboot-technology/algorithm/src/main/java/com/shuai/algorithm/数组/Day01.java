package com.shuai.algorithm.数组;


import java.util.Arrays;


/**
 * https://leetcode-cn.com/problems/sort-array-by-parity/description/
 给定一个非负整数数组 A，返回一个数组，在该数组中， A 的所有偶数元素之后跟着所有奇数元素。
 你可以返回满足此条件的任何数组作为答案。
 示例：

 输入：[3,1,2,4]
 输出：[2,4,3,1]
 输出 [4,2,3,1]，[2,4,1,3] 和 [4,2,1,3] 也会被接受。
  
 提示：
 1 <= A.length <= 5000
 0 <= A[i] <= 5000
 */
public class Day01 {

  private  int[] sort(int[] a){

    int i = a.length;



    return null;
  }

  public int[] sortArrayByParity(int[] A) {

      Integer[] B = new Integer[A.length];
      for (int t = 0; t < A.length; ++t) {
        B[t] = A[t];
      }
      Arrays.sort(B, (a, b) -> Integer.compare(a%2, b%2));

      for (int t = 0; t < A.length; ++t) {
        A[t] = B[t];
      }
      return A;
  }

  public static void main(String[] args) {
    int[] a = {12,11,13,2,4,5,3,65};
    Day01 day01 = new Day01();
    for (int i = a.length - 1; i >= 0; i--) {
      System.out.print(" " + day01.sortArrayByParity(a)[i] + " ");
    }

    System.out.println("");
    System.out.println("===========");

    Arrays.sort(a);
    for (int i = a.length - 1; i >= 0; i--) {
      System.out.print(" " + a[i] + " ");
    }

    System.out.println("");
    System.out.println("===========");

    Integer[] b = {12,11,13,2,4,5,3,65};
    Arrays.sort(b,(x,y) -> Integer.compare(y,x)); // 不可以为int类型
    for (int i = b.length - 1; i >= 0; i--) {
      System.out.print(" " + b[i] + " ");
    }
  }
}
