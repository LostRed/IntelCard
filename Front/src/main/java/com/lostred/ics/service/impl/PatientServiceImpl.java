package com.lostred.ics.service.impl;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Service;
import com.lostred.ics.bean.PatientBean;
import com.lostred.ics.dao.CardDao;
import com.lostred.ics.dao.PatientDao;
import com.lostred.ics.service.PatientService;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;

@Service("PatientService")
public class PatientServiceImpl extends BaseServiceImpl<PatientBean> implements PatientService {
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private CardDao cardDao;

    @Override
    public boolean findIdCard(String idCard) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        boolean flag = false;
        try {
            flag = patientDao.findIdCard(conn, idCard);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return flag;
    }

    @Override
    public boolean findPhone(String phone) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        boolean flag = false;
        try {
            flag = patientDao.findPhone(conn, phone);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return flag;
    }
}
