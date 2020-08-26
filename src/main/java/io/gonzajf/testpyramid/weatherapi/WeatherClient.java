package io.gonzajf.testpyramid.weatherapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient {
	
	@Value("${weather.url}")
	private String baseUrl;
	
	private static final String API_KEY = "bab965417f0e42ccc4a88def77e0b00c";

	private RestTemplate restTemplate;
	
	@Autowired
	public WeatherClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public WeatherResponse getWeatherByCityName(String cityName) {
		return restTemplate.getForObject(baseUrl+"/weather?q={cityName}&appid={apiKey}", WeatherResponse.class, cityName, API_KEY);
	}
}
