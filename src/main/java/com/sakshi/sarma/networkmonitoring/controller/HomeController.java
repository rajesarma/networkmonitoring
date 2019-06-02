package com.sakshi.sarma.networkmonitoring.controller;

import com.sakshi.sarma.networkmonitoring.service.PingStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	private PingStatusService pingStatusService;

	@Autowired
	HomeController(PingStatusService pingStatusService) {
		this.pingStatusService = pingStatusService;
	}

	@GetMapping("/")
	public ModelAndView home() {

		ModelAndView mav = new ModelAndView("home");
		mav.addObject("locations", pingStatusService.getAllLocationsStatus());

		return mav;
	}
}
