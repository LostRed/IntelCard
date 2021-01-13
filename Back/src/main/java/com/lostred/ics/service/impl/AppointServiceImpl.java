package com.lostred.ics.service.impl;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Service;
import com.lostred.ics.bean.*;
import com.lostred.ics.dao.AppointDao;
import com.lostred.ics.dao.CardDao;
import com.lostred.ics.dao.ParamDao;
import com.lostred.ics.dao.RecordDao;
import com.lostred.ics.dto.DayWorkBean;
import com.lostred.ics.service.AppointService;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service("AppointService")
public class AppointServiceImpl extends BaseServiceImpl<AppointBean> implements AppointService {
    @Autowired
    private AppointDao appointDao;
    @Autowired
    private ParamDao paramDao;
    @Autowired
    private CardDao cardDao;
    @Autowired
    private RecordDao recordDao;

    @Override
    public AppointBean insertAppoint(UserBean doctor, Timestamp workTime, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        AppointBean appointBean = new AppointBean();
        try {
            conn.setAutoCommit(false);
            boolean flag = appointDao.checkAppoint(conn, doctor, workTime);
            if (!flag) {
                int appointNewId = appointDao.getNewId(conn);
                appointBean.setAppointId(appointNewId);
                appointBean.setDoctor(doctor);
                appointBean.setWorkTime(workTime);
                appointBean.setState(paramDao.findBeanById(conn, 19));
                appointDao.insert(conn, appointBean);
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("新增医生排班");
                logDao.insert(conn, logBean);
            } else {
                appointBean = null;
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            appointBean = null;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return appointBean;
    }

    @Override
    public int deleteAppoint(int appointId, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            if (appointDao.findBeanById(conn, appointId).getPatientBean() == null) {
                num = appointDao.deleteBeanById(conn, appointId);
            }
            if (num > 0) {
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("删除医生排班");
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
    public List<DayWorkBean> findAppointByWeek(Timestamp currentFirstDate) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<DayWorkBean> list = new ArrayList<>();
        try {
            conn.setAutoCommit(false);
            list = appointDao.findByWeek(conn, currentFirstDate);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public List<AppointBean> findByDoctorAndDate(UserBean doctor, Timestamp timestamp) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<AppointBean> list = new ArrayList<>();
        try {
            conn.setAutoCommit(false);
            list = appointDao.findByDoctorAndDate(conn, doctor, timestamp);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public int register(CardBean cardBean, AppointBean appointBean) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            double remain = cardBean.getAmount() - 20;
            if (remain > 0) {
                cardBean.setAmount(remain);
                cardDao.updateBean(conn, cardBean);
                RecordBean recordBean = new RecordBean();
                recordBean.setCardBean(cardBean);
                recordBean.setName(paramDao.findBeanById(conn, 13));
                recordBean.setAmount(-20);
                recordDao.insertBean(conn, recordBean);
                appointBean.setState(paramDao.findBeanById(conn, 20));
                appointBean.setAppointTime(new Timestamp(System.currentTimeMillis()));
                appointBean.setPatientBean(cardBean.getPatientBean());
                num += appointDao.updateBean(conn, appointBean);
                if (num > 0) {
                    LogBean logBean = new LogBean();
                    logBean.setLogName("就诊人预约");
                    logBean.setDescInfo("就诊人：" + cardBean.getPatientBean().getName() + "，预约医生：" + appointBean.getDoctor().getName());
                    logDao.insert(conn, logBean);
                }
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
    public int cancel(CardBean cardBean, AppointBean appointBean) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            double remain = cardBean.getAmount() + 20;
            if (remain > 0) {
                cardBean.setAmount(remain);
                cardDao.updateBean(conn, cardBean);
                RecordBean recordBean = new RecordBean();
                recordBean.setCardBean(cardBean);
                recordBean.setName(paramDao.findBeanById(conn, 14));
                recordBean.setAmount(20);
                recordDao.insertBean(conn, recordBean);
                appointBean.setState(paramDao.findBeanById(conn, 19));
                appointBean.setAppointTime(new Timestamp(System.currentTimeMillis()));
                appointBean.setPatientBean(null);
                num += appointDao.updateBean(conn, appointBean);
                if (num > 0) {
                    LogBean logBean = new LogBean();
                    logBean.setLogName("就诊人撤销预约");
                    logBean.setDescInfo("就诊人：" + cardBean.getPatientBean().getName() + "，撤销预约ID：" + appointBean.getAppointId());
                    logDao.insert(conn, logBean);
                }
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
    public List<AppointBean> findByPatientId(int patientId) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<AppointBean> list = new ArrayList<>();
        try {
            list = appointDao.findByPatientId(conn, patientId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public int offer(int[] appointIds) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            for (int appointId : appointIds) {
                num += appointDao.changeState(conn, appointId);
            }
            if (num > 0) {
                LogBean logBean = new LogBean();
                logBean.setLogName("就诊人取号");
                logBean.setDescInfo("就诊人：" + appointDao.findBeanById(conn, appointIds[0]).getPatientBean().getName());
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
}
