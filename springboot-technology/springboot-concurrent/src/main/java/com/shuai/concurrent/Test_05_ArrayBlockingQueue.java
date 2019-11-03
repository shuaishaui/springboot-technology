/**
 * 并发容器 - ArrayBlockingQueue
 *  有界容器。
 */
package com.shuai.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Test_05_ArrayBlockingQueue {
	
	final BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
	
	public static void main(String[] args) {
		final Test_05_ArrayBlockingQueue t = new Test_05_ArrayBlockingQueue();
		
		for(int i = 0; i < 5; i++){
			// System.out.println("add method : " + t.queue.add("value"+i));
			/*try {
				t.queue.put("put"+i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("put method : " + i);*/
			// System.out.println("offer method : " + t.queue.offer("value"+i));
			try {
				System.out.println("offer method : " + 
							t.queue.offer("value"+i, 1, TimeUnit.SECONDS));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(t.queue);
	}

}
