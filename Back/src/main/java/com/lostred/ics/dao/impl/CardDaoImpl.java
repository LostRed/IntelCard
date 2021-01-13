package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Dao;
import com.lostred.ics.bean.*;
import com.lostred.ics.dao.CardDao;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Dao("CardDao")
public class CardDaoImpl extends BaseDaoImpl<CardBean> implements CardDao {
    @Override
    public int deleteByCardId(Connection conn, int cardId) throws SQLException {
        String sql = "DELETE FROM T_CARD WHERE CARD_ID=? AND PARAM_STATE_ID=4";
        int num = 0;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, cardId);
        num += ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public int batchInsert(Connection conn, int cardId, List<String> cardCodes) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT ALL ");
        for (int i = 0; i < cardCodes.size(); i++) {
            sql.append("INTO T_CARD (CARD_ID,CARD_CODE,CREATE_TIME,APPLY_ID,PATIENT_ID,PARAM_STATE_ID,AMOUNT,DEPOSIT,DESC_INFO) VALUES (?,?,SYSDATE,NULL,NULL,4,0,0,'') ");
        }
        sql.append("SELECT 1 FROM DUAL");
        PreparedStatement ps = conn.prepareStatement(sql.toString());
        int i = 0;
        for (String cardCode : cardCodes) {
            ps.setInt(++i, cardId++);
            ps.setString(++i, cardCode);
        }
        int num = 0;
        num += ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public List<String> repeatCardCode(Connection conn, String cardCodeStart, String cardCodeEnd) throws SQLException {
        String sql = "SELECT * FROM T_CARD WHERE CARD_CODE>=? AND CARD_CODE<=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, cardCodeStart);
        ps.setString(2, cardCodeEnd);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString("CARD_CODE"));
        }
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public int countCancelable(Connection conn, QueryBean[] queryBeans) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS TOTAL FROM T_CARD WHERE (PARAM_STATE_ID=7 OR PARAM_STATE_ID=8)");
        for (QueryBean queryBean : queryBeans) {
            if (queryBean.getValue() != null && !"".equals(queryBean.getValue())) {
                sql.append(" AND ").append(queryBean.getField()).append(" ").append(queryBean.getOperator()).append(" ?");
            }
        }
        PreparedStatement ps = getPreparedStatement(conn, sql.toString(), queryBeans, null);
        ResultSet rs = ps.executeQuery();
        int num = 0;
        if (rs.next()) {
            num = rs.getInt("TOTAL");
        }
        JdbcUtil.getInstance().release(ps, rs);
        return num;
    }

    @Override
    public List<CardBean> findCancelByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM (SELECT rowNum AS RN,A.* FROM (SELECT * FROM V_CARD WHERE (PARAM_STATE_ID=7 OR PARAM_STATE_ID=8)");
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
        List<CardBean> list = fastListCardBean(rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public int cancel(Connection conn, int cardId) throws SQLException {
        String sql = "UPDATE T_CARD SET PARAM_STATE_ID=6 WHERE CARD_ID=?";
        int num = 0;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, cardId);
        num += ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public int audit(Connection conn, int applyId, String cardCodeStart, String cardCodeEnd) throws SQLException {
        String sql = "UPDATE T_CARD SET PARAM_STATE_ID=7,APPLY_ID=? WHERE CARD_CODE>=? AND CARD_CODE<=? AND PARAM_STATE_ID=4";
        int num = 0;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, applyId);
        ps.setString(2, cardCodeStart);
        ps.setString(3, cardCodeEnd);
        num += ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public int countAvailable(Connection conn, String cardCodeStart, String cardCodeEnd) throws SQLException {
        String sql = "SELECT COUNT(*) TOTAL FROM T_CARD WHERE CARD_CODE>=? AND CARD_CODE<=? AND PARAM_STATE_ID=4";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, cardCodeStart);
        ps.setString(2, cardCodeEnd);
        ResultSet rs = ps.executeQuery();
        int total = 0;
        if (rs.next()) {
            total = rs.getInt("TOTAL");
        }
        JdbcUtil.getInstance().release(ps, rs);
        return total;
    }

    @Override
    public List<String> findByApplyId(Connection conn, int applyId) throws SQLException {
        String sql = "SELECT CARD_CODE FROM T_CARD WHERE APPLY_ID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, applyId);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString("CARD_CODE"));
        }
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public int sale(Connection conn, int amount, String cardCode, int patientId, UserBean actionUser) throws SQLException {
        String sql = "UPDATE T_CARD SET PARAM_STATE_ID=5,SALE_TIME=SYSDATE,AMOUNT=?,DEPOSIT=?,PATIENT_ID=?,SALE_USER_ID=? WHERE CARD_CODE=? AND PARAM_STATE_ID=7";
        int num = 0;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, amount);
        ps.setInt(2, 5);
        ps.setInt(3, patientId);
        ps.setInt(4, actionUser.getUserId());
        ps.setString(5, cardCode);
        num += ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public CardBean findSaleableByCardCode(Connection conn, String cardCode) throws SQLException {
        String sql = "SELECT * FROM T_CARD WHERE CARD_CODE=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, cardCode);
        ResultSet rs = ps.executeQuery();
        CardBean cardBean = null;
        if (rs.next()) {
            if (rs.getInt("PARAM_STATE_ID") == 7) {
                cardBean = getBean(conn, rs);
            }
        }
        JdbcUtil.getInstance().release(ps, rs);
        return cardBean;
    }

    @Override
    public CardBean findSoldOutByCardCode(Connection conn, String cardCode) throws SQLException {
        String sql = "SELECT * FROM T_CARD WHERE CARD_CODE=? AND PARAM_STATE_ID=5";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, cardCode);
        ResultSet rs = ps.executeQuery();
        CardBean cardBean = null;
        if (rs.next()) {
            cardBean = getBean(conn, rs);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return cardBean;
    }

    @Override
    public CardBean findByPatientId(Connection conn, int PatientId) throws SQLException {
        String sql = "SELECT * FROM T_CARD WHERE PATIENT_ID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, PatientId);
        ResultSet rs = ps.executeQuery();
        CardBean cardBean = null;
        if (rs.next()) {
            cardBean = getBean(conn, rs);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return cardBean;
    }

    @Override
    public int countFind(Connection conn, QueryBean[] queryBeans) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS TOTAL FROM V_CARD WHERE 1=1");
        for (QueryBean queryBean : queryBeans) {
            if (queryBean.getValue() != null && !"".equals(queryBean.getValue())) {
                sql.append(" AND ").append(queryBean.getField()).append(" ").append(queryBean.getOperator()).append(" ?");
            }
        }
        PreparedStatement ps = getPreparedStatement(conn, sql.toString(), queryBeans, null);
        ResultSet rs = ps.executeQuery();
        int num = 0;
        if (rs.next()) {
            num = (Integer) getValueFromDB(rs, "TOTAL");
        }
        JdbcUtil.getInstance().release(ps, rs);
        return num;
    }

    @Override
    public List<CardBean> fastFindByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM (SELECT rowNum AS RN,A.* FROM (SELECT * FROM V_CARD WHERE 1=1");
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
        List<CardBean> list = fastListCardBean(rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    private List<CardBean> fastListCardBean(ResultSet rs) throws SQLException {
        List<CardBean> list = new ArrayList<>();
        while (rs.next()) {
//                    A.CARD_ID,A.CARD_CODE,A.CREATE_TIME,
//                    A.SALE_USER_ID,E.NAME SALE_NAME,A.SALE_TIME,
//                    C.APPLY_USER_ID,F.NAME APPLY_NAME,C.APPLY_TIME,
//                    C.AUDIT_USER_ID,G.NAME AUDIT_NAME,C.AUDIT_TIME,
//                    A.PATIENT_ID,D.NAME PATIENT_NAME,
//                    A.PARAM_STATE_ID,B.PARAM_NAME,A.AMOUNT
            //申请单
            int applyId = rs.getInt("APPLY_ID");
            ApplyBean applyBean = null;
            if (applyId != 0) {
                int applyUserId = rs.getInt("APPLY_USER_ID");
                String applyName = rs.getString("APPLY_NAME");
                Timestamp applyTime = rs.getTimestamp("APPLY_TIME");
                UserBean applyUser = new UserBean();
                applyUser.setUserId(applyUserId);
                applyUser.setName(applyName);
                int auditUserId = rs.getInt("AUDIT_USER_ID");
                UserBean auditUser = null;
                Timestamp auditTime = null;
                if (auditUserId != 0) {
                    String auditName = rs.getString("AUDIT_NAME");
                    auditTime = rs.getTimestamp("AUDIT_TIME");
                    auditUser = new UserBean();
                    auditUser.setUserId(auditUserId);
                    auditUser.setName(auditName);
                }
                applyBean = new ApplyBean();
                applyBean.setApplyUser(applyUser);
                applyBean.setApplyTime(applyTime);
                applyBean.setAuditUser(auditUser);
                applyBean.setAuditTime(auditTime);
            }
            //就诊人
            int patientId = rs.getInt("PATIENT_ID");
            PatientBean patientBean = null;
            if (patientId != 0) {
                String patientName = rs.getString("PATIENT_NAME");
                patientBean = new PatientBean();
                patientBean.setPatientId(patientId);
                patientBean.setName(patientName);
            }
            //出售人
            int saleUserId = rs.getInt("SALE_USER_ID");
            UserBean saleUser = null;
            if (saleUserId != 0) {
                String saleName = rs.getString("SALE_NAME");
                saleUser = new UserBean();
                saleUser.setUserId(saleUserId);
                saleUser.setName(saleName);
            }
            //一卡通
            int cardId = rs.getInt("CARD_ID");
            String cardCode = rs.getString("CARD_CODE");
            Timestamp createTime = rs.getTimestamp("CREATE_TIME");
            Timestamp saleTime = rs.getTimestamp("SALE_TIME");
            //一卡通状态
            int paramStateId = rs.getInt("PARAM_STATE_ID");
            String paramName = rs.getString("PARAM_NAME");
            ParamBean paramBean = new ParamBean();
            paramBean.setParamId(paramStateId);
            paramBean.setParamName(paramName);
            double amount = rs.getDouble("AMOUNT");
            CardBean cardBean = new CardBean();
            cardBean.setCardId(cardId);
            cardBean.setCardCode(cardCode);
            cardBean.setCreateTime(createTime);
            cardBean.setApplyBean(applyBean);
            cardBean.setPatientBean(patientBean);
            cardBean.setSaleUser(saleUser);
            cardBean.setSaleTime(saleTime);
            cardBean.setAmount(amount);
            cardBean.setState(paramBean);
            list.add(cardBean);
        }
        return list;
    }
}

