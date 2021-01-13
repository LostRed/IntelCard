package com.lostred.ics.bean;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;

@Entity(value = "PARAM", tableName = "T_PARAM", daoName = "ParamDao", name = "参数")
public class ParamBean {
    @TableColumn(columnName = "PARAM_ID")
    private int paramId;
    @TableColumn(columnName = "PARAM_NAME")
    private String paramName;
    @TableColumn(columnName = "PARAM_TYPE")
    private String paramType;
    @TableColumn(columnName = "PARAM_VALUE")
    private int paramValue;
    @TableColumn(columnName = "PARAM_STATE")
    private String paramState;
    @TableColumn(columnName = "DESC_INFO")
    private String descInfo;

    public ParamBean() {
    }

    public ParamBean(int paramId, String paramName, String paramType, int paramValue, String paramState, String descInfo) {
        this.paramId = paramId;
        this.paramName = paramName;
        this.paramType = paramType;
        this.paramValue = paramValue;
        this.paramState = paramState;
        this.descInfo = descInfo;
    }

    public int getParamId() {
        return paramId;
    }

    public void setParamId(int paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public int getParamValue() {
        return paramValue;
    }

    public void setParamValue(int paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamState() {
        return paramState;
    }

    public void setParamState(String paramState) {
        this.paramState = paramState;
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    @Override
    public String toString() {
        return "ParamBean{" +
                "paramId=" + paramId +
                ", paramName='" + paramName + '\'' +
                ", paramType='" + paramType + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", paramState='" + paramState + '\'' +
                ", descInfo='" + descInfo + '\'' +
                '}';
    }
}
