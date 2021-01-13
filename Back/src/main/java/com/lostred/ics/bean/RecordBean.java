package com.lostred.ics.bean;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;

import java.sql.Timestamp;

@Entity(value = "RECORD", tableName = "T_RECORD", daoName = "RecordDao", name = "一卡通消费记录")
public class RecordBean {
    @TableColumn(columnName = "RECORD_ID")
    private int recordId;
    @TableColumn(columnName = "CARD_ID", reference = CardBean.class)
    private CardBean cardBean;
    @TableColumn(columnName = "PARAM_NAME_ID", reference = ParamBean.class)
    private ParamBean name;
    @TableColumn(columnName = "AMOUNT")
    private double amount;
    @TableColumn(columnName = "USER_ID", reference = UserBean.class)
    private UserBean actionUser;
    @TableColumn(columnName = "RECORD_TIME")
    private Timestamp recordTime;
    @TableColumn(columnName = "DESC_INFO")
    private String descInfo;

    public RecordBean() {
        this.recordTime = new Timestamp(System.currentTimeMillis());
    }

    public RecordBean(int recordId, CardBean cardBean, ParamBean name, double amount, UserBean actionUser, Timestamp recordTime, String descInfo) {
        this.recordId = recordId;
        this.cardBean = cardBean;
        this.name = name;
        this.amount = amount;
        this.actionUser = actionUser;
        this.recordTime = recordTime;
        this.descInfo = descInfo;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public CardBean getCardBean() {
        return cardBean;
    }

    public void setCardBean(CardBean cardBean) {
        this.cardBean = cardBean;
    }

    public ParamBean getName() {
        return name;
    }

    public void setName(ParamBean name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UserBean getActionUser() {
        return actionUser;
    }

    public void setActionUser(UserBean actionUser) {
        this.actionUser = actionUser;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    @Override
    public String toString() {
        return "RecordBean{" +
                "recordId=" + recordId +
                ", cardBean=" + cardBean +
                ", name=" + name +
                ", actionUser=" + actionUser +
                ", recordTime=" + recordTime +
                ", descInfo='" + descInfo + '\'' +
                '}';
    }
}
