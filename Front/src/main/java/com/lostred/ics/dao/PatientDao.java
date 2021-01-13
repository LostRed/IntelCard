package com.lostred.ics.dao;

import com.lostred.ics.bean.PatientBean;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 就诊人dao接口
 */
public interface PatientDao extends BaseDao<PatientBean> {
    /**
     * 根据身份证号码查找该身份证是否存在
     *
     * @param conn   数据库连接对象
     * @param idCard 身份证号码
     * @return 存在返回true，否则返回false
     * @throws SQLException SQL异常
     */
    boolean findIdCard(Connection conn, String idCard) throws SQLException;

    /**
     * 根据手机号码查找该手机号是否存在
     *
     * @param conn  数据库连接对象
     * @param phone 手机号码
     * @return 存在返回true，否则返回false
     * @throws SQLException SQL异常
     */
    boolean findPhone(Connection conn, String phone) throws SQLException;

    /**
     * 根据手机号或身份证号查找就诊人
     *
     * @param conn    数据库连接对象
     * @param keyword 搜索关键字
     * @return 就诊人对象
     * @throws SQLException SQL异常
     */
    PatientBean findByIdCardOrPhone(Connection conn, String keyword) throws SQLException;

    /**
     * 插入就诊人
     * @param conn 数据库连接对象
     * @param patientBean 就诊人对象
     * @return 受影响的数据数量
     * @throws SQLException SQL异常
     */
    int insert(Connection conn, PatientBean patientBean) throws SQLException;
}
