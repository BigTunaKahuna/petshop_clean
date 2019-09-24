package com.petshop.configuration;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableAsync
public class ThreadConfig {

	@Bean(name = "taskExecutor")
	@Primary
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(9);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Thread-");
		executor.initialize();
		return executor;
	}
}
