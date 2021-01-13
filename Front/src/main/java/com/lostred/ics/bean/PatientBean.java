package com.lostred.ics.bean;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;

@Entity(value = "PATIENT", tableName = "T_PATIENT", daoName = "PatientDao", name = "就诊人")
public class PatientBean {
    @TableColumn(columnName = "PATIENT_ID")
    private int patientId;
    @TableColumn(columnName = "NAME")
    private String name;
    @TableColumn(columnName = "AGE")
    private int age;
    @TableColumn(columnName = "WEEK")
    private int week;
    @TableColumn(columnName = "SEX")
    private String sex;
    @TableColumn(columnName = "NATIVE_PLACE")
    private String nativePlace;
    @TableColumn(columnName = "ID_CARD")
    private String idCard;
    @TableColumn(columnName = "PHONE")
    private String phone;
    @TableColumn(columnName = "ADDR")
    private String addr;
    @TableColumn(columnName = "DESC_INFO")
    private String descInfo;

    public PatientBean() {
    }

    public PatientBean(int patientId, String name, int age, int week, String sex, String nativePlace, String idCard, String phone, String addr, String descInfo) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.week = week;
        this.sex = sex;
        this.nativePlace = nativePlace;
        this.idCard = idCard;
        this.phone = phone;
        this.addr = addr;
        this.descInfo = descInfo;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    @Override
    public String toString() {
        return "PatientBean{" +
                "patientId=" + patientId +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", idCard='" + idCard + '\'' +
                ", phone='" + phone + '\'' +
                ", addr='" + addr + '\'' +
                ", descInfo='" + descInfo + '\'' +
                '}';
    }
}
