/**
 * 线程池
 * 固定容量线程池, 简单应用
 */
package com.shuai.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test_04_ParallelComputingWithFixedThreadPool {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long start = System.currentTimeMillis();
		computing(1, 2000000);
		long end = System.currentTimeMillis();
		System.out.println("computing times : " + (end - start));
		
		ExecutorService service = Executors.newFixedThreadPool(5);
		
		ComputingTask t1 = new ComputingTask(1, 60000);
		ComputingTask t2 = new ComputingTask(60001, 110000);
		ComputingTask t3 = new ComputingTask(110001, 150000);
		ComputingTask t4 = new ComputingTask(150001, 180000);
		ComputingTask t5 = new ComputingTask(180001, 200000);
		
		Future<List<Integer>> f1 = service.submit(t1);
		Future<List<Integer>> f2 = service.submit(t2);
		Future<List<Integer>> f3 = service.submit(t3);
		Future<List<Integer>> f4 = service.submit(t4);
		Future<List<Integer>> f5 = service.submit(t5);
		
		start = System.currentTimeMillis();
		f1.get();
		f2.get();
		f3.get();
		f4.get();
		f5.get();
		end = System.currentTimeMillis();
		System.out.println("parallel computing times : " + (end - start));
		
	}
	
	static class ComputingTask implements Callable<List<Integer>>{
		int start, end;
		public ComputingTask(int start, int end){
			this.start = start;
			this.end = end;
		}
		public List<Integer> call() throws Exception{
			List<Integer> results = new ArrayList<>();
			boolean isPrime = true;
			for(int i = start; i <= end; i++){
				for(int j = 1; j < Math.sqrt(i); j++){
					if(i % j == 0){
						isPrime = false;
						break;
					}
				}
				if(isPrime){
					results.add(i);
				}
				isPrime = true;
			}
			
			return results;
		}
	}
	
	private static List<Integer> computing(Integer start, Integer end){
		List<Integer> results = new ArrayList<>();
		boolean isPrime = true;
		for(int i = start; i <= end; i++){
			for(int j = 1; j < Math.sqrt(i); j++){
				if(i % j == 0){
					isPrime = false;
					break;
				}
			}
			if(isPrime){
				results.add(i);
			}
			isPrime = true;
		}
		
		return results;
	}

}
