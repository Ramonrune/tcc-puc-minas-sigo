package com.sigo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.sigo.api.config.property.SigoApiProperty;
import com.sigo.api.filters.SigoZuulFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableConfigurationProperties(SigoApiProperty.class)
public class SigoApiApplication {

	private static ApplicationContext APPLICATION_CONTEXT;

	public static void main(String[] args) {
		APPLICATION_CONTEXT = SpringApplication.run(SigoApiApplication.class, args);
	}

	public static <T> T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}
	
	  @Bean
	  public SigoZuulFilter sigoZuulFilter() {
	    return new SigoZuulFilter();
	  }


}
