package com.sakshi.sarma.networkmonitoring.controller;

import com.sakshi.sarma.networkmonitoring.model.IpStatus;
import com.sakshi.sarma.networkmonitoring.service.PingStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PingStatusController {

	private PingStatusService pingStatusService;

	@Autowired
	PingStatusController(PingStatusService pingStatusService) {
		this.pingStatusService = pingStatusService;
	}

	@GetMapping("/getLocationsStatus")
	public Map<String, Map<String, IpStatus>> getAllLocationsStatus() {

		return pingStatusService.getAllLocationsStatus();
	}

}
