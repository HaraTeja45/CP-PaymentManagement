package com.cp.paymentmanagement.config;

import java.text.MessageFormat;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@Configuration
public class RedisConfig {

	@Value("${redis.cache.host}")
	private String redisHostName;

	@Value("${redis.cache.port}")
	private Integer redisPort;

	@Value("${redis.cluster.enable:false}")
	private boolean isClusterEnabled;

	@Bean
	JedisConnectionFactory createJedisConnectionFactory() {

		JedisConnectionFactory jedisConnectionFactory;

		JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration
				.builder();

		if (isClusterEnabled) {

			RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
			redisClusterConfiguration.clusterNode(redisHostName, redisPort);

			jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration,
					jedisClientConfigurationBuilder.build());

		} else {
			RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHostName,
					redisPort);

			jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
					jedisClientConfigurationBuilder.build());
		}

		return jedisConnectionFactory;

	}

	@Bean("redisTemplate")
	RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

		redisTemplate.setConnectionFactory(redisConnectionFactory);

		return redisTemplate;
	}

	@Bean("redissonClient")
	RedissonClient getRedissonClient() {

		Config config = new Config();

		if (isClusterEnabled) {
			config.useClusterServers().addNodeAddress(MessageFormat.format("{0}:{1}", redisHostName, redisPort));

		} else {
			config.useSingleServer().setAddress(MessageFormat.format("{0}:{1}", redisHostName, redisPort));
	
		}

		return Redisson.create(config);

	}

	@Bean("redisOperation")
	@DependsOn("redisTemplate")
	ValueOperations<String, Object> valueOpertions(RedisTemplate<String, Object> redisTemplate) {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		return valueOperations;
	}

}
