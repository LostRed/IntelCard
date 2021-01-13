package com.lostred.ics.dto;

import com.lostred.ics.query.PageBean;

/**
 * 应答
 */
public class RespBean {
    /**
     * 状态，0表示成功或可继续执行，1表示失败或需要检查输入，-1等其它数字表示异常
     */
    private int state;
    /**
     * 必要的信息
     */
    private String msgInfo;
    /**
     * 分页对象
     */
    private PageBean pageBean;
    /**
     * 需要传输的数据
     */
    private Object data;

    public RespBean() {
    }

    public RespBean(int state, String msgInfo, PageBean pageBean, Object data) {
        this.state = state;
        this.msgInfo = msgInfo;
        this.pageBean = pageBean;
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespBean{" +
                "state=" + state +
                ", msgInfo='" + msgInfo + '\'' +
                ", pageBean=" + pageBean +
                ", data=" + data +
                '}';
    }
}
