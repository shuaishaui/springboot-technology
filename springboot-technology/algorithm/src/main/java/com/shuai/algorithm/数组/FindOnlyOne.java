package com.shuai.algorithm.数组;

public class FindOnlyOne {
 private static int findOnlyOne(int[] nums){
   int ans = nums[0];
     for (int i = 1; i < nums.length; i++) {
       ans = ans ^ nums[i];
   }
   return ans;
 }

  public static void main(String[] args) {
    int[] nums = {1,2,3,3,2,1,1};
    System.out.println(findOnlyOne(nums));
  }
}
