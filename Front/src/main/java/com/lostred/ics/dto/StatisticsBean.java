package com.lostred.ics.dto;

/**
 * 工作量统计
 */
public class StatisticsBean {
    /**
     * 用户id
     */
    private int userId;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 售卡次数
     */
    private int sold;
    /**
     * 换卡次数
     */
    private int change;
    /**
     * 退卡次数
     */
    private int refund;
    /**
     * 处方登记次数
     */
    private int presReg;
    /**
     * 处方退费次数
     */
    private int refundPres;

    public StatisticsBean() {
    }

    public StatisticsBean(int userId, String name, int sold, int change, int refund, int presReg, int refundPres) {
        this.userId = userId;
        this.name = name;
        this.sold = sold;
        this.change = change;
        this.refund = refund;
        this.presReg = presReg;
        this.refundPres = refundPres;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    public int getPresReg() {
        return presReg;
    }

    public void setPresReg(int presReg) {
        this.presReg = presReg;
    }

    public int getRefundPres() {
        return refundPres;
    }

    public void setRefundPres(int refundPres) {
        this.refundPres = refundPres;
    }
}
