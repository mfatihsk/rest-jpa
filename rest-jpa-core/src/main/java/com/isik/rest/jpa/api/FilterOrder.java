package com.isik.rest.jpa.api;

/**
 * @author fatih
 */
public class FilterOrder {
    //order column name
    private String name;
    //indicates order is ascending
    private boolean asc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }
}
