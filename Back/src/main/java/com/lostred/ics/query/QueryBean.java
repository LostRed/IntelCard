package com.lostred.ics.query;

/**
 * 查询条件
 */
public class QueryBean {
    /**
     * 数据库列名
     */
    private String field;
    /**
     * 运算符("="、">"、"LIKE"等)
     */
    private String operator;
    /**
     * 数据
     */
    private Object value;

    public QueryBean() {
    }

    public QueryBean(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "QueryBean{" +
                "field='" + field + '\'' +
                ", operator='" + operator + '\'' +
                ", value=" + value +
                '}';
    }
}
