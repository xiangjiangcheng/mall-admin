package com.river.malladmin.config;


import com.river.malladmin.filter.GlobalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author JiangCheng Xiang
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public GlobalInterceptor globalInterceptor() {
        return new GlobalInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor())
                .excludePathPatterns("/swagger-resources", "/doc.html**/**", "/webjars/**", "/v3/api-docs", "/error");
    }

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("PUT", "POST", "GET", "HEAD", "DELETE", "OPTIONS")
                .allowedOriginPatterns("*")
                .maxAge(3600)
                .allowCredentials(true);
    }

}
