package com.isik.jpa.query.api;

/**
 * @author fatih
 */
public enum Operator {
    EQUALS("EQUALS"),
    NOT_EQUALS("NOT_EQUALS"),
    CONTAINS("CONTAINS"),
    NOT_CONTAINS("NOT_CONTAINS"),
    ENDS_WITH("ENDS_WITH"),
    STARTS_WITH("STARTS_WITH"),
    GREATER_THAN("GREATER_THAN"),
    LESS_THAN("LESS_THAN"),
    GREATER_OR_EQUAL("GREATER_OR_EQUAL"),
    LESS_OR_EQUAL("LESS_OR_EQUAL"),
    IN("IN"),
    NOT_IN("NOT_IN");

    private String code;

    Operator(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
