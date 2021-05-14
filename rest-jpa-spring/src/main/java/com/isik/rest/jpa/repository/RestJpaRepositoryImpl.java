package com.isik.rest.jpa.repository;

import com.isik.rest.jpa.JPAQueryBuilder;
import com.isik.rest.jpa.api.QueryModel;
import com.isik.rest.jpa.api.QueryResult;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class RestJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements RestJpaRepository<T, ID> {

    private EntityManager entityManager;

    public RestJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public QueryResult<T> query(QueryModel query) {
        return JPAQueryBuilder.filter(entityManager, query, getDomainClass());
    }
}
