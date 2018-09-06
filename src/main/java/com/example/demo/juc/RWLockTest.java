package com.example.demo.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockTest {
	ReadWriteLock lock = new ReentrantReadWriteLock(true);
	Lock lock2 = new ReentrantLock(false);

	public static void main(String[] args) {

		RWLockTest test = new RWLockTest();
		new Thread(()->{
			test.read(Thread.currentThread());
		}).start();

		new Thread(()->{
			test.read(Thread.currentThread());
		}).start();

	}

	public void read(Thread thread){
		lock.writeLock().lock();
		try {
			long start = System.currentTimeMillis();
			while(System.currentTimeMillis() - start  <= 1){
				System.out.println(thread.getName() + " Reading...");
			}
			System.out.println( thread.getName() + "读取完成");
		}finally {
			System.out.println( thread.getName() + "释放锁-----");
			lock.writeLock().unlock();
		}


	}
}
