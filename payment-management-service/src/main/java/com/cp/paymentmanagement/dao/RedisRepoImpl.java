package com.cp.paymentmanagement.dao;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.cp.paymentmanagement.config.RedisConfig;

@EnableConfigurationProperties({ RedisConfig.class })
@Component
public class RedisRepoImpl<K, V> implements RedisRepo<K, V> {

	@Autowired
	ValueOperations<String, Object> redisOperation;

	public void save(K key, V value, Long ttl) {
		redisOperation.set(key.toString(), value, ttl, TimeUnit.MINUTES);
	}

	public Object get(K key) {
		return redisOperation.get(key);
	}

}
