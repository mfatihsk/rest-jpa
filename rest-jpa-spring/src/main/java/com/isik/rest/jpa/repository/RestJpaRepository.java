package com.isik.rest.jpa.repository;

import com.isik.rest.jpa.api.QueryModel;
import com.isik.rest.jpa.api.QueryResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface RestJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    /**
     * repository extension method that creates dynamic jpa query
     * <br>
     *
     * @param query
     * @return query result of the dynamically created sql query, returns empty model on any error
     */
    QueryResult<T> query(QueryModel query);
}
