package com.lostred.ics.service.impl;

import com.lostred.ics.annotation.Service;
import com.lostred.ics.bean.LogBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dto.StatisticsBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.LogService;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service("LogService")
public class LogServiceImpl extends BaseServiceImpl<LogBean> implements LogService {

    @Override
    public int logoff(UserBean userBean) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            LogBean logBean = new LogBean();
            logBean.setActionUser(userBean);
            logBean.setLogName("退出");
            num += logDao.insert(conn, logBean);
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
    public int countFindStatistics(QueryBean[] queryBeans) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int total = 0;
        try {
            total = logDao.countFind(conn, queryBeans);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return total;
    }

    @Override
    public List<StatisticsBean> findStatisticsByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<StatisticsBean> list = new ArrayList<>();
        try {
            list = logDao.findByPage(conn, queryBeans, pageBean, field, desc);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public List<LogBean> fastFindByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<LogBean> list = new ArrayList<>();
        try {
            list = logDao.fastFindByPage(conn, queryBeans, pageBean, field, desc);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }
}
