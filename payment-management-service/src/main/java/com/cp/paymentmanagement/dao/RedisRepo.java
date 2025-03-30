package com.cp.paymentmanagement.dao;

public interface RedisRepo<K, V> {
	public void save(final K key, V value, Long ttl);

	public Object get(K key);
}
