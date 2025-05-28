package io.github.l4vid4.core.util;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;

import java.lang.reflect.Field;

public class Tool {
    public static Class<?> resolveIdType(Class<?> entityClass) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        if (tableInfo == null) {
            throw new IllegalStateException("未找到实体类的 TableInfo，请检查配置");
        }
        String idProperty = tableInfo.getKeyProperty();
        if (idProperty == null) {
            throw new IllegalStateException("实体类未配置主键字段");
        }
        try {
            Field idField = entityClass.getDeclaredField(idProperty);
            return idField.getType();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("找不到主键字段对应的实体属性", e);
        }
    }
}
