package io.github.l4vid4.core.util;

import io.github.l4vid4.core.annotation.AutoApi;
import io.github.l4vid4.core.exception.ServiceNotFoundException;
import org.springframework.context.ApplicationContext;

public class ServiceResolver {

    public static Object resolveService(ApplicationContext context, Class<?> entityClass, AutoApi autoApi) {
        // 1. 用户显式指定
        Class<?> serviceClass = autoApi.service();
        if (!serviceClass.equals(Object.class)) {
            return context.getBean(serviceClass);
        }

        // 2. 按命名规则推导
        String entitySimpleName = entityClass.getSimpleName();
        String defaultServiceBeanName = Character.toLowerCase(entitySimpleName.charAt(0))
                + entitySimpleName.substring(1) + "Service";

        if (context.containsBean(defaultServiceBeanName)) {
            return context.getBean(defaultServiceBeanName);
        }

        // 3. 无法找到，抛出异常
        throw new ServiceNotFoundException("未找到实体类 [" + entitySimpleName
                + "] 对应的 Service，默认尝试从 Spring 容器中查找 bean 名为 ["
                + defaultServiceBeanName + "] 的实例失败。请在 @AutoApi 中显式指定 service。");
    }
}
