package io.github.l4vid4.generator.engine;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.*;

public class CodeGenerator {

    protected String author = "l4vid4";
    protected String module;
    protected String basePackage;
    protected List<String> tables = new ArrayList<>();
    protected String url;
    protected String username;
    protected String password;

    protected String javaTemplateDir = "template";  //自定义的模板目录名

    // ===== 静态方法快速使用 =====
    public static void generate(String url, String username, String password, String[] tableName, String basePackage, String moduleName) {
        new CodeGenerator()
                .dataSource(url, username, password)
                .packageName(basePackage)
                .module(moduleName)
                .tables(tableName)
                .build()
                .execute();
    }

    // ===== 链式配置 =====
    public CodeGenerator(){
        // 默认构造函数
    }

    public CodeGenerator author(String author) {
        this.author = author;
        return this;
    }

    public CodeGenerator module(String module) {
        this.module = module;
        return this;
    }

    public CodeGenerator packageName(String pkg) {
        this.basePackage = pkg;
        return this;
    }

    public CodeGenerator tables(String ... tableNames) {
        this.tables = Arrays.asList(tableNames);
        return this;
    }

    public CodeGenerator tables(List<String> tableNames){
        this.tables = new ArrayList<>(tableNames);
        return this;
    }

    public CodeGenerator dataSource(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        return this;
    }


    // ===== 构建并返回 FastAutoGenerator 实例 =====
    public FastAutoGenerator build() {
        String projectDir = System.getProperty("user.dir");
        String baseDir = (module == null || module.isEmpty())
                ? projectDir + "/src/main"
                : projectDir + "/" + module + "/src/main";
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
                            .enableLombok()
                            .javaTemplate(javaTemplateDir + "/entity.java")
                            .serviceBuilder()
                            .serviceTemplate(javaTemplateDir + "/service.java")
                            .serviceImplTemplate(javaTemplateDir + "/serviceImpl.java")
                            .mapperBuilder()
                            .mapperAnnotation(org.apache.ibatis.annotations.Mapper.class)
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .controllerBuilder()
                            .disable()
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