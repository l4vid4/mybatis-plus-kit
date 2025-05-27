package io.github.l4vid4.starter.conf;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mybatis-plus-kit")
@Data
@Getter
public class KitProperties {
    /**
     * 是否启用统一响应封装（默认启用）
     */
    private boolean responseWrapperEnabled = true;

    /**
     * 是否启用统一异常捕获（默认启用）
     */
    private boolean exceptionHandlerEnabled = true;

    /** 是否开启动态代理
     *  true开启，false不开启
     */
    private boolean autoApiProxyEnabled = true; // 支持 "proxy" 或 "apt"，默认为 "proxy"


    private String basePackage = ""; // 基础包名，用于扫描实体类和生成 API 的路径前缀
}
