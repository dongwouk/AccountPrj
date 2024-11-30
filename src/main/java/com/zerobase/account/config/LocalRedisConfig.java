package com.zerobase.account.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import redis.embedded.RedisServer;

@Configuration
public class LocalRedisConfig {
	@Value("${spring.redis.port}")
	private int redisPort;

	private RedisServer redisServer;

	@PostConstruct
	public void startRedis() {
		try {
			redisServer = new RedisServer(redisPort);
			redisServer.start();
		} catch (Exception e) {
			System.err.println("Redis 서버 시작 실패: " + e.getMessage());
		}
	}

	@PreDestroy
	public void stopRedis() {
		if (redisServer != null) {
			try {
				redisServer.stop();
			} catch (Exception e) {
				System.err.println("Redis 서버 종료 실패: " + e.getMessage());
			}
		}
	}
}
