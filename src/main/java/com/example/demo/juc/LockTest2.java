package com.example.demo.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest2 {
	List<Integer> list = new ArrayList<>();
	public static void main(String[] args) {

		LockTest2 test = new LockTest2();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		executorService.execute(()->{
			test.insert(Thread.currentThread());
		});
		executorService.execute(()->{
			test.insert(Thread.currentThread());
		});
		executorService.execute(()->{
			test.insert(Thread.currentThread());
		});
		executorService.execute(()->{
			test.insert(Thread.currentThread());
		});
		executorService.execute(()->{
			test.insert(Thread.currentThread());
		});
		executorService.execute(()->{
			test.insert(Thread.currentThread());
		});
		executorService.execute(()->{
			test.insert(Thread.currentThread());
		});
		executorService.execute(()->{
			test.insert(Thread.currentThread());
		});


		executorService.shutdown();
	}
	Lock lock = new ReentrantLock();
	private void insert(Thread thread) {
		if (lock.tryLock()){
			try {
				System.out.println(thread.getName() + " 获取到锁- ");
				for (int i = 0; i < 500; i++) {
					list.add(i);
				}
			} finally {
				System.out.println(thread.getName() + " 释放到锁 ");
				lock.unlock();
			}
		}else {
			System.out.println(thread.getName() + "获取锁失败 Error");
		}
	}

}
