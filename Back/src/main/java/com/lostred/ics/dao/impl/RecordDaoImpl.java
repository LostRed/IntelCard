package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Dao;
import com.lostred.ics.bean.ParamBean;
import com.lostred.ics.bean.RecordBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dao.RecordDao;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Dao("RecordDao")
public class RecordDaoImpl extends BaseDaoImpl<RecordBean> implements RecordDao {
    @Override
    public int changeRecord(Connection conn, int oldCardId, int newCardId) throws SQLException {
        String sql = "UPDATE T_RECORD SET CARD_ID=? WHERE CARD_ID=?";
        int num = 0;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, newCardId);
        ps.setInt(2, oldCardId);
        num += ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public int deleteByCardId(Connection conn, int cardId) throws SQLException {
        String sql = "DELETE FROM T_RECORD WHERE CARD_ID=?";
        int num = 0;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, cardId);
        num += ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public int countFindByCardId(Connection conn, int cardId) throws SQLException {
        String sql = "SELECT COUNT(*) AS TOTAL FROM T_RECORD WHERE CARD_ID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, cardId);
        ResultSet rs = ps.executeQuery();
        int num = 0;
        if (rs.next()) {
            num = rs.getInt("TOTAL");
        }
        JdbcUtil.getInstance().release(ps, rs);
        return num;
    }

    @Override
    public List<RecordBean> findByPageAndCardId(Connection conn, int cardId, PageBean pageBean) throws SQLException {
        String sql = "SELECT * FROM (SELECT rowNum AS RN,A.* FROM (SELECT * FROM V_RECORD WHERE CARD_ID=? ORDER BY RECORD_TIME DESC) A) WHERE RN>? AND RN<=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, cardId);
        ps.setInt(2, pageBean.getStartRow());
        ps.setInt(3, pageBean.getEndRow());
        List<RecordBean> list = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String paramName = rs.getString("PARAM_NAME");
            ParamBean paramBean = new ParamBean();
            paramBean.setParamName(paramName);
            String actionUserName = rs.getString("NAME");
            UserBean userBean = new UserBean();
            userBean.setName(actionUserName);
            RecordBean recordBean = new RecordBean();
            recordBean.setActionUser(userBean);
            recordBean.setRecordTime(rs.getTimestamp("RECORD_TIME"));
            recordBean.setAmount(rs.getDouble("AMOUNT"));
            recordBean.setName(paramBean);
            list.add(recordBean);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }
}
