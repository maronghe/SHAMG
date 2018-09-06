package com.example.demo.juc;

import java.util.*;
import java.util.concurrent.*;


public class OtherMaps {

	public static void main(String[] args) {
		// this is low way.
		//Collections.synchronizedList(new ArrayList<>());
		List list = new CopyOnWriteArrayList();
		Set<String> set = new CopyOnWriteArraySet<String>();
		Set<Set> treeSet = new ConcurrentSkipListSet<>();


		//	Collections.synchronizedMap(new HashMap<>());
		Map map = new ConcurrentHashMap();
		Map treeMap = new ConcurrentSkipListMap();


		Queue<String> queue = new ArrayBlockingQueue<String>(20);
//		queue.add("123");
//		queue.add("324");
//		System.out.println(queue.peek());
//		System.out.println(queue.poll());
//		System.out.println(queue.poll());

		Queue dequeue = new LinkedBlockingDeque(10);

	}
}
