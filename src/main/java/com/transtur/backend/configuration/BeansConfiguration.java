package com.transtur.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

@Configuration
public class BeansConfiguration {
	
	@Bean	
	public Mapper mapper() {
		return DozerBeanMapperBuilder.buildDefault();
	}

}
