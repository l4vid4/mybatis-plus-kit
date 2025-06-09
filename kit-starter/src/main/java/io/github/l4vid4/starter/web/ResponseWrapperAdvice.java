package io.github.l4vid4.starter.web;

import io.github.l4vid4.starter.conf.KitProperties;
import io.github.l4vid4.starter.model.ResultResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
@ConditionalOnProperty(prefix = "mybatis-plus-kit", name = "response-wrapper-enabled", havingValue = "true", matchIfMissing = true)
@AllArgsConstructor
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    private final KitProperties properties;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 只拦截 application/json
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }


    /**
     * 统一包装响应体
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  org.springframework.http.server.ServerHttpRequest request,
                                  org.springframework.http.server.ServerHttpResponse response) {
        return wrap(body);
    }

    /**
     * 包装统一响应（可根据配置开关控制是否包装）
     */
    private Object wrap(Object body) {
        if (!properties.isResponseWrapperEnabled()) {
            // 不包装，返回原始 body
            return body instanceof ResultResponse<?> ? ((ResultResponse<?>) body).getData() : body;
        } else {
            // 包装成 ResultResponse
            return body instanceof ResultResponse<?> ? body : ResultResponse.success(body);
        }
    }
}
