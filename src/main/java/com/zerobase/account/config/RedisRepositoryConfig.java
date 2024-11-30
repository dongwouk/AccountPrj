package com.zerobase.account.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RedisRepositoryConfig {
	private static final String REDIS_PROTOCOL = "redis://";

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

	@PostConstruct
	private void validatePort() {
		if (redisPort < 1 || redisPort > 65535) {
			throw new IllegalArgumentException("잘못된 Redis 포트 번호: " + redisPort);
		}
	}

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		String redisAddress = REDIS_PROTOCOL + redisHost + ":" + redisPort;
		config.useSingleServer()
				.setAddress(redisAddress);

		System.out.println("Redis 클라이언트 설정: " + redisAddress); // 설정된 Redis 주소를 로깅

		return Redisson.create(config);
	}
}
