package io.gonzajf.testpyramid.controller;

import static org.hamcrest.core.Is.is;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.gonzajf.testpyramid.model.City;
import io.gonzajf.testpyramid.repository.CityRepository;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

	private CityController cityController;
	
	@Mock
	private CityRepository cityRepository;
	
	@BeforeEach
    public void setUp() throws Exception {
        initMocks(this);
        cityController = new CityController(cityRepository);
    }
    
    @Test
    public void should_return_country_of_city() throws Exception {
    	
    	City newCity = new City("Berlin", "Germany");
    	
    	given(cityRepository.findByCityNameIgnoreCase("Berlin"))
    		.willReturn(Optional.of(newCity));
    	
    	String response = cityController.getCity("Berlin");
    	assertThat(response, is("The city of Berlin is in Germany!"));
    }
    
	
}
