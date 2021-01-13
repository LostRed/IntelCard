package com.lostred.ics.dto;

import com.lostred.ics.bean.UserBean;

import java.sql.Timestamp;

/**
 * 医生排班
 */
public class DayWorkBean {
    /**
     * 医生用户
     */
    private UserBean doctor;
    /**
     * 时间
     */
    private Timestamp date;

    public DayWorkBean() {
    }

    public DayWorkBean(UserBean doctor, Timestamp date) {
        this.doctor = doctor;
        this.date = date;
    }

    public UserBean getDoctor() {
        return doctor;
    }

    public void setDoctor(UserBean doctor) {
        this.doctor = doctor;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DayWorkBean{" +
                "doctor=" + doctor +
                ", date=" + date +
                '}';
    }
}
