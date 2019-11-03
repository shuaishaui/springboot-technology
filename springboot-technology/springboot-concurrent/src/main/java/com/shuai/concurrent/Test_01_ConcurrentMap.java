/**
 * 并发容器 - ConcurrentMap
 */
package com.shuai.concurrent;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Test_01_ConcurrentMap {
	
	public static void main(String[] args) {
		// 比较这三个的运行时间，测试时打开对应的
		final Map<String, String> map = new Hashtable<>();
		// final Map<String, String> map = new ConcurrentHashMap<>();
		// final Map<String, String> map = new ConcurrentSkipListMap<>();
		final Random r = new Random();
		Thread[] array = new Thread[100];
		final CountDownLatch latch = new CountDownLatch(array.length);
		
		long begin = System.currentTimeMillis();
		for(int i = 0; i < array.length; i++){
			array[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j = 0; j < 10000; j++){
						map.put("key"+r.nextInt(100000), "value"+r.nextInt(100000));
					}
					latch.countDown();
				}
			});
		}
		for(Thread t : array){
			t.start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("执行时间为 ： " + (end-begin) + "毫秒！");
	}

}
