package com.isik.rest.jpa.test.repos;

import com.isik.rest.jpa.repository.RestJpaRepository;
import com.isik.rest.jpa.test.models.City;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends RestJpaRepository<City, String> {
}
