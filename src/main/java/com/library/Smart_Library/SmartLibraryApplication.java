package com.library.Smart_Library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Owner: Shared Team Integration
 * SRN: TEAM-SHARED
 * Purpose: Bootstraps the Smart Library application.
 * GRASP: Controller (system-level) by delegating startup orchestration to
 * Spring.
 * Pattern: Singleton beans are initialized in the Spring IoC container.
 */
@SpringBootApplication
public class SmartLibraryApplication {

	/**
	 * Application entry point.
	 * GRASP: Controller at system boundary by starting framework workflow.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SmartLibraryApplication.class, args);
	}

}
