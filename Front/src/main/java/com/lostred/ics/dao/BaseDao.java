package com.lostred.ics.dao;

import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 基础dao接口
 *
 * @param <T>数据库实体类
 */
public interface BaseDao<T> {
    /**
     * 获取新的主键
     *
     * @param conn 数据库连接
     * @return 主键值
     * @throws SQLException SQL异常
     */
    int getNewId(Connection conn) throws SQLException;

    /**
     * 插入数据持久化实体
     *
     * @param conn 数据库连接
     * @param bean 数据实体
     * @return 受影响的数据数量
     * @throws SQLException SQL异常
     */
    int insertBean(Connection conn, T bean) throws SQLException;

    /**
     * 根据主键删除数据持久化实体
     *
     * @param conn 数据库连接
     * @param id   主键
     * @return 受影响的数据数量
     * @throws SQLException SQL异常
     */
    int deleteBeanById(Connection conn, int id) throws SQLException;

    /**
     * 更新数据持久化实体
     *
     * @param conn 数据库连接
     * @param bean 数据实体
     * @return 受影响的数据数量
     * @throws SQLException SQL异常
     */
    int updateBean(Connection conn, T bean) throws SQLException;

    /**
     * 查询单个数据持久化实体
     *
     * @param conn 数据库连接
     * @param id   主键
     * @return 数据实体
     * @throws SQLException SQL异常
     */
    T findBeanById(Connection conn, int id) throws SQLException;

    /**
     * 查询一系列数据持久化实体的数据总数
     *
     * @param conn       数据库连接
     * @param queryBeans 查询实体
     * @return 数据总数
     * @throws SQLException SQL异常
     */
    int countFindBean(Connection conn, QueryBean[] queryBeans) throws SQLException;

    /**
     * 查询一系列数据持久化实体
     *
     * @param conn       数据库连接
     * @param queryBeans 查询实体
     * @param pageBean   页码实体
     * @param field      排序字段
     * @param desc       是否降序排序
     * @return 数据持久化实体集合
     * @throws SQLException SQL异常
     */
    List<T> findBeanByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException;

    /**
     * 查询所有数据持久化实体
     *
     * @param conn 数据库连接
     * @return 数据持久化实体集合
     * @throws SQLException SQL异常
     */
    List<T> findAllBean(Connection conn) throws SQLException;
}
