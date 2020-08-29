package io.gonzajf.testpyramid;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.gonzajf.testpyramid.model.City;
import io.gonzajf.testpyramid.repository.CityRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class E2ERestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private CityRepository cityRepository;

	@AfterEach
	public void tearDown() {
		cityRepository.delete(new City("Berlin", "Germany"));
	}

	@Test
	public void shouldReturnWhereIsAvellaneda() throws Exception {
		when().get(String.format("http://localhost:%s/city/avellaneda", port))
			.then()
			.statusCode(is(200))
			.body(containsString("The city of Avellaneda is in Argentina!"));
	}
	
    @Test
    public void shouldReturnBerlin() throws Exception {
        City berlin = new City("Berlin", "Germany");
        cityRepository.save(berlin);

        when().get(String.format("http://localhost:%s/city/berlin", port))
            .then()
            .statusCode(is(200))
            .body(containsString("The city of Berlin is in Germany!"));
    }
    
    @Test
    public void shouldReturnNotFoundedPhrase() throws Exception {
        City berlin = new City("Berlin", "Germany");
        cityRepository.save(berlin);

        when().get(String.format("http://localhost:%s/city/roma", port))
            .then()
            .statusCode(is(200))
            .body(containsString("Where in the world is 'roma'?"));
    }

}
