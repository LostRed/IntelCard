package com.lostred.ics.bean;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;

import java.sql.Timestamp;

@Entity(value = "LOG", tableName = "T_LOG", daoName = "LogDao", name = "日志")
public class LogBean {
    @TableColumn(columnName = "LOG_ID")
    private int logId;
    @TableColumn(columnName = "USER_ID", reference = UserBean.class)
    private UserBean actionUser;
    @TableColumn(columnName = "LOG_NAME")
    private String logName;
    @TableColumn(columnName = "LOG_TIME")
    private Timestamp logTime;
    @TableColumn(columnName = "DESC_INFO")
    private String descInfo;

    public LogBean() {
        this.logTime = new Timestamp(System.currentTimeMillis());
    }

    public LogBean(int logId, UserBean actionUser, String logName, Timestamp logTime, String descInfo) {
        this.logId = logId;
        this.actionUser = actionUser;
        this.logName = logName;
        this.logTime = logTime;
        this.descInfo = descInfo;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public UserBean getActionUser() {
        return actionUser;
    }

    public void setActionUser(UserBean actionUser) {
        this.actionUser = actionUser;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    @Override
    public String toString() {
        return "LogBean{" +
                "logId=" + logId +
                ", actionUser=" + actionUser +
                ", logName='" + logName + '\'' +
                ", logTime=" + logTime +
                ", descInfo='" + descInfo + '\'' +
                '}';
    }
}
