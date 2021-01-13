package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Dao;
import com.lostred.ics.dao.PermDao;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Dao("PermDao")
public class PermDaoImpl implements PermDao {

    @Override
    public int insertPerm(Connection conn, int roleId, int menuId) throws SQLException {
        String sql = "INSERT INTO T_PERM (PERM_ID,ROLE_ID,MENU_ID,DESC_INFO) VALUES (SEQ_PERM.NEXTVAL,?,?,NULL)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, roleId);
        ps.setInt(2, menuId);
        int num = ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public int deletePerm(Connection conn, int roleId, int menuId) throws SQLException {
        String sql = "DELETE FROM T_PERM WHERE ROLE_ID=? AND MENU_ID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, roleId);
        ps.setInt(2, menuId);
        int num = ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public int deleteAll(Connection conn, int roleId) throws SQLException {
        String sql = "DELETE FROM T_PERM WHERE ROLE_ID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, roleId);
        int num = ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public boolean findPerm(Connection conn, int roleId, int menuId) throws SQLException {
        String sql = "SELECT * FROM T_PERM WHERE ROLE_ID=? AND MENU_ID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, roleId);
        ps.setInt(2, menuId);
        boolean flag = false;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            flag = true;
        }
        JdbcUtil.getInstance().release(ps, rs);
        return flag;
    }
}
