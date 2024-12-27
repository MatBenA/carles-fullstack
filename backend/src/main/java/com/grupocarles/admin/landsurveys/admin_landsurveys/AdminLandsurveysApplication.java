package com.grupocarles.admin.landsurveys.admin_landsurveys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AdminLandsurveysApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminLandsurveysApplication.class, args);
	}

}
