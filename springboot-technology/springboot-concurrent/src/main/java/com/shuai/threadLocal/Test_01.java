/**
 * ThreadLocal
	 * 就是一个Map。key - 》 Thread.getCurrentThread().  value - 》 线程需要保存的变量。
 * ThreadLocal.set(value) -> map.put(Thread.getCurrentThread(), value);
 * ThreadLocal.get() -> map.get(Thread.getCurrentThread());
 * 内存问题 ： 在并发量高的时候，可能有内存溢出。
 * 使用ThreadLocal的时候，一定注意回收资源问题，每个线程结束之前，将当前线程保存的线程变量一定要删除 。
 * ThreadLocal.remove();
 */
package com.shuai.threadLocal;

import java.util.concurrent.TimeUnit;

public class Test_01 {

	volatile static String name = "zhangsan";
	static ThreadLocal<String> tl = new ThreadLocal<>();
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(name);
				System.out.println(tl.get());
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				name = "lisi";
				tl.set("wangwu");
			}
		}).start();
	}
	
}
