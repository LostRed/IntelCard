package com.lostred.ics.bean;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;

@Entity(value = "USER", tableName = "T_USER", daoName = "UserDao", name = "用户")
public class UserBean {
    @TableColumn(columnName = "USER_ID")
    private int userId;
    @TableColumn(columnName = "USERNAME")
    private String username;
    @TableColumn(columnName = "PASSWORD")
    private String password;
    @TableColumn(columnName = "NAME")
    private String name;
    @TableColumn(columnName = "ROLE_ID", reference = RoleBean.class)
    private RoleBean roleBean;
    @TableColumn(columnName = "PARAM_SECTION_ID", reference = ParamBean.class)
    private ParamBean section;
    @TableColumn(columnName = "PARAM_STATE_ID", reference = ParamBean.class)
    private ParamBean state;

    public UserBean() {
    }

    public UserBean(int userId, String username, String password, String name, RoleBean roleBean, ParamBean section, ParamBean state) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.roleBean = roleBean;
        this.section = section;
        this.state = state;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleBean getRoleBean() {
        return roleBean;
    }

    public void setRoleBean(RoleBean roleBean) {
        this.roleBean = roleBean;
    }

    public ParamBean getSection() {
        return section;
    }

    public void setSection(ParamBean section) {
        this.section = section;
    }

    public ParamBean getState() {
        return state;
    }

    public void setState(ParamBean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", roleBean=" + roleBean +
                ", section=" + section +
                ", state=" + state +
                '}';
    }
}
