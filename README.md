Initial commit for JPA dynamic entity query filter utility.
# Table Query

##### Usage
``` bash
List<Filter> filters = new ArrayList<>();
TableQuery tableQuery = new TableQuery();
Filter filter = new Filter();
filter.setName("id");
filter.setOperator(Operator.EQUALS);
filter.setValue(genericEntity.getId());
filters.add(filter);
tableQuery.setFilters(filters);

QueryResult<Employee> result = TableQueryBuilder.filter(entityManager, tableQuery, Employee.class);
```


##### Nested Filter
```
filter.setName("addresses.city");
filter.setOperator(Operator.EQUALS);
filter.setValue("35");
filters.add(filter);
tableQuery.setFilters(filters);
result = TableQueryBuilder.filter(entityManager, tableQuery, Employee.class);
```


### TODOS for Release 
 #### 
- [] UI test project 
- [] Improvements/ Bug fixes
- [] More test cases


### Future improvements
