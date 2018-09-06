package com.example.demo.concurrence;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * My Redis test
 * @author Logan
 */
public class MyRedistest {

	public static void main(String[] args) {

		final String watchKeys = "watchKeys";

		// 创建20个线程
		ExecutorService executorService = Executors.newFixedThreadPool(40); // 40个线程并发执行

		// 创建jedis对象
		Jedis jedis = new Jedis("127.0.0.1", 6379,100000);
		System.out.println(jedis.ping());
//		JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost");
		jedis.set(watchKeys, "100");// 起始库存100
		jedis.close();

		// 10000人并发抢购
		for (int i = 0; i < 10000; i++) {
			executorService.execute(new MyRedisRunnable("user" + getRandomString(6)));
		}
	}
	public static String getRandomString(int length) { //length是随机字符串长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
class MyRedisRunnable implements Runnable{

	final String watchys = "watchKeys";

	Jedis jedis = new Jedis("127.0.0.1", 6379);

	String userInfo;

	public MyRedisRunnable() {
	}

	public MyRedisRunnable(String userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public void run() {
		try {

			jedis.watch(watchys);

			String val = jedis.get(watchys);
			int valint = Integer.valueOf(val);
			if (valint <= 100 && valint >= 1){
				Transaction tx = jedis.multi(); // 开启事务
				tx.decrBy(watchys,1);
				List<Object> list = tx.exec();

				if(list == null || list.size() == 0){
					String failUserInfo = "Fail : " + userInfo;
					String failInfo = "用户：" + failUserInfo + "抢购失败";
					System.out.println(failInfo);
					// 抢购失败业务逻辑
					jedis.setnx(failUserInfo,failInfo);
				}else {
					for (Object successObj : list) {
						String successUserInfo = "Success:" + successObj.toString() + userInfo;
						String successInfo = "用户：" +  successUserInfo + "抢购成功，成功人数：" + (1 - (valint - 100));
						System.out.println(successInfo);
						jedis.setnx(successUserInfo, userInfo);
					}
				}
			}else {
				String failUserInfo = "kcfail" + userInfo;
				String failInfo = "用户:"+ failUserInfo +"抢购失败, 库存不足";
				System.out.println(failInfo);
				jedis.setnx(failUserInfo, failInfo);
				return;
			}

		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
}

