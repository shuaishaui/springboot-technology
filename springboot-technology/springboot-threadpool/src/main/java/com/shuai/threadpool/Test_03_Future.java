/**
 * 线程池
 * 固定容量线程池
 */
package com.shuai.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Test_03_Future {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		/*FutureTask<String> task = new FutureTask<>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "first future task";
			}
		});
		
		new Thread(task).start();
		
		System.out.println(task.get());*/
		
		ExecutorService service = Executors.newFixedThreadPool(1);
		
		Future<String> future = service.submit(new Callable<String>() {
			@Override
			public String call() {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("aaa");
				return Thread.currentThread().getName() + " - test executor";
			}
		});
		System.out.println(future);
		System.out.println(future.isDone()); // 查看线程是否结束， 任务是否完成。 call方法是否执行结束
		
		System.out.println(future.get()); // 获取call方法的返回值。
		System.out.println(future.isDone());
	}

}
