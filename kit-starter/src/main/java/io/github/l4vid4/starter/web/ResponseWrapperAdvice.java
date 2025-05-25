package io.github.l4vid4.starter.web;

import io.github.l4vid4.starter.conf.KitProperties;
import io.github.l4vid4.starter.model.ResultResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
@ConditionalOnProperty(prefix = "mybatis-plus-kit", name = "response-wrapper-enabled", havingValue = "true", matchIfMissing = true)
@AllArgsConstructor
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    private final KitProperties properties;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
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
        // 已是 ApiResponse 不再包装
        if (body instanceof ResultResponse) {
            return body;
        }
        return ResultResponse.success(body);
    }

    /**
     * 包装统一响应（可根据配置开关控制是否包装）
     */
    private Object wrap(Object body) {
        if (!properties.isResponseWrapperEnabled()) {
            // 如果关闭封装，直接返回原始数据
            if (body instanceof ResultResponse<?>) {
                return ((ResultResponse<?>) body).getData();
            }
            return body;
        }
        return body;
    }
}
