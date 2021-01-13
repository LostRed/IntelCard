package com.lostred.ics.service;

import com.lostred.ics.bean.AppointBean;
import com.lostred.ics.bean.CardBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dto.DayWorkBean;

import java.sql.Timestamp;
import java.util.List;

/**
 * 排班和预约业务接口
 */
public interface AppointService extends BaseService<AppointBean> {
    /**
     * 新增排班
     *
     * @param doctor     医生用户
     * @param workTime   工作时间
     * @param actionUser 执行用户
     * @return 排班对象
     */
    AppointBean insertAppoint(UserBean doctor, Timestamp workTime, UserBean actionUser);

    /**
     * 删除排班
     *
     * @param appointId  排班id
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int deleteAppoint(int appointId, UserBean actionUser);

    /**
     * 查找当前周的排班
     *
     * @param currentFirstDate 当前星期第一天的日期
     * @return 医生排班集合
     */
    List<DayWorkBean> findAppointByWeek(Timestamp currentFirstDate);

    /**
     * 根据医生和日期查询排班
     *
     * @param doctor    医生用户
     * @param timestamp 时间
     * @return 排班集合
     */
    List<AppointBean> findByDoctorAndDate(UserBean doctor, Timestamp timestamp);

    /**
     * 就诊人预约
     *
     * @param cardBean    一卡通对象
     * @param appointBean 排班/预约对象
     * @return 受影响的数据数量
     */
    int register(CardBean cardBean, AppointBean appointBean);

    /**
     * 就诊人撤销预约
     *
     * @param cardBean    一卡通对象
     * @param appointBean 排班/预约对象
     * @return 受影响的数据数量
     */
    int cancel(CardBean cardBean, AppointBean appointBean);

    /**
     * 根据就诊人id插叙预约
     *
     * @param patientId 就诊人id
     * @return 预约对象集合
     */
    List<AppointBean> findByPatientId(int patientId);

    /**
     * 取号
     *
     * @param appointIds 预约id数组
     * @return 受影响的数据数量
     */
    int offer(int[] appointIds);
}
