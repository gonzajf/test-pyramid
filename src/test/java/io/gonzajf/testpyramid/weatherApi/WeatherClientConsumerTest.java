package io.gonzajf.testpyramid.weatherApi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.gonzajf.testpyramid.weatherapi.WeatherClient;
import io.gonzajf.testpyramid.weatherapi.WeatherResponse;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "weatherApiProvider", port = "8089")
@SpringBootTest
public class WeatherClientConsumerTest {
	
	@Autowired
	private WeatherClient weatherClient;
	
	 @Pact(consumer="test_consumer")
	 public RequestResponsePact createPact(PactDslWithProvider builder) throws IOException {
        return builder
                .given("weather forecast data")
                .uponReceiving("A request for a weather request for Berlin")
	                .path("/weather")
                	.method("GET")
                	.query("q=berlin&appid=bab965417f0e42ccc4a88def77e0b00c")
                .willRespondWith()
                    .status(200)
                    .body(FileLoader.read("classpath:__files/weatherApiResponse.json"),
                            ContentType.APPLICATION_JSON)
                .toPact();
	   }

	 @Test
	 @PactTestFor(pactMethod = "createPact")
	 public void shouldFetchWeatherInformation() throws IOException {
		
		 WeatherResponse response = weatherClient.getWeatherByCityName("berlin");
		 assertEquals(response.getWeather().get(0).getMain(), "Rain");
	 }
	 

    public static class FileLoader {
    	public static String read(String filePath) throws IOException {
    		File file = ResourceUtils.getFile(filePath);
    		return new String(Files.readAllBytes(file.toPath()));
    	}
    }
}
