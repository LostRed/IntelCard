package com.lostred.ics.bean;

import com.lostred.ics.annotation.Entity;
import com.lostred.ics.annotation.TableColumn;

@Entity(value = "MENU", tableName = "T_MENU", daoName = "MenuDao", name = "菜单")
public class MenuBean {
    @TableColumn(columnName = "MENU_ID")
    private int menuId;
    @TableColumn(columnName = "MENU_SEQ")
    private int menuSeq;
    @TableColumn(columnName = "MENU_NAME")
    private String menuName;
    @TableColumn(columnName = "PAGE_HREF")
    private String pageHref;
    @TableColumn(columnName = "DESC_INFO")
    private String descInfo;
    @TableColumn(columnName = "PARENT_ID", reference = MenuBean.class)
    private MenuBean parentMenuBean;

    public MenuBean() {
    }

    public MenuBean(int menuId, int menuSeq, String menuName, String pageHref, String descInfo, MenuBean parentMenuBean) {
        this.menuId = menuId;
        this.menuSeq = menuSeq;
        this.menuName = menuName;
        this.pageHref = pageHref;
        this.descInfo = descInfo;
        this.parentMenuBean = parentMenuBean;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getMenuSeq() {
        return menuSeq;
    }

    public void setMenuSeq(int menuSeq) {
        this.menuSeq = menuSeq;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPageHref() {
        return pageHref;
    }

    public void setPageHref(String pageHref) {
        this.pageHref = pageHref;
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    public MenuBean getParentMenuBean() {
        return parentMenuBean;
    }

    public void setParentMenuBean(MenuBean parentMenuBean) {
        this.parentMenuBean = parentMenuBean;
    }

    @Override
    public String toString() {
        return "MenuBean{" +
                "menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", pageHref='" + pageHref + '\'' +
                ", descInfo='" + descInfo + '\'' +
                ", parentMenuBean=" + parentMenuBean +
                '}';
    }
}
