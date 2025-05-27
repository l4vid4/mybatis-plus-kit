package io.github.l4vid4.generator.engine;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ControllerGenerator extends CodeGenerator{

    @Override
    public FastAutoGenerator build() {
        String baseDir = System.getProperty("user.dir") + "/" + module + "/src/main";
        String outputDir = baseDir + "/java";
        String xmlOutput = baseDir + "/resources/mapper";

        if (basePackage == null || basePackage.isEmpty()) {
            throw new IllegalArgumentException("Base package must be specified.");
        }
        if (tables.isEmpty()) {
            throw new IllegalArgumentException("At least one table must be specified.");
        }
        if (url == null || username == null || password == null) {
            throw new IllegalArgumentException("Database connection details must be specified.");
        }

        return FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author)
                            .outputDir(outputDir)
                            .disableOpenDir();
                })
                .packageConfig(builder -> {
                    builder.parent(basePackage)
                            .pathInfo(Collections.singletonMap(OutputFile.xml, xmlOutput));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            .entityBuilder()
                            .disable()
                            .serviceBuilder()
                            .disableService()
                            .disableServiceImpl()
                            .mapperBuilder()
                            .disableMapper()
                            .controllerBuilder()
                            .template(javaTemplateDir + "/controller.java")
                            .build();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .injectionConfig(consumer -> {
                    Map<String, Object> customMap = new HashMap<>();
                    customMap.put("apiMode", "proxy");
                    consumer.customMap(customMap);
                });
    }
}
