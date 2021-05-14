Initial commit for JPA dynamic entity query filter utility.
# Using Query model

##### Usage
``` bash
List<Filter> filters = new ArrayList<>();
QueryModel tableQuery = new QueryModel();
Filter filter = new Filter();
filter.setName("id");
filter.setOperator(Operator.EQUALS);
filter.setValue(genericEntity.getId());
filters.add(filter);
tableQuery.setFilters(filters);

QueryResult<Employee> result = JPAQueryBuilder.filter(entityManager, tableQuery, Employee.class);
```


##### Nested Filter
```
filter.setName("addresses.city");
filter.setOperator(Operator.EQUALS);
filter.setValue("35");
filters.add(filter);
tableQuery.setFilters(filters);
result = JPAQueryBuilder.filter(entityManager, tableQuery, Employee.class);
```


### TODOS for Release 
 #### 
- [] UI test project 
- [] Improvements/ Bug fixes
- [] More test cases


### Future improvements
