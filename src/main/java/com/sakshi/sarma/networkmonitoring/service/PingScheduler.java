package com.sakshi.sarma.networkmonitoring.service;

import com.sakshi.sarma.networkmonitoring.config.PingLocationsConfigurationProperties;
import com.sakshi.sarma.networkmonitoring.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PingScheduler {

	private PingService pingService;
	private PingLocationsConfigurationProperties pingLocations;

	@Autowired
	PingScheduler(final PingService pingService, PingLocationsConfigurationProperties pingLocations){
		this.pingService = pingService;
		this.pingLocations = pingLocations;
	}

	@Scheduled(fixedDelayString = "${interval:30000}")
	public void checkSiteConnectivity() {
		List<Location> locations = pingLocations.getLocations();

		for(Location location : locations) {
			pingService.checkNetworkStatus(location.getLocation(), location.getIps());
		}
	}
}
