package com.lostred.ics.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 权限dao接口
 */
public interface PermDao {
    /**
     * 角色新增一项权限
     *
     * @param conn   数据库连接对象
     * @param roleId 角色id
     * @param menuId 菜单id
     * @return 受影响的数据数量
     * @throws SQLException SQL异常
     */
    int insertPerm(Connection conn, int roleId, int menuId) throws SQLException;

    /**
     * 角色移除一项权限
     *
     * @param conn   数据库连接对象
     * @param roleId 角色id
     * @param menuId 菜单id
     * @return 受影响的数据数量
     * @throws SQLException SQL异常
     */
    int deletePerm(Connection conn, int roleId, int menuId) throws SQLException;

    /**
     * 角色移除所有权限
     *
     * @param conn   数据库连接对象
     * @param roleId 角色id
     * @return 受影响的数据数量
     * @throws SQLException SQL异常
     */
    int deleteAll(Connection conn, int roleId) throws SQLException;

    /**
     * 查询角色id下是否拥有该菜单id的菜单(排除一级菜单)
     *
     * @param conn   数据库连接对象
     * @param roleId 角色id
     * @param menuId 菜单id
     * @return 存在返回true，否则返回false
     * @throws SQLException SQL异常
     */
    boolean findPerm(Connection conn, int roleId, int menuId) throws SQLException;
}
