package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Dao;
import com.lostred.ics.bean.MenuBean;
import com.lostred.ics.dao.MenuDao;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Dao("MenuDao")
public class MenuDaoImpl extends BaseDaoImpl<MenuBean> implements MenuDao {
    @Override
    public List<MenuBean> findByUserId(Connection conn, int userId) throws SQLException {
        String sql = "SELECT * FROM(SELECT * FROM V_MENU_USER WHERE USER_ID=?" +
                " UNION " +
                "SELECT * FROM V_MENU_USER WHERE MENU_ID IN (" +
                "SELECT DISTINCT PARENT_ID FROM V_MENU_USER WHERE USER_ID=?)) ORDER BY MENU_SEQ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, userId);
        ResultSet rs = ps.executeQuery();
        List<MenuBean> list = listBean(conn, rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public List<MenuBean> findHasByRoleId(Connection conn, int roleId) throws SQLException {
        String sql = "SELECT * FROM V_MENU_ROLE WHERE ROLE_ID=? ORDER BY MENU_SEQ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, roleId);
        ResultSet rs = ps.executeQuery();
        List<MenuBean> list = listBean(conn, rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public List<MenuBean> findHasNotByRoleId(Connection conn, int roleId) throws SQLException {
        String sql = "SELECT * FROM V_MENU_ROLE WHERE MENU_ID NOT IN(" +
                "SELECT MENU_ID FROM V_MENU_ROLE WHERE ROLE_ID=?) ORDER BY MENU_SEQ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, roleId);
        ResultSet rs = ps.executeQuery();
        List<MenuBean> list = listBean(conn, rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }
}
