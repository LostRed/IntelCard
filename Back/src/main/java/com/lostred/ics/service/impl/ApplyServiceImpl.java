package com.lostred.ics.service.impl;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Service;
import com.lostred.ics.bean.ApplyBean;
import com.lostred.ics.bean.LogBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dao.ApplyDao;
import com.lostred.ics.dao.CardDao;
import com.lostred.ics.dao.ParamDao;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.ApplyService;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service("ApplyService")
public class ApplyServiceImpl extends BaseServiceImpl<ApplyBean> implements ApplyService {
    @Autowired
    private ApplyDao applyDao;
    @Autowired
    private CardDao cardDao;
    @Autowired
    private ParamDao paramDao;

    @Override
    public int auditApply(ApplyBean applyBean, String cardCodeStart, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            String prefix = cardCodeStart.substring(0, cardCodeStart.length() - 8);
            String subString = cardCodeStart.substring(cardCodeStart.length() - 8);
            int subCode = Integer.parseInt(subString);
            int applyNum = applyBean.getApplyNum();
            String cardCodeEnd = prefix + String.format("%08d", subCode + applyNum - 1);
            int total = cardDao.countAvailable(conn, cardCodeStart, cardCodeEnd);
            if (total == applyNum) {
                num += cardDao.audit(conn, applyBean.getApplyId(), cardCodeStart, cardCodeEnd);
                applyBean.setState(paramDao.findBeanById(conn, 10));
                applyBean.setAuditUser(actionUser);
                applyBean.setAuditTime(new Timestamp(System.currentTimeMillis()));
                applyDao.updateBean(conn, applyBean);
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("审批一卡通");
                logBean.setDescInfo("审批的一卡通申请ID为" + applyBean.getApplyId());
                logDao.insert(conn, logBean);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            num = 0;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return num;
    }

    @Override
    public int insertApply(int applyNum, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            ApplyBean applyBean = new ApplyBean();
            applyBean.setApplyUser(actionUser);
            applyBean.setApplyNum(applyNum);
            applyBean.setState(paramDao.findBeanById(conn, 9));
            num += applyDao.insertBean(conn, applyBean);
            LogBean logBean = new LogBean();
            logBean.setActionUser(actionUser);
            logBean.setLogName("申请一卡通");
            logDao.insert(conn, logBean);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            num = 0;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return num;
    }

    @Override
    public List<ApplyBean> fastFindBeanByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<ApplyBean> list = new ArrayList<>();
        try {
            list = applyDao.fastFindBeanByPage(conn, queryBeans, pageBean, field, desc);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }
}
