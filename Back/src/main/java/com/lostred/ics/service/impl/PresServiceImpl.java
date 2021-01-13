package com.lostred.ics.service.impl;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Service;
import com.lostred.ics.bean.*;
import com.lostred.ics.dao.CardDao;
import com.lostred.ics.dao.ParamDao;
import com.lostred.ics.dao.PresDao;
import com.lostred.ics.dao.RecordDao;
import com.lostred.ics.service.PresService;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

@Service("PresService")
public class PresServiceImpl extends BaseServiceImpl<PresBean> implements PresService {
    @Autowired
    private PresDao presDao;
    @Autowired
    private ParamDao paramDao;
    @Autowired
    private CardDao cardDao;
    @Autowired
    private RecordDao recordDao;

    @Override
    public int insert(PresBean presBean, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            double total = presBean.getNum() * presBean.getPrice();
            CardBean cardBean = presBean.getCardBean();
            double remain = cardBean.getAmount() - total;
            if (remain >= 0) {
                cardBean.setAmount(remain);
                cardDao.updateBean(conn, cardBean);
                presBean.setState(paramDao.findBeanById(conn, 17));
                presBean.setCreateTime(new Timestamp(System.currentTimeMillis()));
                num += presDao.insertBean(conn, presBean);
                RecordBean recordBean = new RecordBean();
                recordBean.setActionUser(actionUser);
                recordBean.setCardBean(cardBean);
                recordBean.setName(paramDao.findBeanById(conn, 15));
                recordBean.setAmount(-total);
                recordDao.insertBean(conn, recordBean);
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("登记处方");
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
    public int refund(CardBean cardBean, PresBean[] presBeans, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            double total = 0;
            for (PresBean presBean : presBeans) {
                total += presBean.getPrice() * presBean.getNum();
                presBean.setState(paramDao.findBeanById(conn, 18));
                num += presDao.updateBean(conn, presBean);
            }
            cardBean.setAmount(cardBean.getAmount() + total);
            cardDao.updateBean(conn, cardBean);
            RecordBean recordBean = new RecordBean();
            recordBean.setActionUser(actionUser);
            recordBean.setCardBean(cardBean);
            recordBean.setName(paramDao.findBeanById(conn, 16));
            recordBean.setAmount(total);
            recordDao.insertBean(conn, recordBean);
            LogBean logBean = new LogBean();
            logBean.setActionUser(actionUser);
            logBean.setLogName("处方退费");
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
}
