package com.example.demo.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {


	public static void main(String[] args) throws InterruptedException {
		CopyOnWriteArrayList<Integer>  list = new CopyOnWriteArrayList<>();
//		List<Integer>  list = new ArrayList<>();
		ExecutorService executorService = Executors.newFixedThreadPool(200);
		list.add(0);
		System.gc();

		// 模拟50个线程
		for (int i = 0; i < 101; i++) {
			int finalI = i;
			executorService.execute(()->{
				list.add(0);
			});

			executorService.execute(()->{
				if(list.size() > 0){
					synchronized (Test.class){
						list.remove(0);
					}
				}
			});
//			Thread.sleep(100)
		}

		System.out.println(list.size());
//		executorService.awaitTermination(10, TimeUnit.SECONDS);

		Future<Integer> future = executorService.submit(()->{
			return 1;
		});
		try {
			System.out.println(future.get());
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println(executorService.awaitTermination(0,TimeUnit.SECONDS));
		executorService.shutdown();
		System.out.println(executorService.awaitTermination(0,TimeUnit.SECONDS));
		executorService.shutdownNow();
		System.out.println(executorService.awaitTermination(0,TimeUnit.SECONDS));
	}

	public void print() throws InterruptedException {
		Lock lock = new ReentrantLock();
		lock.lock();
		lock.tryLock();
		lock.tryLock(10,TimeUnit.SECONDS);
		lock.lockInterruptibly();
		try {

		}catch (Exception e){

		}finally {
			lock.unlock();
		}
	}

}
