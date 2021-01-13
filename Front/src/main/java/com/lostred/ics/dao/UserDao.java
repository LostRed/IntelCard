package com.lostred.ics.dao;

import com.lostred.ics.bean.UserBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户dao接口
 */
public interface UserDao extends BaseDao<UserBean> {
    /**
     * 按用户名和密码查找用户
     *
     * @param conn     数据库连接
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    UserBean findByUsernameAndPassword(Connection conn, String username, String password) throws SQLException;

    /**
     * 改变用户的状态
     *
     * @param conn    数据库连接
     * @param paramId 参数id
     * @param userId  用户id
     * @return 受影响的记录数量
     */
    int changeState(Connection conn, int paramId, int userId) throws SQLException, IOException;


    /**
     * 重置用户的密码
     *
     * @param conn   数据库连接
     * @param userId 用户id
     * @return 重置后的密码
     */
    String resetPassword(Connection conn, int userId) throws IOException, SQLException;

    /**
     * 插入用户
     *
     * @param conn     数据库连接
     * @param userBean 用户
     * @return 受影响的记录数量
     */
    int insert(Connection conn, UserBean userBean) throws SQLException;

    /**
     * 根据角色id查找用户
     *
     * @param conn   数据库连接
     * @param roleId 角色id
     * @return 用户集合
     * @throws SQLException SQL异常
     */
    List<UserBean> findByRoleId(Connection conn, int roleId) throws SQLException;

    /**
     * 根据条件分页查询用户
     *
     * @param queryBeans 查询对象
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序排序
     * @return 用户对象集合
     * @throws SQLException SQL异常
     */
    List<UserBean> fastFindBeanByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException;
}
