package com.lostred.ics.service.impl;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Service;
import com.lostred.ics.bean.LogBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dao.UserDao;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.UserService;
import com.lostred.ics.util.JdbcUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service("UserService")
public class UserServiceImpl extends BaseServiceImpl<UserBean> implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserBean loginCheck(String username, String password, String remoteAddr) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        UserBean userBean = null;
        try {
            conn.setAutoCommit(false);
            userBean = userDao.findByUsernameAndPassword(conn, username, password);
            if (userBean != null) {
                LogBean logBean = new LogBean();
                logBean.setActionUser(userBean);
                logBean.setLogName("登录");
                logBean.setDescInfo("登录IP：" + remoteAddr);
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
            if (userBean != null) {
                userBean.setState(null);
            }
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return userBean;
    }

    @Override
    public int changeState(int paramId, int[] userIds, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            LogBean logBean = new LogBean();
            logBean.setActionUser(actionUser);
            if (paramId != 3) {
                for (int userId : userIds) {
                    UserBean userBean = userDao.findBeanById(conn, userId);
                    if ("启用".equals(userBean.getState().getParamName())) {
                        num += userDao.changeState(conn, 2, userId);
                        logBean.setLogName("禁用用户");
                    } else {
                        num += userDao.changeState(conn, 1, userId);
                        logBean.setLogName("启用用户");
                    }
                }
            } else {
                for (int userId : userIds) {
                    num += userDao.changeState(conn, 3, userId);
                    logBean.setLogName("删除用户");
                }
            }
            logBean.setDescInfo("更改了" + num + "条数据");
            logDao.insert(conn, logBean);
            conn.commit();
        } catch (SQLException | IOException e) {
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
    public String resetPassword(int userId, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        String password;
        try {
            conn.setAutoCommit(false);
            password = userDao.resetPassword(conn, userId);
            LogBean logBean = new LogBean();
            logBean.setActionUser(actionUser);
            logBean.setLogName("重置密码");
            logBean.setDescInfo("重置密码的用户ID为" + userId);
            logDao.insert(conn, logBean);
            conn.commit();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            password = null;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return password;
    }

    @Override
    public String insert(UserBean userBean, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        String username;
        try {
            conn.setAutoCommit(false);
            int userId = userDao.getNewId(conn);
            userBean.setUserId(userId);
            username = "user" + userId;
            userBean.setUsername(username);
            userDao.insert(conn, userBean);
            LogBean logBean = new LogBean();
            logBean.setActionUser(actionUser);
            logBean.setLogName("新增用户");
            logBean.setDescInfo("新增用户的用户ID为" + userId);
            logDao.insert(conn, logBean);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            username = null;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return username;
    }

    @Override
    public List<UserBean> findByRoleId(int roleId) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<UserBean> list = new ArrayList<>();
        try {
            list = userDao.findByRoleId(conn, roleId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public int modifyPwd(String oldPwd, String newPwd, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            if (actionUser.getPassword().equals(oldPwd)) {
                actionUser.setPassword(newPwd);
                num += userDao.updateBean(conn, actionUser);
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("修改密码");
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
    public List<UserBean> findWorkUser() {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<UserBean> list = new ArrayList<>();
        try {
            list = userDao.findByRoleId(conn, 1);
            list.addAll(userDao.findByRoleId(conn, 2));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public List<UserBean> fastFindBeanByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<UserBean> list = new ArrayList<>();
        try {
            list = userDao.fastFindBeanByPage(conn, queryBeans, pageBean, field, desc);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }
}
