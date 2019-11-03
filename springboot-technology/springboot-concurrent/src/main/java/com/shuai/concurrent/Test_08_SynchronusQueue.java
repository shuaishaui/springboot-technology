/**
 * 并发容器 - SynchronousQueue
 */
package com.shuai.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class Test_08_SynchronusQueue {
	
	BlockingQueue<String> queue = new SynchronousQueue<>();
	
	public static void main(String[] args) {
		final Test_08_SynchronusQueue t = new Test_08_SynchronusQueue();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + " thread begin " );
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + " - " + t.queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "output thread").start();
		
		/*try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		// t.queue.add("test add");
		try {
			t.queue.put("test put");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName() + " queue size : " + t.queue.size());
	}

}
