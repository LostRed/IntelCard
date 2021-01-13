package com.lostred.ics.service.impl;


import com.lostred.ics.annotation.Entity;
import com.lostred.ics.bean.LogBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dao.BaseDao;
import com.lostred.ics.dao.LogDao;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.BaseService;
import com.lostred.ics.util.JdbcUtil;
import com.lostred.ics.util.WiredUtil;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected final LogDao logDao;
    private final Class<?> clazz;
    private final BaseDao<T> baseDao;

    @SuppressWarnings("unchecked")
    public BaseServiceImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<?>) pt.getActualTypeArguments()[0];
        baseDao = (BaseDao<T>) WiredUtil.DAO_INSTANCE_MAP.get(clazz.getAnnotation(Entity.class).daoName());
        logDao = (LogDao) WiredUtil.DAO_INSTANCE_MAP.get("LogDao");
    }

    @Override
    public int insertBean(T bean, UserBean userBean) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            num += baseDao.insertBean(conn, bean);
            LogBean logBean = new LogBean();
            logBean.setActionUser(userBean);
            logBean.setLogName("新增" + clazz.getAnnotation(Entity.class).name());
            logBean.setDescInfo("插入了" + num + "条数据");
            logDao.insertBean(conn, logBean);
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
    public int deleteBeanById(int id, UserBean userBean) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            num += baseDao.deleteBeanById(conn, id);
            LogBean logBean = new LogBean();
            logBean.setActionUser(userBean);
            logBean.setLogName("删除" + clazz.getAnnotation(Entity.class).name());
            logBean.setDescInfo("删除了" + num + "条数据");
            logDao.insertBean(conn, logBean);
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
    public int batchDeleteBean(int[] ids, UserBean userBean) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            for (int id : ids) {
                num += baseDao.deleteBeanById(conn, id);
            }
            LogBean logBean = new LogBean();
            logBean.setActionUser(userBean);
            logBean.setLogName("批量删除" + clazz.getAnnotation(Entity.class).name());
            logBean.setDescInfo("删除了" + num + "条数据");
            logDao.insertBean(conn, logBean);
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
    public int updateBean(T bean, UserBean userBean) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            num += baseDao.updateBean(conn, bean);
            LogBean logBean = new LogBean();
            logBean.setActionUser(userBean);
            logBean.setLogName("更新" + clazz.getAnnotation(Entity.class).name());
            logBean.setDescInfo("更新了" + num + "条数据");
            logDao.insertBean(conn, logBean);
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
    public T findBeanById(int id) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        T bean = null;
        try {
            bean = baseDao.findBeanById(conn, id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return bean;
    }

    @Override
    public int countFindBean(QueryBean[] queryBeans) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int count = 0;
        try {
            count = baseDao.countFindBean(conn, queryBeans);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return count;
    }

    @Override
    public List<T> findBeanByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<T> list = new ArrayList<>();
        try {
            list = baseDao.findBeanByPage(conn, queryBeans, pageBean, field, desc);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public List<T> findAllBean() {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<T> list = new ArrayList<>();
        try {
            list = baseDao.findAllBean(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }
}
