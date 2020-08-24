package io.gonzajf.testpyramid.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.gonzajf.testpyramid.model.City;
import io.gonzajf.testpyramid.repository.CityRepository;

@RestController
public class CityController {

	private final CityRepository repository;

	@Autowired
	public CityController(final CityRepository repository) {
		this.repository = repository;
	}

    @GetMapping("/city/{cityName}")
    public String getCity(@PathVariable final String cityName) {
        
    	Optional<City> foundedCity = repository.findByCityNameIgnoreCase(cityName);
    	
    	return foundedCity.map(city -> String.format("The city of %s is in %s!", 
    						city.getCityName(), 
    						city.getCountry()))
    				.orElse(String.format("Where is '%s' that you're talking about?",
                        cityName));
    }
}