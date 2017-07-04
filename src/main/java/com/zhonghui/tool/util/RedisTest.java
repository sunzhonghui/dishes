package com.zhonghui.tool.util;

import redis.clients.jedis.Jedis;

public class RedisTest {

		public static void main(String[] args) {
			
			
			Jedis redis=new Jedis("localhost",6379);
			System.out.println(redis.ping());
//			redis.set("k1","v1");
//			redis.set("k2","v2");
//			redis.set("k3","v3");
			System.out.println(redis.exists("k1"));
			System.out.println(redis.move("k3",1));
			System.out.println(redis.keys("*"));
			System.out.println(redis.select(1));
			System.out.println(redis.keys("*"));
			
			
		}
}
