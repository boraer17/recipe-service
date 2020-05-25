package com.rs.data.repository.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.redisearch.client.Client;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RediSearchConfiguration {

	@Value("${app.redis.recipeIndex}")
	private String recipeIndex;

	@Value("${app.redis.categoryIndex}")
	private String categoryIndex;

	@Value("${app.redis.host}")
	private String host;

	@Value("${app.redis.port}")
	private Integer port;

	@Bean
	@Qualifier(value = "recipeClient")
	public Client redisRecipeClient() {
		return new Client(recipeIndex, host, port);
	}

	@Bean
	@Qualifier(value = "categoryClient")
	public Client redisCategoryClient() {

		return new Client(categoryIndex, host, port);
	}

}
