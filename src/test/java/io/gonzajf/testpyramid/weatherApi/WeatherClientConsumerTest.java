package io.gonzajf.testpyramid.weatherApi;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.util.ResourceUtils;

import com.jayway.jsonpath.JsonPath;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "weatherApiProvider", port = "8089")
public class WeatherClientConsumerTest {
	
	 @Pact(consumer="test_consumer")
	 public RequestResponsePact createPact(PactDslWithProvider builder) throws IOException {
        return builder
                .given("weather forecast data")
                .uponReceiving("a request for a weather request for Berlin")
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
	 public void shouldFetchWeatherInformation(MockServer mockServer) throws IOException {
		
		 HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/weather?q=berlin&appid=bab965417f0e42ccc4a88def77e0b00c")
				 						.execute()
				 						.returnResponse();
		
		 assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
		 assertThat(JsonPath.read(httpResponse.getEntity().getContent(), "$.weather[0].main").toString()).isEqualTo("Rain");
	 }
	 

    public static class FileLoader {
    	public static String read(String filePath) throws IOException {
    		File file = ResourceUtils.getFile(filePath);
    		return new String(Files.readAllBytes(file.toPath()));
    	}
    }
}
