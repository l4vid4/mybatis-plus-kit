package io.github.l4vid4.starter.web;

import io.github.l4vid4.starter.conf.KitProperties;
import io.github.l4vid4.starter.model.ResultResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ConditionalOnProperty(prefix = "mybatis-plus-kit", name = "exception-handler-enabled", havingValue = "true", matchIfMissing = true)
@AllArgsConstructor
public class ExceptionHandlerAdvice {

    private final KitProperties properties;

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        return ResultResponse.fail("服务器异常: " + e.getMessage());
    }
}
