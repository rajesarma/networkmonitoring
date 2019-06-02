package com.sakshi.sarma.networkmonitoring.config;

import com.sakshi.sarma.networkmonitoring.model.Location;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties("sakshi")
public class PingLocationsConfigurationProperties {

	private List<Location> locations = new ArrayList<>();

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}


	/*public void getLocations(){
		System.out.println("These are the locations");
	}*/

	@Override
	public String toString() {
		return "LocationsConfiguration{" +
				"locations=" + locations +
				'}';
	}
}
