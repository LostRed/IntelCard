package com.lostred.ics.bean;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;

import java.sql.Timestamp;

@Entity(value = "APPLY", tableName = "T_APPLY", daoName = "ApplyDao", name = "领卡申请")
public class ApplyBean {
    @TableColumn(columnName = "APPLY_ID")
    private int applyId;
    @TableColumn(columnName = "APPLY_TIME")
    private Timestamp applyTime;
    @TableColumn(columnName = "APPLY_NUM")
    private int applyNum;
    @TableColumn(columnName = "APPLY_USER_ID", reference = UserBean.class)
    private UserBean applyUser;
    @TableColumn(columnName = "AUDIT_USER_ID", reference = UserBean.class)
    private UserBean auditUser;
    @TableColumn(columnName = "PARAM_STATE_ID", reference = ParamBean.class)
    private ParamBean state;
    @TableColumn(columnName = "AUDIT_TIME")
    private Timestamp auditTime;
    @TableColumn(columnName = "DESC_INFO")
    private String descInfo;

    public ApplyBean() {
        this.applyTime = new Timestamp(System.currentTimeMillis());
    }

    public ApplyBean(int applyId, Timestamp applyTime, int applyNum, UserBean applyUser, UserBean auditUser, ParamBean state, Timestamp auditTime, String descInfo) {
        this.applyId = applyId;
        this.applyTime = applyTime;
        this.applyNum = applyNum;
        this.applyUser = applyUser;
        this.auditUser = auditUser;
        this.state = state;
        this.auditTime = auditTime;
        this.descInfo = descInfo;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    public int getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(int applyNum) {
        this.applyNum = applyNum;
    }

    public UserBean getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(UserBean applyUser) {
        this.applyUser = applyUser;
    }

    public UserBean getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(UserBean auditUser) {
        this.auditUser = auditUser;
    }

    public ParamBean getState() {
        return state;
    }

    public void setState(ParamBean state) {
        this.state = state;
    }

    public Timestamp getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    @Override
    public String toString() {
        return "ApplyBean{" +
                "applyId=" + applyId +
                ", applyTime=" + applyTime +
                ", applyNum=" + applyNum +
                ", applyUser=" + applyUser +
                ", auditUser=" + auditUser +
                ", state=" + state +
                ", descInfo='" + descInfo + '\'' +
                '}';
    }
}
