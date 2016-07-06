package com.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import com.util.LogUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestRedis {
	private Logger log = LogUtil.getLog();
	JedisPool pool;
	Jedis jedis;
	@Before
	public void setUp() {
		// 此处IP是本机IP地址
		JedisPoolConfig config = new JedisPoolConfig();  
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
        config.setMaxActive(500);  
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
        config.setMaxIdle(5);  
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
        config.setMaxWait(1000 * 100);  
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
        config.setTestOnBorrow(true);  
		pool = new JedisPool(config, "127.0.0.1", 6379);
//		pool.returnResource(jedis);
		jedis = pool.getResource();
		if(jedis==null){
			log.info("未获取到了jedis实例");
		}
		log.info("创建jedisPool成功，并获取到了jedis实例");
		// jedis.auth("password");
	}

	@Test
	public void testGet() {
		System.out.println(jedis.get("xm"));
	}
	@Test
	public void testSetKey() {
		System.out.println(jedis.set("dy","dingyi"));
		System.out.println(jedis.set("xm","小明"));
		
	}
	/**
	 * Redis存储初级的字符串 CRUD
	 */
	@Test
	public void testBasicString() {
		// -----添加数据----------
		jedis.set("name", "minxr");// 向key-->name中放入了value-->minxr
		System.out.println(jedis.get("name"));// 执行结果：minxr
		// -----修改数据-----------
		// 1、在原来基础上添加
		jedis.append("name", "jarorwar"); // 很直观，类似map 将jarorwar
											// append到已经有的value之后
		System.out.println(jedis.get("name"));// 执行结果:minxrjarorwar
		// 2、直接覆盖原来的数据
		jedis.set("name", "闵晓荣");
		System.out.println(jedis.get("name"));// 执行结果：闵晓荣
		// 删除key对应的记录
		jedis.del("name");
		System.out.println(jedis.get("name"));// 执行结果：null
		/**
		 * mset相当于 jedis.set("name","minxr"); jedis.set("jarorwar","闵晓荣");
		 */
		jedis.mset("name", "minxr", "jarorwar", "闵晓荣","user","李四");
		System.out.println(jedis.mget("name", "jarorwar","user"));
	}

	/**
	 * jedis操作Map
	 */
	@Test
	public void testMap() {
		Map<String, String> us = new HashMap<String, String>();
		us.put("s1", "minxr");
		us.put("pwd", "pwd");
		jedis.hmset("us", us);
		// 取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		// 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
		List<String> rsmap = jedis.hmget("us", "s1");
		System.out.println(rsmap);
		// 删除map中的某个键值
		// jedis.hdel("user","pwd");
		System.out.println(jedis.hmget("us", "pwd")); // 因为删除了，所以返回的是null
		System.out.println(jedis.hlen("us")); // 返回key为user的键中存放的值的个数1
		System.out.println(jedis.exists("us"));// 是否存在key为user的记录 返回true
		System.out.println(jedis.hkeys("us"));// 返回map对象中的所有key [pwd, name]
		System.out.println(jedis.hvals("us"));// 返回map对象中的所有value [minxr,
												// password]
		Iterator<String> iter = jedis.hkeys("us").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("us", key));
		}
	}

	/**
	 * jedis操作List
	 */
	@Test
	public void testList() {
		// 开始前，先移除所有的内容
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		// 先向key java framework中存放三条数据
		jedis.lpush("java framework", "spring");
		jedis.lpush("java framework", "struts");
		jedis.lpush("java framework", "hibernate");
		// 再取出所有数据jedis.lrange是按范围取出，
		// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
		System.out.println("测试list");
		System.out.println(jedis.lrange("java framework", 2, 2));
	}

	/**
	 * jedis操作Set
	 */
	@Test
	public void testSet() {
		// 添加
		jedis.sadd("sname", "minxr");
		jedis.sadd("sname", "jarorwar");
		jedis.sadd("sname", "闵晓荣");
		jedis.sadd("sanme", "noname");
		// 移除noname
		jedis.srem("sname", "noname");
		System.out.println(jedis.smembers("sname"));// 获取所有加入的value
		System.out.println(jedis.sismember("sname", "minxr"));// 判断 minxr
																// 是否是sname集合的元素
		System.out.println(jedis.srandmember("sname"));
		System.out.println(jedis.scard("sname"));// 返回集合的元素个数
	}

	@Test
	public void test() throws InterruptedException {
		// keys中传入的可以用通配符
		System.out.println(jedis.keys("*")); // 返回当前库中所有的key [sose, sanme, name,
												// jarorwar, foo, sname, java
												// framework, user, braand]
		System.out.println(jedis.keys("*name"));// 返回的sname [sname, name]
		System.out.println(jedis.del("sanmdde"));// 删除key为sanmdde的对象 删除成功返回1
													// 删除失败（或者不存在）返回 0
		System.out.println(jedis.ttl("sname"));// 返回给定key的有效时间，如果是-1则表示永远有效
		jedis.setex("timekey", 10, "张三");// 通过此方法，可以指定key的存活（有效时间） 时间为秒
		Thread.sleep(5000);// 睡眠5秒后，剩余时间将为<=5
		System.out.println(jedis.ttl("timekey")); // 输出结果为5
		jedis.setex("timekey", 1, "李四"); // 设为1后，下面再看剩余时间就是1了
		System.out.println(jedis.ttl("timekey")); // 输出结果为1
		System.out.println(jedis.exists("key"));// 检查key是否存在
		// System.out.println(jedis.rename("timekey","time"));
		System.out.println(jedis.get("timekey"));// 因为移除，返回为null
		System.out.println(jedis.get("time")); // 因为将timekey 重命名为time 所以可以取得值
												// min
		// jedis 排序
		// 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
		jedis.del("a");// 先清除数据，再加入数据进行测试
		jedis.rpush("a", "1");
		jedis.lpush("a", "6");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");
		System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
		System.out.println(jedis.sort("a")); // [1, 3, 6, 9] //输出排序后结果
		System.out.println(jedis.lrange("a", 0, -1));
	}
}
