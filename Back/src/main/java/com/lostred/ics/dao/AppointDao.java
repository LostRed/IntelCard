package com.lostred.ics.dao;

import com.lostred.ics.bean.AppointBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dto.DayWorkBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 排班和预约dao接口
 */
public interface AppointDao extends BaseDao<AppointBean> {
    /**
     * 插入新排班
     *
     * @param conn        数据库连接对象
     * @param appointBean 排班对象
     * @return 受影响的数据数量
     * @throws SQLException SQL异常
     */
    int insert(Connection conn, AppointBean appointBean) throws SQLException;

    /**
     * 检查某医生用户在某时间是否存在排班
     *
     * @param conn     数据库连接对象
     * @param doctor   医生用户对象
     * @param workTime 时间
     * @return 存在返回true，否则返回false
     * @throws SQLException SQL异常
     */
    boolean checkAppoint(Connection conn, UserBean doctor, Timestamp workTime) throws SQLException;

    /**
     * 查询当前周内的所有排班
     *
     * @param conn             数据库连接对象
     * @param currentFirstDate 本周第一天的日期
     * @return 医生排班对象集合
     * @throws SQLException SQL异常
     */
    List<DayWorkBean> findByWeek(Connection conn, Timestamp currentFirstDate) throws SQLException;

    /**
     * 根据医生和日期查找排班
     *
     * @param conn   数据库连接对象
     * @param doctor 医生用户
     * @param date   日期
     * @return 排班对象集合
     * @throws SQLException SQL异常
     */
    List<AppointBean> findByDoctorAndDate(Connection conn, UserBean doctor, Timestamp date) throws SQLException;

    /**
     * 根据就诊人id查找预约
     *
     * @param conn      数据库连接对象
     * @param patientId 就诊人id
     * @return 预约对象集合
     * @throws SQLException SQL异常
     */
    List<AppointBean> findByPatientId(Connection conn, int patientId) throws SQLException;

    /**
     * 根据预约id修改预约状态为已过期
     *
     * @param conn      数据库连接对象
     * @param appointId 预约id
     * @return 受影响的数据数量
     * @throws SQLException SQL异常
     */
    int changeState(Connection conn, int appointId) throws SQLException;
}
