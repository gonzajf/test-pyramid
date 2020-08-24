package io.gonzajf.testpyramid.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "cities")
public class City {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String cityName;
    private String country;
	
    public City() {
    }
    
	public City(String cityName, String country) {
		this.cityName = cityName;
		this.country = country;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public String getCountry() {
		return country;
	}	
}