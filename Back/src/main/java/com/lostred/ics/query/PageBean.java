package com.lostred.ics.query;

/**
 * 分页查询页码
 */
public class PageBean {
    /**
     * 当前页码
     */
    private int currentPage;
    /**
     * 总页码
     */
    private int totalPage;
    /**
     * 开始行数
     */
    private int startRow;
    /**
     * 截止行数
     */
    private int endRow;
    /**
     * 每一页显示的行数
     */
    private int pageSize;
    /**
     * 数据总行数
     */
    private int count;

    public PageBean() {
    }

    public PageBean(int currentPage, int totalPage, int startRow, int endRow, int pageSize, int count) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.startRow = startRow;
        this.endRow = endRow;
        this.pageSize = pageSize;
        this.count = count;
    }

    public static PageBean createPageBean(String currentPageString, String pageSizeString) {
        int currentPage = 1;
        if (!"".equals(currentPageString)) {
            currentPage = Integer.parseInt(currentPageString);
        }
        int pageSize = 1;
        if (!"".equals(pageSizeString)) {
            pageSize = Integer.parseInt(pageSizeString);
        }
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        return pageBean;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        setPage();
        setRow();
    }

    public void setRow() {
        this.startRow = (currentPage - 1) * pageSize;
        this.endRow = startRow + pageSize;
    }

    public void setPage() {
        if (count % pageSize == 0 && count != 0) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        if (currentPage > totalPage) {
            currentPage = totalPage;
        }
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", pageSize=" + pageSize +
                ", count=" + count +
                '}';
    }
}
