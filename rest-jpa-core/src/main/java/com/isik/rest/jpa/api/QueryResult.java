package com.isik.rest.jpa.api;

import java.util.List;

/**
 * @author fatih
 * @param <T>
 */
public class QueryResult<T> {
    //result objects
    private List<T> results;
    //total count
    private long totalCount;

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
