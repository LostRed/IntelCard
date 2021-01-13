package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Dao;
import com.lostred.ics.bean.ApplyBean;
import com.lostred.ics.bean.ParamBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dao.ApplyDao;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Dao("ApplyDao")
public class ApplyDaoImpl extends BaseDaoImpl<ApplyBean> implements ApplyDao {
    @Override
    public List<Integer> findIdByApplyUserId(Connection conn, int ApplyUserId) throws SQLException {
        String sql = "SELECT * FROM T_APPLY WHERE APPLY_USER_ID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, ApplyUserId);
        ResultSet rs = ps.executeQuery();
        List<Integer> applyIds = new ArrayList<>();
        while (rs.next()) {
            int applyId = rs.getInt("APPLY_ID");
            applyIds.add(applyId);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return applyIds;
    }

    @Override
    public List<ApplyBean> fastFindBeanByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM (SELECT rowNum AS RN,A.* FROM (SELECT * FROM V_APPLY WHERE 1=1");
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
        List<ApplyBean> list = new ArrayList<>();
        while (rs.next()) {
            int applyId = rs.getInt("APPLY_ID");
            Timestamp applyTime = rs.getTimestamp("APPLY_TIME");
            int num = rs.getInt("APPLY_NUM");
            Timestamp auditTime = rs.getTimestamp("AUDIT_TIME");
            int applyUserId = rs.getInt("APPLY_USER_ID");
            String applyUserName = rs.getString("APPLY_USER");
            int auditUserId = rs.getInt("AUDIT_USER_ID");
            String auditUserName = rs.getString("AUDIT_USER");
            UserBean applyUser = new UserBean();
            applyUser.setUserId(applyUserId);
            applyUser.setName(applyUserName);
            UserBean auditUser = new UserBean();
            auditUser.setUserId(auditUserId);
            auditUser.setName(auditUserName);
            int paramStateId = rs.getInt("PARAM_STATE_ID");
            String stateName = rs.getString("STATE");
            ParamBean state = new ParamBean();
            state.setParamId(paramStateId);
            state.setParamName(stateName);
            ApplyBean applyBean = new ApplyBean();
            applyBean.setApplyId(applyId);
            applyBean.setApplyNum(num);
            applyBean.setApplyTime(applyTime);
            applyBean.setAuditTime(auditTime);
            applyBean.setApplyUser(applyUser);
            applyBean.setAuditUser(auditUser);
            applyBean.setState(state);
            list.add(applyBean);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }
}
