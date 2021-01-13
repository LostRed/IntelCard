package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Dao;
import com.lostred.ics.bean.ParamBean;
import com.lostred.ics.dao.ParamDao;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Dao("ParamDao")
public class ParamDaoImpl extends BaseDaoImpl<ParamBean> implements ParamDao {

    @Override
    public List<ParamBean> findByType(Connection conn, String type) throws SQLException {
        String sql = "SELECT * FROM T_PARAM WHERE PARAM_TYPE=? AND PARAM_STATE='可用'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, type);
        ResultSet rs = ps.executeQuery();
        List<ParamBean> list = listBean(conn, rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public List<ParamBean> findCardState(Connection conn, int value) throws SQLException {
        String sql = "SELECT * FROM T_PARAM WHERE PARAM_TYPE='一卡通状态' AND PARAM_STATE='可用'";
        if (value != 0) {
            sql += " AND PARAM_VALUE=?";
        }
        PreparedStatement ps = conn.prepareStatement(sql);
        if (value != 0) {
            ps.setInt(1, value);
        }
        ResultSet rs = ps.executeQuery();
        List<ParamBean> list = listBean(conn, rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public List<ParamBean> findUserState(Connection conn, int value) throws SQLException {
        String sql = "SELECT * FROM T_PARAM WHERE PARAM_TYPE='用户状态' AND PARAM_STATE='可用'";
        if (value != 0) {
            sql += " AND PARAM_VALUE=?";
        }
        PreparedStatement ps = conn.prepareStatement(sql);
        if (value != 0) {
            ps.setInt(1, value);
        }
        ResultSet rs = ps.executeQuery();
        List<ParamBean> list = listBean(conn, rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public List<String> findAllParamType(Connection conn) throws SQLException {
        String sql = "SELECT DISTINCT PARAM_TYPE FROM T_PARAM";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString("PARAM_TYPE"));
        }
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public int changeParam(Connection conn, int paramId) throws SQLException {
        String sql = "UPDATE T_PARAM SET PARAM_STATE='不可用' WHERE PARAM_ID=?";
        int num = 0;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, paramId);
        num += ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }
}
