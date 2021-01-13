package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Dao;
import com.lostred.ics.bean.ParamBean;
import com.lostred.ics.bean.RoleBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dao.UserDao;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.util.JdbcUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Dao("UserDao")
public class UserDaoImpl extends BaseDaoImpl<UserBean> implements UserDao {
    @Override
    public UserBean findByUsernameAndPassword(Connection conn, String username, String password) throws SQLException {
        String sql = "SELECT * FROM T_USER WHERE USERNAME=? AND PASSWORD=? AND PARAM_STATE_ID!=3";
        UserBean userBean = null;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            userBean = getBean(conn, rs);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return userBean;
    }

    @Override
    public int changeState(Connection conn, int paramId, int userId) throws SQLException, IOException {
        String sql = "UPDATE T_USER SET PARAM_STATE_ID=? WHERE USER_ID=?";
        int num = 0;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, paramId);
        ps.setInt(2, userId);
        num += ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public String resetPassword(Connection conn, int userId) throws IOException, SQLException {
        String sql = "UPDATE T_USER SET PASSWORD=? WHERE USER_ID=?";
        Properties prop = new Properties();
        prop.load(UserDaoImpl.class.getClassLoader().getResourceAsStream("config.properties"));
        String password = prop.getProperty("defaultPassword");
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, password);
        ps.setInt(2, userId);
        ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return password;
    }

    @Override
    public int insert(Connection conn, UserBean userBean) throws SQLException {
        String sql = "INSERT INTO T_USER (USER_ID,USERNAME,PASSWORD,NAME,ROLE_ID,PARAM_SECTION_ID,PARAM_STATE_ID,DESC_INFO) VALUES " +
                "(?,?,?,?,?,?,1,'')";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userBean.getUserId());
        ps.setString(2, userBean.getUsername());
        ps.setString(3, userBean.getPassword());
        ps.setString(4, userBean.getName());
        ps.setInt(5, userBean.getRoleBean().getRoleId());
        ps.setInt(6, userBean.getSection().getParamId());
        int num = ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public List<UserBean> findByRoleId(Connection conn, int roleId) throws SQLException {
        String sql = "SELECT * FROM T_USER WHERE ROLE_ID=? AND PARAM_STATE_ID=1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, roleId);
        ResultSet rs = ps.executeQuery();
        List<UserBean> list = listBean(conn, rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public List<UserBean> fastFindBeanByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM (SELECT rowNum AS RN,A.* FROM (SELECT * FROM V_USER WHERE 1=1");
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
        List<UserBean> list = new ArrayList<>();
        while (rs.next()) {
            int userId = rs.getInt("USER_ID");
            String name = rs.getString("NAME");
            int roleId = rs.getInt("ROLE_ID");
            String roleName = rs.getString("ROLE_NAME");
            RoleBean roleBean = new RoleBean();
            roleBean.setRoleId(roleId);
            roleBean.setRoleName(roleName);
            int paramSectionId = rs.getInt("PARAM_SECTION_ID");
            String sectionName = rs.getString("SECTION");
            ParamBean section = new ParamBean();
            section.setParamId(paramSectionId);
            section.setParamName(sectionName);
            int paramStateId = rs.getInt("PARAM_STATE_ID");
            String stateName = rs.getString("STATE");
            ParamBean state = new ParamBean();
            state.setParamId(paramStateId);
            state.setParamName(stateName);
            UserBean userBean = new UserBean();
            userBean.setUserId(userId);
            userBean.setName(name);
            userBean.setRoleBean(roleBean);
            userBean.setSection(section);
            userBean.setState(state);
            list.add(userBean);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }
}
