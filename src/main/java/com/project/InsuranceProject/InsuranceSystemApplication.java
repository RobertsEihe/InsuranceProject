package com.project.InsuranceProject;


import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@EnableVaadin
@Theme("chat-theme")
public class InsuranceSystemApplication extends SpringBootServletInitializer implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceSystemApplication.class, args);
	}
}
