package com.sakshi.sarma.networkmonitoring.config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

	public class CustomErrorController implements ErrorController {

		@RequestMapping("/error")
		public String handleError() {
			return "error";
		}

		@Override
		public String getErrorPath() {
			return "/error";
		}
	}
