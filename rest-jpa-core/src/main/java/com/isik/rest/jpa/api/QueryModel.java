package com.isik.rest.jpa.api;

import com.isik.rest.jpa.util.Util;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author fatih
 */
public class QueryModel {

    private List<Filter> filters;

    private List<FilterOrder> orders;

    private int pageSize;

    private int currentPage;

    public QueryModel() {
        this.pageSize = 10;
        this.currentPage = 0;
    }

    @Nonnull
    public List<Filter> getFilters() {
        return Util.requireNonNullElse (filters, Collections.emptyList());
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Nonnull
    public List<FilterOrder> getOrders() {
        return Util.requireNonNullElse (orders, Collections.emptyList());
    }

    public void setOrders(List<FilterOrder> filterOrders) {
        this.orders = filterOrders;
    }
}
