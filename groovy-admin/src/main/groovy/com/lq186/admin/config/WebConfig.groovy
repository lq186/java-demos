package com.lq186.admin.config

import com.lq186.admin.interceptor.PageRequestInterceptor
import com.lq186.admin.interceptor.UserIdInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
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
    void addInterceptors(InterceptorRegistry registry) {
        final jsonPattern = "/json/**.json"
        registry.addInterceptor(pageRequestInterceptor).excludePathPatterns(jsonPattern)
        registry.addInterceptor(userIdInterceptor).excludePathPatterns(jsonPattern)
    }


}
