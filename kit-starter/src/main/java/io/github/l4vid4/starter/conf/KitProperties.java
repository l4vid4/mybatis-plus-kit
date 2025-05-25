package io.github.l4vid4.starter.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mybatis-plus-kit")
@Data
public class KitProperties {
    /**
     * 是否启用统一响应封装（默认启用）
     */
    private boolean responseWrapperEnabled = true;

    /**
     * 是否启用统一异常捕获（默认启用）
     */
    private boolean exceptionHandlerEnabled = true;
}
