package com.xiaoR.mp.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/8/26
 * @description MybatisPlus配置类, 用于配置MybatisPlus的各个插件
 */
@Configuration
public class MybatisConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 1. 生成核心插件: 不负责具体功能
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 2. 添加分页插件
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);   // 设置数据库类型
        pageInterceptor.setMaxLimit(1000L);  // 设置最大分页数量

        // 3. 注册插件
        interceptor.addInnerInterceptor(pageInterceptor);
        return interceptor;
    }
}
