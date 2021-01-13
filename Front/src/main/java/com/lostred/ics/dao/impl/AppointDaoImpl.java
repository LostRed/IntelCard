package com.lostred.ics.dao.impl;

import com.lostred.ics.annotation.Dao;
import com.lostred.ics.bean.AppointBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dao.AppointDao;
import com.lostred.ics.dao.UserDao;
import com.lostred.ics.dto.DayWorkBean;
import com.lostred.ics.util.JdbcUtil;
import com.lostred.ics.util.TimeUtil;
import com.lostred.ics.util.WiredUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Dao("AppointDao")
public class AppointDaoImpl extends BaseDaoImpl<AppointBean> implements AppointDao {
    @Override
    public int insert(Connection conn, AppointBean appointBean) throws SQLException {
        String sql = "INSERT INTO T_APPOINT (APPOINT_ID,USER_ID,WORK_TIME,APPOINT_TIME,CLINIC_TIME,PATIENT_ID,PARAM_STATE_ID)" +
                "VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, appointBean.getAppointId());
        ps.setInt(2, appointBean.getDoctor().getUserId());
        ps.setTimestamp(3, appointBean.getWorkTime());
        ps.setTimestamp(4, appointBean.getAppointTime());
        ps.setTimestamp(5, appointBean.getClinicTime());
        if (appointBean.getPatientBean() != null) {
            ps.setInt(6, appointBean.getPatientBean().getPatientId());
        } else {
            ps.setInt(6, 0);
        }
        ps.setInt(7, appointBean.getState().getParamId());
        int num = ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }

    @Override
    public boolean checkAppoint(Connection conn, UserBean doctor, Timestamp workTime) throws SQLException {
        String sql = "SELECT * FROM T_APPOINT WHERE USER_ID=? AND WORK_TIME=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, doctor.getUserId());
        ps.setTimestamp(2, workTime);
        ResultSet rs = ps.executeQuery();
        boolean flag = false;
        if (rs.next()) {
            flag = true;
        }
        JdbcUtil.getInstance().release(ps, rs);
        return flag;
    }

    @Override
    public List<DayWorkBean> findByWeek(Connection conn, Timestamp currentFirstDate) throws SQLException {
        String sql = "SELECT DISTINCT TO_CHAR(WORK_TIME,'YYYY-MM-DD') AS DAY,USER_ID FROM T_APPOINT WHERE TO_CHAR(WORK_TIME,'YYYY-MM-DD')>=? AND TO_CHAR(WORK_TIME,'YYYY-MM-DD')<? AND WORK_TIME>SYSDATE";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, TimeUtil.toDayString(currentFirstDate));
        long time = currentFirstDate.getTime() + 7 * 24 * 60 * 60 * 1000;
        Timestamp endTime = new Timestamp(time);
        ps.setString(2, TimeUtil.toDayString(endTime));
        ResultSet rs = ps.executeQuery();
        List<DayWorkBean> list = new ArrayList<>();
        while (rs.next()) {
            int userId = rs.getInt("USER_ID");
            UserDao userDao = (UserDao) WiredUtil.DAO_INSTANCE_MAP.get("UserDao");
            UserBean doctor = userDao.findBeanById(conn, userId);
            Timestamp timestamp = TimeUtil.toTimestamp(rs.getString("DAY"));
            DayWorkBean dayWorkBean = new DayWorkBean(doctor, timestamp);
            list.add(dayWorkBean);
        }
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public List<AppointBean> findByDoctorAndDate(Connection conn, UserBean doctor, Timestamp date) throws SQLException {
        String sql = "SELECT * FROM T_APPOINT WHERE TO_CHAR(WORK_TIME,'YYYY-MM-DD')=? AND USER_ID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, TimeUtil.toDayString(date));
        ps.setInt(2, doctor.getUserId());
        ResultSet rs = ps.executeQuery();
        List<AppointBean> list = listBean(conn, rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public List<AppointBean> findByPatientId(Connection conn, int patientId) throws SQLException {
        String sql = "SELECT * FROM T_APPOINT WHERE PATIENT_ID=? AND PARAM_STATE_ID=20 AND WORK_TIME>SYSDATE";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, patientId);
        ResultSet rs = ps.executeQuery();
        List<AppointBean> list = listBean(conn, rs);
        JdbcUtil.getInstance().release(ps, rs);
        return list;
    }

    @Override
    public int changeState(Connection conn, int appointId) throws SQLException {
        String sql = "UPDATE T_APPOINT SET PARAM_STATE_ID=21,CLINIC_TIME=SYSDATE WHERE APPOINT_ID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, appointId);
        int num = ps.executeUpdate();
        JdbcUtil.getInstance().release(ps, null);
        return num;
    }
}
