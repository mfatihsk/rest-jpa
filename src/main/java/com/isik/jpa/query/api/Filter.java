package com.isik.jpa.query.api;

import java.util.Locale;

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
    private boolean     caseInsensitive;
    /** client locale */
    private String      locale;

    public Filter() {
        this.caseInsensitive = false;
    }

    public Filter(String name, Operator operator, Object value, boolean caseInsensitive, String locale) {
        this.name = name;
        this.operator = operator;
        this.value = value;
        this.caseInsensitive = caseInsensitive;
        this.locale = locale;
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
        return caseInsensitive || locale != null;
    }

    public void setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
