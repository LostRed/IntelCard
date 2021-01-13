package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Dao;
import com.lostred.ics.bean.LogBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dao.LogDao;
import com.lostred.ics.dto.StatisticsBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Dao("LogDao")
public class LogDaoImpl extends BaseDaoImpl<LogBean> implements LogDao {
    @Override
    public int insert(Connection conn, LogBean logBean) throws SQLException {
        String sql = "INSERT INTO T_LOG (LOG_ID,USER_ID,LOG_NAME,LOG_TIME,DESC_INFO)" +
                "VALUES (SEQ_LOG.NEXTVAL,?,?,SYSDATE,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        if (logBean.getActionUser() == null) {
            ps.setInt(1, 0);
        } else {
            ps.setInt(1, logBean.getActionUser().getUserId());
        }
        ps.setString(2, logBean.getLogName());
        ps.setString(3, logBean.getDescInfo());
        int num = ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public int countFind(Connection conn, QueryBean[] queryBeans) throws SQLException {
        StringBuilder cond1 = new StringBuilder("WHERE 1=1");
        StringBuilder cond2 = new StringBuilder("WHERE 1=1");
        for (QueryBean queryBean : queryBeans) {
            if (queryBean.getValue() != null && !"".equals(queryBean.getValue()) && queryBean.getField().equals("TIME")) {
                cond1.append(" AND ").append(queryBean.getField()).append(" ").append(queryBean.getOperator()).append(" ?");
            } else if (queryBean.getValue() != null && !"".equals(queryBean.getValue())) {
                cond2.append(" AND ").append(queryBean.getField()).append(" ").append(queryBean.getOperator()).append(" ?");
            }
        }
        String sql = "SELECT COUNT(*) AS TOTAL FROM(" + "SELECT USER_ID,NAME,SUM(SOLD),SUM(CHANGE),SUM(REFUND),SUM(PRES_REG),SUM(REFUND_PRES) FROM((" +
                "SELECT USER_ID,NAME," +
                "SUM(SOLD) AS SOLD," +
                "SUM(CHANGE) AS CHANGE," +
                "SUM(REFUND) AS REFUND," +
                "SUM(PRES_REG) AS PRES_REG," +
                "SUM(REFUND_PRES) AS REFUND_PRES " +
                "FROM V_STATISTICS " +
                cond1 +
                " GROUP BY USER_ID,NAME) UNION (SELECT USER_ID,NAME,0,0,0,0,0 FROM V_STATISTICS)) " + cond2 +
                " GROUP BY USER_ID,NAME )";
        PreparedStatement ps = getPreparedStatement(conn, sql, queryBeans, null);
        ResultSet rs = ps.executeQuery();
        int total = 0;
        if (rs.next()) {
            total = rs.getInt("TOTAL");
        }
        JdbcUtil.getInstance().release(ps, rs);
        return total;
    }

    @Override
    public List<StatisticsBean> findByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException {
        StringBuilder cond1 = new StringBuilder("WHERE 1=1");
        StringBuilder cond2 = new StringBuilder("WHERE 1=1");
        for (QueryBean queryBean : queryBeans) {
            if (queryBean.getValue() != null && !"".equals(queryBean.getValue()) && queryBean.getField().equals("TIME")) {
                cond1.append(" AND ").append(queryBean.getField()).append(" ").append(queryBean.getOperator()).append(" ?");
            } else if (queryBean.getValue() != null && !"".equals(queryBean.getValue())) {
                cond2.append(" AND ").append(queryBean.getField()).append(" ").append(queryBean.getOperator()).append(" ?");
            }
        }
        StringBuilder sql = new StringBuilder("SELECT * FROM (SELECT rowNum AS RN,A.* FROM (");
        sql.append("SELECT USER_ID,NAME,SUM(SOLD) AS SOLD,SUM(CHANGE) AS CHANGE,SUM(REFUND) AS REFUND,SUM(PRES_REG) AS PRES_REG,SUM(REFUND_PRES) AS REFUND_PRES FROM ((" +
                "SELECT USER_ID,NAME," +
                "SUM(SOLD) AS SOLD," +
                "SUM(CHANGE) AS CHANGE," +
                "SUM(REFUND) AS REFUND," +
                "SUM(PRES_REG) AS PRES_REG," +
                "SUM(REFUND_PRES) AS REFUND_PRES " +
                "FROM V_STATISTICS ").append(cond1);
        sql.append(" GROUP BY USER_ID,NAME) UNION (SELECT USER_ID,NAME,0,0,0,0,0 FROM V_STATISTICS)) ").append(cond2);
        sql.append(" GROUP BY USER_ID,NAME ");
        if (field != null) {
            sql.append(" ORDER BY ").append(field);
        }
        if (desc) {
            sql.append(" DESC");
        }
        sql.append(") A) WHERE RN>? AND RN<=?");
        PreparedStatement ps = getPreparedStatement(conn, sql.toString(), queryBeans, pageBean);
        ResultSet rs = ps.executeQuery();
        List<StatisticsBean> list = new ArrayList<>();
        while (rs.next()) {
            int userId = rs.getInt("USER_ID");
            String name = rs.getString("NAME");
            int sold = rs.getInt("SOLD");
            int change = rs.getInt("CHANGE");
            int refund = rs.getInt("REFUND");
            int presReg = rs.getInt("PRES_REG");
            int refundPres = rs.getInt("REFUND_PRES");
            StatisticsBean statisticsBean = new StatisticsBean(userId, name, sold, change, refund, presReg, refundPres);
            list.add(statisticsBean);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public List<LogBean> fastFindByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM (SELECT rowNum AS RN,A.* FROM (SELECT * FROM V_LOG_USER WHERE 1=1");
        for (QueryBean queryBean : queryBeans) {
            if (queryBean.getValue() != null && !"".equals(queryBean.getValue())) {
                sql.append(" AND ").append(queryBean.getField()).append(" ").append(queryBean.getOperator()).append(" ?");
            }
        }
        if (field != null) {
            sql.append(" ORDER BY ").append(field);
        }
        if (desc) {
            sql.append(" DESC");
        }
        sql.append(") A) WHERE RN>? AND RN<=?");
        PreparedStatement ps = getPreparedStatement(conn, sql.toString(), queryBeans, pageBean);
        ResultSet rs = ps.executeQuery();
        List<LogBean> list = new ArrayList<>();
        while (rs.next()) {
            int logId = rs.getInt("LOG_ID");
            int userId = rs.getInt("USER_ID");
            String name = rs.getString("NAME");
            UserBean user = new UserBean();
            user.setUserId(userId);
            user.setName(name);
            String logName = rs.getString("LOG_NAME");
            Timestamp logTime = rs.getTimestamp("LOG_TIME");
            LogBean logBean = new LogBean(logId, user, logName, logTime, null);
            list.add(logBean);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }
}
