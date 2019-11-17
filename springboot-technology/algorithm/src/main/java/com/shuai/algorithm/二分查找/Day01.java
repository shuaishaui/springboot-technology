package com.shuai.algorithm.二分查找;

/**
 * leetcode-81 链接：https://leetcode.com/problems/search-in-rotated-sorted-array-ii
 *
 * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
 *
 * ( 例如，数组 [0,0,1,2,2,5,6] 可能变为 [2,5,6,0,0,1,2] )。
 *
 * 编写一个函数来判断给定的目标值是否存在于数组中。若存在返回 true，否则返回 false。
 *
 * 示例 1:
 *
 * 输入: nums = [2,5,6,0,0,1,2], target = 0输出: true 示例 2:
 *
 * 输入: nums = [2,5,6,0,0,1,2], target = 3输出: false 进阶:
 *
 * 这是 搜索旋转排序数组 的延伸题目，本题中的 nums  可能包含重复元素。
 *
 * 这会影响到程序的时间复杂度吗？会有怎样的影响，为什么？
 */
public class Day01 {

  public boolean search(int[] nums, int target) {
    int i = nums.length;
    // 判断数组为空的情况
    if (i == 0){
      return false;
    }


    // 首先判断队首和队尾
    if (nums[0] == target || nums[i-1] == target){
      return true;
    }

    // 现在从队首开始
    if (target > nums[0]){
      for (int i1 = 1; i1 < nums.length; i1++) {
        // 如果比nums[0]小，说明已经到了第二队
        if (nums[0] > nums[i1]){
          return false;
        }
        if (target == nums[i1]){
          return true;
        }
      }
    }

    // 现在开始从队尾比较
    if (target < nums[i-1]){
      for (int i1 = nums.length - 2; i1 >= 0; i1--) {
        // 说明已经到了第一对
        if (nums[i-1] < nums[i1]){
          return false;
        }
        if (target == nums[i1]){
          return true;
        }
      }
    }
    return false;
  }



}
