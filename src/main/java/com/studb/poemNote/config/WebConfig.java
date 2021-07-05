package com.studb.poemNote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
                    .addMapping("/api/v1/**")
                    .allowedOrigins("*")
                    .allowedMethods("*");
			}
		};
	}
}

// WebMvcConfigurationSupport를 상속받는 방법은 좋지 않음.
// 이를 상속받으면 Resource Path를 지정하는 함수, Request 파라미터의 타입을 Converting 해주는 함수/hanlder를 설정하는 부분들이 다 깨져버려서 직접 설정을 해주어야 함.
// 
// @Configuration
// public class WebConfig extends WebMvcConfigurationSupport{

//     private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
//         "classpath:/resources/", "classpath:/static/"};

//     @Override
// 	public void addCorsMappings(CorsRegistry registry) {
// 		registry.addMapping("/api/v1/**")
// 			.allowedOrigins("*")
// 			.allowedMethods("GET", "POST", "DELETE", "PUT");
// 	}

//     @Override
//     public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         registry.addResourceHandler("/**")
//                 .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
//     }
    
// }
