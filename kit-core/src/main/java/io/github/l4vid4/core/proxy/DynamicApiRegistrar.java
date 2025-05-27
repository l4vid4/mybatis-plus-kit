package io.github.l4vid4.core.proxy;

import io.github.l4vid4.core.base.BaseController;
import io.github.l4vid4.core.annotation.AutoApi;
import io.github.l4vid4.core.enums.AutoApiMode;
import io.github.l4vid4.core.util.ServiceResolver;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class DynamicApiRegistrar implements ApplicationListener<ContextRefreshedEvent> {

    private final ApplicationContext context;
    private final String basePackage;

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    public DynamicApiRegistrar(ApplicationContext context, String basePackage) {
        this.context = context;
        this.basePackage = basePackage;
    }

    public void register() throws Exception {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(AutoApi.class));

        for (BeanDefinition bd : scanner.findCandidateComponents(basePackage)) {
            Class<?> entityClass = Class.forName(bd.getBeanClassName());

            AutoApi autoApi = entityClass.getAnnotation(AutoApi.class);

            if(autoApi.mode() != AutoApiMode.PROXY){
                continue;
            }

            String path = autoApi.path().isEmpty() ? "/" + entityClass.getSimpleName().toLowerCase() : autoApi.path();

            registerDynamicController(entityClass, autoApi, path);
        }
    }

    private void registerDynamicController(Class<?> entityClass, AutoApi autoApi, String path) {
        try {
            // 2. 创建继承自 BaseController 的动态类
            Class<? extends BaseController> dynamicControllerClass = new ByteBuddy()
                    .subclass(BaseController.class)
                    .name(basePackage + ".AutoApi" + entityClass.getSimpleName() + "Controller")
                    .annotateType(AnnotationDescription.Builder.ofType(RestController.class).build())
                    .annotateType(AnnotationDescription.Builder.ofType(RequestMapping.class)
                            .defineArray("value", path)
                            .build())
                    .make()
                    .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getLoaded();


            // 3. 注册到 Spring 容器
            ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) context;
            DefaultListableBeanFactory factory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();

            Object serviceInstance = ServiceResolver.resolveService(applicationContext, entityClass, autoApi);
            Object controllerInstance = dynamicControllerClass.getDeclaredConstructor().newInstance();

            // 注入 Service
            Field serviceField = ReflectionUtils.findField(dynamicControllerClass, "service");
            if (serviceField != null) {
                serviceField.setAccessible(true);
                serviceField.set(controllerInstance, serviceInstance);
            }

            // 注入 entityClass
            Method setEntityClass = dynamicControllerClass.getMethod("setEntityClass", Class.class);
            setEntityClass.invoke(controllerInstance, entityClass);

            String beanName = entityClass.getSimpleName().toLowerCase() + "Controller";
            factory.registerSingleton(beanName, controllerInstance);

            // 4. 调用 detectHandlerMethods
            Method method = AbstractHandlerMethodMapping.class.getDeclaredMethod("detectHandlerMethods", Object.class);
            method.setAccessible(true);
            method.invoke(handlerMapping, beanName);

            System.out.println("[AutoApi] Registered controller for " + entityClass.getSimpleName());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to register controller for " + entityClass.getSimpleName(), e);
        }
    }



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            register();
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Failed to register dynamic APIs", e);
        }
    }
}