package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Dao;
import com.lostred.ics.bean.PatientBean;
import com.lostred.ics.dao.PatientDao;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Dao("PatientDao")
public class PatientDaoImpl extends BaseDaoImpl<PatientBean> implements PatientDao {
    @Override
    public boolean findIdCard(Connection conn, String idCard) throws SQLException {
        String sql = "SELECT * FROM T_PATIENT WHERE ID_CARD=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idCard);
        ResultSet rs = ps.executeQuery();
        boolean flag = false;
        if (rs.next()) {
            flag = true;
        }
        JdbcUtil.getInstance().release(ps, rs);
        return flag;
    }

    @Override
    public boolean findPhone(Connection conn, String phone) throws SQLException {
        String sql = "SELECT * FROM T_PATIENT WHERE PHONE=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, phone);
        ResultSet rs = ps.executeQuery();
        boolean flag = false;
        if (rs.next()) {
            flag = true;
        }
        JdbcUtil.getInstance().release(ps, rs);
        return flag;
    }

    @Override
    public PatientBean findByIdCardOrPhone(Connection conn, String keyword) throws SQLException {
        String sql = "SELECT * FROM T_PATIENT WHERE PHONE=? OR ID_CARD=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, keyword);
        ps.setString(2, keyword);
        ResultSet rs = ps.executeQuery();
        PatientBean patientBean = null;
        if (rs.next()) {
            patientBean = getBean(conn, rs);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return patientBean;
    }

    @Override
    public int insert(Connection conn, PatientBean patientBean) throws SQLException {
        String sql = "INSERT INTO T_PATIENT (PATIENT_ID,NAME,AGE,WEEK,SEX,NATIVE_PLACE,ID_CARD,PHONE,ADDR,DESC_INFO) VALUES " +
                "(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, patientBean.getPatientId());
        ps.setString(2, patientBean.getName());
        ps.setInt(3, patientBean.getAge());
        ps.setInt(4, patientBean.getWeek());
        ps.setString(5, patientBean.getSex());
        ps.setString(6, patientBean.getNativePlace());
        ps.setString(7, patientBean.getIdCard());
        ps.setString(8, patientBean.getPhone());
        ps.setString(9, patientBean.getAddr());
        ps.setString(10, patientBean.getDescInfo());
        int num = ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }
}
