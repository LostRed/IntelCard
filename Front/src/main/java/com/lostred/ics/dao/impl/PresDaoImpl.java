package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Dao;
import com.lostred.ics.bean.PresBean;
import com.lostred.ics.dao.PresDao;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Dao("PresDao")
public class PresDaoImpl extends BaseDaoImpl<PresBean> implements PresDao {
    @Override
    public int deleteByCardId(Connection conn, int cardId) throws SQLException {
        String sql = "DELETE FROM T_PRES WHERE CARD_ID=?";
        int num = 0;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, cardId);
        num += ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }
}
