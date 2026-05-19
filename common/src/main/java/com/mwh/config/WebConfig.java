package com.mwh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author admin
 * @Description web配置类
 * @Date 2026/05/19/12:41
 * @Version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * @Author admin
     * @Description 枚举转换器
     * @Date 2026/05/19/12:41
     * @Param [registry]
     * @Return void
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToUserEnumConverter());
    }
}
