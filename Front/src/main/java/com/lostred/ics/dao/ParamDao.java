package com.lostred.ics.dao;

import com.lostred.ics.bean.ParamBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 参数dao接口
 */
public interface ParamDao extends BaseDao<ParamBean> {
    /**
     * 根据参数类型查询参数实体
     *
     * @param conn 数据库连接对象
     * @param type 参数类型
     * @return 参数对象集合
     * @throws SQLException SQL异常
     */
    List<ParamBean> findByType(Connection conn, String type) throws SQLException;

    /**
     * 根据参数值查询一卡通状态
     *
     * @param conn  数据库连接对象
     * @param value 参数值
     * @return 参数对象集合
     * @throws SQLException SQL异常
     */
    List<ParamBean> findCardState(Connection conn, int value) throws SQLException;

    /**
     * 根据参数值查询用户状态
     *
     * @param conn  数据库连接对象
     * @param value 参数值
     * @return 参数对象集合
     * @throws SQLException SQL异常
     */
    List<ParamBean> findUserState(Connection conn, int value) throws SQLException;

    /**
     * 查询所有参数类型
     *
     * @param conn 数据库连接对象
     * @return 参数类型集合
     * @throws SQLException SQL异常
     */
    List<String> findAllParamType(Connection conn) throws SQLException;

    /**
     * 将参数状态改为不可用
     *
     * @param conn    数据库连接对象
     * @param paramId 参数id
     * @return 受影响的数据数量
     * @throws SQLException SQL异常
     */
    int changeParam(Connection conn, int paramId) throws SQLException;
}
