package com.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CORSConfig {

    @Bean
    public CorsFilter corsFilter(){
        //创建cors配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //配置请求方式
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOrigin("http://manage.leyou.com");
        corsConfiguration.addAllowedOrigin("http://www.leyou.com");
        //是否容许携带cookie
        corsConfiguration.setAllowCredentials(true);
        //添加路径映射信息
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //允许所有路径使用配置
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(source);
    }

}
