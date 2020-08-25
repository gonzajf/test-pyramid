package io.gonzajf.testpyramid.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.gonzajf.testpyramid.model.City;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CityRepositoryIntegrationTest {

    @Autowired
    private CityRepository repository;
    
    @Autowired
	private TestEntityManager entityManager;
    
	@Test
	public void getCar_returnsCarDetails() throws Exception {
	
		City city = entityManager.persistAndFlush(new City("Paris", "France"));
		Optional<City> founded = repository.findByCityNameIgnoreCase("paris");
		
		assertThat(founded.get().getCityName()).isEqualTo(city.getCityName());
		assertThat(founded.get().getCountry()).isEqualTo(city.getCountry());
	}
	
}
