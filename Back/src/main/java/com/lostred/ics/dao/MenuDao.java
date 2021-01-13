package com.lostred.ics.dao;

import com.lostred.ics.bean.MenuBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 菜单dao接口
 */
public interface MenuDao extends BaseDao<MenuBean> {
    /**
     * 查询该用户id拥有的菜单
     *
     * @param conn   数据库连接对象
     * @param userId 用户id
     * @return 菜单对象集合
     * @throws SQLException SQL异常
     */
    List<MenuBean> findByUserId(Connection conn, int userId) throws SQLException;

    /**
     * 查询该角色id拥有的菜单
     *
     * @param conn   数据库连接对象
     * @param roleId 角色id
     * @return 菜单对象集合
     * @throws SQLException SQL异常
     */
    List<MenuBean> findHasByRoleId(Connection conn, int roleId) throws SQLException;

    /**
     * 查询该角色id未拥有的菜单
     *
     * @param conn   数据库连接对象
     * @param roleId 角色id
     * @return 菜单对象集合
     * @throws SQLException SQL异常
     */
    List<MenuBean> findHasNotByRoleId(Connection conn, int roleId) throws SQLException;
}
