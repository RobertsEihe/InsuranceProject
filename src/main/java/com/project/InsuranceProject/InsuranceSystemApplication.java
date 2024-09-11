package com.project.InsuranceProject;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@EnableVaadin
public class InsuranceSystemApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceSystemApplication.class, args);
	}

	// roberts edit
}
