package com.shuai.datastructure.排序;

public class BubbleSort {

  // 冒泡排序
  private static void bubble(int[] a, int n) {
    int count = 0;
    for (int pass = n - 1; pass >= 0; pass--) {
      for (int i = 0; i < pass - 1; i++) {
        if (a[i] > a[i + 1]) {
          int temp = a[i];
          a[i] = a[i + 1];
          a[i + 1] = temp;
          count++;
        }
      }
    }
    System.out.println("count:" + count);
  }

  public static void main(String[] args) {
    int[] a = {4,1,2,3,4,5};
    bubble(a, a.length);
    for (int i : a) {
      System.out.print(i + " ");
    }

    System.out.println("");

    int[] b = {4,1,2,3,4,5};
    bubble1(b, b.length);
    for (int i : b) {
      System.out.print(i + " ");
    }
  }

  // 冒泡排序的改进，增加一个标志：若没有发生交换，表示原有序列就是有序的
  private static void bubble1(int[] a, int n) {
    int count = 0;
    boolean swapped = true;
    for (int pass = n - 1; pass >= 0 && swapped; pass--) {
      swapped = false;
      for (int i = 0; i < pass - 1; i++) {
        if (a[i] > a[i + 1]) {
          int temp = a[i];
          a[i] = a[i + 1];
          a[i + 1] = temp;
          swapped = true;
          count++;
        }
      }
    }
    System.out.println("count:" + count);
  }

  // 希尔排序
  static void shellSort(int[] a,int size){
    int i,j,h,v;
//    for (h = 1;h =size/9;h=3*h + 1);
  }
}
