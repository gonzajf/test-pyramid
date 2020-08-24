package io.gonzajf.testpyramid.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import io.gonzajf.testpyramid.model.City;

public interface CityRepository extends CrudRepository<City, String> {
	
    Optional<City> findByCityNameIgnoreCase(String cityName);
}
