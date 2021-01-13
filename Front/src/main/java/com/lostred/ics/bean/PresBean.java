package com.lostred.ics.bean;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;

import java.sql.Timestamp;

@Entity(value = "PRES", tableName = "T_PRES", daoName = "PresDao", name = "处方")
public class PresBean {
    @TableColumn(columnName = "PRES_ID")
    private int presId;
    @TableColumn(columnName = "CARD_ID", reference = CardBean.class)
    private CardBean cardBean;
    @TableColumn(columnName = "NAME")
    private String name;
    @TableColumn(columnName = "TAKE")
    private String take;
    @TableColumn(columnName = "NUM")
    private int num;
    @TableColumn(columnName = "UNIT")
    private String unit;
    @TableColumn(columnName = "PRICE")
    private double price;
    @TableColumn(columnName = "CREATE_TIME")
    private Timestamp createTime;
    @TableColumn(columnName = "PARAM_STATE_ID", reference = ParamBean.class)
    private ParamBean state;

    public PresBean() {
    }

    public PresBean(int presId, CardBean cardBean, String name, String take, int num, String unit, double price, Timestamp createTime, ParamBean state) {
        this.presId = presId;
        this.cardBean = cardBean;
        this.name = name;
        this.take = take;
        this.num = num;
        this.unit = unit;
        this.price = price;
        this.createTime = createTime;
        this.state = state;
    }

    public int getPresId() {
        return presId;
    }

    public void setPresId(int presId) {
        this.presId = presId;
    }

    public CardBean getCardBean() {
        return cardBean;
    }

    public void setCardBean(CardBean cardBean) {
        this.cardBean = cardBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTake() {
        return take;
    }

    public void setTake(String take) {
        this.take = take;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public ParamBean getState() {
        return state;
    }

    public void setState(ParamBean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PresBean{" +
                "presId=" + presId +
                ", cardBean=" + cardBean +
                ", take='" + take + '\'' +
                ", num='" + num + '\'' +
                ", unit='" + unit + '\'' +
                ", price='" + price + '\'' +
                ", createTime=" + createTime +
                ", state=" + state +
                '}';
    }
}
