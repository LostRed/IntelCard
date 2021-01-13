package com.lostred.ics.service.impl;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Service;
import com.lostred.ics.bean.LogBean;
import com.lostred.ics.bean.ParamBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dao.ParamDao;
import com.lostred.ics.service.ParamService;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service("ParamService")
public class ParamServiceImpl extends BaseServiceImpl<ParamBean> implements ParamService {
    @Autowired
    private ParamDao paramDao;

    @Override
    public List<ParamBean> findByType(String type) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<ParamBean> list = new ArrayList<>();
        try {
            list = paramDao.findByType(conn, type);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public List<ParamBean> findCardState(int value) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<ParamBean> list = new ArrayList<>();
        try {
            list = paramDao.findCardState(conn, value);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public List<ParamBean> findUserState(int value) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<ParamBean> list = new ArrayList<>();
        try {
            list = paramDao.findUserState(conn, value);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public List<String> findAllParamType() {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<String> list = new ArrayList<>();
        try {
            list = paramDao.findAllParamType(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public int changeParam(int[] paramIds, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            for (int paramId : paramIds) {
                num += paramDao.changeParam(conn, paramId);
            }
            if (num > 0) {
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("删除参数");
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
}
