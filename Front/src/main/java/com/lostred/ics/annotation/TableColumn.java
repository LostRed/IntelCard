package com.lostred.ics.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类属性对应表格列名的注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn {
    /**
     * 对应的数据库表格列名
     *
     * @return 对应的数据库表格列名
     */
    String columnName();

    /**
     * 数据库表格列名关联的实体类对象
     *
     * @return 数据库表格列名关联的实体类对象
     */
    Class<?> reference() default Object.class;
}
