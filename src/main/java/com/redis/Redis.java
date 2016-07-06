package com.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Component
public class Redis {
	@Autowired
	private ShardedJedisPool shardedJedisPool;

	public ShardedJedis getRedis() {
		return shardedJedisPool.getResource();// 获取jedis实例
	}

	public void close(ShardedJedis jedis) {// 释放jedis资源
		shardedJedisPool.returnResource(jedis);
	}
}
