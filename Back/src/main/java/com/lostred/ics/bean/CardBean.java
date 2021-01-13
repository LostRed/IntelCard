package com.lostred.ics.bean;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;

import java.sql.Timestamp;

@Entity(value = "CARD", tableName = "T_CARD", daoName = "CardDao", name = "一卡通")
public class CardBean {
    @TableColumn(columnName = "CARD_ID")
    private int cardId;
    @TableColumn(columnName = "CARD_CODE")
    private String cardCode;
    @TableColumn(columnName = "CREATE_TIME")
    private Timestamp createTime;
    @TableColumn(columnName = "SALE_TIME")
    private Timestamp saleTime;
    @TableColumn(columnName = "APPLY_ID", reference = ApplyBean.class)
    private ApplyBean applyBean;
    @TableColumn(columnName = "SALE_USER_ID", reference = UserBean.class)
    private UserBean saleUser;
    @TableColumn(columnName = "PATIENT_ID", reference = PatientBean.class)
    private PatientBean patientBean;
    @TableColumn(columnName = "PARAM_STATE_ID", reference = ParamBean.class)
    private ParamBean state;
    @TableColumn(columnName = "AMOUNT")
    private double amount;
    @TableColumn(columnName = "DEPOSIT")
    private double deposit;
    @TableColumn(columnName = "DESC_INFO")
    private String descInfo;

    public CardBean() {
    }

    public CardBean(int cardId, String cardCode, Timestamp createTime, Timestamp saleTime, ApplyBean applyBean, UserBean saleUser, PatientBean patientBean, ParamBean state, double amount, double deposit, String descInfo) {
        this.cardId = cardId;
        this.cardCode = cardCode;
        this.createTime = createTime;
        this.saleTime = saleTime;
        this.applyBean = applyBean;
        this.saleUser = saleUser;
        this.patientBean = patientBean;
        this.state = state;
        this.amount = amount;
        this.deposit = deposit;
        this.descInfo = descInfo;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Timestamp saleTime) {
        this.saleTime = saleTime;
    }

    public ApplyBean getApplyBean() {
        return applyBean;
    }

    public void setApplyBean(ApplyBean applyBean) {
        this.applyBean = applyBean;
    }

    public UserBean getSaleUser() {
        return saleUser;
    }

    public void setSaleUser(UserBean saleUser) {
        this.saleUser = saleUser;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    @Override
    public String toString() {
        return "CardBean{" +
                "cardId=" + cardId +
                ", cardCode='" + cardCode + '\'' +
                ", createTime=" + createTime +
                ", applyBean=" + applyBean +
                ", patientBean=" + patientBean +
                ", state=" + state +
                ", amount=" + amount +
                ", deposit=" + deposit +
                ", descInfo='" + descInfo + '\'' +
                '}';
    }
}
