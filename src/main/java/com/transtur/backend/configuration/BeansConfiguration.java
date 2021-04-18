package com.transtur.backend.configuration;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class BeansConfiguration {
	
	@Bean
	public Mapper mapper() {
		return DozerBeanMapperBuilder.buildDefault();
	}

}
