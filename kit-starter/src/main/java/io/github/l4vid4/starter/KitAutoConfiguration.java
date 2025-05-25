package io.github.l4vid4.starter;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.github.l4vid4.starter.conf.KitProperties;
import io.github.l4vid4.starter.web.ExceptionHandlerAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.l4vid4.starter.web.ResponseWrapperAdvice;

@Configuration
@EnableConfigurationProperties(KitProperties.class)
public class KitAutoConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件（你可以支持多数据库）
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    @ConditionalOnMissingBean(ResponseWrapperAdvice.class)
    @ConditionalOnProperty(prefix = "mybatis-plus-kit", name = "response-wrapper-enabled", havingValue = "true", matchIfMissing = true)
    public ResponseWrapperAdvice responseWrapperAdvice(KitProperties properties) {
        return new ResponseWrapperAdvice(properties);
    }


    @Bean
    @ConditionalOnMissingBean(ExceptionHandlerAdvice.class)
    @ConditionalOnProperty(prefix = "mybatis-plus-kit", name = "exception-handler-enabled", havingValue = "true", matchIfMissing = true)
    public ExceptionHandlerAdvice baseControllerAdvice(KitProperties properties) {
        return new ExceptionHandlerAdvice(properties);
    }

}
