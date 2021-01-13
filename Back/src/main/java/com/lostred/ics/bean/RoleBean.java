package com.lostred.ics.bean;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;

@Entity(value = "ROLE", tableName = "T_ROLE", daoName = "RoleDao", name = "角色")
public class RoleBean {
    @TableColumn(columnName = "ROLE_ID")
    private int roleId;
    @TableColumn(columnName = "ROLE_NAME")
    private String roleName;
    @TableColumn(columnName = "DESC_INFO")
    private String descInfo;

    public RoleBean() {
    }

    public RoleBean(int roleId, String roleName, String descInfo) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.descInfo = descInfo;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    @Override
    public String toString() {
        return "RoleBean{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", descInfo='" + descInfo + '\'' +
                '}';
    }
}
