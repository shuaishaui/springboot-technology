/**
 * 并发容器 - ConcurrentLinkedQueue
 *  队列 - 链表实现的。 
 */
package com.shuai.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Test_03_ConcurrentLinkedQueue {
	
	public static void main(String[] args) {
		Queue<String> queue = new ConcurrentLinkedQueue<>();
		for(int i = 0; i < 10; i++){
			queue.offer("value" + i);
		}
		
		System.out.println(queue);
		System.out.println(queue.size());
		
		// peek() -> 查看queue中的首数据
		System.out.println(queue.peek());
		System.out.println(queue.size());
		
		// poll() -> 获取queue中的首数据
		System.out.println(queue.poll());
		System.out.println(queue.size());
	}

}
