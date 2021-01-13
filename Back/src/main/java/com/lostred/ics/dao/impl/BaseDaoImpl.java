package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;
import com.lostred.ics.dao.BaseDao;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.util.JdbcUtil;
import com.lostred.ics.util.WiredUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    private final Class<?> clazz;
    private final String tableName;
    private final String seqNext;
    private final Field[] fields;

    public BaseDaoImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<?>) pt.getActualTypeArguments()[0];
        tableName = clazz.getAnnotation(Entity.class).tableName();
        seqNext = "SEQ_" + clazz.getAnnotation(Entity.class).value() + ".nextVal";
        fields = clazz.getDeclaredFields();
    }

    @SuppressWarnings("unchecked")
    public T getBean(Connection conn, ResultSet rs) throws SQLException {
        T bean = null;
        try {
            bean = (T) clazz.newInstance();
            for (Field field : fields) {
                field.setAccessible(true);
                Class<?> clazz = field.getAnnotation(TableColumn.class).reference();
                String columnName = field.getAnnotation(TableColumn.class).columnName();
                if (!clazz.getSimpleName().equals("Object")) {
                    int id = rs.getInt(columnName);
                    BaseDao<?> dao = (BaseDao<?>) WiredUtil.DAO_INSTANCE_MAP.get(clazz.getAnnotation(Entity.class).daoName());
                    Object object = dao.findBeanById(conn, id);
                    field.set(bean, object);
                } else {
                    field.set(bean, getValueFromDB(rs, columnName));
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<T> listBean(Connection conn, ResultSet rs) throws SQLException {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            T bean = getBean(conn, rs);
            list.add(bean);
        }
        return list;
    }

    public String getFieldString() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String columnName = fields[i].getAnnotation(TableColumn.class).columnName();
            if (i == fields.length - 1) {
                sb.append(columnName);
            } else {
                sb.append(columnName).append(",");
            }
        }
        return sb.toString();
    }

    public String getPlaceholderString() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            sb.append(",?");
        }
        return sb.toString();
    }

    public Object getValueFromDB(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName);
        if (value instanceof BigDecimal) {
            value = Integer.parseInt(value.toString());
        }
//        else if (value instanceof Timestamp) {
//            value = new Date(((Timestamp) value).getTime());
//        }
        return value;
    }

    public Object getValueFromBean(T bean, Field field) {
        field.setAccessible(true);
        Class<?> clazz = field.getAnnotation(TableColumn.class).reference();
        Object value = null;
        try {
            value = field.get(bean);
            if (!clazz.getSimpleName().equals("Object")) {
                if (value == null) {
                    value = 0;
                } else {
                    Field fieldId = value.getClass().getDeclaredFields()[0];
                    fieldId.setAccessible(true);
                    value = fieldId.get(value);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    public PreparedStatement getPreparedStatement(Connection conn, String sql, QueryBean[] queryBeans, PageBean pageBean) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 0;
        for (QueryBean queryBean : queryBeans) {
            if (queryBean.getValue() != null && !"".equals(queryBean.getValue())) {
                if (queryBean.getOperator().equals("LIKE")) {
                    ps.setObject(++i, "%" + queryBean.getValue() + "%");
                } else {
                    ps.setObject(++i, queryBean.getValue());
                }
            }
        }
        if (pageBean != null) {
            ps.setInt(++i, pageBean.getStartRow());
            ps.setInt(++i, pageBean.getEndRow());
        }
        return ps;
    }

    @Override
    public int getNewId(Connection conn) throws SQLException {
        String sql = "SELECT " + seqNext + " FROM DUAL";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int id = -1;
        if (rs.next()) {
            id = rs.getInt(1);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return id;
    }

    @Override
    public int insertBean(Connection conn, T bean) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (" + getFieldString() + ") VALUES (" + seqNext + getPlaceholderString() + ")";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 1; i < fields.length; i++) {
            ps.setObject(i, getValueFromBean(bean, fields[i]));
        }
        int num = ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public int deleteBeanById(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + fields[0].getAnnotation(TableColumn.class).columnName() + "=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1, id);
        int num = ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public int updateBean(Connection conn, T bean) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        for (int i = 1; i < fields.length; i++) {
            String columnName = fields[i].getAnnotation(TableColumn.class).columnName();
            if (i == fields.length - 1) {
                sql.append(columnName).append("=?");
            } else {
                sql.append(columnName).append("=?,");
            }
        }
        sql.append(" WHERE ").append(fields[0].getAnnotation(TableColumn.class).columnName()).append("=?");
        PreparedStatement ps = conn.prepareStatement(sql.toString());
        int i;
        for (i = 1; i < fields.length; i++) {
            ps.setObject(i, getValueFromBean(bean, fields[i]));
        }
        ps.setObject(i, getValueFromBean(bean, fields[0]));
        int num = ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public T findBeanById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE " + fields[0].getAnnotation(TableColumn.class).columnName() + "=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1, id);
        ResultSet rs = ps.executeQuery();
        T bean = null;
        if (rs.next()) {
            bean = getBean(conn, rs);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return bean;
    }

    @Override
    public int countFindBean(Connection conn, QueryBean[] queryBeans) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS TOTAL FROM " + tableName + " WHERE 1=1");
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
    public List<T> findBeanByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM (SELECT rowNum AS RN,A.* FROM (SELECT * FROM " + tableName + " WHERE 1=1");
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
        List<T> list = listBean(conn, rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public List<T> findAllBean(Connection conn) throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<T> list = listBean(conn, rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }
}
