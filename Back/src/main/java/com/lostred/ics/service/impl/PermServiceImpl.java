package com.lostred.ics.service.impl;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Service;
import com.lostred.ics.bean.LogBean;
import com.lostred.ics.bean.MenuBean;
import com.lostred.ics.bean.RoleBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dao.LogDao;
import com.lostred.ics.dao.PermDao;
import com.lostred.ics.service.PermService;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;

@Service("PermService")
public class PermServiceImpl implements PermService {
    @Autowired
    private PermDao permDao;
    @Autowired
    private LogDao logDao;

    @Override
    public int insertPerm(RoleBean roleBean, MenuBean menuBean, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            num += permDao.insertPerm(conn, roleBean.getRoleId(), menuBean.getMenuId());
            LogBean logBean = new LogBean();
            logBean.setActionUser(actionUser);
            logBean.setLogName("新增权限");
            logBean.setDescInfo("新增了" + num + "条权限");
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
    public int insertAllPerm(RoleBean roleBean, MenuBean[] menuBeans, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            for (MenuBean menuBean : menuBeans) {
                boolean flag = permDao.findPerm(conn, roleBean.getRoleId(), menuBean.getMenuId());
                if (!flag && menuBean.getParentMenuBean() != null) {
                    num += permDao.insertPerm(conn, roleBean.getRoleId(), menuBean.getMenuId());
                }
            }
            LogBean logBean = new LogBean();
            logBean.setActionUser(actionUser);
            logBean.setLogName("新增权限");
            logBean.setDescInfo("新增了" + num + "条权限");
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
    public int removePerm(RoleBean roleBean, MenuBean menuBean, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            num += permDao.deletePerm(conn, roleBean.getRoleId(), menuBean.getMenuId());
            LogBean logBean = new LogBean();
            logBean.setActionUser(actionUser);
            logBean.setLogName("移除权限");
            logBean.setDescInfo("移除了" + num + "条权限");
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
    public int removeAllPerm(RoleBean roleBean, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            num += permDao.deleteAll(conn, roleBean.getRoleId());
            LogBean logBean = new LogBean();
            logBean.setActionUser(actionUser);
            logBean.setLogName("移除权限");
            logBean.setDescInfo("移除了" + num + "条权限");
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
