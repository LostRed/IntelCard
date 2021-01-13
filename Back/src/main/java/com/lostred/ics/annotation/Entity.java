package com.lostred.ics.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库实体类注解，标记的表示对应数据库实体的JavaBean
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    /**
     * 实体名
     *
     * @return 实体名
     */
    String value();

    /**
     * 数据库表名
     *
     * @return 数据库表名
     */
    String tableName();

    /**
     * 对应的Dao接口名
     *
     * @return 对应的Dao接口名
     */
    String daoName();

    /**
     * 实体中文名
     *
     * @return 实体中文名
     */
    String name();
}
