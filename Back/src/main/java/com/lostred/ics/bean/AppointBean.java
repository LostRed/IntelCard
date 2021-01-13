package com.lostred.ics.bean;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;

import java.sql.Timestamp;

@Entity(value = "APPOINT", tableName = "T_APPOINT", daoName = "AppointDao", name = "排班预约")
public class AppointBean {
    @TableColumn(columnName = "APPOINT_ID")
    private int appointId;
    @TableColumn(columnName = "USER_ID", reference = UserBean.class)
    private UserBean doctor;
    @TableColumn(columnName = "WORK_TIME")
    private Timestamp workTime;
    @TableColumn(columnName = "APPOINT_TIME")
    private Timestamp appointTime;
    @TableColumn(columnName = "CLINIC_TIME")
    private Timestamp clinicTime;
    @TableColumn(columnName = "PATIENT_ID", reference = PatientBean.class)
    private PatientBean patientBean;
    @TableColumn(columnName = "PARAM_STATE_ID", reference = ParamBean.class)
    private ParamBean state;

    public AppointBean() {
    }

    public AppointBean(int appointId, UserBean doctor, Timestamp workTime, Timestamp appointTime, Timestamp clinicTime, PatientBean patientBean, ParamBean state) {
        this.appointId = appointId;
        this.doctor = doctor;
        this.workTime = workTime;
        this.appointTime = appointTime;
        this.clinicTime = clinicTime;
        this.patientBean = patientBean;
        this.state = state;
    }

    public int getAppointId() {
        return appointId;
    }

    public void setAppointId(int appointId) {
        this.appointId = appointId;
    }

    public UserBean getDoctor() {
        return doctor;
    }

    public void setDoctor(UserBean doctor) {
        this.doctor = doctor;
    }

    public Timestamp getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Timestamp workTime) {
        this.workTime = workTime;
    }

    public Timestamp getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Timestamp appointTime) {
        this.appointTime = appointTime;
    }

    public Timestamp getClinicTime() {
        return clinicTime;
    }

    public void setClinicTime(Timestamp clinicTime) {
        this.clinicTime = clinicTime;
    }

    public PatientBean getPatientBean() {
        return patientBean;
    }

    public void setPatientBean(PatientBean patientBean) {
        this.patientBean = patientBean;
    }

    public ParamBean getState() {
        return state;
    }

    public void setState(ParamBean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AppointBean{" +
                "appointId=" + appointId +
                ", doctor=" + doctor +
                ", workTime=" + workTime +
                ", appointTime=" + appointTime +
                ", clinicTime=" + clinicTime +
                ", patientBean=" + patientBean +
                ", state=" + state +
                '}';
    }
}
