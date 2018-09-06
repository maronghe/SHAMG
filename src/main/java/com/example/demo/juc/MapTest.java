package com.example.demo.juc;

import com.example.demo.vo.User;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapTest {
	public static void main(String[] args) {
		Map<String, String> commonMap = new HashMap<>(5);
		commonMap.put("sq","2");
		commonMap.put("asd","1");
		commonMap.put("asa","0");
		commonMap.put("vac","1");

		for(Map.Entry<String, String> entry : commonMap.entrySet()){
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

		// treeMap sort by key.
		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("maronghe","2");
		treeMap.put("abxc","1");
		treeMap.put("abasd","0");
		treeMap.put("qe","1");
		System.out.println("=============");
		for(Map.Entry<String, String> entry : treeMap.entrySet()){
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

		Set<String> treeSet = new TreeSet<String>();
		treeSet.add("abc");
		treeSet.add("123");
		treeSet.add("ac23");
		treeSet.add("bdc");
		System.out.println("------------");
		List<String> list = treeSet.stream().filter(e -> e instanceof String).map(String::toUpperCase).collect(Collectors.toList());
		list.forEach(System.out :: println);


		// =============================
		List<User> userList = Arrays.asList(
				new User("52","name"),
				new User("123","asdsd")
				);
		System.out.println("--------------");
		List<String> userNameList = userList.stream().map(e -> e.getName()).collect(Collectors.toList());
		userNameList.stream().forEach(System.out::println);



		// ========================
		String[][] data = new String[][]{
				{"a","b"},
				{"c","d"}
		};

		// stream temp
		Stream<String[]> temp = Arrays.stream(data);

		// filter a stream of String, and return a stringp[] ?
//		Stream<String[]> stream123 = temp.filter(x -> "a".equalsIgnoreCase(x.toString()));
//		stream123.forEach(System.out::println);


		temp.flatMap(x -> Arrays.stream(x)).filter(x -> x instanceof String).forEach(System.out::println);







	}



}
