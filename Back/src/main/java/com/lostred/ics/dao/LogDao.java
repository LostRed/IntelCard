package com.lostred.ics.dao;

import com.lostred.ics.bean.LogBean;
import com.lostred.ics.dto.StatisticsBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 日志dao接口
 */
public interface LogDao extends BaseDao<LogBean> {
    /**
     * 新增日志
     *
     * @param conn    数据库连接对象
     * @param logBean 日志对象
     * @return 受影响的数据数量
     * @throws SQLException SQL异常
     */
    int insert(Connection conn, LogBean logBean) throws SQLException;

    /**
     * 根据条件查询日志的数据总数
     *
     * @param conn       数据库连接对象
     * @param queryBeans 查询实体
     * @return 数据总数
     * @throws SQLException SQL异常
     */
    int countFind(Connection conn, QueryBean[] queryBeans) throws SQLException;

    /**
     * 根据条件分页查询日志
     *
     * @param conn       数据库连接对象
     * @param queryBeans 查询实体
     * @param pageBean   页码实体
     * @param field      排序字段
     * @param desc       是否降序排序
     * @return 数据持久化实体集合
     * @throws SQLException SQL异常
     */
    List<StatisticsBean> findByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException;

    /**
     * 快速分页日志(该方法生成的日志对象为贫血实体，字段属性不完全)
     *
     * @param conn       数据库连接对象
     * @param queryBeans 查询对象数组
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序
     * @return 日志集合
     */
    List<LogBean> fastFindByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException;
}
