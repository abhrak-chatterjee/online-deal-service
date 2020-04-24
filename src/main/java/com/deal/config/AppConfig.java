package com.deal.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class AppConfig {
	
	@Value("${spring.rabbitmq.host}")
	private String rabbitHost;
	
	@Value("${deal.poolTaskExecutor.size}")
	private int dealPoolSize;
	
	/**
	 * Method used for Controller to be exposed on Swaggers!
	 * 
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	/**
	 * Method for configuration of threadPoolTaskExecutor
	 * 
	 * @return executor
	 */
	@Bean("threadPoolTaskExecutor")
	public ExecutorService getAsyncExecutor() {
		return Executors.newFixedThreadPool(dealPoolSize);
	}
	
}
