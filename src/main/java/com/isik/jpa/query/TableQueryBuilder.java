package com.isik.jpa.query;

import com.isik.jpa.query.api.*;
import com.isik.jpa.query.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author fatih
 */
public class TableQueryBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableQueryBuilder.class);

    private TableQueryBuilder(){
    }

    /**
     *
     * @param entityManager entitymanager for clazz
     * @param tableQuery query object
     * @param clazz destination filtered JPA Entity class
     * @param <T>
     * @return
     */
    public static <T> QueryResult<T> filter(EntityManager entityManager, TableQuery tableQuery, Class<T> clazz) {
        QueryResult<T> result = new QueryResult<>();
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(clazz);
            Root<T> root = query.from(clazz);
            //apply filters
            List<Predicate> predicates = getPredicates(tableQuery, builder, root, clazz);
            if(!predicates.isEmpty()){
                query.where(predicates.toArray(new Predicate[]{}));
            }
            //apply orders
            List<Order> orders = getOrders(tableQuery, builder, root, clazz);
            if (!orders.isEmpty()){
                query.orderBy(orders);
            }
            //apply paging
            TypedQuery<T> typedQuery = entityManager.createQuery(query);
            typedQuery.setMaxResults(tableQuery.getPageSize());
            typedQuery.setFirstResult(tableQuery.getCurrentPage() * tableQuery.getPageSize());
            //set results
            result.setResults(typedQuery.getResultList());
            //set total count
            result.setTotalCount(getTotalCount(entityManager, tableQuery, clazz,builder));
        } catch (Exception e) {
            LOGGER.error("",e);
        }
        return result;
    }

    public static <T,Z,Y extends Comparable<? super Y>> Predicate getCriteria(Filter filter, CriteriaBuilder cb, Root<T> root, Class<T> rootClazz) throws NoSuchFieldException {
        Class<Z> columnClazz  = Util.getFilterColumnClass(rootClazz, filter.getName());
        switch (filter.getOperator()) {
            case EQUALS:
                return Util.getPredicate(cb::equal, root, rootClazz, columnClazz, filter, cb);
            case NOT_EQUALS:
                return Util.getPredicate(cb::notEqual, root, rootClazz, columnClazz, filter, cb);
            case IS_NULL:
                return cb.isNull(Util.getFilterPath(filter.getName(), root,rootClazz));
            case IS_NOT_NULL:
                return cb.isNotNull(Util.getFilterPath(filter.getName(), root,rootClazz));
            case CONTAINS:
            case STARTS_WITH:
            case ENDS_WITH:
                TableQueryBuilder.setLikeValue(filter);
                Class<String> likeClazz = Util.getFilterColumnClass(rootClazz, filter.getName());
                return Util.getPredicate(cb::like, root, rootClazz, likeClazz, filter, cb);
            case NOT_CONTAINS:
                TableQueryBuilder.setLikeValue(filter);
                Class<String> notLikeClazz = Util.getFilterColumnClass(rootClazz, filter.getName());
                return Util.getPredicate(cb::notLike, root, rootClazz, notLikeClazz, filter, cb);
            case GREATER_THAN:
            case GREATER_OR_EQUAL:
            case LESS_THAN :
            case LESS_OR_EQUAL:
                Class<Y> comparableClazz = Util.getFilterColumnClass(rootClazz,filter.getName());
                return Util.getPredicate( TableQueryBuilder.getCriteria(filter.getOperator(), cb), root, rootClazz, comparableClazz, filter, cb);
            case IN:
                throw new NoSuchFieldException("IN not implemented ");
            default:
                return null;
        }
    }

    private static <T> long getTotalCount(EntityManager entityManager, TableQuery filter, Class<T> clazz, CriteriaBuilder builder) throws NoSuchFieldException {
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = query.from(clazz);
        query.select(builder.count( root ));
        List<Predicate> predicates = getPredicates(filter, builder, root, clazz);
        if(!predicates.isEmpty()){
            query.where(predicates.toArray(new Predicate[]{}));
        }
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }

    private static <T> Order getOrder(CriteriaBuilder cb, Root<T> root, Class<T> rootClazz, FilterOrder o) throws NoSuchFieldException {
        Path<Object> path = Util.getFilterPath( o.getName(), root, rootClazz);
        return o.isAsc() ? cb.asc(path) : cb.desc(path);
    }

    private static <T> List<Order> getOrders(TableQuery tableQuery, CriteriaBuilder cb, Root<T> root, Class<T> rootClazz) throws NoSuchFieldException {
        List<Order> list = new ArrayList<>();
        for (FilterOrder o : tableQuery.getOrders()) {
            Order order = getOrder(cb, root, rootClazz, o);
            list.add(order);
        }
        return list;
    }

    private static <T> List<Predicate> getPredicates(TableQuery tableQuery, CriteriaBuilder cb, Root<T> root, Class<T> clazz) throws NoSuchFieldException {
        List<Predicate> conditions = new ArrayList<>();
        for (Filter filter : tableQuery.getFilters()) {
            Predicate predicate = getCriteria(filter, cb, root, clazz);
            if (predicate != null) {
                conditions.add(predicate);
            }
        }
        return conditions;
    }

    /**
     *
     * @param operator
     * @param cb
     * @param <Y>
     * @return
     * @throws NoSuchFieldException
     */
    public static <Y extends Comparable<? super Y>>  BiFunction<Expression<Y>,Y,Predicate> getCriteria(Operator operator, CriteriaBuilder cb) throws NoSuchFieldException {
        switch (operator){
            case GREATER_THAN:
                return cb::greaterThan;
            case GREATER_OR_EQUAL:
                return cb::greaterThanOrEqualTo;
            case LESS_THAN :
                return cb::lessThan;
            case LESS_OR_EQUAL:
                return cb::lessThanOrEqualTo;
            default:
                throw new NoSuchFieldException("");
        }
    }

    public static void setLikeValue( Filter filter) {
        if(filter.getOperator() == Operator.CONTAINS || filter.getOperator() == Operator.NOT_CONTAINS){
            filter.setValue("%" + filter.getValue() + "%");
        } else if(filter.getOperator() == Operator.STARTS_WITH){
            filter.setValue("%" + filter.getValue() );
        } else if(filter.getOperator() == Operator.ENDS_WITH){
            filter.setValue(filter.getValue() +"%");
        }
    }
}
