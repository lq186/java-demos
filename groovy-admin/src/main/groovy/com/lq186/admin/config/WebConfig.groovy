package com.lq186.admin.config

import com.lq186.admin.interceptor.PageRequestInterceptor
import com.lq186.admin.interceptor.UserIdInterceptor
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

import javax.annotation.Resource

@Configuration
class WebConfig implements WebMvcConfigurer {

    @Resource
    PageRequestInterceptor pageRequestInterceptor

    @Resource
    UserIdInterceptor userIdInterceptor

    /*
    @Bean("charsetFilterRegistrationBean")
    FilterRegistrationBean<CharacterEncodingFilter> charsetFilterRegistrationBean() {
        final defaultEncoding = "utf-8"
        final forceEncoding = true
        FilterRegistrationBean registrationBean = new FilterRegistrationBean()
        registrationBean.setFilter(new CharacterEncodingFilter(defaultEncoding, forceEncoding))
        registrationBean.addUrlPatterns("/*")
        registrationBean.setOrder(1)
        return registrationBean
    }
    */

    @Override
    void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        final patterns = ["/json/**.json", "/swagger**", "/webjars/**"]
        registry.addInterceptor(pageRequestInterceptor).excludePathPatterns(patterns)
        registry.addInterceptor(userIdInterceptor).excludePathPatterns(patterns)
    }


}
