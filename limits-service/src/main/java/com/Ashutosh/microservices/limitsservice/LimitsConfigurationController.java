package com.Ashutosh.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ashutosh.microservices.limitsservice.bean.LimitsConfiguration;

@RestController
public class LimitsConfigurationController {

	@Autowired
	private Configuration config;
	
	@GetMapping(path="/limits")
	public LimitsConfiguration retriveLimitsFromConfiguration() {
		return new LimitsConfiguration(config.getMin(),config.getMax());
	}
}
