package com.shuai.datastructure.队列;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 问题10给定一个整数太和一个整数队列，如何把队列中前及个元素逆置，其余的
 完素次序候持不变？例如、如果等于4、队列中元素序列为[10、20、30，40，50，60，
 0、S8、90]、那会应该输出[40、30、20、10、50、60.70,80,90]、
 */
public class Reversal {

  private static void reverse(int k,Queue<Integer> q){
    if (q == null || k > q.size()){
      throw new IllegalArgumentException();
    }else if (k > 0){
      // 加入一个栈来保存前k个元素，再运用栈的先进后出特性，进行k的反转
      Stack<Integer> s = new Stack<>();
      for (int i = 0; i < k; i++) {
        s.push(q.remove());
      }while (!s.isEmpty()){
        q.add(s.pop());
      }

      for (int i = 0; i < q.size() - k; i++) {
        // 特别注意这条语句，队列的特性，一边出，一边入，这样就把前size-k个元素加到后面去了
        q.add(q.remove());
      }
    }
  }

  public static void main(String[] args) {
    Queue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
    queue.add(10);
    queue.add(20);
    queue.add(30);
    queue.add(40);
    queue.add(50);
    queue.add(60);
    queue.add(70);
    queue.add(80);

    reverse(4,queue );

    System.out.println(queue.toString());
  }
}
