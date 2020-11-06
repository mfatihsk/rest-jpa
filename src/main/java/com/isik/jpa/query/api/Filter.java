package com.isik.jpa.query.api;

/**
 * @author fatih
 */
public class Filter {
    //field name
    private String      name;
    //field operator
    private Operator    operator;
    //field filter value
    private Object      value;
    //indicates checking string field equality with lower case
    private boolean caseInsensitive;

    public Filter() {
        this.caseInsensitive = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isCaseInsensitive() {
        return caseInsensitive;
    }

    public void setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }
}
