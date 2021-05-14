package com.isik.rest.jpa.test.repos;

import com.isik.rest.jpa.repository.RestJpaRepository;
import com.isik.rest.jpa.test.models.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends RestJpaRepository<Employee, String> {
}
